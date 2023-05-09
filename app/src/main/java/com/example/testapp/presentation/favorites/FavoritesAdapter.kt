package com.example.testapp.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.data.entity.posts.PostsItem
import com.example.testapp.databinding.PostItemRowBinding
import com.example.testapp.utils.common.PostsIDiffCallback

class FavoritesAdapter(
    private val itemClick: (Int) -> Unit,
    private val onFavouriteClick: (PostsItem) -> Unit,
) : ListAdapter<PostsItem, FavoritesAdapter.VH>(PostsIDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostItemRowBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val postsItem = getItem(position)
        holder.bind(postsItem)
        holder.itemView.setOnClickListener {
            itemClick.invoke(postsItem.id)
        }
    }

    inner class VH(private val postItemRowBinding: PostItemRowBinding) :
        RecyclerView.ViewHolder(postItemRowBinding.root) {

        fun bind(postsItem: PostsItem) {
            with(postItemRowBinding) {
                postsItem?.title?.let {
                    tvTitle.text = it
                }
                postsItem?.body?.let {
                    tvBody.text = it
                }
                faveIcon.apply {
                    setImageResource(
                        if (postsItem.isFave) R.drawable.ic_bookmark
                        else R.drawable.ic_bookmark_border
                    )
                    setOnClickListener {
                        onFavouriteClick.invoke(postsItem)
                    }
                }

            }
        }
    }
}


