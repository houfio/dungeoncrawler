package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RoomUI extends UI {
    private Game game;
    private BufferedImage room;
    private BufferedImage carpet;

    @Override
    public void initialize(Game game) {
        this.game = game;
        try {
            this.room = ImageIO.read(RoomUI.class.getResourceAsStream("/assets/room.png"));
            this.carpet = ImageIO.read(RoomUI.class.getResourceAsStream("/assets/carpet.png"));
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
        g.drawImage(this.room, 100, 100, null, null);

        for (var exit : this.game.ingame.getDoors()) {
            switch (exit) {
                case NORTH:
                    g.drawImage(this.carpet, 194, 85, 256, 195, 109, 0, 171, 110, null, null);
                    break;
                case EAST:
                    g.drawImage(this.carpet, 255, 194, 365, 256, 170, 109, 280, 171, null, null);
                    break;
                case SOUTH:
                    g.drawImage(this.carpet, 194, 255, 256, 365, 109, 170, 171, 280, null, null);
                    break;
                case WEST:
                    g.drawImage(this.carpet, 85, 194, 195, 256, 0, 109, 110, 171, null, null);
                    break;
            }
        }

        this.game.ingame.currentRoom().render(g);
    }
}
