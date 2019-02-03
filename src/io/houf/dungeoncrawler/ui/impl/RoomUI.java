package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;

public class RoomUI extends UI {
    private Game game;

    @Override
    public void initialize(Game game) {
        this.game = game;
    }

    @Override
    public void render(Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setColor(new Color(99, 55, 38));
        g.drawRect(100, 100, 250, 250);

        g.setColor(new Color(57, 39, 29));
        g.fillRect(102, 102, 246, 246);

        var room = this.game.ingame.currentRoom();

        for (var exit : room.exits.keySet()) {
            switch (exit) {
                case NORTH:
                    g.drawLine(200, 100, 250, 100);
                    break;
                case EAST:
                    g.drawLine(350, 200, 350, 250);
                    break;
                case SOUTH:
                    g.drawLine(200, 350, 250, 350);
                    break;
                case WEST:
                    g.drawLine(100, 200, 100, 250);
                    break;
            }
        }

        g.setStroke(new BasicStroke(1));
    }
}
