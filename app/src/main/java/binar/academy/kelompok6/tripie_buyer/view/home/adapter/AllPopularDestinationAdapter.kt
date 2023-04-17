package binar.academy.kelompok6.tripie_buyer.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.data.model.response.airport.Airport
import binar.academy.kelompok6.tripie_buyer.databinding.ItemDestinasiAllBinding
import com.bumptech.glide.Glide

class AllPopularDestinationAdapter (private val onClick : PopularInterface) : RecyclerView.Adapter<AllPopularDestinationAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<Airport>(){
        override fun areItemsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(val binding : ItemDestinasiAllBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(airport: Airport){
            binding.txtNamaBandara.text = airport.airportName
            binding.txtLokasiBandara.text = airport.city
            Glide.with(itemView)
                .load(airport.foto)
                .into(binding.ivAllPopular)

            itemView.setOnClickListener {
                onClick.onItemClick(airport)
            }
        }
    }

    interface PopularInterface {
        fun onItemClick(airport: Airport)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDestinasiAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<Airport>){
        differ.submitList(data)
    }
}
