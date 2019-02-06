package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.ui.Sprite;

public class BloodEntity extends Entity {
    public BloodEntity(float x, float y, float velocityX, float velocityY) {
        super(new Sprite("blood", 10, 100), x, y, 10, 10, (float) Math.random() * 0.8f);

        this.setVelocityX(velocityX);
        this.setVelocityY(velocityY);
    }
}
