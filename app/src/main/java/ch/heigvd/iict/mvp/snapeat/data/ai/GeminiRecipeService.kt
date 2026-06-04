package ch.heigvd.iict.mvp.snapeat.data.ai

import android.graphics.Bitmap
import ch.heigvd.iict.mvp.snapeat.data.ai.dto.RecipeResponseDto
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import com.google.gson.Gson

class GeminiRecipeService {

    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-3.5-flash")

    private val gson = Gson()

    suspend fun generateRecipesFromImage(bitmap: Bitmap): Result<RecipeResponseDto> {
        val prompt = """
            Analyze this image of food ingredients.

            Identify the visible ingredients and generate 3 realistic recipes that can be prepared using them.
            
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
                      "optional": false
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
            - If an ingredient is uncertain, mark it as optional.
            
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

        try{
            val json = response.text ?: error("Empty answer")
            val dto = gson.fromJson(json, RecipeResponseDto::class.java)
            return Result.success(dto)
        }catch (e: Exception){
            return Result.failure(e)
        }
    }
}