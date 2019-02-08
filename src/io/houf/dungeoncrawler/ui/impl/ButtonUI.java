package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class ButtonUI extends UI implements Selectable {
    private final String text;
    private final int x;
    private final int y;

    private boolean selected = false;

    protected ButtonUI(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public abstract void click(Game game);

    @Override
    public void render(Game game, Graphics2D g) {
        String string = (this.selected ? "> " : "  ") + this.text;

        g.setColor(this.selected ? Color.YELLOW : Color.WHITE);
        g.drawString(string, this.x, this.y);
    }

    @Override
    public void keyPressed(Game game, int code, char key) {
        if (!this.selected || code != KeyEvent.VK_ENTER) {
            return;
        }

        this.click(game);
    }

    @Override
    public void setSelected(Game game, boolean selected) {
        if (!this.selected && selected) {
            game.getAudio().play("menu");
        }

        this.selected = selected;
    }
}
