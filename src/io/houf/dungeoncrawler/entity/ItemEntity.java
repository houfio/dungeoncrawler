package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class ItemEntity extends Entity {
    public final Item item;

    public ItemEntity(Item item, float x, float y) {
        super(null, x, y, 16, 16);

        this.item = item;
    }

    @Override
    public void update(Game game) {
        super.update(game);
    }

    @Override
    public void render(Game game, Graphics2D g) {
        super.render(game, g);

        g.drawImage(this.item.image, 100 + (int) this.x, 100 + (int) this.y, null, null);
    }
}
