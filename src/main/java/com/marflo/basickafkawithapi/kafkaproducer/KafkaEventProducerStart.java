package com.marflo.basickafkawithapi.kafkaproducer;

import com.marflo.basickafkawithapi.dto.KafkaEvent;

public class KafkaEventProducerStart {

    public static void main(String[] args) {
        int numberOfMessages = 10;
        KafkaProducer producer = new KafkaProducer();

        for (int i = 0; i < numberOfMessages; i++) {
            KafkaEvent event = new KafkaEvent(false, "AA21"+i, "BB21"+i, "CC21"+i);
            producer.sendMessageToTopic("testTopic", event);
        }
    }
}
