package com.marflo.basickafkawithapi.kafkaconsumer;

public class KafkaEventConsumerStart {

    static KafkaEventConsumer consumer;

    public static void main(String[] args) {
        consumer = new KafkaEventConsumer("group1", "testTopic");
        consumer.run();
    }
}
