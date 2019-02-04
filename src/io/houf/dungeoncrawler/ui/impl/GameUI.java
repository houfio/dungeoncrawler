package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Ingame;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameUI extends UI {
    private final CommandUI command = new CommandUI();

    private Game game;

    @Override
    public void initialize(Game game) {
        this.game = game;

        this.game.ingame = new Ingame(this.game, this.command);
        this.game.ingame.initialize();
    }

    @Override
    public void cleanup() {
        this.game.ingame.cleanup();
    }

    @Override
    public List<UI> getChildren() {
        return Arrays.asList(
            this.command,
            new RoomUI()
        );
    }

    @Override
    public List<Selectable> getSelectables() {
        return Collections.singletonList(
            this.command
        );
    }

    @Override
    public void render(Graphics2D g) {
    }
}
