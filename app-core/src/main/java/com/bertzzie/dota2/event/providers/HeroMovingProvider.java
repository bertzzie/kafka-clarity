package com.bertzzie.dota2.event.providers;

import com.bertzzie.dota2.event.OnHeroMoving;
import com.bertzzie.dota2.helpers.EntityHelpers;
import com.bertzzie.dota2.model.EntityCoordinate;
import com.bertzzie.dota2.model.HeroCoordinate;
import skadistats.clarity.event.Event;
import skadistats.clarity.event.EventListener;
import skadistats.clarity.event.Initializer;
import skadistats.clarity.event.Provides;
import skadistats.clarity.model.Entity;
import skadistats.clarity.model.FieldPath;
import skadistats.clarity.processor.entities.OnEntityCreated;
import skadistats.clarity.processor.entities.OnEntityDeleted;
import skadistats.clarity.processor.entities.OnEntityUpdated;
import skadistats.clarity.processor.entities.UsesEntities;
import skadistats.clarity.processor.runner.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
@UsesEntities
@Provides(OnHeroMoving.class)
public class HeroMovingProvider {
    private final Map<Integer, EntityCoordinate> entityCoordinate = new HashMap<>();
    private final Map<Integer, EntityCoordinate> entityCurrentCoordinate = new HashMap<>();

    private Event<OnHeroMoving> entityMovingEvent;

    private void init(Context context) {}

    @Initializer(OnHeroMoving.class)
    public void initOnEntityMoving(final Context context, final EventListener<OnHeroMoving> eventListener) {
        init(context);
        entityMovingEvent = context.createEvent(OnHeroMoving.class, Entity.class);
    }

    @OnEntityCreated
    public void onCreated(Context context, Entity entity) {
        if (EntityHelpers.isHero(entity)) {
            clearCachedCoordinate(entity);
            initializeFieldPaths(entity);

            EntityCoordinate coordinate = getEntityCoordinate(entity);
            if (coordinate != null) {
                processCoordinateChange(context, entity, coordinate);
            }
        }
    }

    @OnEntityUpdated
    public void onUpdated(Context context, Entity entity, FieldPath[] fieldPaths, int num) {
        if (EntityHelpers.isHero(entity)) {
            EntityCoordinate coordinate = EntityHelpers.getCoordinate(entity);
            if (coordinate != null) {
                processCoordinateChange(context, entity, coordinate);
            }
        }
    }

    @OnEntityDeleted
    public void onDeleted(Context context, Entity entity) {
        if (EntityHelpers.isHero(entity)) {
            clearCachedCoordinate(entity);
        }
    }

    private void initializeFieldPaths(Entity entity) {
        Integer cid = entity.getDtClass().getClassId();
        if (!entityCoordinate.containsKey(cid)) {
            EntityCoordinate coordinate = EntityHelpers.getCoordinate(entity);
            entityCoordinate.put(cid, coordinate);
        }
    }

    private void clearCachedCoordinate(Entity entity) {
        entityCurrentCoordinate.remove(entity.getIndex());
    }

    private EntityCoordinate getEntityCoordinate(Entity entity) {
        return entityCoordinate.get(entity.getDtClass().getClassId());
    }

    private void processCoordinateChange(Context context, Entity entity, EntityCoordinate newCoordinate) {
        EntityCoordinate oldCoordinate = entityCurrentCoordinate.getOrDefault(entity.getIndex(), EntityCoordinate.DUMMY_COORDINATE);
        if (!newCoordinate.equals(oldCoordinate)) {
            entityCurrentCoordinate.put(entity.getIndex(), newCoordinate);

            HeroCoordinate hero = HeroCoordinate.builder()
                    .coordinate(newCoordinate)
                    .name(entity.getDtClass().getDtName())
                    .tick(context.getTick())
                    .build();
            entityMovingEvent.raise(hero);
        }
    }
}
