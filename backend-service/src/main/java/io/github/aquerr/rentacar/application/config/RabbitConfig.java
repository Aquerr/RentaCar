package io.github.aquerr.rentacar.application.config;

import io.github.aquerr.rentacar.application.mail.listener.RabbitSendMailListener;
import io.github.aquerr.rentacar.application.rabbit.RabbitMessageSender;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig
{
    @Value("${rabbit.exchange.name}")
    private String rentacarExchangeName;

    @Value("${rabbit.queue.mail-send.name}")
    private String mailSendQueueName;

    @Value("${rabbit.queue.account-activation-request.name}")
    private String accountActivationQueueName;

    @Bean
    Queue sendMailQueue()
    {
        return new Queue(mailSendQueueName, true);
    }

    @Bean
    Queue accountActivationRequestQueue()
    {
        return new Queue(accountActivationQueueName, true);
    }

    @Bean
    TopicExchange rentacarExchange()
    {
        return new TopicExchange(rentacarExchangeName, true, false);
    }

    @Bean
    Binding mailSendQueueBinding(Queue sendMailQueue, TopicExchange rentacarExchange)
    {
        return BindingBuilder.bind(sendMailQueue).to(rentacarExchange).with("mail.send.request");
    }

    @Bean
    Binding accountActivationRequestQueueBinding(Queue accountActivationRequestQueue, TopicExchange rentacarExchange)
    {
        return BindingBuilder.bind(accountActivationRequestQueue).to(rentacarExchange).with("account.activation.request");
    }

    @Bean
    SimpleMessageListenerContainer mailListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter mailMessageListenerAdapter)
    {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(mailSendQueueName);
        container.setMessageListener(mailMessageListenerAdapter);
        return container;
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerAdapter mailMessageListenerAdapter(RabbitSendMailListener rabbitSendMailListener)
    {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(rabbitSendMailListener, "process");
        messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter());
        return messageListenerAdapter;
    }

    @Bean
    RabbitMessageSender rabbitMessageSender(RabbitTemplate rabbitTemplate, Jackson2JsonMessageConverter jackson2JsonMessageConverter)
    {
        RabbitMessageSender rabbitMessageSender = new RabbitMessageSender(rabbitTemplate, jackson2JsonMessageConverter);
        rabbitMessageSender.setExchange(rentacarExchangeName);
        return rabbitMessageSender;
    }
}
