package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.Sprite;

import java.awt.*;
import java.io.IOException;

public class PlayerEntity extends Entity {
    private Sprite sprite;

    public PlayerEntity() {
        super(114, 109, 22, 32);
    }

    @Override
    public void initialize(Game game) {
        try {
            this.sprite = new Sprite("/assets/player.png", 22, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        super.update();

        if (this.sprite != null) {
            this.sprite.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (this.sprite != null) {
            this.sprite.render(100 + (int) this.x, 100 + (int) this.y, g);
        }
    }
}
