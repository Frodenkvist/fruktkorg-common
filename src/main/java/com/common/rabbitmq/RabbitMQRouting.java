package com.common.rabbitmq;

public class RabbitMQRouting {
    private RabbitMQRouting() {}

    public enum Exchange {
        PERSON
    }

    public enum Person {
        CREATE, DELETE
    }
}
