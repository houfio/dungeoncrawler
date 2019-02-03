package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class GameUI extends UI {
    private final CommandUI command = new CommandUI();

    @Override
    public void initialize(Game game) {
    }

    @Override
    public List<UI> getChildren() {
        return Collections.singletonList(
            this.command
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
