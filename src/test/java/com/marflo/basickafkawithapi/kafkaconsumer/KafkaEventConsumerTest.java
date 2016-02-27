package com.marflo.basickafkawithapi.kafkaconsumer;

import com.marflo.basickafkawithapi.dto.AddedEvents;
import com.marflo.basickafkawithapi.dto.KafkaEvent;
import com.marflo.basickafkawithapi.kafkaproducer.KafkaProducer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KafkaEventConsumerTest {

    KafkaProducer producer;
    AddedEvents eventsHolder = AddedEvents.getInstance();

    @Before
    public void setup() {
        producer = new KafkaProducer();
    }

    @Test
    public void checkThatMessagesAreConsumedAndStored() throws InterruptedException {
        generateData(10);
        Thread.sleep(100);
        assertEquals(1, eventsHolder.getObjectA().size());
    }

    private void generateData(int numberOfMessages) {
        for (int i = 0; i < numberOfMessages; i++) {
            KafkaEvent event = new KafkaEvent(false, "AA11"+i, "BB11"+i, "CC11"+i);
            producer.sendMessageToTopic("testTopic", event);
        }
    }
}
