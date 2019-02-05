package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RoseUI extends UI {
    private BufferedImage rose;

    @Override
    public void initialize(Game game) {
        try {
            this.rose = ImageIO.read(RoomUI.class.getResourceAsStream("/assets/rose.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.rose, 380, 20, null, null);
        g.setColor(Color.WHITE);
        g.drawString("N", 400, 20);
        g.drawString("E", 430, 50);
        g.drawString("S", 400, 80);
        g.drawString("W", 370, 50);
    }
}
