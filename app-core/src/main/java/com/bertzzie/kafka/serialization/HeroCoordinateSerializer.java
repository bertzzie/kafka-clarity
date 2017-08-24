package com.bertzzie.kafka.serialization;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
public class HeroCoordinateSerializer implements Serializer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void configure(Map configs, boolean isKey) { }

    @Override
    public byte[] serialize(String topic, Object data) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            oos.close();

            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("Error in serializing data", e);
            return new byte[0];
        }
    }

    @Override
    public void close() { }
}
