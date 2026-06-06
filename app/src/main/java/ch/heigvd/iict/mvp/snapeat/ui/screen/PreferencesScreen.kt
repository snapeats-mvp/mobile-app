@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)

package ch.heigvd.iict.mvp.snapeat.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.heigvd.iict.mvp.snapeat.model.UserPreferences
import ch.heigvd.iict.mvp.snapeat.model.DietaryRestriction
import ch.heigvd.iict.mvp.snapeat.theme.BackgroundBeige

@Composable
fun PreferencesScreen(
    currentPreferences: UserPreferences,
    onPreferencesChanged: (UserPreferences) -> Unit,
    onCloseClick: () -> Unit
) {
    var selectedDietary by remember { mutableStateOf(currentPreferences.dietaryRestrictions.toMutableSet()) }
    var selectedAllergies by remember { mutableStateOf(currentPreferences.allergies.toMutableSet()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBeige)
    ) {
        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Dietary Restrictions
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Régime alimentaire",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DietaryRestriction.values().forEach { diet ->
                            FilterChip(
                                selected = selectedDietary.contains(diet),
                                onClick = {
                                    selectedDietary = selectedDietary.toMutableSet().apply {
                                        if (contains(diet)) {
                                            remove(diet)
                                        } else {
                                            add(diet)
                                        }
                                    }
                                },
                                label = {
                                    Text(
                                        text = diet.name
                                            .replace("_", " ")
                                            .lowercase()
                                            .replaceFirstChar { it.uppercase() }
                                    )
                                },
                                shape = MaterialTheme.shapes.medium,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.error,
                                    selectedLabelColor = MaterialTheme.colorScheme.onError
                                )
                            )
                        }
                    }
                }
            }

            // Allergies
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Allergies et intolérances",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    val allergiesList = listOf("Gluten", "Lactose", "Arachides", "Fruits à coque")

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        allergiesList.forEach { allergy ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Checkbox(
                                    checked = selectedAllergies.contains(allergy),
                                    onCheckedChange = {
                                        selectedAllergies = selectedAllergies.toMutableSet().apply {
                                            if (it) {
                                                add(allergy)
                                            } else {
                                                remove(allergy)
                                            }
                                        }
                                    }
                                )
                                Text(
                                    text = allergy,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // Save Button
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onCloseClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Annuler",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = {
                            val updated = currentPreferences.copy(
                                dietaryRestrictions = selectedDietary.toList(),
                                allergies = selectedAllergies.toList()
                            )
                            onPreferencesChanged(updated)
                            onCloseClick()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(
                            text = "Enregistrer",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
