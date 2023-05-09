package com.example.testapp.utils.common

import androidx.recyclerview.widget.DiffUtil
import com.example.testapp.data.entity.comments.CommentsItem

class CommentDiffCallback : DiffUtil.ItemCallback<CommentsItem>() {


    override fun areItemsTheSame(oldItem: CommentsItem, newItem: CommentsItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CommentsItem,
        newItem: CommentsItem
    ) = oldItem == newItem
}