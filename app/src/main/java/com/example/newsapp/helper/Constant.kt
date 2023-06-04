package com.example.newsapp.helper

import androidx.annotation.StringRes
import com.example.newsapp.R

object Constant {

    const val ARG_SECTION_NUMBER = "section_number"
    const val DEFAULT_COUNTRY = "us"
    @StringRes
    val DETAIL_TAB_TITLES = intArrayOf(
        R.string.tech_title,
        R.string.health_title,
        R.string.science_title,
    )
    val DETAIL_TAB_VALUE = arrayOf(
        "general",
        "technology",
        "health",
        "science",
    )
}