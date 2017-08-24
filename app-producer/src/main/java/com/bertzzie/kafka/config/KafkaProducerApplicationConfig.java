package com.bertzzie.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
@Data
@ConfigurationProperties
public class KafkaProducerApplicationConfig {
    private Properties producer;
}
