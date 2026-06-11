package ch.heigvd.iict.mvp.snapeat.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import ch.heigvd.iict.mvp.snapeat.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.heigvd.iict.mvp.snapeat.model.UserPreferences
import ch.heigvd.iict.mvp.snapeat.theme.BackgroundBeige
import ch.heigvd.iict.mvp.snapeat.theme.AccentOrange
import ch.heigvd.iict.mvp.snapeat.viewModel.PhotoViewModel

@Composable
fun HomeScreen(
    photoViewModel: PhotoViewModel,
    preferences: UserPreferences,
    onCameraClick: () -> Unit,
    onGalerieClick: () -> Unit,
    onNavigateToRecipes: () -> Unit,
    onNavigateToPreferences: () -> Unit
) {
    // Controls whether the image source selection dialog is displayed
    var showImageSourceDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    // Launcher used to open the device gallery and select an image
    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            photoViewModel.saveSelectedImage(uri)

            if(uri != null){
                photoViewModel.generateRecipes(context, preferences)
                onGalerieClick()
            }
        }

    // Launcher used to open the device camera and capture a picture
    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            photoViewModel.saveCapturedBitmap(bitmap)

            if(bitmap != null){
                photoViewModel.generateRecipes(context, preferences)
                onCameraClick()
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                cameraLauncher.launch()
            }
        }

    Box(
        modifier = Modifier.fillMaxSize().background(BackgroundBeige).padding(24.dp)
    ) {
        IconButton(
            onClick = onNavigateToPreferences,
            modifier = Modifier.align(Alignment.TopEnd).padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Préférences"
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
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
                onClick = {
                    showImageSourceDialog = true
                },
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
        }
    }

    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = {
                showImageSourceDialog = false
            },
            title = {
                Text("Ajouter une photo")
            },
            text = {
                Column {
                    Button(
                        onClick = {
                            showImageSourceDialog = false
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                cameraLauncher.launch()
                            } else {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Prendre une photo")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = {
                            showImageSourceDialog = false
                            galleryLauncher.launch("image/*")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Choisir dans la galerie")
                    }
                }
            },

            // No default confirmation button.
            // The dialog actions are implemented directly inside the content.
            confirmButton = {}
        )
    }
}
