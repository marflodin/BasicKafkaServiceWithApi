package com.marflo.basickafkawithapi.kafkaproducer;

import com.google.gson.Gson;
import com.marflo.basickafkawithapi.dto.KafkaEvent;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class KafkaProducer {

    private Producer<String, String> producer;
    private Gson gson = new Gson();

    public KafkaProducer() {
        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);
        producer = new Producer<String, String>(config);
    }

    public void sendMessageToTopic(String topic, KafkaEvent message) {

        String messageFromJson = gson.toJson(message);
        KeyedMessage<String, String> data =
                new KeyedMessage<String, String>(topic, messageFromJson);
        producer.send(data);
    }
}
