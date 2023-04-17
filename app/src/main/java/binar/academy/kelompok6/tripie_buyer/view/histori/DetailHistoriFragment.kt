package binar.academy.kelompok6.tripie_buyer.view.histori

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import binar.academy.kelompok6.tripie_buyer.data.model.response.history.Booking
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentDetailHistoriBinding
import binar.academy.kelompok6.tripie_buyer.utils.RupiahConverter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailHistoriFragment : Fragment() {
    lateinit var binding : FragmentDetailHistoriBinding
    private val args : DetailHistoriFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailHistoriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  data = args.dataHistory

        binding.buttonBack.setOnClickListener{
            findNavController().navigateUp()
        }

        setData(data)
    }

    private fun setData(data: Booking) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val flightDate = SimpleDateFormat("d MMMM y",
            Locale.getDefault()).format(dateFormat.parse(data.flightDate)!!)
        val flightBackDate = data.flightBackDate?.let {
            dateFormat.parse(it)?.let { date->
                SimpleDateFormat("d MMMM y",
                    Locale.getDefault()).format(date)
            }
        }

        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val convertedDepartHour = timeFormat.parse(data.departureHour)?.let {
            SimpleDateFormat("HH:mm",
                Locale.getDefault()).format(it)
        }
        val convertedArriveHour = timeFormat.parse(data.arrivalHour)?.let {
            SimpleDateFormat("HH:mm",
                Locale.getDefault()).format(it)
        }

        binding.apply {
            tvNamaPenumpang.text = data.passengerName
            tvBandaraAsal.text = "${data.originCode}, ${data.originCity}"
            tvBandaraTujuan.text = "${data.destinationCode}, ${data.destinationCity}"
            tvTanggalBerangkat.text = flightDate
            tvTanggalSampai.text = flightDate
            tvJamBerangkat.text = convertedDepartHour
            tvJamSampai.text = convertedArriveHour
            tvHargatiket.text = RupiahConverter.rupiah(data.price)
            tvTipePenerbangan.text = data.flightType
            tvBookingID.text = data.id.toString()
            tvKelas.text = data.planeClass
            tvSubHeaderMaskapai.text = data.airlineName

            if (data.flightType.lowercase() == "round trip"){
                tvPulang.text = flightBackDate
            }else{
                tvPulang.text = "-"
            }
        }
    }
}