package io.houf.dungeoncrawler;

import javax.swing.*;

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
        var game = new Game();
        var main = new Main(game);

        game.launch();
        main.setVisible(true);
    }
}
