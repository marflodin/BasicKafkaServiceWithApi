package com.marflo.basickafkawithapi.kafkaconsumer;

import com.google.gson.Gson;
import com.marflo.basickafkawithapi.dto.AddedEvents;
import com.marflo.basickafkawithapi.dto.KafkaEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import java.util.*;

public class KafkaEventConsumer implements Runnable {

    private final KafkaConsumer<String, String> consumer;
    private final String topic;
    private Gson gson = new Gson();
    private AddedEvents eventsHolder = AddedEvents.getInstance();

    public KafkaEventConsumer(int id, String groupId, String topic) {
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
        this.topic = topic;
        this.consumer = new KafkaConsumer<>(props);
    }

    @Override
    public void run() {
        try {
            TopicPartition partition0 = new TopicPartition(topic, 0);
            consumer.assign(Arrays.asList(partition0));
            consumer.seekToBeginning(partition0);

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
                            eventsHolder.removeStringFromObjectA(event.getObjectA());
                            eventsHolder.removeStringFromObjectB(event.getObjectB());
                            eventsHolder.removeStringFromObjectC(event.getObjectC());
                        } else {
                            System.out.println("add object from list: " + event.getObjectA());
                            eventsHolder.addStringToObjectA(event.getObjectA());
                            System.out.println("l2: " + eventsHolder.getObjectA().size());
                            eventsHolder.addStringToObjectB(event.getObjectB());
                            eventsHolder.addStringToObjectC(event.getObjectC());
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

