package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class OptionsUI extends UI {
    private final ButtonUI audioButton;
    private final ButtonUI backButton;

    public OptionsUI() {
        this.audioButton = new ButtonUI("Audio", 100, 180) {
            @Override
            public String getDisplayText(Game game) {
                return super.getDisplayText(game) + " (" + (game.getAudio().enabled() ? "on" : "off") + ")";
            }

            @Override
            public void click(Game game) {
                game.getAudio().toggle();
                game.getAudio().play("menu");
            }
        };
        this.backButton = new ButtonUI("Back to menu", 100, 220) {
            @Override
            public void click(Game game) {
                game.openUI(new MainUI());
            }
        };
    }

    @Override
    public java.util.List<UI> getChildren() {
        return Arrays.asList(
            this.audioButton,
            this.backButton
        );
    }

    @Override
    public List<Selectable> getSelectables() {
        return Arrays.asList(
            this.audioButton,
            this.backButton
        );
    }

    @Override
    public void render(Game game, Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(Game.BIG_FONT);
        g.drawString("Options", 100, 100);
        g.setFont(Game.NORMAL_FONT);
        g.drawString("Because YOU deserve the best.", 100, 120);
    }
}
