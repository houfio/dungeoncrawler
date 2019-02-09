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

    public CongratulationsUI() {
        this.againButton = new ButtonUI("Play again", 100, 180) {
            @Override
            public void click(Game game) {
                // Start the game again by opening the game ui
                game.openUI(new GameUI());
            }
        };
        this.menuButton = new ButtonUI("Back to menu", 100, 220) {
            @Override
            public void click(Game game) {
                // Go back to the start screen
                game.openUI(new MainUI());
            }
        };
    }

    @Override
    public java.util.List<UI> getChildren() {
        return Arrays.asList(
            this.againButton,
            this.menuButton
        );
    }

    @Override
    public List<Selectable> getSelectables() {
        return Arrays.asList(
            this.againButton,
            this.menuButton
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
