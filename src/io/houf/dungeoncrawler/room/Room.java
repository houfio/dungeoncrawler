package io.houf.dungeoncrawler.room;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.entity.PlayerEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Room {
    public final PlayerEntity player;
    public final List<Entity> entities;

    public final int x;
    public final int y;

    private final Game game;

    public Room(Game game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.player = new PlayerEntity();
        this.entities = new ArrayList<>();

        this.entities.add(this.player);
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
        entity.initialize(this.game);
    }

    public void update() {
        var dead = new ArrayList<Entity>();
        this.entities.forEach(entity -> {
            if (entity.dead) {
                dead.add(entity);

                return;
            }

            entity.update(this.game);
        });

        this.entities.removeAll(dead);
    }

    public void render(Graphics2D g) {
        this.entities.forEach(entity -> entity.render(this.game, g));
    }
}
