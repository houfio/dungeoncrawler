package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.entity.impl.BulletEntity;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class GunItem extends Item {
    public GunItem() {
        super("gun");
    }

    @Override
    public Item onUse(Game game) {
        var room = game.getCurrent().currentRoom();
        var player = game.getCurrent().player;
        var enemy = room.entities.stream()
            .filter(Entity::hostile)
            .findFirst()
            .orElse(null);

        if (enemy == null) {
            game.getLogger().printLine("You couldn't decide where to point the barrel, so you ended up not pulling the trigger.", Color.ORANGE);

            return this;
        }

        var xEnemy = enemy.getX() + enemy.width / 2;
        var yEnemy = enemy.getY() + enemy.height / 2;
        var xPlayer = player.getX() + player.width / 2;
        var yPlayer = player.getY() + player.height / 2;

        var xDelta = xEnemy - xPlayer;
        var yDelta = yEnemy - yPlayer;
        var radians = Math.atan2(yDelta, xDelta);

        room.addEntity(game, new BulletEntity(xPlayer, yPlayer, (float) Math.cos(radians) * 20.0f, (float) Math.sin(radians) * 20.0f));

        game.getLogger().printLine("You pulled the trigger. The gun made a loud bang.", Color.YELLOW);

        return this;
    }

    @Override
    public boolean drop() {
        return false;
    }
}
