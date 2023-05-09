package com.example.testapp.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.entity.comments.CommentsItem
import com.example.testapp.databinding.CommentItemRowBinding
import com.example.testapp.utils.common.CommentDiffCallback

class DetailsAdapter(
) : ListAdapter<CommentsItem, DetailsAdapter.VH>(CommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CommentItemRowBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val commentsItem = getItem(position)
        holder.bind(commentsItem)

    }

    inner class VH(private val productItemBinding: CommentItemRowBinding) :
        RecyclerView.ViewHolder(productItemBinding.root) {

        fun bind(commentsItem: CommentsItem) {
            with(productItemBinding) {

                tvName.text = commentsItem.name
                tvEmail.text = commentsItem.email
                tvBody.text = commentsItem.body

            }
        }
    }
}


