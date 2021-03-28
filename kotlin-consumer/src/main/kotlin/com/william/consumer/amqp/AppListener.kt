package com.william.consumer.amqp

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class AppListener {

    @RabbitListener(queues = ["my-queue"])
    fun listen(message: String) = println(message)
}