package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameUI extends UI {
    private final CommandUI command = new CommandUI();

    @Override
    public void initialize(Game game) {
        // Initialize the current game
        game.initialize(this.command);
    }

    @Override
    public void cleanup(Game game) {
        // Reset the current game
        game.getCurrent().cleanup();
    }

    @Override
    public List<UI> getChildren() {
        return Arrays.asList(
            this.command,
            new RoomUI(),
            new RoseUI(),
            new PackUI()
        );
    }

    @Override
    public List<Selectable> getSelectables() {
        return Collections.singletonList(
            this.command
        );
    }

    @Override
    public void render(Game game, Graphics2D g) {
        // Don't render anything, only the children will render something
    }
}
