package io.houf.dungeoncrawler.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sprite {
    private final BufferedImage sprite;
    private final int width;
    private final int speed;

    private int tick = 0;
    private int index = 0;

    public Sprite(String sprite, int width, int speed) throws IOException {
        this.sprite = ImageIO.read(Sprite.class.getResourceAsStream(sprite));
        this.width = width;
        this.speed = speed;
    }

    public void update() {
        this.tick++;

        if (this.tick % this.speed == 0) {
            this.index = (this.index + 1) % (this.sprite.getWidth() / this.width);
        }
    }

    public void render(int x, int y, Graphics2D g) {
        var offset = this.index * this.width;

        g.drawImage(this.sprite, x, y, x + this.width, y + this.sprite.getHeight(), offset, 0, offset + this.width, this.sprite.getHeight(), null, null);
    }
}
