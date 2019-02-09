package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.entity.impl.BulletEntity;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class GunItem extends Item {
    public GunItem() {
        super("gun", "When you blow in the barrel it makes a funny noise.");
    }

    @Override
    public Item onUse(Game game) {
        var room = game.getCurrent().currentRoom();
        var player = game.getCurrent().player;
        // Find first enemy in the room
        var enemy = room.entities.stream()
            .filter(Entity::hostile)
            .findFirst()
            .orElse(null);

        if (enemy == null) {
            // Just call it quits when there are no enemies
            game.getLogger().printLine("You couldn't decide where to point the barrel, so you ended up not pulling the trigger.", Color.ORANGE);

            return this;
        }

        var xEnemy = enemy.getX() + enemy.width / 2;
        var yEnemy = enemy.getY() + enemy.height / 2;
        var xPlayer = player.getX() + player.width / 2;
        var yPlayer = player.getY() + player.height / 2;

        var xDelta = xEnemy - xPlayer;
        var yDelta = yEnemy - yPlayer;
        // Calculate where to point the barrel
        var radians = Math.atan2(yDelta, xDelta);

        // Add bullet entity with velocity towards the hostile entity
        room.addEntity(game, new BulletEntity(xPlayer, yPlayer, (float) Math.cos(radians) * 20.0f, (float) Math.sin(radians) * 20.0f));

        // Very creative!
        game.getLogger().printLine("You pulled the trigger. The gun made a loud bang.", Color.YELLOW);
        game.getAudio().play("gun");

        return this;
    }

    @Override
    public boolean drop() {
        // Please don't drop your gun, you'll break the game
        return false;
    }
}
