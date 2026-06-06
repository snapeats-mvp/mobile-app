package ch.heigvd.iict.mvp.snapeat.ui.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.heigvd.iict.mvp.snapeat.model.Recipe
import ch.heigvd.iict.mvp.snapeat.model.UserPreferences
import ch.heigvd.iict.mvp.snapeat.ui.screen.*
import ch.heigvd.iict.mvp.snapeat.viewModel.PhotoViewModel

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
    val photoViewModel: PhotoViewModel = viewModel()

    var preferences by remember { mutableStateOf(UserPreferences()) }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                photoViewModel = photoViewModel,
                preferences = preferences,
                onCameraClick = {navController.navigate(Screen.RecipesList.route)},
                onGalerieClick = {navController.navigate(Screen.RecipesList.route)},
                onNavigateToRecipes = { navController.navigate(Screen.RecipesList.route) },
                onNavigateToPreferences = { navController.navigate(Screen.Preferences.route) }
            )
        }

        composable(Screen.RecipesList.route) {
            RecipesRoute(
                photoViewModel = photoViewModel,
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