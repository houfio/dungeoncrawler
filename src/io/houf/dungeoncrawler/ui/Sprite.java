package io.houf.dungeoncrawler.ui;

import io.houf.dungeoncrawler.Asset;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    private final BufferedImage sheet;
    private final int width;
    private final int speed;

    private int tick = 0;
    private int index = 0;

    public Sprite(String sheet, int width, int speed) {
        this.sheet = Asset.read(sheet);
        this.width = width;
        this.speed = speed;
    }

    public void update() {
        // Increment sprite tick
        this.tick++;

        if (this.tick % this.speed == 0) {
            // Update current sprite index
            this.index = (this.index + 1) % (this.sheet.getWidth() / this.width);
        }
    }

    public void render(int x, int y, Graphics2D g) {
        var offset = this.index * this.width;

        // Draw a single image of the spritesheet using the sprite index
        g.drawImage(this.sheet, x, y, x + this.width, y + this.sheet.getHeight(), offset, 0, offset + this.width, this.sheet.getHeight(), null, null);
    }
}
