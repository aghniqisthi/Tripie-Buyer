package binar.academy.kelompok6.tripie_buyer.view.notifikasi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.response.notification.DataNotification
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentNotificationBinding
import binar.academy.kelompok6.tripie_buyer.view.notifikasi.adapter.NotifikasiAdapter
import binar.academy.kelompok6.tripie_buyer.view.notifikasi.viewmodel.NotifikasiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private var _binding : FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPref
    private lateinit var adapter : NotifikasiAdapter
    private val vmNotifikasi : NotifikasiViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        setRvNotif()
    }

    private fun setRvNotif() {
        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){ userId ->
            if (userId != "Undefined"){
                vmNotifikasi.getNotifikasi(userId.toInt())
                vmNotifikasi.getNotifikasiObserver().observe(viewLifecycleOwner){ response ->
                    when(response){
                        is ApiResponse.Loading -> {
                            showLoading()
                            Log.d("Loading: ", response.toString())
                        }
                        is ApiResponse.Success -> {
                            stopLoading()
                            response.data?.let {
                                val sortedNotif = it.data.sortedByDescending { data-> data.id }
                                showRvDataNotif(sortedNotif)
                            }
                            Log.d("Success: ", response.toString())
                        }
                        is ApiResponse.Error -> {
                            stopLoading()
                            Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                            Log.d("Error: ", response.toString())
                        }
                    }
                }
            }
        }
    }

    private fun showRvDataNotif(sortedNotif: List<DataNotification>) {
        adapter = NotifikasiAdapter()
        adapter.setData(sortedNotif)

        binding.apply {
            rvNotif.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvNotif.adapter = adapter
        }
    }

    private fun stopLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}