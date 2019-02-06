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

    public Room(int x, int y) {
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

        this.entities.removeAll(dead);
    }

    public void render(Game game, Graphics2D g) {
        new ArrayList<>(this.entities).forEach(entity -> entity.render(game, g));
    }

    public void onEnter(Game game) {
        if (!this.entered) {
            if (this.chance()) {
                this.addEntity(game, new GnomeEntity(40, 40));
            }

            if (this.chance()) {
                this.addEntity(game, new GnomeEntity(190, 40));
            }

            if (this.chance()) {
                this.addEntity(game, new GnomeEntity(190, 190));
            }

            if (this.chance()) {
                this.addEntity(game, new GnomeEntity(40, 190));
            }

            game.getLogger().executeCommand(game, "look");
        }

        this.entered = true;
    }

    private boolean chance() {
        return Math.random() > 0.5d;
    }
}
