package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MainUI extends UI {
    private final ButtonUI startButton;
    private final ButtonUI quitButton;

    public MainUI() {
        this.startButton = new ButtonUI("Start", 100, 180) {
            @Override
            public void click(Game game) {
                game.openUI(new GameUI());
            }
        };
        this.quitButton = new ButtonUI("Quit", 100, 200) {
            @Override
            public void click(Game game) {
                game.quit();
            }
        };
    }

    @Override
    public List<UI> getChildren() {
        return Arrays.asList(
            this.startButton,
            this.quitButton
        );
    }

    @Override
    public List<Selectable> getSelectables() {
        return Arrays.asList(
            this.startButton,
            this.quitButton
        );
    }

    @Override
    public void render(Game game, Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(Game.BIG_FONT);
        g.drawString("Dungeon Crawler", 100, 100);
        g.setFont(Game.NORMAL_FONT);
        g.drawString("The most disappointing room explorer game.", 100, 120);
    }
}
