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
public class CreatedOrderListener extends BaseListener {

    private final PaymentOrderService paymentOrderService;

    protected CreatedOrderListener(
            PaymentOrderService paymentOrderService,
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.exchanges.order_management_events}")
            String exchange,
            @Value("${spring.rabbitmq.queues.fetch_payment.retry_queue}")
            String retryRoutingKey,
            @Value("${spring.rabbitmq.queues.fetch_payment.dead_queue}")
            String deadRoutingKey,
            @Value("${spring.rabbitmq.max_retry}")
            Integer maxRetry) {

        super(rabbitTemplate, exchange, retryRoutingKey, deadRoutingKey, maxRetry);
        this.paymentOrderService = paymentOrderService;
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queues.fetch_payment.queue}")
    public void listen(Message message) {

        try {
            PaymentRequestDto requestDto = JsonParserUtil.fromBytes(message.getBody(), PaymentRequestDto.class);

            log.info("CreatedOrderListener.listen - Data to fetch payment received | data: {}", requestDto);

            paymentOrderService.savePayment(requestDto);

        } catch (InternalException | BusinessException e) {
            log.error("CreatedOrderListener.listen - An error has occurred | error: {}", e.getMessage(), e);
            super.sendToDead(message);
        } catch (Exception e) {
            log.error("CreatedOrderListener.listen - An error has occurred | error: {}", e.getMessage(), e);
            super.sendToRetry(message);
        }
    }
}
