package io.houf.dungeoncrawler.ui;

import io.houf.dungeoncrawler.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class UI {
    public abstract void initialize(Game game);

    public List<UI> getChildren() {
        return new ArrayList<>();
    }

    public List<Selectable> getSelectables() {
        return new ArrayList<>();
    }

    public void update() {
    }

    public abstract void render(Graphics2D g);

    public void keyPressed(int code, char key) {
    }
}
