package io.houf.dungeoncrawler.entity.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;

import java.awt.*;

public class BloodEntity extends Entity {
    private final int red;

    public BloodEntity(float x, float y, float velocityX, float velocityY) {
        super(null, x, y, 2 + (int) (Math.random() * 6.0d), (float) Math.random() * 0.8f);

        this.setVelocity(velocityX, velocityY);

        // Give blood random red color
        this.red = 100 + (int) (Math.random() * 155.0d);
    }

    @Override
    public void render(Game game, Graphics2D g) {
        super.render(game, g);

        // Render rectangle instead of sprite
        g.setColor(new Color(this.red, 0, 0));
        g.fillRect(100 + (int) this.getX(), 100 + (int) this.getY(), this.width, this.height);
    }

    @Override
    public int priority() {
        // Render blood behind all other entities
        return -1;
    }

    @Override
    public boolean sound() {
        // Don't make sound when bounced on wall
        return false;
    }
}
