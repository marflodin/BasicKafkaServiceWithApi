package com.marflo.basickafkawithapi.kafkaconsumer;

import java.util.ArrayList;
import java.util.List;

public class KafkaEventConsumerStart {

    static KafkaEventConsumer consumer;
    static List<String> TEST_TOPIC = new ArrayList<String>(){{add("testTopic");}};

    public static void main(String[] args) {
        consumer = new KafkaEventConsumer(1, "group1000", TEST_TOPIC);
        consumer.run();
    }
}
