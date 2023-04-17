package binar.academy.kelompok6.tripie_buyer.view.histori.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.data.model.response.history.Booking
import binar.academy.kelompok6.tripie_buyer.databinding.ItemHistoriBinding
import binar.academy.kelompok6.tripie_buyer.utils.RupiahConverter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter(
    private var listBooking : List<Booking>,
    private val onClick : HistoryInterface) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>()
    , Filterable {

    private var filteredBooking : List<Booking> = listBooking

    private val differCallback = object : DiffUtil.ItemCallback<Booking>(){
        override fun areItemsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)


    inner class HistoryViewHolder(val binding : ItemHistoriBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(booking: Booking) {
            binding.apply {
                val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val convertedDepartHour = timeFormat.parse(booking.departureHour)?.let {
                    SimpleDateFormat("HH:mm",
                        Locale.getDefault()).format(it)
                }
                val convertedArriveHour = timeFormat.parse(booking.arrivalHour)?.let {
                    SimpleDateFormat("HH:mm",
                        Locale.getDefault()).format(it)
                }

                tvKodeBandaraAsal.text = booking.originCode
                tvKodeBandaraTujuan.text = booking.destinationCode
                tvKotaBandaraAsal.text = booking.originCity
                tvKotaBandaraTujuan.text = booking.destinationCity
                tvJamBerangkat.text = convertedDepartHour
                tvJamPulang.text = convertedArriveHour
                tvHargatiket.text = RupiahConverter.rupiah(booking.price)

                btnDetails.setOnClickListener {
                    onClick.onItemClick(booking)
                }
            }
        }
    }

    interface HistoryInterface {
        fun onItemClick(booking: Booking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = ItemHistoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setData(data : List<Booking>) {
        differ.submitList(data)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()

                if (charString.isEmpty()){
                    filteredBooking = listBooking
                }else{
                    val filtered = ArrayList<Booking>()
                    for (row in listBooking){
                        if (row.originCity.lowercase().contains(constraint.toString().lowercase())
                            || row.destinationCity.lowercase().contains(constraint.toString().lowercase())
                            || row.originCode.lowercase().contains(constraint.toString().lowercase())
                            || row.destinationCode.lowercase().contains(constraint.toString().lowercase())
                        ){
                            filtered.add(row)
                        }
                        filteredBooking = filtered
                    }
                }

                val result = FilterResults()
                result.values = filteredBooking
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredBooking =
                    if (results?.values == null){
                        ArrayList()
                    }else{
                        results.values as ArrayList<Booking>
                    }
                differ.submitList(filteredBooking)
            }

        }
    }
}