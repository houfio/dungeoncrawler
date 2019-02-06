package io.houf.dungeoncrawler.room;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.entity.GnomeEntity;
import io.houf.dungeoncrawler.entity.PlayerEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Room {
    public final PlayerEntity player;
    public final List<Entity> entities;

    public final int x;
    public final int y;

    private boolean entered = false;

    public Room( int x, int y) {
        this.x = x;
        this.y = y;
        this.player = new PlayerEntity();
        this.entities = new ArrayList<>();

        this.entities.add(this.player);
    }

    public void addEntity(Game game, Entity entity) {
        this.entities.add(entity);
        entity.initialize(game);
    }

    public void update(Game game) {
        var dead = new ArrayList<Entity>();
        this.entities.forEach(entity -> {
            if (entity.dead) {
                dead.add(entity);

                return;
            }

            entity.update(game);
        });

        this.entities.removeAll(dead);
    }

    public void render(Game game, Graphics2D g) {
        this.entities.forEach(entity -> entity.render(game, g));
    }

    public void onEnter(Game game) {
        if (!this.entered) {
            this.addEntity(game, new GnomeEntity(25, 25));
        }

        this.entered = true;
    }
}
