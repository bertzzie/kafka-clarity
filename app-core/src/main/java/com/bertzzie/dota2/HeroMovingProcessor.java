package com.bertzzie.dota2;

import com.bertzzie.dota2.event.OnHeroMoving;
import com.bertzzie.dota2.model.HeroCoordinate;
import skadistats.clarity.processor.runner.Context;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
public class HeroMovingProcessor {
    @OnHeroMoving
    public void onHeroMoving(Context context, HeroCoordinate hero) {
        System.out.format("%06d: %s at pos(%f, %f)\n",
                hero.getTick(),
                hero.getName(),
                hero.getCoordinate().getX(),
                hero.getCoordinate().getY()
        );
    }
}
