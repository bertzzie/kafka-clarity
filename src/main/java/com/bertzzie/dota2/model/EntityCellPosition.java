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
public class EntityCellPosition {
    private Integer cellX;
    private Integer cellY;
    private Integer cellZ;
}
