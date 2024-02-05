package com.example.marveluniverse.ui.list_page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.marveluniverse.databinding.LoadingStateLayoutBinding
import com.example.marveluniverse.ui.list_page.viewholder.LoadStateViewHolder

class LoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadStateViewHolder {
        val binding = LoadingStateLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}
