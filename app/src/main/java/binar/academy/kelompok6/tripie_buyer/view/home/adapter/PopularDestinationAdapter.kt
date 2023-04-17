package binar.academy.kelompok6.tripie_buyer.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.data.model.response.airport.Airport
import binar.academy.kelompok6.tripie_buyer.databinding.ItemDestinasiPopularBinding
import com.bumptech.glide.Glide

class PopularDestinationAdapter (private val onClick : PopularInterface) : RecyclerView.Adapter<PopularDestinationAdapter.ViewHolder>(){

    private val limit = 5

    private val differCallback = object : DiffUtil.ItemCallback<Airport>(){
        override fun areItemsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(val binding : ItemDestinasiPopularBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(airport: Airport){
            binding.txtNamaBandara.text = airport.airportName
            binding.txtLokasiBandara.text = airport.city
            Glide.with(itemView.context)
                .load(airport.foto)
                .into(binding.ivBandaraPopular)

            itemView.setOnClickListener {
                onClick.onItemClick(airport)
            }
        }
    }

    interface PopularInterface {
        fun onItemClick(airport: Airport)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDestinasiPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return if (differ.currentList.size > limit){
            limit
        }else{
            differ.currentList.size
        }
    }

    fun setData(data : List<Airport>){
        differ.submitList(data)
    }
}
