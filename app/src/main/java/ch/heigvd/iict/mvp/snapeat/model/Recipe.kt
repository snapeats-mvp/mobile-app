package ch.heigvd.iict.mvp.snapeat.model

data class Recipe(
    val title: String,
    val imageUrl: String?,
    val cookingTime: Int,
    val difficulty: String,
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
    val tags: List<String>,
    val servings: Int = 4
)

data class Ingredient(
    val name: String,
    val quantity: Double,
    val unit: String,
    val inPhoto: Boolean
)

data class UserPreferences(
    val dietaryRestrictions: List<DietaryRestriction> = emptyList(),
    val allergies: List<String> = emptyList(),
    val favoriteIngredients: List<String> = emptyList()
)

enum class DietaryRestriction {
    VEGETARIAN,
    VEGAN,
    GLUTEN_FREE,
    DAIRY_FREE,
    NUT_FREE
}