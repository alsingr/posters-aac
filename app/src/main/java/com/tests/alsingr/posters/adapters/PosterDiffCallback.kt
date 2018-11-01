package com.tests.alsingr.posters.adapters

import androidx.recyclerview.widget.DiffUtil
import com.tests.alsingr.posters.data.domain.Poster

class PosterDiffCallback : DiffUtil.ItemCallback<Poster>() {

    override fun areItemsTheSame(oldItem: Poster, newItem: Poster): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Poster, newItem: Poster): Boolean {
        return oldItem == newItem
    }
}