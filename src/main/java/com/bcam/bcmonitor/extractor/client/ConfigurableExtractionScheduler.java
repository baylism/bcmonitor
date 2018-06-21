package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.stream.KafkaBlockchainProducer;

import java.net.UnknownHostException;

/**
 * Schedules extraction calls using properties file
 *
 * see http://www.baeldung.com/spring-scheduled-tasks
 */
public class ConfigurableExtractionScheduler {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableExtractionScheduler sc = new ConfigurableExtractionScheduler();

        KafkaBlockchainProducer producer = new KafkaBlockchainProducer();

        for (int i = 0; i < 100; i++)
            producer.sendData("transactions", Integer.toString(i), Integer.toString(i));
    }


}
