package com.bertzzie.kafka.serialization;

import org.apache.kafka.common.serialization.Serializer;

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
    @Override
    public void configure(Map configs, boolean isKey) { }

    @Override
    public byte[] serialize(String topic, Object data) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            oos.close();

            byte[] b = baos.toByteArray();
            return b;
        } catch (IOException e) {
            return new byte[0];
        }
    }

    @Override
    public void close() { }
}
