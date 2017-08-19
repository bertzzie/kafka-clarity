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
public class EntityVectorPosition {
    private Float vectorX;
    private Float vectorY;
    private Float vectorZ;
}
