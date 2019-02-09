package io.houf.dungeoncrawler.room;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.entity.impl.GnomeEntity;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Room {
    public final List<Entity> entities;

    public final int x;
    public final int y;
    public final String description;

    private final Map<Side, Room> doors;
    private final Encounter[] encounters;

    private boolean entered = false;

    public Room(int x, int y) {
        this(x, y, "It's a normal room.");
    }

    public Room(int x, int y, String description) {
        this(x, y, description,
            // Default hostile encounters
            new Encounter(new GnomeEntity(40.0f, 40.0f), 0.5d),
            new Encounter(new GnomeEntity(190.0f, 40.0f), 0.5d),
            new Encounter(new GnomeEntity(190.0f, 190.0f), 0.5d),
            new Encounter(new GnomeEntity(40.0f, 190.0f), 0.5d)
        );
    }

    public Room(int x, int y, String description, Encounter... encounters) {
        this.x = x;
        this.y = y;
        this.description = description;
        this.entities = new ArrayList<>();
        this.doors = new HashMap<>();
        this.encounters = encounters;
    }

    public void initialize(Game game) {
        for (var side : Side.values()) {
            var room = game.getCurrent().floor.getRoom(this.x + side.x, this.y + side.y);

            if (room != null) {
                this.doors.put(side, room);
            }
        }
    }

    public Set<Side> getDoors() {
        return this.doors.keySet();
    }

    public Room getRoomOnSide(Side side) {
        return this.doors.get(side);
    }

    public void addEntity(Game game, Entity entity) {
        entity.initialize(game);
        this.entities.add(entity);
    }

    public void update(Game game) {
        var dead = new ArrayList<Entity>();
        var e = new ArrayList<>(this.entities);
        e.forEach(entity -> {
            if (entity.isDead()) {
                dead.add(entity);

                // If marked as dead don't update
                return;
            }

            entity.update(game);

            // Check collision with other entities
            e.stream()
                .filter(e1 -> entity.getX() < e1.getX() + e1.width && entity.getX() + entity.width > e1.getX() && entity.getY() < e1.getY() + e1.height && entity.height + entity.getY() > e1.getY())
                .forEach(e1 -> entity.collide(game, e1));
        });

        dead.forEach(d -> {
            // Check if dead entity has drops
            var drops = d.drops(game);

            if (drops == null) {
                return;
            }

            // Filter drops on chance and add them to the floor
            Arrays.stream(drops)
                .filter(drop -> Math.random() > 1.0d - drop.chance)
                .forEach(drop -> this.addEntity(game, new ItemEntity(drop.item, d.getX(), d.getY(), this.getRandomVelocity(), this.getRandomVelocity())));
        });
        // Remove all dead entities from room
        this.entities.removeAll(dead);
    }

    public void render(Game game, Graphics2D g) {
        // Render all entities sorted on priority
        new ArrayList<>(this.entities).stream()
            .sorted(Comparator.comparingInt(Entity::priority))
            .forEach(entity -> entity.render(game, g));
    }

    public void onEnter(Game game) {
        if (!this.entered) {
            // Add random encounters to the room upon first enter
            Arrays.stream(this.encounters)
                .filter(encounter -> Math.random() > 1.0d - encounter.chance)
                .forEach(encounter -> this.addEntity(game, encounter.entity));
        }

        game.getAudio().play("enter");
        // Give room introduction upon every enter (mainly for console mode)
        game.getLogger().executeCommand(game, "look");

        this.entered = true;
    }

    private float getRandomVelocity() {
        // Get random velocity from -10 to 10
        return (float) Math.random() * 20.0f - 10.0f;
    }
}
