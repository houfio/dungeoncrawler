package io.houf.dungeoncrawler.entity.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.ui.Sprite;

public class BulletEntity extends Entity {
    private int age = 0;

    public BulletEntity(float x, float y, float velocityX, float velocityY) {
        super(new Sprite("entity/bullet", 5, 100), x, y, 5, 1.0f);

        this.setVelocityX(velocityX);
        this.setVelocityY(velocityY);
    }

    @Override
    public void update(Game game) {
        super.update(game);

        if (this.age++ >= 100) {
            this.setDead();
        }
    }

    @Override
    public void collide(Game game, Entity entity) {
        if (!entity.hostile()) {
            return;
        }

        for (var i = 0; i < 100; i++) {
            game.getCurrent().currentRoom().addEntity(game, new BloodEntity(this.getX(), this.getY(), this.getVelocityX() * 2.0f + ((float) Math.random() * 50.0f - 25.0f), this.getVelocityY() * 2.0f + ((float) Math.random() * 50.0f - 25.0f)));
        }

        entity.setDead();
        this.setDead();
    }

    @Override
    public int priority() {
        return 1;
    }
}
