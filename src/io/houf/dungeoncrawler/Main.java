package io.houf.dungeoncrawler;

import javax.swing.*;
import java.util.Arrays;

public class Main extends JFrame {
    public Main(Game game) {
        this.setTitle("Dungeon Crawler");
        this.setSize(750, 500);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);

        var listener = new MoveListener(this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

        this.add(game);
    }

    public static void main(String[] args) {
        var hasUI = Arrays.asList(args).contains("ui");
        var game = new Game(hasUI);
        var main = new Main(game);

        game.launch();

        if (hasUI) {
            // Show ui when in ui mode
            main.setVisible(true);
        }
    }
}
