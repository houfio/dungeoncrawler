package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Asset;
import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RoomUI extends UI {
    private final BufferedImage room;
    private final BufferedImage carpet;

    public RoomUI() {
        this.room = Asset.read("ui/room");
        this.carpet = Asset.read("ui/carpet");
    }

    @Override
    public void update(Game game) {
        // Update the entities of current room
        game.getCurrent().currentRoom().update(game);
    }

    @Override
    public void render(Game game, Graphics2D g) {
        var current = game.getCurrent();

        // Draw the main room asset
        g.drawImage(this.room, 100, 100, null, null);

        // Draw the carpet to the doors
        for (var exit : current.currentRoom().getDoors()) {
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

        // Render the entities of the current room
        current.currentRoom().render(game, g);
    }
}
