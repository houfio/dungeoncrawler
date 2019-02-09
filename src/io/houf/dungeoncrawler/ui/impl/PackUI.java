package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;

public class PackUI extends UI {
    @Override
    public void render(Game game, Graphics2D g) {
        var items = game.getCurrent().player.backpack;
        var size = items.size();

        for (var i = 0; i < size; i++) {
            var item = items.get(i);
            var y = 450 - (size - i) * 25;

            g.setColor(Color.WHITE);
            g.drawImage(item.image, 10, y, null, null);
            g.drawString(item.name, 35, y + 12);
        }
    }
}
