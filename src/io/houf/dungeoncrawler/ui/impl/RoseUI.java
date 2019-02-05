package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Asset;
import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RoseUI extends UI {
    private final BufferedImage rose;

    public RoseUI() {
        this.rose = Asset.read("rose");
    }

    @Override
    public void render(Game game, Graphics2D g) {
        g.drawImage(this.rose, 380, 20, null, null);
        g.setColor(Color.WHITE);
        g.drawString("N", 400, 20);
        g.drawString("E", 430, 50);
        g.drawString("S", 400, 80);
        g.drawString("W", 370, 50);
    }
}
