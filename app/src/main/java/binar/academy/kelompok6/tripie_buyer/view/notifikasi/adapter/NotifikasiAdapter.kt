package binar.academy.kelompok6.tripie_buyer.view.notifikasi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.data.model.response.notification.DataNotification
import binar.academy.kelompok6.tripie_buyer.databinding.ItemNotificationBinding

class NotifikasiAdapter : RecyclerView.Adapter<NotifikasiAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataNotification>(){
        override fun areItemsTheSame(oldItem: DataNotification, newItem: DataNotification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataNotification, newItem: DataNotification): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding : ItemNotificationBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(dataNotification: DataNotification){
                binding.tvNotifikasiMsg.text = dataNotification.message
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<DataNotification>){
        differ.submitList(data)
    }
}