package ch.heigvd.iict.mvp.snapeat.data.ai.dto

import ch.heigvd.iict.mvp.snapeat.model.Ingredient
import ch.heigvd.iict.mvp.snapeat.model.Recipe



fun RecipeDto.toRecipe(): Recipe {
    return Recipe(
        title = title,
        imageUrl = null,
        cookingTime = cookingTime,
        difficulty = difficulty,
        ingredients = ingredients.map { it.toIngredient() },
        instructions = instructions,
        tags = tags,
        servings = servings
    )
}

fun IngredientDto.toIngredient(): Ingredient {
    return Ingredient(
        name = name,
        quantity = quantity,
        unit = unit,
        inPhoto = inPhoto
    )
}
