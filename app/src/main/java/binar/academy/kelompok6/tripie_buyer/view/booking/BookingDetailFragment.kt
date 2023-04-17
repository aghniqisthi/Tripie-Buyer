package binar.academy.kelompok6.tripie_buyer.view.booking

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
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_buyer.data.model.DataForBooking
import binar.academy.kelompok6.tripie_buyer.data.model.SearchBundle
import binar.academy.kelompok6.tripie_buyer.data.model.request.BookingTicketRequest
import binar.academy.kelompok6.tripie_buyer.data.network.ApiResponse
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentBookingDetailBinding
import binar.academy.kelompok6.tripie_buyer.utils.RupiahConverter
import binar.academy.kelompok6.tripie_buyer.view.booking.viewmodel.BookingTiketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingDetailFragment : Fragment() {
    private var _binding : FragmentBookingDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPref
    private val vmBookingTicket : BookingTiketViewModel by viewModels()
    private var totalPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        val data = arguments?.getParcelable<DataForBooking>("dataBuatBooking") as DataForBooking

        binding.btnBacktoResult.setOnClickListener {
            findNavController().navigateUp()
        }

        totalPrice = data.price * data.totalPassenger

        binding.tvHargaTiket.text = RupiahConverter.rupiah(totalPrice)

        binding.btnBookingTiket.setOnClickListener {
            bookingTiket(data, totalPrice)
        }
    }

    private fun bookingTiket(data: DataForBooking, tprice : Int) {
        if (binding.etNamaLengkapPenumpang.text.toString().isEmpty() || binding.etNomorTeleponPenumpang.text.toString().isEmpty()){
            Toast.makeText(requireContext(), "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }else{
            sharedPref.getIdUser.asLiveData().observe(viewLifecycleOwner){ userId ->
                vmBookingTicket.bookingTicket(BookingTicketRequest(
                    userId = userId.toInt(),
                    scheduleId = data.scheduleId,
                    originName = data.originName,
                    destinationName = data.destinationName,
                    planeClass = data.planeClass,
                    totalPassenger = data.totalPassenger,
                    flightType = data.flightType,
                    flightDate = data.flightDate,
                    flightBackDate = data.flightBackDate,
                    departureHour = data.departureHour,
                    arrivalHour = data.arrivalHour,
                    price = tprice,
                    passengerName = binding.etNamaLengkapPenumpang.text.toString(),
                    phoneNumber = binding.etNomorTeleponPenumpang.text.toString()
                ))

            }
        }

        vmBookingTicket.postBookingObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading ->{
                    showLoading()
                    Log.d("Loading", response.toString())
                }
                is ApiResponse.Success ->{
                    stopLoading()
                    findNavController().navigate(R.id.action_bookingDetailFragment_to_bookingSuccessFragment)
                    Log.d("Success", response.toString())
                }
                is ApiResponse.Error ->{
                    stopLoading()
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                    Log.d("Error", response.toString())
                }
            }
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