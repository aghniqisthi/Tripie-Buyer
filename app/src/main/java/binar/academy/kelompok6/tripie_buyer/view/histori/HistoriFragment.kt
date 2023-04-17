package binar.academy.kelompok6.tripie_buyer.view.histori

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.response.history.Booking
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHistoriBinding
import binar.academy.kelompok6.tripie_buyer.utils.Constant.Companion.LOGIN_SUCCESSFUL
import binar.academy.kelompok6.tripie_buyer.view.histori.adapter.HistoryAdapter
import binar.academy.kelompok6.tripie_buyer.view.histori.viewmodel.ViewModelHistory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoriFragment : Fragment(), HistoryAdapter.HistoryInterface {
    private var _binding : FragmentHistoriBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterHistory: HistoryAdapter
    private val viewModelHistory : ViewModelHistory by viewModels()
    private lateinit var sharedPref : SharedPref

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val navController = findNavController()
//
//        val currentBackStackEntry = navController.currentBackStackEntry!!
//        val savedStateHandle = currentBackStackEntry.savedStateHandle
//        savedStateHandle.getLiveData<Boolean>(LOGIN_SUCCESSFUL)
//            .observe(currentBackStackEntry){ success ->
//                if (!success){
//                    val startDestination = navController.graph.startDestinationId
//                    val navOptions = NavOptions.Builder()
//                        .setPopUpTo(startDestination, false, saveState = true)
//                        .build()
//                    navController.navigate(startDestination, null, navOptions)
//                }
//            }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        checkUser()
        populateData()
    }

    private fun checkUser(){
        sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){
            if(it == "Undefined"){
                findNavController().navigate(R.id.action_historiFragment_to_loginFragment)
                Toast.makeText(context, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateData() {
        viewModelHistory.getHistory()
        viewModelHistory.getLiveDataHistory().observe(viewLifecycleOwner) {
            when(it){
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.data.let { data ->
                        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner) { id ->
                            val filteredHistory = data!!.booking.filter { booking -> booking.userId.toString() == id }
                            showRvData(filteredHistory)
                        }
                    }
                    binding.rvHistory.adapter = adapterHistory
                }
                is ApiResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showRvData(booking: List<Booking>) {
        adapterHistory = HistoryAdapter(booking as ArrayList<Booking>,this)
        adapterHistory.setData(booking)

        binding.apply {
            rvHistory.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            rvHistory.adapter = adapterHistory

            etSearchTiket.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapterHistory.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapterHistory.filter.filter(newText)
                    return true
                }
            })
        }

    }

    override fun onItemClick(booking: Booking) {
        val action = HistoriFragmentDirections.actionHistoriFragmentToDetailHistoriFragment(booking)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}