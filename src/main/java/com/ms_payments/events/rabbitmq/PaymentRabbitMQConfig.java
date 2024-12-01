package com.ms_payments.events.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRabbitMQConfig {

    private final String exchange;
    private final String routingKey;
    private final String queue;
    private final String retryQueue;
    private final String deadQueue;

    public PaymentRabbitMQConfig(
            @Value("${spring.rabbitmq.exchanges.order_management_events}") String exchange,
            @Value("${spring.rabbitmq.routing_keys.created_order_routing_key}") String routingKey,
            @Value("${spring.rabbitmq.queues.fetch_payment.queue}") String queue,
            @Value("${spring.rabbitmq.queues.fetch_payment.retry_queue}") String retryQueue,
            @Value("${spring.rabbitmq.queues.fetch_payment.dead_queue}") String deadQueue
    ) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.queue = queue;
        this.retryQueue = retryQueue;
        this.deadQueue = deadQueue;
    }

    @Bean
    public TopicExchange createExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable(retryQueue)
                .withArgument("x-dead-letter-exchange", exchange)
                .withArgument("x-dead-letter-routing-key", queue)
                .withArgument("x-message-ttl", 5)
                .build();
    }

    @Bean
    public Queue deadQueue() {
        return new Queue(deadQueue);
    }

    @Bean
    public Binding queueBinding() {
        return BindingBuilder.bind(queue())
                .to(createExchange())
                .with(routingKey);
    }

    @Bean
    public Binding retryQueueBinding() {
        return BindingBuilder.bind(retryQueue())
                .to(createExchange())
                .with(retryQueue);
    }

    @Bean
    public Binding deadQueueBinding() {
        return BindingBuilder.bind(deadQueue())
                .to(createExchange())
                .with(deadQueue);
    }
}