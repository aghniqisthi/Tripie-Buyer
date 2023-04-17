package binar.academy.kelompok6.tripie_buyer.view.whistlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_buyer.data.room.Favorit
import binar.academy.kelompok6.tripie_buyer.databinding.ItemWishlistBinding
import com.bumptech.glide.Glide

class FavoritAdapter(
    private var listFavorit : List<Favorit>,
    private val onClick : FavoritInterface) :
    RecyclerView.Adapter<FavoritAdapter.ViewHolder>(), Filterable{

    private var filteredFav : List<Favorit> = listFavorit

    private val differCallback = object : DiffUtil.ItemCallback<Favorit>(){
        override fun areItemsTheSame(oldItem: Favorit, newItem: Favorit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorit, newItem: Favorit): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding : ItemWishlistBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(favorit: Favorit){
            binding.txtNamaBandara.text = favorit.airportName
            binding.txtLokasiBandara.text = favorit.city
            Glide.with(itemView)
                .load(favorit.foto)
                .into(binding.ivFavorit)

            itemView.setOnClickListener {
                onClick.onItemClick(favorit)
            }
        }
    }

    interface FavoritInterface {
        fun onItemClick(favorit: Favorit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemWishlistBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<Favorit>){
        differ.submitList(data)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()

                if (charString.isEmpty()){
                    filteredFav = listFavorit
                }else{
                    val filtered = ArrayList<Favorit>()
                    for (row in listFavorit){
                        if (row.airportName!!.lowercase().contains(constraint.toString().lowercase()) || row.city!!.lowercase().contains(constraint.toString().lowercase())){
                            filtered.add(row)
                        }
                        filteredFav = filtered
                    }
                }

                val result = FilterResults()
                result.values = filteredFav
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredFav =
                    if (results?.values == null){
                        ArrayList()
                    }else{
                        results.values as ArrayList<Favorit>
                    }
                differ.submitList(filteredFav)
            }

        }
    }
}