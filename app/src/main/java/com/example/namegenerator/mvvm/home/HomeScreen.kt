package com.example.namegenerator.mvvm.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.namegenerator.models.Baby

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val baby by viewModel.babyState.collectAsState()

    HomeScreenContent(
        baby = baby,
        onGetBabyClick = viewModel::getRandomBaby
    )
}

@Composable
private fun HomeScreenContent(
    baby: Baby?,
    onGetBabyClick: (Baby.Gender) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Home") })
        },
        backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                BabyButton(text = "Male") {
                    onGetBabyClick(Baby.Gender.MALE)
                }

                Spacer(modifier = Modifier.width(10.dp))

                BabyButton(text = "Female") {
                    onGetBabyClick(Baby.Gender.FEMALE)
                }
            }

            baby?.let {
                Spacer(modifier = Modifier.height(30.dp))
                
                CardBaby(
                    modifier = Modifier.padding(16.dp),
                    baby = it
                )
            }
        }
    }
}

@Composable
private fun CardBaby(
    modifier: Modifier = Modifier,
    baby: Baby
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = when(baby.gender) {
            Baby.Gender.MALE -> Color.Blue.copy(alpha = 0.3f)
            Baby.Gender.FEMALE -> Color.Yellow.copy(alpha = 0.3f)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ) {
            BabyRow(title = "Name:", subtitle = baby.name)
            BabyRow(title = "Gender:", subtitle = baby.gender.gender)
            BabyRow(title = "Ethnicity:", subtitle = baby.ethnicity)
            BabyRow(title = "Year of birth:", subtitle = baby.birth)
            BabyRow(title = "N. babies with name:", subtitle = baby.numberSameName)
            BabyRow(title = "Ranking:", subtitle = baby.rank)
        }
    }
}

@Composable
private fun BabyRow(
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(text = subtitle)
    }
}

@Composable
private fun BabyButton(
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardBaby() {
    CardBaby(
        baby = Baby(
            "2016",
            Baby.Gender.FEMALE,
            "ASIAN AND PACIFIC ISLANDER",
            "Olivia",
            "172",
            "1"
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    HomeScreenContent(
        Baby(
            "2016",
            Baby.Gender.FEMALE,
            "ASIAN AND PACIFIC ISLANDER",
            "Olivia",
            "172",
            "1"
        ),
        onGetBabyClick = {}
    )
}