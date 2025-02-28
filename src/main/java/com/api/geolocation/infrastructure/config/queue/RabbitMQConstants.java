package com.api.geolocation.infrastructure.config.queue;

public class RabbitMQConstants {
    public static final String BRANCH_SUBSCRIPTION_QUEUE = "branch.subscription.queue";
    public static final String BRANCH_UNSUBSCRIPTION_QUEUE = "branch.unsubscription.queue";
    public static final String MEDICINE_SEARCH_QUEUE = "medicine.search.queue";
    public static final String BRANCHES_WITH_MEDICINE_QUEUE = "branches.with.medicine.queue";

    public static final String BRANCH_EXCHANGE = "branch.exchange";
    public static final String MEDICINE_EXCHANGE = "medicine.exchange";

    public static final String BRANCH_SUBSCRIPTION_KEY = "branch.subscription";
    public static final String BRANCH_UNSUBSCRIPTION_KEY = "branch.unsubscription";
    public static final String MEDICINE_SEARCH_KEY = "medicine.search";
    public static final String BRANCHES_WITH_MEDICINE_KEY = "branches.with.medicine";
}
