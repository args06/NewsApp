package com.example.newsapp.ui.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.helper.Constant
import com.example.newsapp.helper.Constant.DETAIL_TAB_TITLES
import com.example.newsapp.ui.adapter.DetailSectionsPagerAdapter
import com.example.newsapp.ui.adapter.HeadlineAdapter
import com.example.newsapp.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private var isNewsEmpty: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.headlineNews.observe(this) { headlineNews ->
            if (headlineNews.isNotEmpty()){
                isNewsEmpty = false
                setHeadlineData(headlineNews)
            }
            else {
                isNewsEmpty = true
                binding.headlineLayout.clTopHeadline.visibility = View.GONE
                binding.headlineLayout.rvHeadline.visibility = View.GONE
                binding.headlineLayout.tvNoNews.visibility = View.VISIBLE
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.getHeadlineNews(Constant.DETAIL_TAB_VALUE[0],5)
        setTabLayout()
    }

    private fun showRecyclerView() {
        val layoutManager = GridLayoutManager(this,2)
        binding.headlineLayout.rvHeadline.layoutManager = layoutManager
    }

    private fun setHeadlineData(
        headlineNews: List<ArticlesItem>
    ) {
        val headlineNewsTop = headlineNews[0]
        val headlineNewsBottom = headlineNews.drop(1)

        Glide.with(applicationContext).load(headlineNewsTop.urlToImage).error(R.drawable.ic_launcher_background).into(binding.headlineLayout.ivTopHeadlineImage)
        binding.headlineLayout.tvTopHeadlineTitle.text = headlineNewsTop.title
        binding.headlineLayout.tvTopHeadlineSource.text = resources.getString(R.string.source_template, headlineNewsTop.source.name)
        binding.headlineLayout.tvTopHeadlineDate.text = headlineNewsTop.publishedAt.substring(0,10)
        binding.headlineLayout.clTopHeadline.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(headlineNewsTop.url)
            it.context.startActivity(intent)
        }

        val adapter = HeadlineAdapter(headlineNewsBottom)
        binding.headlineLayout.rvHeadline.adapter = adapter
        showRecyclerView()
    }

    private fun setTabLayout() {
        val detailSectionsPagerAdapter = DetailSectionsPagerAdapter(this, 3)
        binding.vpNews.adapter = detailSectionsPagerAdapter

        TabLayoutMediator(binding.tlNews, binding.vpNews) { tab, position ->
            tab.text = resources.getString(DETAIL_TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getSearchNews(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mainViewModel.getSearchNews(newText)
                if (newText == "") mainViewModel.getHeadlineNews(Constant.DETAIL_TAB_VALUE[0],5)
                if (!isNewsEmpty) {
                    binding.headlineLayout.clTopHeadline.visibility = View.VISIBLE
                    binding.headlineLayout.rvHeadline.visibility = View.VISIBLE
                    binding.headlineLayout.tvNoNews.visibility = View.GONE
                }
                return false
            }
        })
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}