package com.bertzzie.kafka.stream.serde;

import com.bertzzie.dota2.model.HeroCoordinate;
import com.bertzzie.kafka.serialization.HeroCoordinateDeserializer;
import com.bertzzie.kafka.serialization.HeroCoordinateSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
public class HeroCoordinateSerde implements Serde<HeroCoordinate> {
    private Serializer<HeroCoordinate> serializer = new HeroCoordinateSerializer();
    private Deserializer<HeroCoordinate> deserializer = new HeroCoordinateDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) { }

    @Override
    public void close() { }

    @Override
    public Serializer<HeroCoordinate> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<HeroCoordinate> deserializer() {
        return deserializer;
    }
}
