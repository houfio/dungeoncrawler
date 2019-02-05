package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.Sprite;

import java.awt.*;

public abstract class Entity {
    public final int width;
    public final int height;

    public float x;
    public float y;
    public float velocityX;
    public float velocityY;

    public boolean dead;

    private final Sprite sprite;

    public Entity(Sprite sprite, float x, float y, int width, int height) {
        this.width = width;
        this.height = height;

        this.x = x;
        this.y = y;

        this.sprite = sprite;
    }

    public void initialize(Game game) {
    }

    public void update(Game game) {
        this.x += this.velocityX;
        this.y += this.velocityY;

        this.velocityX *= 0.9d;
        this.velocityY *= 0.9d;

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
}
