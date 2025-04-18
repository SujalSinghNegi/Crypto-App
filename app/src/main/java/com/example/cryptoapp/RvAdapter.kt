package com.example.cryptoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.example.cryptoapp.databinding.RvItemBinding

class RvAdapter(var userList: ArrayList<ItemData>): RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding= RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        anim(holder.itemView)
        holder.binding.name.text = userList[position].name
        holder.binding.symbol.text = userList[position].symbol
        holder.binding.price.text = userList[position].price
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(val binding: RvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemData){
            binding.name.text = item.name
            binding.symbol.text = item.symbol
            binding.price.text = item.price
        }
    }
    fun anim(view:View){
        var animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration= 600
        view.startAnimation(animation)
    }
}