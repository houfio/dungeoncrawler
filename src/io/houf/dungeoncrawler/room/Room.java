package io.houf.dungeoncrawler.room;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.entity.impl.GnomeEntity;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Room {
    public final List<Entity> entities;

    public final int x;
    public final int y;

    private final Encounter[] encounters;

    private boolean entered = false;

    public Room(int x, int y) {
        this(x, y,
            new Encounter(new GnomeEntity(40.0f, 40.0f), 0.5d),
            new Encounter(new GnomeEntity(190.0f, 40.0f), 0.5d),
            new Encounter(new GnomeEntity(190.0f, 190.0f), 0.5d),
            new Encounter(new GnomeEntity(40.0f, 190.0f), 0.5d)
        );
    }

    public Room(int x, int y, Encounter... encounters) {
        this.x = x;
        this.y = y;
        this.entities = new ArrayList<>();
        this.encounters = encounters;
    }

    public void addEntity(Game game, Entity entity) {
        this.entities.add(entity);
        entity.initialize(game);
    }

    public void update(Game game) {
        var dead = new ArrayList<Entity>();
        var e = new ArrayList<>(this.entities);
        e.forEach(entity -> {
            if (entity.isDead()) {
                dead.add(entity);

                return;
            }

            entity.update(game);

            e.stream()
                .filter(e1 -> entity.getX() < e1.getX() + e1.width && entity.getX() + entity.width > e1.getX() && entity.getY() < e1.getY() + e1.height && entity.height + entity.getY() > e1.getY())
                .forEach(e1 -> entity.collide(game, e1));
        });

        dead.forEach(d -> {
            var drops = d.drops(game);

            if (drops == null) {
                return;
            }

            Arrays.stream(drops)
                .filter(drop -> Math.random() > 1.0d - drop.chance)
                .forEach(drop -> this.addEntity(game, new ItemEntity(drop.item, d.getX(), d.getY(), this.getRandomVelocity(), this.getRandomVelocity())));
        });
        this.entities.removeAll(dead);
    }

    public void render(Game game, Graphics2D g) {
        new ArrayList<>(this.entities).stream()
            .sorted(Comparator.comparingInt(Entity::priority))
            .forEach(entity -> entity.render(game, g));
    }

    public void onEnter(Game game) {
        if (!this.entered) {
            Arrays.stream(this.encounters)
                .filter(encounter -> Math.random() > 1.0d - encounter.chance)
                .forEach(encounter -> this.addEntity(game, encounter.entity));
        }

        game.getLogger().executeCommand(game, "look");

        this.entered = true;
    }

    private float getRandomVelocity() {
        return (float) Math.random() * 20.0f - 10.0f;
    }

    public static class Encounter {
        public final Entity entity;
        public final double chance;

        public Encounter(Entity entity, double chance) {
            this.entity = entity;
            this.chance = chance;
        }
    }
}
