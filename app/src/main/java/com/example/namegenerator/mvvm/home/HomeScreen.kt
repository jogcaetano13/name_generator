package com.example.namegenerator.mvvm.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.namegenerator.models.Baby
import com.example.namegenerator.state.ResponseState

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val baby by viewModel.babyState.collectAsState()
    val ethnicities by viewModel.ethnicities.collectAsState()
    val ethnicitySelected by viewModel.ethnicitySelected.collectAsState()
    val babiesState by viewModel.babiesState.collectAsState()
    val genderSelected by viewModel.babyGender.collectAsState()
    val error by viewModel.toastError.collectAsState()

    error?.let {
        AlertDialog(
            onDismissRequest = viewModel::dismissError,
            title = { Text(text = "Error") },
            text = { Text(text = it) },
            confirmButton = {
                Button(onClick = viewModel::dismissError) {
                    Text(text = "Ok")
                }
            }
        )
    }

    HomeScreenContent(
        baby = baby,
        onGetBabyClick = viewModel::getRandomBaby,
        updateGender = viewModel::updateBabyGender,
        ethnicities = ethnicities,
        ethnicitySelected = ethnicitySelected,
        updateSelected = viewModel::updateEthnicitySelected,
        babiesState = babiesState,
        genderSelected = genderSelected
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HomeScreenContent(
    baby: Baby?,
    onGetBabyClick: () -> Unit,
    updateGender: (Baby.Gender) -> Unit,
    ethnicities: List<String>,
    ethnicitySelected: String?,
    updateSelected: (String) -> Unit,
    babiesState: ResponseState<List<Baby>>,
    genderSelected: Baby.Gender?
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var buttonsEnabled by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Home") })
        },
        backgroundColor = Color.LightGray
    ) {
        when(babiesState) {
            ResponseState.Empty -> {}
            is ResponseState.Error -> Text(text = babiesState.error)
            ResponseState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Getting babies...",
                        textAlign = TextAlign.Center
                    )
                }
            }
            is ResponseState.Success -> {
                buttonsEnabled = true

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        BabyButton(
                            text = "Male",
                            isSelected = genderSelected == Baby.Gender.MALE,
                            enabled = buttonsEnabled
                        ) {
                            updateGender(Baby.Gender.MALE)
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        BabyButton(
                            text = "Female",
                            isSelected = genderSelected == Baby.Gender.FEMALE,
                            enabled = buttonsEnabled
                        ) {
                            updateGender(Baby.Gender.FEMALE)
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))

                    Column {
                        OutlinedTextField(
                            value = ethnicitySelected ?: "Select ethnicity",
                            onValueChange = {},
                            enabled = false,
                            trailingIcon = {
                                IconButton(onClick = { isExpanded = !isExpanded }) {
                                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Ethnicity")
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledTextColor = MaterialTheme.colors.onSurface,
                                disabledBorderColor = MaterialTheme.colors.primary,
                                disabledTrailingIconColor = MaterialTheme.colors.onSurface
                            )
                        )

                        DropdownMenu(
                            expanded = isExpanded, onDismissRequest = { isExpanded = false }
                        ) {
                            ethnicities.forEach {
                                DropdownMenuItem(onClick = {
                                    updateSelected(it)
                                    isExpanded = false
                                }) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                if (ethnicitySelected == it)
                                                    Color.LightGray
                                                else
                                                    Color.Transparent
                                            )
                                            .padding(10.dp),
                                        text = it
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    BabyButton(
                        text = "Generate",
                        enabled = buttonsEnabled,
                        onClick = onGetBabyClick
                    )

                    baby?.let {
                        AnimatedContent(targetState = baby) {
                            Spacer(modifier = Modifier.height(30.dp))

                            CardBaby(
                                modifier = Modifier.padding(16.dp),
                                baby = it
                            )
                        }
                    }
                }
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
    enabled: Boolean,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected)
                Color.Gray
            else
                MaterialTheme.colors.primary
        )
    ) {
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
        baby = Baby(
            "2016",
            Baby.Gender.FEMALE,
            "ASIAN AND PACIFIC ISLANDER",
            "Olivia",
            "172",
            "1"
        ),
        onGetBabyClick = {},
        updateGender = {},
        ethnicities = emptyList(),
        ethnicitySelected = null,
        updateSelected = {},
        babiesState = ResponseState.Success(emptyList()),
        genderSelected = Baby.Gender.MALE
    )
}