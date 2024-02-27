package com.noemi.worldcountries.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.noemi.worldcountries.R
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.screens.viewmodel.FavoritesViewModel
import com.noemi.worldcountries.ui.theme.philosopherFamily
import kotlinx.coroutines.delay


@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel) {
    val favoritesState by viewModel.favoriteCountriesState.collectAsState()
    val countryState by viewModel.countryState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.label_favorites),
                    fontFamily = philosopherFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            },
            elevation = 6.dp,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        )

        CountrySearchComponent(viewModel = viewModel)

        FavoriteComponent(countries = favoritesState)

        countryState.country?.let { country ->
            SearchedCountryDialog(country = country) {
                viewModel.dismissCountry()
                viewModel.updateSearchedCountryName("")
            }
        }
    }
}

@Composable
private fun CountrySearchComponent(viewModel: FavoritesViewModel) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        delay(120)
        focusRequester.requestFocus()
    }

    OutlinedTextField(
        modifier = Modifier
            .focusRequester(focusRequester = focusRequester)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp),
        value = viewModel.searchedCountryName,
        onValueChange = { countryName -> viewModel.searchingCountry(countryName) },
        label = { Text(text = stringResource(id = R.string.label_country)) },
        placeholder = { Text(text = stringResource(id = R.string.label_search_hint)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.scrim,
            unfocusedBorderColor = MaterialTheme.colorScheme.scrim,
            cursorColor = MaterialTheme.colorScheme.scrim
        )
    )
}

@Composable
private fun FavoriteComponent(countries: List<Country>) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val column = createRef()

        LazyColumn(
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 20.dp, bottom = 20.dp),
            modifier = Modifier.constrainAs(column) {
                linkTo(parent.start, parent.end)
                linkTo(parent.top, parent.bottom)
                height = Dimension.fillToConstraints
            }
        ) {

            items(countries) { country ->
                CountryItem(country = country)
            }
        }
    }
}

@Composable
private fun CountryItem(country: Country) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = country.emoji,
            modifier = Modifier
                .size(36.dp)
                .padding(start = 8.dp)
        )

        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = country.name,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp),
                fontFamily = philosopherFamily,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                style = androidx.compose.material.MaterialTheme.typography.h1
            )

            Text(
                text = country.capital,
                modifier = Modifier
                    .padding(start = 16.dp, top = 6.dp, bottom = 8.dp),
                fontSize = 16.sp,
                fontFamily = philosopherFamily,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                style = androidx.compose.material.MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
private fun SearchedCountryDialog(country: Country, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {

        Card(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = country.emoji,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(start = 8.dp)
                )

                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = country.name,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp),
                        fontFamily = philosopherFamily,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        style = androidx.compose.material.MaterialTheme.typography.h1
                    )

                    Text(
                        text = country.capital,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 6.dp, bottom = 8.dp),
                        fontSize = 16.sp,
                        fontFamily = philosopherFamily,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic,
                        style = androidx.compose.material.MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}