package com.bertzzie.kafka.producer;

import com.bertzzie.dota2.event.OnHeroMoving;
import com.bertzzie.dota2.model.HeroCoordinate;
import lombok.Builder;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.processor.runner.Context;

import javax.annotation.PreDestroy;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
@Builder
public class HeroMovingKafkaProcessor {
    private Producer<String, HeroCoordinate> producer;
    private String gameID;

    private final String HERO_MOVEMENT_TOPIC = "hero_movement";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PreDestroy
    private void shutDown() {
        this.producer.close();
    }

    @OnHeroMoving
    public void onHeroMoving(Context context, HeroCoordinate hero) {
        logger.info("Sending data to kafka: " + hero.toString() + " with key = " + gameID);

        this.producer.send(new ProducerRecord<>(HERO_MOVEMENT_TOPIC, gameID, hero));
    }
}
