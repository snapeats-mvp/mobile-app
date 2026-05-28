package ch.heigvd.iict.mvp.snapeat.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.heigvd.iict.mvp.snapeat.theme.BackgroundBeige

data class GlossaryEntry(
    val term: String,
    val description: String,
    val category: String
)

@Composable
fun CookingGlossaryScreen(onCloseClick: () -> Unit) {
    val glossaryEntries = listOf(
        GlossaryEntry(
            term = "Blanchir",
            description = "Plonger brièvement un aliment dans l'eau bouillante puis dans l'eau froide pour stopper la cuisson.",
            category = "Cuisson"
        ),
        GlossaryEntry(
            term = "Cleiter",
            description = "Couper finement en petits morceaux réguliers, généralement des herbes ou des oignons.",
            category = "Découpe"
        ),
        GlossaryEntry(
            term = "Déglacer",
            description = "Verser un liquide dans une casserole chaude pour dissoudre les sucs caramélisés.",
            category = "Technique"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBeige)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Glossaire de cuisine",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Fermer"
                )
            }
        }

        // Search
        var searchQuery by remember { mutableStateOf("") }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text("Rechercher un terme...")
            },
            shape = MaterialTheme.shapes.medium,
            singleLine = true
        )

        // Glossary List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(glossaryEntries.filter {
                it.term.contains(searchQuery, ignoreCase = true)
            }) { entry ->
                GlossaryCard(entry = entry)
            }
        }
    }
}

@Composable
fun GlossaryCard(entry: GlossaryEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
        ) {
            Text(
                text = entry.term,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = entry.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier.wrapContentSize(),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
            ) {
                Text(
                    text = entry.category,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}
