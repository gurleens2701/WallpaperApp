package com.example.wallpaperapp.data.api.model

import com.google.gson.annotations.SerializedName


data class PicSumItem(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerializedName("download_url") // from api
    val downloadUrl: String //down load url
)
