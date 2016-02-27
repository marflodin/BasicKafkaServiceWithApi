package com.marflo.basickafkawithapi.kafkaconsumer;

import com.google.gson.Gson;
import com.marflo.basickafkawithapi.dto.AddedEvents;
import com.marflo.basickafkawithapi.dto.KafkaEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.util.*;

public class KafkaEventConsumer implements Runnable {

    private final KafkaConsumer<String, String> consumer;
    private final List<String> topics;
    private final int id;
    private Gson gson = new Gson();
    private AddedEvents eventsHolder = AddedEvents.getInstance();

    public KafkaEventConsumer(int id, String groupId, List<String> topics) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest");
        this.topics = topics;
        this.id = id;
        this.consumer = new KafkaConsumer<>(props);
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(topics);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (ConsumerRecord<String, String> record : records) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("value", record.value());
                    try {
                        KafkaEvent event = gson.fromJson(record.value(), KafkaEvent.class);
                        if (event.getIsRemoved()) {
                            System.out.println("remove object from list: " + event.getObjectA());
                            eventsHolder.removeStringFromObjectA(event.getObjectA());
                            System.out.println("l: " + eventsHolder.getObjectA().size());
                        } else {
                            System.out.println("add object from list: " + event.getObjectA());
                            eventsHolder.addStringToObjectA(event.getObjectA());
                            System.out.println("l2: " + eventsHolder.getObjectA().size());
                        }
                    } catch (Exception e) {
                    //TODO: handle exception
                    }
                }
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            consumer.close();
        }
    }

    public void shutdown() {
        consumer.wakeup();
    }
}

