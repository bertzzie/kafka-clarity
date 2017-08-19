package com.bertzzie.dota2.model;

import lombok.Builder;
import lombok.Data;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
@Data
@Builder
public class HeroCoordinate {
    private Integer tick;
    private String name;
    private EntityCoordinate coordinate;

    @Override
    public String toString() {
        return "{ tick:" + tick +
               ", name: " + name +
               ", coordinate: { x: " + coordinate.getX() + ", y: " + coordinate.getY() + " }}" ;
    }
}
