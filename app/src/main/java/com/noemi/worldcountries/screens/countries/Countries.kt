package com.noemi.worldcountries.screens.countries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.noemi.worldcountries.R
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.models.DetailedCountry
import com.noemi.worldcountries.utils.CountryAppBar

@Composable
fun CountriesScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<CountriesViewModel>()
    val countriesState by viewModel.countriesState.collectAsState()
    val countryState by viewModel.countryState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        CountryAppBar(title = stringResource(id = R.string.label_countries))

        CountriesRoot(countriesState = countriesState, onSaveCountry = viewModel::saveDisplayCountry)

        countryState.selectedCountry?.let {
            CountryDialog(country = it, onDismiss = viewModel::dismissCountry)
        }
    }
}

@Composable
private fun CountriesRoot(countriesState: CountriesViewModel.CountriesState, onSaveCountry: (Country) -> Unit, modifier: Modifier = Modifier) {

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (indicator, column) = createRefs()

        when (countriesState.isLoading) {

            true -> CircularProgressIndicator(
                modifier = modifier.constrainAs(indicator) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                strokeWidth = 3.dp
            )

            else -> LazyColumn(
                contentPadding = PaddingValues(top = 8.dp, start = 8.dp, bottom = 20.dp, end = 8.dp),
                modifier = modifier.constrainAs(column) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
                }) {

                items(
                    items = countriesState.countries,
                    key = { it.capital }
                ) { country ->
                    CountryItemRow(
                        country = country,
                        onSaveCountry = onSaveCountry
                    )
                }
            }
        }
    }
}

@Composable
private fun CountryItemRow(country: Country, onSaveCountry: (Country) -> Unit, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSaveCountry(country) },
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
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun CountryDialog(country: DetailedCountry, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    Dialog(onDismissRequest = onDismiss) {

        Card(
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = country.emoji,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = modifier.width(8.dp))

                    Text(
                        text = country.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.label_continent, country.continent),
                    modifier = modifier
                        .padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.label_currency, country.currency),
                    modifier = modifier
                        .padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.label_capital, country.capital),
                    modifier = modifier
                        .padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.label_languages, country.language),
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = modifier.height(8.dp))
            }
        }
    }
}