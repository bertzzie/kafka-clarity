package com.bertzzie.kafka.serialization;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
public class HeroCoordinateDeserializer implements Deserializer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void configure(Map configs, boolean isKey) { }

    @Override
    public Object deserialize(String topic, byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException exception) {
            logger.error("Error deserializing data", exception);
            return null;
        } catch (ClassNotFoundException exception) {
            logger.error("Error deserializing data", exception);
            return null;
        }
    }

    @Override
    public void close() { }
}
