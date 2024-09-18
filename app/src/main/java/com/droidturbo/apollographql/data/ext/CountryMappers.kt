package com.droidturbo.apollographql.data.ext

import com.droidturbo.CountriesQuery
import com.droidturbo.CountryQuery
import com.droidturbo.apollographql.data.model.DetailedCountry
import com.droidturbo.apollographql.data.model.SimpleCountry

fun CountryQuery.Country.toDetailedCountry(): DetailedCountry = DetailedCountry(
    code = code,
    name = name,
    emoji = emoji,
    capital = capital ?: "No capital",
    currency = currency ?: "No currency",
    languages = languages.mapNotNull { it.name },
    continent = continent.name
)


fun CountriesQuery.Country.toSimpleCountry(): SimpleCountry = SimpleCountry(
    code = code,
    name = name,
    emoji = emoji,
    capital = capital ?: "No capital",
)