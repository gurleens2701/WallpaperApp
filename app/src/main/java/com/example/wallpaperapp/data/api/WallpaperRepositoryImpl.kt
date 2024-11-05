package com.example.wallpaperapp.data.api

import com.example.wallpaperapp.data.api.model.PicSumItem
import com.example.wallpaperapp.domain.entity.WallpaperLink
import com.example.wallpaperapp.domain.repository.WallpaperRepository
import com.example.wallpaperapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject



class WallpaperRepositoryImpl @Inject constructor(val picSumApi: PicSumApi): WallpaperRepository {


    override fun getImages(): Flow<Resource<List<WallpaperLink>>> = flow {
        //api.getImages()
        try {
            val response = picSumApi.getWallpaperImages()
            response?.let {
                val wallpaperLinks: List<WallpaperLink> = response.map {
                    WallpaperLink(it.downloadUrl)
                }

                emit(Resource.Success(wallpaperLinks))
            }
        } catch (e: Exception) {
            var errorOutput = ""
            if (e.message != null) {
                errorOutput = e.message!!
            } else {
                errorOutput = "Unknown Error"
            }
            emit(Resource.Error(null, errorOutput))

        }

    }
}

