package com.tests.alsingr.posters.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tests.alsingr.posters.R
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.databinding.ListItemPosterBinding
import timber.log.Timber

class PosterAdapter : ListAdapter<Poster, PosterAdapter.ViewHolder>(PosterDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val poster = getItem(position)
        holder.apply {
            bind(createOnClickListener(poster.id.toString()), poster)
            itemView.tag = poster
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemPosterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(posterId: String): View.OnClickListener {
        return View.OnClickListener {
            Timber.v("Item has been clicked")
        }
    }

    class ViewHolder(
        private val binding: ListItemPosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Poster) {
            binding.apply {
                clickListener = listener
                poster = item
                executePendingBindings()
            }
        }
    }
}