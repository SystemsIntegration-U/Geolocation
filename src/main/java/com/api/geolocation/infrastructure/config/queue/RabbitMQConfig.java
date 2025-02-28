package com.api.geolocation.infrastructure.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCHES_WITH_MEDICINE_KEY;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCHES_WITH_MEDICINE_QUEUE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_EXCHANGE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_SUBSCRIPTION_KEY;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_SUBSCRIPTION_QUEUE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_UNSUBSCRIPTION_KEY;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_UNSUBSCRIPTION_QUEUE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.MEDICINE_EXCHANGE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.MEDICINE_SEARCH_KEY;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.MEDICINE_SEARCH_QUEUE;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange branchExchange() {
        return new DirectExchange(BRANCH_EXCHANGE);
    }

    @Bean
    public DirectExchange medicineExchange() {
        return new DirectExchange(MEDICINE_EXCHANGE);
    }

    @Bean
    public Queue branchSubscriptionQueue() {
        return new Queue(BRANCH_SUBSCRIPTION_QUEUE, true);
    }

    @Bean
    public Queue branchUnsubscriptionQueue() {
        return new Queue(BRANCH_UNSUBSCRIPTION_QUEUE, true);
    }

    @Bean
    public Queue medicineSearchQueue() {
        return new Queue(MEDICINE_SEARCH_QUEUE, true);
    }

    @Bean
    public Queue branchesWithMedicineQueue() {
        return new Queue(BRANCHES_WITH_MEDICINE_QUEUE, true);
    }

    @Bean
    public Binding branchSubscriptionBinding(Queue branchSubscriptionQueue, DirectExchange branchExchange) {
        return BindingBuilder.bind(branchSubscriptionQueue)
                .to(branchExchange)
                .with(BRANCH_SUBSCRIPTION_KEY);
    }

    @Bean
    public Binding branchUnsubscriptionBinding(Queue branchUnsubscriptionQueue, DirectExchange branchExchange) {
        return BindingBuilder.bind(branchUnsubscriptionQueue)
                .to(branchExchange)
                .with(BRANCH_UNSUBSCRIPTION_KEY);
    }

    @Bean
    public Binding medicineSearchBinding(Queue medicineSearchQueue, DirectExchange medicineExchange) {
        return BindingBuilder.bind(medicineSearchQueue)
                .to(medicineExchange)
                .with(MEDICINE_SEARCH_KEY);
    }

    @Bean
    public Binding branchesWithMedicineBinding(Queue branchesWithMedicineQueue, DirectExchange medicineExchange) {
        return BindingBuilder.bind(branchesWithMedicineQueue)
                .to(medicineExchange)
                .with(BRANCHES_WITH_MEDICINE_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
