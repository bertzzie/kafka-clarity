package com.bertzzie.dota2.helpers;

import com.bertzzie.dota2.model.EntityCellPosition;
import com.bertzzie.dota2.model.EntityCoordinate;
import com.bertzzie.dota2.model.EntityVectorPosition;
import skadistats.clarity.model.Entity;
import skadistats.clarity.model.FieldPath;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
public class EntityHelpers {
    private static final String FIELD_PATH_HERO_UNIT = "CDOTA_Unit_Hero";

    private static final String FIELD_PATH_CELL_X = "CBodyComponent.m_cellX";
    private static final String FIELD_PATH_CELL_Y = "CBodyComponent.m_cellY";
    private static final String FIELD_PATH_CELL_Z = "CBodyComponent.m_cellZ";

    private static final String FIELD_PATH_VEC_X = "CBodyComponent.m_vecX";
    private static final String FIELD_PATH_VEC_Y = "CBodyComponent.m_vecY";
    private static final String FIELD_PATH_VEC_Z = "CBodyComponent.m_vecZ";

    private static final Integer CELL_SIZE        = 128;
    private static final Integer DOUBLE_CELL_SIZE = CELL_SIZE * 2;
    private static final Float   MAP_MAX_WIDTH    = 32768.0f;

    public static EntityCellPosition getEntityCellPosition(Entity entity) {
        FieldPath cellXFP = entity.getDtClass().getFieldPathForName(FIELD_PATH_CELL_X);
        Integer cellX = entity.getPropertyForFieldPath(cellXFP);

        FieldPath cellYFP = entity.getDtClass().getFieldPathForName(FIELD_PATH_CELL_Y);
        Integer cellY = entity.getPropertyForFieldPath(cellYFP);

        FieldPath cellZFP = entity.getDtClass().getFieldPathForName(FIELD_PATH_CELL_Z);
        Integer cellZ = entity.getPropertyForFieldPath(cellZFP);

        return EntityCellPosition.builder()
                .cellX(cellX)
                .cellY(cellY)
                .cellZ(cellZ)
                .build();
    }

    public static EntityVectorPosition getEntityVectorPosition(Entity entity) {
        FieldPath vecXFP = entity.getDtClass().getFieldPathForName(FIELD_PATH_VEC_X);
        Float vecX = entity.getPropertyForFieldPath(vecXFP);

        FieldPath vecYFP = entity.getDtClass().getFieldPathForName(FIELD_PATH_VEC_Y);
        Float vecY = entity.getPropertyForFieldPath(vecYFP);

        FieldPath vecZFP = entity.getDtClass().getFieldPathForName(FIELD_PATH_VEC_Z);
        Float vecZ = entity.getPropertyForFieldPath(vecZFP);

        return EntityVectorPosition.builder()
                .vectorX(vecX)
                .vectorY(vecY)
                .vectorZ(vecZ)
                .build();
    }

    // Center (0, 0) on the bottom left of the map (radiant home base)
    public static EntityCoordinate getCoordinate(Entity entity) {
        EntityCellPosition cell = getEntityCellPosition(entity);
        EntityVectorPosition vec = getEntityVectorPosition(entity);

        Float xCoord = cell.getCellX() * CELL_SIZE + vec.getVectorX();
        Float yCoord = cell.getCellY() * CELL_SIZE + vec.getVectorY();

        return EntityCoordinate.builder()
                .X(xCoord)
                .Y(yCoord)
                .build();
    }

    // Center (0, 0) is on the middle of the map (around mid river)
    public static EntityCoordinate getMiddleCoordinate(Entity entity) {
        EntityCellPosition cell = getEntityCellPosition(entity);
        EntityVectorPosition vec = getEntityVectorPosition(entity);

        Float xCoord = cell.getCellX() * DOUBLE_CELL_SIZE + vec.getVectorX() - MAP_MAX_WIDTH;
        Float yCoord = cell.getCellY() * DOUBLE_CELL_SIZE + vec.getVectorY() - MAP_MAX_WIDTH;

        return EntityCoordinate.builder()
                .X(xCoord)
                .Y(yCoord)
                .build();
    }

    public static Boolean isHero(Entity entity) {
        return entity.getDtClass().getDtName().startsWith(FIELD_PATH_HERO_UNIT);
    }

    public void dumpState(Entity entity) {
        String name = entity.getDtClass().getDtName();
        System.out.println(entity.getDtClass().dumpState(name + " current State", entity.getState()));
    }
}
