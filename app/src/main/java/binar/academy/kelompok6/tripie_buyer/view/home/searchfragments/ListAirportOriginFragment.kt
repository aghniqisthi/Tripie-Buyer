package binar.academy.kelompok6.tripie_buyer.view.home.searchfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.response.airport.Airport
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentListAirportOriginBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.AirportAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.AirportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListAirportOriginFragment : Fragment(), AirportAdapter.AirportInterface {
    private var _binding : FragmentListAirportOriginBinding? = null
    private val binding get() = _binding!!
    private val airportViewModel : AirportViewModel by viewModels()
    private lateinit var adapter : AirportAdapter
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListAirportOriginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setFlightDataAirport()
    }

    private fun setFlightDataAirport() {
        airportViewModel.getAllAirport()
        airportViewModel.getAllAirportObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    showLoading()
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    stopLoading()
                    response.data?.let {
                        val sortedAirport = it.data.sortedBy { data -> data.id }
                        showRvDataAirport(sortedAirport)
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

    private fun showRvDataAirport(sortedAirport: List<Airport>) {
        adapter = AirportAdapter(this)
        adapter.setData(sortedAirport)

        binding.apply {
            rvListOriginAirport.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvListOriginAirport.adapter = adapter
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

    override fun onItemClick(airport: Airport) {
        CoroutineScope(Dispatchers.IO).launch {
            sharedPref.saveDataOriginAirport(airport.airportCode!!, airport.city!!)
        }
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            "namaAirportOrigin",
            airport.airportName
        )
        findNavController().navigateUp()
    }
}