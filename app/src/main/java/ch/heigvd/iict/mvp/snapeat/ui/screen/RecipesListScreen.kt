package ch.heigvd.iict.mvp.snapeat.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.heigvd.iict.mvp.snapeat.model.Recipe
import coil.compose.AsyncImage
import ch.heigvd.iict.mvp.snapeat.theme.AccentOrange
import ch.heigvd.iict.mvp.snapeat.theme.BackgroundBeige
import ch.heigvd.iict.mvp.snapeat.viewModel.PhotoViewModel

@Composable
fun RecipesRoute(
    photoViewModel: PhotoViewModel,
    onRecipeClick: (Recipe) -> Unit,
    onBackClick: () -> Unit
) {
    val state = photoViewModel.uiState

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center).offset(y = (-80).dp),
                    horizontalAlignment =  Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 4.dp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Analyse des ingrédients...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        state.errorMessage != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(state.errorMessage)
            }
        }

        else -> {
            RecipesListScreen(
                recipes = state.recipes,
                onRecipeClick = onRecipeClick,
                onBackClick = onBackClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesListScreen(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Recettes suggérées",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundBeige
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundBeige)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(recipes) { recipe ->
                RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe) })
            }
        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // Image
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Title
                Text(
                    text = recipe.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Info Row (Time, Difficulty, Ingredients)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InfoChip(icon = "⏱", text = "${recipe.cookingTime} min")
                    InfoChip(icon = "📊", text = recipe.difficulty)
                    InfoChip(
                        icon = "🥘",
                        text = "${recipe.ingredients.size} ingrédients"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tags
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    recipe.tags.forEach { tag ->
                        SuggestionTag(text = tag)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(icon: String, text: String) {
    Surface(
        modifier = Modifier.wrapContentSize(),
        shape = MaterialTheme.shapes.small,
        color = AccentOrange.copy(alpha = 0.1f)
    ) {
        Text(
            text = "$icon $text",
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun SuggestionTag(text: String) {
    Surface(
        modifier = Modifier.wrapContentSize(),
        shape = MaterialTheme.shapes.small,
        color = AccentOrange
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
