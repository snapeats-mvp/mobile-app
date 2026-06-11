package ch.heigvd.iict.mvp.snapeat.data.pexels

import ch.heigvd.iict.mvp.snapeat.data.pexels.dto.PexelsResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApi {

    @GET("v1/search")
    suspend fun searchPhotos(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 1
        ): PexelsResponseDto
}