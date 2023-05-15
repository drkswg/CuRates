package org.drkswg

import com.fasterxml.jackson.annotation.JsonProperty

data class ExchangeRateApiResponse(
    @JsonProperty("result")
    val result: String,
    @JsonProperty("documentation")
    val documentation: String,
    @JsonProperty("terms_of_use")
    val termsOfUse: String,
    @JsonProperty("time_last_update_unix")
    val timeLastUpdateUnix: Long,
    @JsonProperty("time_last_update_utc")
    val timeLastUpdateUtc: String,
    @JsonProperty("time_next_update_unix")
    val timeNextUpdateUnix: Long,
    @JsonProperty("time_next_update_utc")
    val timeNextUpdateUtc: String,
    @JsonProperty("base_code")
    val baseCode: String,
    @JsonProperty("conversion_rates")
    val conversionRates: Map<String, Double>
)