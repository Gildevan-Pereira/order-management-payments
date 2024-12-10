package com.ms_payments.events;

import com.ms_payments.exception.InternalException;
import com.ms_payments.messages.MessageEnum;
import com.ms_payments.model.dto.request.PaymentReceiptDto;
import com.ms_payments.utils.JsonParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentSlipPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final  String exchange;

    private final String routingKey;

    public PaymentSlipPublisher(RabbitTemplate rabbitTemplate,
                                @Value("${spring.rabbitmq.exchanges.order_management_events}")
                                String exchange,
                                @Value("${spring.rabbitmq.routing_keys.proof_payment_routing_key}")
                                String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void send(PaymentReceiptDto dto) {
        try {
            var json = JsonParserUtil.toJson(dto);
            rabbitTemplate.convertAndSend(exchange, routingKey, json);
            log.info("PaymentSlipPublisher.send - Proof event sent | data: {}", json);
        } catch (Exception e) {
            log.error("PaymentSlipPublisher.send - Error while sending message", e);
            throw new InternalException(MessageEnum.ERROR_PUBLISHER, HttpStatus.BAD_GATEWAY);
        }
    }
}