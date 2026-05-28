package ch.heigvd.iict.mvp.snapeat.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.heigvd.iict.mvp.snapeat.model.Recipe
import ch.heigvd.iict.mvp.snapeat.model.UserPreferences
import ch.heigvd.iict.mvp.snapeat.ui.screen.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object RecipesList : Screen("recipes_list")
    object RecipeDetail : Screen("recipe_detail/{recipeId}")
    object Preferences : Screen("preferences")
    object Glossary : Screen("glossary")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var preferences by remember { mutableStateOf(UserPreferences()) }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    // Sample recipes for demonstration
    val sampleRecipes = listOf(
        Recipe(
            id = "1",
            title = "Poulet Rôti aux Légumes",
            imageUrl = "https://via.placeholder.com/300x250?text=Poulet+Roti",
            cookingTime = 60,
            difficulty = "Facile",
            ingredients = listOf(
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Poulet entier", 1.5, "kg"),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Carottes", 4.0, "pièces"),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Pommes de terre", 6.0, "pièces"),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Oignon", 2.0, "pièces"),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Thym", 3.0, "branches", true),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Huile d'olive", 3.0, "c.à.s")
            ),
            instructions = listOf(
                "Préchauffer le four à 200°C",
                "Assaisonner le poulet à l'intérieur et à l'extérieur",
                "Éplucher et couper les légumes en morceaux",
                "Disposer les légumes dans un plat et poser le poulet dessus",
                "Arroser d'huile d'olive et ajouter le thym",
                "Rôtir pendant 1h en arrosant régulièrement",
                "Laisser reposer 10 minutes avant de découper"
            ),
            tags = listOf("Facile", "Classique", "Famille")
        ),
        Recipe(
            id = "2",
            title = "Risotto aux Champignons",
            imageUrl = "https://via.placeholder.com/300x250?text=Risotto",
            cookingTime = 35,
            difficulty = "Moyen",
            ingredients = listOf(
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Riz arborio", 300.0, "g"),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Champignons", 250.0, "g"),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Oignon", 1.0, "pièce"),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Vin blanc", 1.0, "verre"),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Bouillon de légumes", 1.0, "litre"),
                ch.heigvd.iict.mvp.snapeat.model.Ingredient("Parmesan", 100.0, "g")
            ),
            instructions = listOf(
                "Faire chauffer le bouillon",
                "Faire revenir les champignons",
                "Ajouter le riz et mélanger",
                "Verser le vin blanc et laisser absorber",
                "Ajouter progressivement le bouillon chaud",
                "Remuer jusqu'à obtention d'une texture crémeuse"
            ),
            tags = listOf("Végétarien", "Créatif", "Moyen")
        )
    )

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onTakePhotoClick = { navController.navigate(Screen.RecipesList.route) },
                onNavigateToRecipes = { navController.navigate(Screen.RecipesList.route) }
            )
        }

        composable(Screen.RecipesList.route) {
            RecipesListScreen(
                recipes = sampleRecipes,
                onRecipeClick = { recipe ->
                    selectedRecipe = recipe
                    navController.navigate(Screen.RecipeDetail.route)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.RecipeDetail.route) {
            selectedRecipe?.let { recipe ->
                RecipeDetailScreen(
                    recipe = recipe,
                    onBackClick = { navController.popBackStack() },
                    onAddToShoppingList = {
                        // Handle shopping list action
                    }
                )
            }
        }

        composable(Screen.Preferences.route) {
            PreferencesScreen(
                currentPreferences = preferences,
                onPreferencesChanged = { preferences = it },
                onCloseClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Glossary.route) {
            CookingGlossaryScreen(
                onCloseClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun CookingGlossaryScreen(onCloseClick: () -> Boolean) {
    TODO("Not yet implemented")
}