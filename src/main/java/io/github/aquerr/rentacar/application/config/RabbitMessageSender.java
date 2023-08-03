package io.github.aquerr.rentacar.application.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@RequiredArgsConstructor
@Setter
public class RabbitMessageSender
{
    private final RabbitTemplate rabbitTemplate;
    private final Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    private String exchange = "";

    public void send(String routingKey, Object object)
    {
        this.rabbitTemplate.send(exchange, routingKey, jackson2JsonMessageConverter.toMessage(object, prepareMessageProperties()));
    }

    private MessageProperties prepareMessageProperties()
    {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return messageProperties;
    }
}
