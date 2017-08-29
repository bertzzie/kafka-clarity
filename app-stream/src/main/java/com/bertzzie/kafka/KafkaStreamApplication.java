package com.bertzzie.kafka;

import com.bertzzie.dota2.model.HeroCoordinate;
import com.bertzzie.kafka.config.KafkaStreamApplicationConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Arrays;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
@SpringBootApplication
@EnableConfigurationProperties({
        KafkaStreamApplicationConfig.class
})
public class KafkaStreamApplication implements ApplicationRunner {
    @Autowired
    private KafkaStreamApplicationConfig kafkaStreamApplicationConfig;

    private String TOPIC_FROM = "hero_movement";
    private String TOPIC_TO   = "hero_movement_central";

    public void stream() {
        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, HeroCoordinate> coordinates = builder.stream(TOPIC_FROM);

        KTable<String, Long> centralCoordinate = coordinates
                .map((key, value) -> new KeyValue<>(value.getName(), value.getCentralCoordinateString()))
                .groupBy((key, value) -> value, Serdes.String(), Serdes.String())
                .count("CoordinateCount");

        centralCoordinate.to(Serdes.String(), Serdes.Long(), TOPIC_TO);

        KafkaStreams streams = new KafkaStreams(builder, kafkaStreamApplicationConfig.getStreams());
        streams.start();
    }

    @Override
    public void run(ApplicationArguments arguments) { stream(); }

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamApplication.class, args);
    }
}
