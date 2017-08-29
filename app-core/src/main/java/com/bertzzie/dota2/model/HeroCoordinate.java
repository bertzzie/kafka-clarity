package com.bertzzie.dota2.model;

import com.bertzzie.dota2.helpers.EntityHelpers;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
@Data
@Builder
public class HeroCoordinate implements Serializable {
    private Integer tick;
    private String name;
    private EntityCoordinate coordinate;

    @Override
    public String toString() {
        return "{ tick:" + tick +
               ", name: " + name +
               ", coordinate: { x: " + coordinate.getX() + ", y: " + coordinate.getY() + " }}" ;
    }

    public HeroCoordinate getCentralCoordinate() {
        EntityCoordinate centralCoordinate = EntityCoordinate.builder()
                .X(coordinate.getX() - EntityHelpers.MAP_MAX_WIDTH)
                .Y(coordinate.getY() - EntityHelpers.MAP_MAX_WIDTH)
                .build();

        return new HeroCoordinate(tick, name, centralCoordinate);
    }

    public String getCentralCoordinateString() {
        return getCentralCoordinate().getCoordinate().toString();
    }
}
