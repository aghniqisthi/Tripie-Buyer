package binar.academy.kelompok6.tripie_buyer.view.home

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
import binar.academy.kelompok6.tripie_buyer.data.model.response.airport.Airport
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentPopularDestinationBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.AllPopularDestinationAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.AirportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularDestinationFragment : Fragment(), AllPopularDestinationAdapter.PopularInterface {

    private var _binding: FragmentPopularDestinationBinding? = null
    private val binding get() = _binding!!
    private val vmAirport : AirportViewModel by viewModels()
    private lateinit var adapter : AllPopularDestinationAdapter
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View {
        _binding = FragmentPopularDestinationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
        setDestinasiPopuler()

        binding.apply {
            buttonBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    private fun setDestinasiPopuler() {
        vmAirport.getAllAirport()
        vmAirport.getAllAirportObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    binding.progressbar.visibility = View.GONE
                    response.data?.let {
                        showRvDataAirport(it.data)
                    }
                    Log.d("Success: ", response.toString())
                }
                is ApiResponse.Error -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                    Log.d("Error: ", response.toString())
                }
            }
        }
    }

    private fun showRvDataAirport(airport: List<Airport>) {
        adapter = AllPopularDestinationAdapter(this)
        adapter.setData(airport)

        binding.apply {
            rvAllDestPopular.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvAllDestPopular.adapter = adapter
        }
    }

    override fun onItemClick(airport: Airport) {
        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner) {
            val action =
                PopularDestinationFragmentDirections.actionPopularDestinationFragmentToDetailFlightFragment(
                    Favorit(
                        airport.id,
                        it,
                        airport.airportCode,
                        airport.airportName,
                        airport.city,
                        airport.foto,
                        airport.description
                    )
                )
            findNavController().navigate(action)
        }
    }
}