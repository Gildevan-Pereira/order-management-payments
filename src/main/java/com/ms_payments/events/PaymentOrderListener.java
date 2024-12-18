package com.ms_payments.events;

import com.ms_payments.events.rabbitmq.BaseListener;
import com.ms_payments.exception.BusinessException;
import com.ms_payments.exception.InternalException;
import com.ms_payments.model.dto.request.PaymentRequestDto;
import com.ms_payments.service.PaymentOrderService;
import com.ms_payments.utils.JsonParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentOrderListener extends BaseListener {

    private final PaymentOrderService paymentOrderService;

    protected PaymentOrderListener(
            PaymentOrderService paymentOrderService,
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.exchanges.order_management_events}")
            String exchange,
            @Value("${spring.rabbitmq.queues.order_payment.retry_queue}")
            String retryRoutingKey,
            @Value("${spring.rabbitmq.queues.order_payment.dead_queue}")
            String deadRoutingKey,
            @Value("${spring.rabbitmq.max_retry}")
            Integer maxRetry) {

        super(rabbitTemplate, exchange, retryRoutingKey, deadRoutingKey, maxRetry);
        this.paymentOrderService = paymentOrderService;
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queues.order_payment.queue}")
    public void listen(Message message) {

        try {
            PaymentRequestDto requestDto = JsonParserUtil.fromBytes(message.getBody(), PaymentRequestDto.class);

            log.info("PaymentOrderListener.listen - Data to fetch payment received | data: {}", requestDto);

            paymentOrderService.savePayment(requestDto);

        } catch (InternalException | BusinessException e) {
            log.error("PaymentOrderListener.listen - An error has occurred | error: {}", e.getMessage(), e);
            super.sendToDead(message);
        } catch (Exception e) {
            log.error("PaymentOrderListener.listen - An error has occurred | error: {}", e.getMessage(), e);
            super.sendToRetry(message);
        }
    }
}
