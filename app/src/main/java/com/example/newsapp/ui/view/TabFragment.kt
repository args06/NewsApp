package com.example.newsapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.databinding.FragmentTabBinding
import com.example.newsapp.helper.Constant.ARG_SECTION_NUMBER
import com.example.newsapp.helper.Constant.DETAIL_TAB_VALUE
import com.example.newsapp.ui.adapter.CategoryAdapter
import com.example.newsapp.viewmodel.MainViewModel

class TabFragment : Fragment() {

    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.headlineNews.observe(viewLifecycleOwner) { categoryNews ->
            setHeadlineData(categoryNews)
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        setTabData()
    }

    private fun setHeadlineData(
        categoryNews: List<ArticlesItem>
    ) {

        val adapter = CategoryAdapter(categoryNews)
        binding.rvCategoryNews.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        binding.rvCategoryNews.layoutManager = layoutManager

    }

    private fun setTabData() {
        when (arguments?.getInt(ARG_SECTION_NUMBER, 1)) {
            1 -> mainViewModel.getHeadlineNews(DETAIL_TAB_VALUE[1])
            2 -> mainViewModel.getHeadlineNews(DETAIL_TAB_VALUE[2])
            3 -> mainViewModel.getHeadlineNews(DETAIL_TAB_VALUE[3])
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.lpiProgressBar.visibility = View.VISIBLE
        } else {
            binding.lpiProgressBar.visibility = View.GONE
        }
    }
}