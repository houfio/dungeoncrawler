package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class CongratulationsUI extends UI {
    private final ButtonUI againButton;
    private final ButtonUI menuButton;
    private final ButtonUI quitButton;

    public CongratulationsUI() {
        this.againButton = new ButtonUI("Play again", 100, 180) {
            @Override
            public void click(Game game) {
                game.openUI(new GameUI());
            }
        };
        this.menuButton = new ButtonUI("Menu", 100, 200) {
            @Override
            public void click(Game game) {
                game.openUI(new MainUI());
            }
        };
        this.quitButton = new ButtonUI("Quit", 100, 220) {
            @Override
            public void click(Game game) {
                game.quit();
            }
        };
    }

    @Override
    public java.util.List<UI> getChildren() {
        return Arrays.asList(
            this.againButton,
            this.menuButton,
            this.quitButton
        );
    }

    @Override
    public List<Selectable> getSelectables() {
        return Arrays.asList(
            this.againButton,
            this.menuButton,
            this.quitButton
        );
    }

    @Override
    public void render(Game game, Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(Game.BIG_FONT);
        g.drawString("Congratulations!", 100, 100);
        g.setFont(Game.NORMAL_FONT);
        g.drawString("You escaped the dungeon through a hole in the ceiling.", 100, 120);
    }
}
