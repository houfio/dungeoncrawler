package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.Item;
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

        if ((this.x <= 10 && this.velocityX < 0.0f) || (this.x >= 240 - this.width && this.velocityX > 0.0f)) {
            this.velocityX *= -1;
        }

        if ((this.y <= 10 && this.velocityY < 0.0f) || (this.y >= 240 - this.height && this.velocityY > 0.0f)) {
            this.velocityY *= -1;
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

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelocityX() {
        return this.velocityX;
    }

    public void setVelocityX(float x) {
        this.velocityX = x;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityY(float y) {
        this.velocityY = y;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead() {
        this.dead = true;
    }

    public static class Drop {
        public final Item item;
        public final double chance;

        public Drop(Item item, double chance) {
            this.item = item;
            this.chance = chance;
        }
    }
}
