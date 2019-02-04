package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RoomUI extends UI {
    private Game game;
    private BufferedImage image;

    @Override
    public void initialize(Game game) {
        this.game = game;
        try {
            this.image = ImageIO.read(RoomUI.class.getResourceAsStream("/assets/room.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        this.game.ingame.currentRoom().update();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.image, 100, 100, null, null);

        var room = this.game.ingame.currentRoom();

        g.setColor(new Color(113, 30, 26));
        g.fillRect(185, 185, 80, 80);

        for (var exit : this.game.ingame.getDoors()) {
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

        room.render(g);
    }
}
