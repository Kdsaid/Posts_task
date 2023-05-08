package com.example.testapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.models.posts_model.PostsItem
import com.example.testapp.databinding.PostItemRowBinding

class HomeDataAdapter(
    private var teams: List<PostsItem>?, private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<HomeDataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, postion: Int) {
        holder.bind(teams?.get(postion), itemClick)
    }


    override fun getItemCount() = teams?.size ?: 0

    class ViewHolder(private var binding: PostItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(get: PostsItem?, itemClick: (Int) -> Unit) {
            get?.title?.let {
                binding.tvTitle.text = it

            }
            get?.body?.let {
                binding.tvBody.text = it
            }

            itemView.setOnClickListener {
                get?.let {
                    itemClick(it.id)

                }
            }

        }
    }


}



