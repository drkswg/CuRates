package org.drkswg

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
data class Config(@Value("\${exchange.api.url}") private val exchangeApiUrl: String,
                  @Value("\${exchange.api.token}") private val exchangeApiToken: String,
                  @Value("\${telegram.bot.name}") private val botName: String,
                  @Value("\${telegram.bot.token}") private val botToken: String,
                  @Value("\${telegram.channel.id}") private val channelId: String)  {

    @Bean
    fun getBot() : Bot {
        return Bot(botName, botToken, channelId, exchangeApiUrl, exchangeApiToken)
    }
}