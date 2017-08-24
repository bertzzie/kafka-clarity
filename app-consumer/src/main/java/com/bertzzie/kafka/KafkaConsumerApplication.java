package com.bertzzie.kafka;

import com.bertzzie.dota2.model.HeroCoordinate;
import com.bertzzie.kafka.config.KafkaConsumerApplicationConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Collections;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
@SpringBootApplication
@EnableConfigurationProperties({
        KafkaConsumerApplicationConfig.class
})
public class KafkaConsumerApplication implements ApplicationRunner {
    @Autowired
    private KafkaConsumerApplicationConfig kafkaConsumerApplicationConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void consume() {
        KafkaConsumer<String, HeroCoordinate> consumer = new KafkaConsumer<>(kafkaConsumerApplicationConfig.getConsumer());
        consumer.subscribe(Collections.singleton("hero_movement"));

        while (true) {
            ConsumerRecords<String, HeroCoordinate> records = consumer.poll(100);
            for (ConsumerRecord<String, HeroCoordinate> record : records) {
                logger.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
            }
        }
    }

    @Override
    public void run(ApplicationArguments arguments) {
        consume();
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }
}
