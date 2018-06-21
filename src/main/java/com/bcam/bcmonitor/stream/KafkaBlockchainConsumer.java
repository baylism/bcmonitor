package com.bcam.bcmonitor.stream;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

public class KafkaBlockchainConsumer {

    private Consumer consumer;

    public KafkaBlockchainConsumer() {
        Properties config = new Properties();
        try {
            config.put("client.id", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        config.put("bootstrap.servers", "localhost:9092");
        config.put("acks", "0");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // create the producer
        consumer = new KafkaConsumer(config);

    }

    public void listen(ArrayList<String> topics) {

        consumer.subscribe(topics);
        // while (flag) {
        //     // maximum amount of time that the consumer will block while it awaits records at the current position
        //     ConsumerRecords<K, V> records = consumer.poll(Long.MAX_VALUE);
        //     process(records); // application-specific processing
        //     consumer.commitSync();
        // }

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (ConsumerRecord<String, String> record : records)
                    System.out.println(record.offset() + ":" + record.value());
            }
        } catch (WakeupException e) {   // ignore for shutdown
        } finally {
            consumer.close();
        }
    }

    public void stop() {
        consumer.wakeup();
    }
}
