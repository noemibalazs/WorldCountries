package com.noemi.worldcountries.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.noemi.worldcountries.R
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.screens.viewmodel.FavoritesViewModel
import com.noemi.worldcountries.utils.CountryAppBar
import kotlinx.coroutines.delay


@Composable
fun FavoritesScreen() {
    val viewModel = hiltViewModel<FavoritesViewModel>()
    val favoritesState by viewModel.favoriteCountriesState.collectAsState()
    val countryState by viewModel.countryState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        CountryAppBar(title = stringResource(id = R.string.label_favorites))

        CountrySearchTextField(
            searchedCountry = viewModel.searchedCountryName,
            onUpdateSearchedCountry = viewModel::updateSearchedCountryName,
            searchError = viewModel.isSearchError,
            onUpdateError = viewModel::updateErrorState,
            onFindCountry = viewModel::findCountryByName
        )

        FavoriteScreenRoot(countries = favoritesState)

        countryState.country?.let { country ->
            DisplaySearchedCountryDialog(country = country, onDismiss = {
                viewModel.dismissCountry()
                viewModel.updateSearchedCountryName("")
            })
        }
    }
}

@Composable
private fun CountrySearchTextField(
    searchedCountry: String,
    onUpdateSearchedCountry: (String) -> Unit,
    searchError: Boolean,
    onUpdateError: (Boolean) -> Unit,
    onFindCountry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(120)
        focusRequester.requestFocus()
    }

    OutlinedTextField(
        modifier = modifier
            .focusRequester(focusRequester = focusRequester)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp),
        value = searchedCountry,
        onValueChange = { countryName ->
            onUpdateSearchedCountry(countryName)
        },
        label = { Text(text = stringResource(id = R.string.label_country)) },
        placeholder = {
            Text(
                text = stringResource(id = R.string.label_search_hint),
                style = MaterialTheme.typography.titleMedium
            )
        },
        isError = searchError,
        supportingText = {
            if (searchError)
                Text(text = stringResource(id = R.string.label_error_no_saved_result))
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(onDone = {
            onFindCountry.invoke()
        }),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        textStyle = MaterialTheme.typography.titleMedium,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = modifier.clickable {
                    keyboardController?.hide()
                    onUpdateSearchedCountry.invoke("")
                    onUpdateError.invoke(false)
                }
            )
        }
    )
}

@Composable
private fun FavoriteScreenRoot(countries: List<Country>, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val column = createRef()

        LazyColumn(
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 20.dp, bottom = 20.dp),
            modifier = modifier.constrainAs(column) {
                linkTo(parent.start, parent.end)
                linkTo(parent.top, parent.bottom)
                height = Dimension.fillToConstraints
            }
        ) {

            items(
                items = countries,
                key = { it.capital }
            ) { country ->
                FavoriteCountryItemRow(country = country)
            }
        }
    }
}

@Composable
private fun FavoriteCountryItemRow(country: Country, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = country.emoji,
            modifier = modifier
                .size(36.dp)
                .padding(start = 8.dp)
        )

        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = country.name,
                modifier = modifier
                    .padding(start = 16.dp, top = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = country.capital,
                modifier = modifier
                    .padding(start = 16.dp, top = 6.dp, bottom = 8.dp),
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun DisplaySearchedCountryDialog(country: Country, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    Dialog(onDismissRequest = onDismiss) {

        Card(
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = country.emoji,
                    modifier = modifier
                        .size(36.dp)
                        .padding(start = 8.dp)
                )

                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = country.name,
                        modifier = modifier
                            .padding(start = 16.dp, top = 8.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = country.capital,
                        modifier = modifier
                            .padding(start = 16.dp, top = 6.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}