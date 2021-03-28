package com.william.consumer.amqp

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    @Bean
    fun myQueue() = Queue("my-queue", false)

    @Bean
    fun myExchange() = DirectExchange("my-exchange")

    @Bean
    fun bindQueueToExchange() = BindingBuilder
            .bind(myQueue())
            .to(myExchange())
            .with("my-routing-key")
}
