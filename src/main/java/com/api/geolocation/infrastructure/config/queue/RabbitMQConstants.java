package com.api.geolocation.infrastructure.config.queue;

public class RabbitMQConstants {
    public static final String BRANCH_SUBSCRIPTION_QUEUE = "branch.subscription.queue";
    public static final String BRANCH_UNSUBSCRIPTION_QUEUE = "branch.unsubscription.queue";
    public static final String MEDICINE_SEARCH_QUEUE = "medicine.search.queue";
    public static final String BRANCHES_WITH_MEDICINE_QUEUE = "branches.with.medicine.queue";

    public static final String BRANCH_SUBSCRIPTION_EXCHANGE = "branch.subscription.exchange";
    public static final String BRANCH_UNSUBSCRIPTION_EXCHANGE = "branch.unsubscription.exchange";
    public static final String MEDICINE_SEARCH_EXCHANGE = "medicine.search.exchange";
    public static final String BRANCHES_WITH_MEDICINE_EXCHANGE = "branches.with.medicine.exchange";

    public static final String FANOUT_KEY = "";
}
