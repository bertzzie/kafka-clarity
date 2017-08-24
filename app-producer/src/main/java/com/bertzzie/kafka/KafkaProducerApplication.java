package com.bertzzie.kafka;

import com.bertzzie.kafka.config.KafkaProducerApplicationConfig;
import com.bertzzie.kafka.producer.HeroMovingKafkaProcessor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.source.MappedFileSource;
import skadistats.clarity.source.Source;

import java.io.File;
import java.io.IOException;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
@SpringBootApplication
@EnableConfigurationProperties({
        KafkaProducerApplicationConfig.class
})
public class KafkaProducerApplication implements ApplicationRunner {

    @Autowired
    private KafkaProducerApplicationConfig kafkaProducerApplicationConfig;

    @Autowired
    private ApplicationContext context;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String[] replays = new String[]{
            "replay/3372622939_490339365.dem",
            "replay/3372676225_558108515.dem",
            "replay/3372726385_1808632461.dem"
    };

    private void processReplay(String match) {
        String fileName = "";
        try {
            Integer matchNo = Integer.valueOf(match);
            if (matchNo < 0 || matchNo > 2) {
                throw new IllegalArgumentException("--match only accept 0, 1, or 2");
            }

            fileName = replays[matchNo];
        } catch (NumberFormatException exception) {
            logger.error("Please input number 0, 1, or 2 in --match");
            SpringApplication.exit(context);
            return;
        } catch (IllegalArgumentException exception) {
            logger.error(exception.getMessage());
            SpringApplication.exit(context);
            return;
        }

        try {
            String replayPath = getClass().getClassLoader().getResource(fileName).getFile();
            File file = new File(replayPath);
            Source source = new MappedFileSource(file);
            SimpleRunner runner = new SimpleRunner(source);

            String gameID = fileName.replace("replay/", "")
                                    .replace(".dem", "");
            HeroMovingKafkaProcessor kafkaProcessor = HeroMovingKafkaProcessor.builder()
                    .producer(new KafkaProducer<>(kafkaProducerApplicationConfig.getProducer()))
                    .gameID(gameID)
                    .build();

            runner.runWith(kafkaProcessor);
        } catch(IOException exception) {
            logger.error("IOException thrown: Likely no file found");
            SpringApplication.exit(context);
        } catch (NullPointerException exception) {
            logger.error("NullPointerException thrown: Likely no file found");
            SpringApplication.exit(context);
        }
    }

    @Override
    public void run(ApplicationArguments arguments) {
        arguments.getNonOptionArgs().forEach(this::processReplay);
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }
}
