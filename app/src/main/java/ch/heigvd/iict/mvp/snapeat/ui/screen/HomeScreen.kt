package ch.heigvd.iict.mvp.snapeat.ui.screen

import ch.heigvd.iict.mvp.snapeat.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.heigvd.iict.mvp.snapeat.theme.BackgroundBeige
import ch.heigvd.iict.mvp.snapeat.theme.AccentOrange

@Composable
fun HomeScreen(
    onTakePhotoClick: () -> Unit,
    onNavigateToRecipes: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBeige)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo or Title
        Text(
            text = "SnapEats",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // Camera Icon
        Surface(
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 32.dp),
            shape = MaterialTheme.shapes.large,
            color = AccentOrange
        ) {
            Icon(
                //painter = painterResource(id = R.drawable.ic_camera),
                imageVector = Icons.Filled.PhotoCamera,
                contentDescription = "Camera",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp),
                tint = MaterialTheme.colorScheme.onError
            )
        }

        // Title
        Text(
            text = "Trouvez votre recette",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Subtitle
        Text(
            text = "Prenez une photo de vos ingrédients et découvrez des recettes personnalisées",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // Take Photo Button
        Button(
            onClick = onTakePhotoClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(
                //painter = painterResource(id = R.drawable.ic_camera),
                imageVector = Icons.Filled.PhotoCamera,
                contentDescription = "Camera",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp),
                tint = MaterialTheme.colorScheme.onError
            )
            Text(
                text = "Prendre une photo",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Browse Recipes Button
        OutlinedButton(
            onClick = onNavigateToRecipes,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 24.dp),
            border = ButtonDefaults.outlinedButtonBorder
        ) {
            Text(
                text = "Parcourir les recettes",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
