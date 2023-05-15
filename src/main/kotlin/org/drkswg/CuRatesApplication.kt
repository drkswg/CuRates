package org.drkswg

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main(args: Array<String>) {
    runApplication<CuRatesApplication>(*args)
}

@SpringBootApplication
@EnableScheduling
class CuRatesApplication(private val bot : Bot) : CommandLineRunner {

    override fun run(vararg args: String?) {
        try {
            val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
            botsApi.registerBot(bot)
        } catch (e: TelegramApiRequestException) {
            e.printStackTrace()
        }
    }

    @Scheduled(cron = "0 0 7 * * *")
    fun sendRateToChannel() {
        bot.sendRateToChannel()
    }
}
