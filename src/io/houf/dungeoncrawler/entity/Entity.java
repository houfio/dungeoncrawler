package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.Sprite;

import java.awt.*;

public abstract class Entity {
    public final int width;
    public final int height;

    private float x;
    private float y;
    private float velocityX;
    private float velocityY;

    private boolean dead;

    private final Sprite sprite;
    private final float gravity;

    public Entity(Sprite sprite, float x, float y, int width, int height) {
        this(sprite, x, y, width, height, 0.9f);
    }

    public Entity(Sprite sprite, float x, float y, int size, float gravity) {
        this(sprite, x, y, size, size, gravity);
    }

    public Entity(Sprite sprite, float x, float y, int width, int height, float gravity) {
        this.width = width;
        this.height = height;

        this.x = x;
        this.y = y;

        this.sprite = sprite;
        this.gravity = gravity;
    }

    public void initialize(Game game) {
    }

    public void update(Game game) {
        this.x += this.velocityX;
        this.y += this.velocityY;

        this.velocityX *= this.gravity;
        this.velocityY *= this.gravity;

        var bounced = false;

        // Check if out of bounds on x axis (the walls are 10px thick)
        if ((this.x <= 10 && this.velocityX < 0.0f) || (this.x >= 240 - this.width && this.velocityX > 0.0f)) {
            // Invert x velocity
            this.velocityX *= -1;

            bounced = true;
        }

        // Check if out of bounds on y axis (the walls are 10px thick)
        if ((this.y <= 10 && this.velocityY < 0.0f) || (this.y >= 240 - this.height && this.velocityY > 0.0f)) {
            // Invert y velocity
            this.velocityY *= -1;

            bounced = true;
        }

        if (bounced && this.sound()) {
            // Play bounce sound
            game.getAudio().play("collide");
        }

        if (this.sprite != null) {
            this.sprite.update();
        }
    }

    public void render(Game game, Graphics2D g) {
        if (this.sprite != null) {
            this.sprite.render(100 + (int) this.x, 100 + (int) this.y, g);
        }
    }

    public void collide(Game game, Entity entity) {
    }

    public Drop[] drops(Game game) {
        return null;
    }

    public boolean hostile() {
        return false;
    }

    public int priority() {
        return 0;
    }

    public boolean sound() {
        return true;
    }

    public void move(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void setLocation(float x, float y) {
        if (x >= 0.0f) {
            // Only update x if valid location
            this.x = x;
        }

        if (y >= 0.0f) {
            // Only update y if valid location
            this.y = y;
        }
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getVelocityX() {
        return this.velocityX;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public void setVelocity(float x, float y) {
        this.velocityX = x;
        this.velocityY = y;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead() {
        this.dead = true;
    }
}
