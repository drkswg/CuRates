package org.drkswg

import org.springframework.web.client.RestTemplate
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class Bot(private val botName: String,
          botToken: String,
          private val channelId: String,
          private val exchangeApiUrl: String,
          private val exchangeApiToken: String) : TelegramLongPollingBot(botToken) {

    fun sendRateToChannel() {
        val finalText = getHumanReadableRates()
        val message = SendMessage(channelId, finalText)

        try {
            execute(message)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    private fun getHumanReadableRates() : String {
        var messageText = ""

        for (rate in getRates(exchangeApiUrl, exchangeApiToken)) {
            messageText += when (rate?.baseCode) {
                "USD" -> "USD/RUB = ${rate.conversionRates["RUB"]}\nUSD/AMD = ${rate.conversionRates["AMD"]}\n"
                "EUR" -> "EUR/RUB = ${rate.conversionRates["RUB"]}\nEUR/AMD = ${rate.conversionRates["AMD"]}\n"
                "RUB" -> "RUB/AMD = ${rate.conversionRates["AMD"]}\n"
                "GEL" -> "GEL/RUB = ${rate.conversionRates["RUB"]}"
                else -> throw UnknownCurrencyException("Unknown currency")
            }
        }

        return messageText
    }

    private fun getRates(url: String, token: String): List<ExchangeRateApiResponse?> {
        val rates = mutableListOf<ExchangeRateApiResponse?>()
        val restTemplate = RestTemplate()
        val currencies = listOf("USD", "EUR", "RUB", "GEL")

        for (currency in currencies) {
            val currencyRates = restTemplate.getForObject(
                "$url/$token/latest/$currency", ExchangeRateApiResponse::class.java
            )
            rates.add(currencyRates)
        }

        return rates
    }

    override fun getBotUsername(): String {
        return botName
    }

    override fun onUpdateReceived(update: Update?) {}
}