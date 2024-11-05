package com.example.wallpaperapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapp.presentation.adapter.ImagesRecyclerViewAdapter
import com.example.wallpaperapp.databinding.ActivityMainBinding
import com.example.wallpaperapp.domain.entity.WallpaperLink
import com.example.wallpaperapp.presentation.WallPaperUiState
import com.example.wallpaperapp.presentation.adapter.ItemOnClickListener
import com.example.wallpaperapp.presentation.viewmodel.WallpaperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityMainBinding
    private val wallpaperViewModel: WallpaperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupViews()
        collectUiState()
        wallpaperViewModel.fetchWallpapers()
    }

    fun setupViews() {
        binding.imagesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    fun collectUiState() {
        lifecycleScope.launch(Dispatchers.Main) {
            wallpaperViewModel.wallpaperList.collect() { wallpaperUiState ->
                when (wallpaperUiState) {
                    is WallPaperUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity,"Wallpapers are currently loading", Toast.LENGTH_SHORT).show()
                    }

                    is WallPaperUiState.EmptyList -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity,"Wallpapers are currently Empty", Toast.LENGTH_SHORT).show()

                    }

                    is WallPaperUiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        //todo update recyclerview
                        populateDataInRecyclerView(wallpaperUiState.data)
                    }

                    is WallPaperUiState.Error -> {
                        Toast.makeText(this@MainActivity,"Error occured", Toast.LENGTH_SHORT).show()

                    }
                }

            }
        }

    }

    fun populateDataInRecyclerView(list : List<WallpaperLink>) {
        val wallpaperAdapter = ImagesRecyclerViewAdapter(list, this::onClickImage)
        binding.imagesRecyclerView.adapter = wallpaperAdapter
    }



    fun onClickImage(wallpaperLink: String) {
        TODO("Not yet implemented")
    }


}
