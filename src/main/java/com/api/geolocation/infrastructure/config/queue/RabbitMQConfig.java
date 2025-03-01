package com.api.geolocation.infrastructure.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCHES_WITH_MEDICINE_EXCHANGE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCHES_WITH_MEDICINE_QUEUE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_SUBSCRIPTION_EXCHANGE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_SUBSCRIPTION_QUEUE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_UNSUBSCRIPTION_EXCHANGE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.BRANCH_UNSUBSCRIPTION_QUEUE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.MEDICINE_SEARCH_EXCHANGE;
import static com.api.geolocation.infrastructure.config.queue.RabbitMQConstants.MEDICINE_SEARCH_QUEUE;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange branchSubscriptionExchange() {
        return new FanoutExchange(BRANCH_SUBSCRIPTION_EXCHANGE);
    }

    @Bean
    public FanoutExchange branchUnsubscriptionExchange() {
        return new FanoutExchange(BRANCH_UNSUBSCRIPTION_EXCHANGE);
    }

    @Bean
    public FanoutExchange medicineSearchExchange() {
        return new FanoutExchange(MEDICINE_SEARCH_EXCHANGE);
    }

    @Bean
    public FanoutExchange branchesWithMedicineExchange() {
        return new FanoutExchange(BRANCHES_WITH_MEDICINE_EXCHANGE);
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
    public Binding branchSubscriptionBinding(Queue branchSubscriptionQueue, FanoutExchange branchSubscriptionExchange) {
        return BindingBuilder.bind(branchSubscriptionQueue).to(branchSubscriptionExchange);
    }

    @Bean
    public Binding branchUnsubscriptionBinding(Queue branchUnsubscriptionQueue, FanoutExchange branchUnsubscriptionExchange) {
        return BindingBuilder.bind(branchUnsubscriptionQueue).to(branchUnsubscriptionExchange);
    }

    @Bean
    public Binding medicineSearchBinding(Queue medicineSearchQueue, FanoutExchange medicineSearchExchange) {
        return BindingBuilder.bind(medicineSearchQueue).to(medicineSearchExchange);
    }

    @Bean
    public Binding branchesWithMedicineBinding(Queue branchesWithMedicineQueue, FanoutExchange branchesWithMedicineExchange) {
        return BindingBuilder.bind(branchesWithMedicineQueue).to(branchesWithMedicineExchange);
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
