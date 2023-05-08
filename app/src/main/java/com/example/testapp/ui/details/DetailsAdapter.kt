package com.example.testapp.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.models.comments_model.CommentsModelItem
import com.example.testapp.databinding.TeamPlayerItemRowBinding


class DetailsAdapter(private var squad: List<CommentsModelItem>?) :
    RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TeamPlayerItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, postion: Int) {
        val squad = squad?.get(postion)
        squad?.let { holder.bind(it) }
    }


    override fun getItemCount() = squad?.size ?: 0

    class ViewHolder(var binding: TeamPlayerItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(it: CommentsModelItem) {
            binding.tvName.text = it.name
            binding.tvEmail.text = it.email
            binding.tvBody.text = it.body
        }
    }

}
