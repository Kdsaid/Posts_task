package com.example.testapp.utils.common

import androidx.recyclerview.widget.DiffUtil
import com.example.testapp.data.entity.posts.PostsItem

class PostsIDiffCallback : DiffUtil.ItemCallback<PostsItem>() {
    override fun areItemsTheSame(oldItem: PostsItem, newItem: PostsItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PostsItem, newItem: PostsItem) = oldItem == newItem
}