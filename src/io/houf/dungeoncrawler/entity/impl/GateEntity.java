package io.houf.dungeoncrawler.entity.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.ui.Sprite;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GateEntity extends Entity {
    public GateEntity(float x, float y) {
        super(new Sprite("gate", 25, 3), x, y, 25, 25);
    }

    public void activate(Game game) {
        var size = new AtomicInteger();
        var opacity = new AtomicInteger(255);

        game.startAnimation(25, a -> a
            .keyframe(0, g -> {
                g.setColor(new Color(0, 255, 255, opacity.get()));
                g.fillOval(100 + (int) this.getX() + this.width / 2 - size.get() / 2, 100 + (int) this.getY() + this.height / 2 - size.get() / 2, size.get(), size.get());
            })
            .action(0, () -> size.addAndGet(100))
            .action(15, () -> opacity.addAndGet(-28))
            .callback(15, a1 -> game.getCurrent().moveToRandom())
            .callback(24, a1 -> this.setDead()));
    }
}
