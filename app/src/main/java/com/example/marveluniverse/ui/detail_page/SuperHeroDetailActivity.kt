package com.example.marveluniverse.ui.detail_page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.marveluniverse.R
import com.example.marveluniverse.databinding.SuperHeroDetailActivityBinding
import com.example.marveluniverse.ui.detail_page.ui_action.SuperHeroDetailUiAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SuperHeroDetailActivity : AppCompatActivity() {

    private val viewModel: SuperHeroDetailViewModel by viewModels()
    private lateinit var binding: SuperHeroDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.super_hero_detail_activity)
        if (viewModel.id.isBlank()) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            finish()
        }
        initObserver()
        initClickListener()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.superHero.collect {
                    binding.model = it
                    actionBar?.title = it.name
                }
            }
        }
    }

    private fun initClickListener() {
        binding.bookmarkIcon.setOnClickListener {
            viewModel.handleUiAction(SuperHeroDetailUiAction.FavouriteClick)
        }
    }

    companion object {
        fun newInstance(context: Context, characterId: String): Intent {
            return Intent(context, SuperHeroDetailActivity::class.java).apply {
                putExtra(SuperHeroDetailViewModel.CHARACTER_ID, characterId)
            }
        }
    }
}
