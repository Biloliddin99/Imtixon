package com.example.imtixon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imtixon.databinding.ItemRvBinding
import com.example.imtixon.models.Users

class RvAdapter(val list: ArrayList<Users>) :
    RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(val itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: Users) {
            itemRvBinding.apply {
                tvId.text = user.id.toString()
                tvTitle.text = user.title

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.onBind(list[position])


    override fun getItemCount(): Int = list.size


}
//interface RvClick{
//    fun itemClicked(user: MyCurrency,position: Int)
//}