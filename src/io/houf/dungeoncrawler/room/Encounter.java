package io.houf.dungeoncrawler.room;

import io.houf.dungeoncrawler.entity.Entity;

public class Encounter {
    public final Entity entity;
    public final double chance;

    public Encounter(Entity entity, double chance) {
        this.entity = entity;
        this.chance = chance;
    }
}
