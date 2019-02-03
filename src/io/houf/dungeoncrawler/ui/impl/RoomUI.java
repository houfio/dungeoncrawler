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
        g.setColor(new Color(57, 39, 29));
        g.fillRect(100, 100, 250, 250);

        g.setStroke(new BasicStroke(10));
        g.setColor(new Color(99, 55, 38));
        g.drawRect(100, 100, 250, 250);
        g.setStroke(new BasicStroke(1));

        var room = this.game.ingame.currentRoom();

        g.setColor(new Color(113, 30, 26));
        g.fillRect(185, 185, 80, 80);

        for (var exit : room.exits.keySet()) {
            switch (exit) {
                case NORTH:
                    g.fillRect(185, 75, 80, 110);
                    break;
                case EAST:
                    g.fillRect(265, 185, 110, 80);
                    break;
                case SOUTH:
                    g.fillRect(185, 265, 80, 110);
                    break;
                case WEST:
                    g.fillRect(75, 185, 110, 80);
                    break;
            }
        }
    }
}
