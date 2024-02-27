package com.noemi.worldcountries.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.noemi.worldcountries.R
import com.noemi.worldcountries.models.Country
import com.noemi.worldcountries.models.DetailedCountry
import com.noemi.worldcountries.screens.viewmodel.CountryViewModel
import com.noemi.worldcountries.ui.theme.philosopherFamily

@Composable
fun HomeScreen(viewModel: CountryViewModel) {

    val countriesState by viewModel.countriesState.collectAsState()
    val countryState by viewModel.countryState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.label_countries),
                    fontFamily = philosopherFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            },
            elevation = 6.dp,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        )

        MainContent(countriesState = countriesState, viewModel = viewModel)

        countryState.selectedCountry?.let {
            CountryDialog(country = it) {
                viewModel.dismissCountry()
            }
        }
    }
}

@Composable
private fun MainContent(countriesState: CountryViewModel.CountriesState, viewModel: CountryViewModel) {

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (indicator, column) = createRefs()

        when (countriesState.isLoading) {

            true -> CircularProgressIndicator(
                modifier = Modifier.constrainAs(indicator) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
                },
                color = MaterialTheme.colorScheme.secondaryContainer,
                strokeWidth = 3.dp
            )

            else -> LazyColumn(
                contentPadding = PaddingValues(top = 8.dp, start = 8.dp, bottom = 20.dp, end = 8.dp),
                modifier = Modifier.constrainAs(column) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
                }) {

                items(countriesState.countries) { country ->
                    CountryItem(
                        country = country,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
private fun CountryItem(country: Country, viewModel: CountryViewModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.saveDisplayCountry(country) },
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
private fun CountryDialog(country: DetailedCountry, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {

        Card(
            modifier = Modifier
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = country.emoji,
                        fontFamily = philosopherFamily,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        fontSize = 42.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = country.name,
                        fontFamily = philosopherFamily,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        fontSize = 42.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Continent: ${country.continent}",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp),
                    fontFamily = philosopherFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Currency: ${country.currency}",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp),
                    fontFamily = philosopherFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Capital: ${country.capital}",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp),
                    fontFamily = philosopherFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Language(s): ${country.language}",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp, end = 16.dp),
                    fontFamily = philosopherFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}