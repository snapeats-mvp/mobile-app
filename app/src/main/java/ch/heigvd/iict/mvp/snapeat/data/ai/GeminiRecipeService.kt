package ch.heigvd.iict.mvp.snapeat.data.ai

import android.graphics.Bitmap
import android.util.Log
import ch.heigvd.iict.mvp.snapeat.data.ai.dto.RecipeResponseDto
import ch.heigvd.iict.mvp.snapeat.model.UserPreferences
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import com.google.gson.Gson

class GeminiRecipeService {

    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")

    private val gson = Gson()

    suspend fun generateRecipesFromImage(bitmap: Bitmap, preferences: UserPreferences): Result<RecipeResponseDto> {
        val preferencesPrompt =
            if (
                preferences.dietaryRestrictions.isEmpty() &&
                preferences.allergies.isEmpty()
            ) {
                "No dietary restrictions or allergies."
            } else {
                buildString{
                    if(preferences.dietaryRestrictions.isNotEmpty()){
                        appendLine("Dietary restrictions:")
                        preferences.dietaryRestrictions.forEach{
                            appendLine("- ${formatPreference(it)}")
                        }
                    }

                    if(preferences.allergies.isNotEmpty()){
                        appendLine("Allergies and intolerances:")
                        preferences.allergies.forEach{
                            appendLine("- $it")
                        }
                    }
                }
            }

        val prompt = """
            Analyze this image of food ingredients.
            
            User dietary profile:
            $preferencesPrompt
            
            Important rules:
            - Respect dietary restrictions.
            - Never use ingredients listed in allergies or intolerances, even if they are visible in the image.
            - If the detected ingredients conflict with dietary restrictions or allergies, exclude them and suggest alternatives.

            Identify the visible ingredients and generate 3 realistic recipes mainly based on them.
            
            Some additional ingredients may be added to complete a recipe, but they must be marked with inPhoto = false.
            
            Return ONLY valid JSON.
            
            All fields are required.
            
            Ingredients must contain realistic quantities and units.
            
            Expected format:
            
            {
              "recipes": [
                {
                  "title": "string",
                  "cookingTime": 0,
                  "difficulty": "EASY",
                  "ingredients": [
                    {
                      "name": "string",
                      "quantity": 0,
                      "unit": "string",
                      "inPhoto": true
                    }
                  ],
                  "instructions": [
                    "step 1",
                    "step 2"
                  ],
                  "tags": [
                    "tag1",
                    "tag2"
                  ],
                  "servings": 4
                }
              ]
            }

            Rules:
            - Generate exactly 3 recipes.
            - cookingTime must be an integer representing minutes.
            - difficulty must be exactly one of: EASY, MEDIUM, HARD.
            - instructions must contain at least 3 steps.
            - tags must contain between 1 and 5 tags.
            - servings must be a positive integer.
            
            - For each recipe ingredient:
              - Set inPhoto = true only if the ingredient is clearly visible in the uploaded image.
              - Set inPhoto = false if the ingredient is required for the recipe but not visible in the image.
              - Do not assume an ingredient is visible unless it can actually be seen.
              - If an ingredient is uncertain, set inPhoto = false.
            
            Do not include markdown.
            Do not include explanations.
            Do not include comments.
            Return JSON only.
        """.trimIndent()

        val response = model.generateContent(
            content {
                image(bitmap)
                text(prompt)
            }
        )

        Log.d("GEMINI", response.text ?: "NULL")

        try{
            val json = response.text
                ?.replace("```json", "")
                ?.replace("```", "")
                ?.trim()
                ?: error("Empty answer")

            Log.d("GEMINI_JSON", json)

            val dto = gson.fromJson(json, RecipeResponseDto::class.java)
            return Result.success(dto)
        }catch (e: Exception){
            Log.e("GEMINI", "Erreur", e)
            return Result.failure(e)
        }
    }

    private fun formatPreference(value: Any): String {
        return value.toString()
            .replace("_", " ")
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    }
}