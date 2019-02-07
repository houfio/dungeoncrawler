package io.houf.dungeoncrawler.ui;

import io.houf.dungeoncrawler.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class UI {
    public void initialize(Game game) {
    }

    public void cleanup(Game game) {
    }

    public List<UI> getChildren() {
        return new ArrayList<>();
    }

    public List<Selectable> getSelectables() {
        return new ArrayList<>();
    }

    public void update(Game game) {
    }

    public abstract void render(Game game, Graphics2D g);

    public void keyPressed(Game game, int code, char key) {
    }
}
