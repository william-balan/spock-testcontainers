package com.william.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppConsumer

fun main(args: Array<String>) {
    runApplication<AppConsumer>(*args)
}
