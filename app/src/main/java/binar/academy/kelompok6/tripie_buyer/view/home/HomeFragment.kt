package binar.academy.kelompok6.tripie_buyer.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.SearchBundle
import binar.academy.kelompok6.tripie_buyer.data.model.request.SearchTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.model.response.airport.Airport
import binar.academy.kelompok6.tripie_buyer.data.model.response.search.ResponseSearchTicket
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentHomeBinding
import binar.academy.kelompok6.tripie_buyer.view.home.adapter.PopularDestinationAdapter
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.AirportViewModel
import binar.academy.kelompok6.tripie_buyer.view.home.viewmodel.HomeViewModel
import binar.academy.kelompok6.tripie_buyer.view.profile.viewmodel.ViewModelProfile
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(), PopularDestinationAdapter.PopularInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeVm : HomeViewModel by viewModels()
    private val vmAirport : AirportViewModel by viewModels()
    private val vmProfile : ViewModelProfile by viewModels()
    private lateinit var sharedPref: SharedPref
    private lateinit var adapter : PopularDestinationAdapter
    private var flightType = "One Way Trip"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        setGreeting()

        setDestinasiPopuler()

        binding.apply {
            btnToNotif.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
            }

            etDepartureDateOw.setOnClickListener {
                getDateFromDatePickerOneway()
            }
            etDepartureDateRt.setOnClickListener {
                getDateFromDatePickerRtDeparture()
            }

            etArriveDateRt.setOnClickListener {
                getDateFromDatePickerRtArrive()
            }

            btnOneWay.setOnClickListener {
                linearLayout7.visibility = View.VISIBLE
                btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                linearLayout8.visibility = View.GONE
                btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                flightType = "One Way Trip"
            }

            btnRoundTrip.setOnClickListener {
                linearLayout7.visibility = View.GONE
                btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                linearLayout8.visibility = View.VISIBLE
                btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                flightType = "Round Trip"
            }

            editTextDari.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_listAirportOriginFragment)
            }

            getNameOriginAirport(flightType)

            editTextKe.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_listAirportDestinationFragment)
            }

            getNameDestinationAirport(flightType)

            etPlaneClass.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_listPlaneClassFragment)
            }

            getPlaneClass(flightType)

            btnSearch.setOnClickListener {
                reqSearch(flightType)
            }

            tvLihatSemuaDestPopular.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_popularDestinationFragment)
            }
        }
    }

    private fun setGreeting() {
        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){ id ->
            sharedPref.getUsername.asLiveData().observe(viewLifecycleOwner){ username ->

                binding.apply {
                    if (id == "Undefined" && username == "Undefined"){
                        layoutGreet.visibility = View.GONE
                    }else{
                        layoutGreet.visibility = View.VISIBLE
                        tvGreetUser.text = "Halo, $username!"
                        setImageGreet(id)
                    }
                }
            }
        }
    }

    private fun setImageGreet(id: String?) {
        id?.let { vmProfile.getProfile(it.toInt()) }
        vmProfile.getLiveDataProfile().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    Log.d("Loading", response.toString())
                }
                is ApiResponse.Success -> {
                    Glide.with(requireContext())
                        .load(response.data?.data?.foto)
                        .placeholder(R.drawable.shape_round_imageview)
                        .into(binding.circleImageView)
                }
                is ApiResponse.Error -> {
                    Log.d("Error", response.msg.toString())
                }
            }
        }
    }

    private fun getNameOriginAirport(checkTypeOw: String) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "namaAirportOrigin"
        )?.observe(viewLifecycleOwner) { name ->

            if (checkTypeOw == "One Way Trip"){
                binding.linearLayout7.visibility = View.VISIBLE
                binding.linearLayout8.visibility = View.GONE
                binding.btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                binding.btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                flightType = "One Way Trip"
            }else if (checkTypeOw == "Round Trip"){
                binding.linearLayout7.visibility = View.GONE
                binding.linearLayout8.visibility = View.VISIBLE
                binding.btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                binding.btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                binding.btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                flightType = "Round Trip"
            }
            binding.editTextDari.setText(name)
        }
    }

    private fun getNameDestinationAirport(checkTypeOw: String) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "namaAirportDestination"
        )?.observe(viewLifecycleOwner) { name ->

            if (checkTypeOw == "One Way Trip"){
                binding.linearLayout7.visibility = View.VISIBLE
                binding.linearLayout8.visibility = View.GONE
                binding.btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                binding.btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                flightType = "One Way Trip"
            }else if (checkTypeOw == "Round Trip"){
                binding.linearLayout7.visibility = View.GONE
                binding.linearLayout8.visibility = View.VISIBLE
                binding.btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                binding.btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                binding.btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                flightType = "Round Trip"
            }
            binding.editTextKe.setText(name)
        }
    }

    private fun getPlaneClass(checkTypeOw: String) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "planeClassName"
        )?.observe(viewLifecycleOwner){

            if (checkTypeOw == "One Way Trip"){
                binding.linearLayout7.visibility = View.VISIBLE
                binding.linearLayout8.visibility = View.GONE
                binding.btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                binding.btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                flightType = "One Way Trip"
            }else if (checkTypeOw == "Round Trip"){
                binding.linearLayout7.visibility = View.GONE
                binding.linearLayout8.visibility = View.VISIBLE
                binding.btnOneWay.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_purple))
                binding.btnOneWay.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_black))
                binding.btnRoundTrip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.btnRoundTrip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                flightType = "Round Trip"
            }
            binding.etPlaneClass.setText(it)
        }
    }

    private fun getDateFromDatePickerOneway() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tanggal Penerbangan")
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            binding.etDepartureDateOw.setText(dateFormat.format(Date(it)))
        }
    }

    private fun getDateFromDatePickerRtDeparture() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tanggal Penerbangan")
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            binding.etDepartureDateRt.setText(dateFormat.format(Date(it)))
        }
    }

    private fun getDateFromDatePickerRtArrive() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tanggal Penerbangan")
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            binding.etArriveDateRt.setText(dateFormat.format(Date(it)))
        }
    }

    private fun reqSearch(checkTypeOw : String) {
        if (binding.editTextDari.text.toString().isEmpty() || binding.editTextKe.text.toString().isEmpty() || binding.etPlaneClass.text.toString().isEmpty() || binding.etJumlahPenumpang.text.toString().isEmpty()){
            Toast.makeText(requireContext(), "Tidak bisa melakukan search, data kosong!", Toast.LENGTH_SHORT).show()
        }else{
            if (checkTypeOw == "One Way Trip"){
                if (binding.etDepartureDateOw.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), "Tidak bisa melakukan search, data kosong!", Toast.LENGTH_SHORT).show()
                }else{
                    homeVm.searchData(
                        SearchTicketRequest(
                            originName = binding.editTextDari.text.toString(),
                            destinationName = binding.editTextKe.text.toString(),
                            planeClass = binding.etPlaneClass.text.toString(),
                            flightDate = binding.etDepartureDateOw.text.toString(),
                            totalPassenger = binding.etJumlahPenumpang.text.toString().toInt()
                        )
                    )
                }
            }else if (checkTypeOw == "Round Trip"){
                if (binding.etDepartureDateRt.text.toString().isEmpty() ||  binding.etArriveDateRt.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), "Tidak bisa melakukan search, data kosong!", Toast.LENGTH_SHORT).show()
                }else{
                    homeVm.searchData(
                        SearchTicketRequest(
                            originName = binding.editTextDari.text.toString(),
                            destinationName = binding.editTextKe.text.toString(),
                            planeClass = binding.etPlaneClass.text.toString(),
                            flightDate = binding.etDepartureDateRt.text.toString(),
                            totalPassenger = binding.etJumlahPenumpang.text.toString().toInt()
                        )
                    )
                }
            }

            homeVm.ambilLiveDataSearch().observe(viewLifecycleOwner){ response->
                when(response){
                    is ApiResponse.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                        Log.d("Loading: ", response.toString())
                    }
                    is ApiResponse.Success -> {
                        binding.progressbar.visibility = View.GONE
                        response.data?.let {
                            Log.d("flightType: ", checkTypeOw)
                            saveDataPref(binding.etJumlahPenumpang.text.toString().toInt(), checkTypeOw)
                            saveBundle(checkTypeOw, it)
                        }
                        Log.d("Success: ", response.toString())
                    }
                    is ApiResponse.Error -> {
                        binding.progressbar.visibility = View.GONE
                        Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                        Log.d("Error: ", response.toString())
                        Log.d("flightType: ", checkTypeOw)
                    }
                }
            }
        }
    }

    private fun saveBundle(checkTypeOw: String, responseSearchTicket: ResponseSearchTicket) {
        val bundle = Bundle()
        if (checkTypeOw == "One Way Trip"){
            bundle.putParcelable("searchResult", SearchBundle(
                dataRequest = SearchTicketRequest(
                    originName = binding.editTextDari.text.toString(),
                    destinationName = binding.editTextKe.text.toString(),
                    planeClass = binding.etPlaneClass.text.toString(),
                    flightDate = binding.etDepartureDateOw.text.toString(),
                    totalPassenger = binding.etJumlahPenumpang.text.toString().toInt()
                ),
                dataResponse = responseSearchTicket,
                flight_back_date = "00-00-0000",
                flight_type = "One Way Trip"
            ))
        }else if (checkTypeOw == "Round Trip"){
            bundle.putParcelable("searchResult", SearchBundle(
                dataRequest = SearchTicketRequest(
                    originName = binding.editTextDari.text.toString(),
                    destinationName = binding.editTextKe.text.toString(),
                    planeClass = binding.etPlaneClass.text.toString(),
                    flightDate = binding.etDepartureDateRt.text.toString(),
                    totalPassenger = binding.etJumlahPenumpang.text.toString().toInt()
                ),
                dataResponse = responseSearchTicket,
                flight_back_date = binding.etArriveDateRt.text.toString(),
                flight_type = "Round Trip"
            ))
        }
        findNavController().navigate(R.id.action_homeFragment_to_hasilSearchFragment, bundle)
    }

    private fun saveDataPref(tpass: Int, checkTypeOw: String) {
        CoroutineScope(Dispatchers.IO).launch {
            sharedPref.saveSearch(tpass, checkTypeOw)
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
        adapter = PopularDestinationAdapter(this)
        adapter.setData(airport)

        binding.apply {
            rvDestPopular.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvDestPopular.adapter = adapter
        }
    }

    override fun onItemClick(airport: Airport) {
        sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFlightFragment(
                Favorit(airport.id, it, airport.airportCode, airport.airportName, airport.city, airport.foto, airport.description)
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}