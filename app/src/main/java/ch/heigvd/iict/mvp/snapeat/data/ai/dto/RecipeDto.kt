package ch.heigvd.iict.mvp.snapeat.data.ai.dto

data class RecipeDto(
    val title: String,
    val cookingTime: Int,
    val difficulty: String,
    val ingredients: List<IngredientDto>,
    val instructions: List<String>,
    val tags: List<String>,
    val servings: Int
)
