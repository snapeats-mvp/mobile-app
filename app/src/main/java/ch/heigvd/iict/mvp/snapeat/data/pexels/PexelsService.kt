package ch.heigvd.iict.mvp.snapeat.data.pexels

import ch.heigvd.iict.mvp.snapeat.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PexelsService {

    private val api = Retrofit.Builder()
        .baseUrl("https://api.pexels.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PexelsApi::class.java)

    suspend fun searchRecipeImage(recipeTitle: String) : String? {
        return try{
            val response = api.searchPhotos(
                apiKey = BuildConfig.PEXELS_API_KEY,
                query = "$recipeTitle food recipe"
            )

            response.photos.firstOrNull()?.src?.medium
        } catch (e: Exception){
            null
        }
    }
}