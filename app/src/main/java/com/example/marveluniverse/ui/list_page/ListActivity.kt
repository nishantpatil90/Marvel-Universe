package com.example.marveluniverse.ui.list_page

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.marveluniverse.R
import com.example.marveluniverse.databinding.ListActivityBinding
import com.example.marveluniverse.ui.detail_page.SuperHeroDetailActivity
import com.example.marveluniverse.ui.list_page.adapter.LoadStateAdapter
import com.example.marveluniverse.ui.list_page.adapter.SuperHeroListAdapter
import com.example.marveluniverse.ui.list_page.ui_state.SuperHeroListUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()
    private val superHeroListAdapter by lazy {
        SuperHeroListAdapter(viewModel::handleUiEvents)
    }

    private lateinit var binding: ListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.list_activity)
        actionBar?.title = getString(R.string.app_name)
        initRecyclerView()
        initObservers()
        initClickListener()
    }

    private fun initRecyclerView() {
        superHeroListAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { superHeroListAdapter.retry() },
            footer = LoadStateAdapter { superHeroListAdapter.retry() }
        )
        binding.superheroRv.apply {
            adapter = superHeroListAdapter
        }
    }

    private fun initClickListener() {
        binding.retryButton.setOnClickListener { superHeroListAdapter.retry() }
        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = true
            superHeroListAdapter.refresh()
        }
    }

    private fun launchDetailScreen(characterId: String) {
        startActivity(SuperHeroDetailActivity.newInstance(this, characterId))
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.superHeroesList.collectLatest {
                    superHeroListAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    val state = it.getContentIfNotHandled() ?: return@collectLatest
                    when (state) {
                        is SuperHeroListUiState.LaunchDetailScreen -> launchDetailScreen(state.id)
                    }
                }
            }
        }

        lifecycleScope.launch {
            superHeroListAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && superHeroListAdapter.itemCount == 0
                with(binding) {
                    emptyList.isVisible = isListEmpty
                    superheroRv.isVisible = !isListEmpty
                    swipeContainer.isRefreshing  = loadState.refresh is LoadState.Loading
                    retryButton.isVisible = loadState.refresh is LoadState.Error
                }

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        this@ListActivity,
                        "${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
