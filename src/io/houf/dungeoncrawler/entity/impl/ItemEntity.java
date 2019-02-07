package io.houf.dungeoncrawler.entity.impl;

import io.houf.dungeoncrawler.Asset;
import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class ItemEntity extends Entity {
    public final Item item;

    public double angle;

    public ItemEntity(Item item, float x, float y) {
        this(item, x, y, 0.0f, 0.0f);
    }

    public ItemEntity(Item item, float x, float y, float velocityX, float velocityY) {
        super(null, x, y, 16, 16);

        this.item = item;

        this.setVelocityX(velocityX);
        this.setVelocityY(velocityY);
    }

    @Override
    public void update(Game game) {
        super.update(game);

        this.angle += Math.sqrt(Math.pow(this.getVelocityX(), 2) + Math.pow(this.getVelocityY(), 2));
    }

    @Override
    public void render(Game game, Graphics2D g) {
        super.render(game, g);

        g.drawImage(Asset.rotate(this.item.image, this.angle), 100 + (int) this.getX(), 100 + (int) this.getY(), null, null);
    }
}
