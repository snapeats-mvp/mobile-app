package ch.heigvd.iict.mvp.snapeat.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.heigvd.iict.mvp.snapeat.data.ai.GeminiRecipeService
import ch.heigvd.iict.mvp.snapeat.data.ai.dto.toRecipe
import ch.heigvd.iict.mvp.snapeat.data.pexels.PexelsService
import ch.heigvd.iict.mvp.snapeat.model.Recipe
import ch.heigvd.iict.mvp.snapeat.model.UserPreferences
import kotlinx.coroutines.launch

data class RecipesUiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val errorMessage: String? = null
)

class PhotoViewModel : ViewModel() {

    private val geminiRecipeService = GeminiRecipeService()
    private val pexelsService = PexelsService()
    var selectedImageUri by mutableStateOf<Uri?>(null)
        private set

    var capturedBitmap by mutableStateOf<Bitmap?>(null)
        private set

    var uiState by mutableStateOf(RecipesUiState())
        private set

    fun saveSelectedImage(uri : Uri?){
        selectedImageUri = uri
        capturedBitmap = null
    }

    fun saveCapturedBitmap(bitmap : Bitmap?){
        capturedBitmap = bitmap
        selectedImageUri = null
    }

    fun generateRecipes(context: Context, preferences: UserPreferences){
        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            try{
                val bitmap = capturedBitmap ?: selectedImageUri?.let{
                    uriToBitmap(context, it)
                } ?: error("Aucune image sélectionnée")

                val result = geminiRecipeService.generateRecipesFromImage(bitmap, preferences)

                result
                    .onSuccess { dto ->
                        val recipes = dto.recipes.map { it.toRecipe() }
                        val recipesWithImages = recipes.map { recipe ->
                            val imageUrl = pexelsService.searchRecipeImage(recipe.title)

                            recipe.copy(
                                imageUrl = imageUrl
                            )
                        }

                        uiState = RecipesUiState(
                            isLoading = false,
                            recipes = recipesWithImages
                        )
                    }
                    .onFailure{ e ->
                        uiState = RecipesUiState(
                            isLoading = false,
                            errorMessage = e.message ?: "Erreur Gemini"
                        )
                    }
            } catch (e: Exception) {
                uiState = RecipesUiState(
                    isLoading = false,
                    errorMessage = e.message ?: "Erreur inconnue"
                )
            }
        }
    }

    private fun uriToBitmap(context: Context, uri: Uri): Bitmap{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source) {decoder, _, _ ->
                decoder.isMutableRequired = true
            }
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }
}
