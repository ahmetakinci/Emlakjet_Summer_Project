package com.example.emlakjet_summer_project.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue advertStatusChangeQueue() {
        return new Queue("advert_status_changes");
    }
}
