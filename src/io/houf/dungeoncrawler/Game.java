package io.houf.dungeoncrawler;

import io.houf.dungeoncrawler.loop.Loop;
import io.houf.dungeoncrawler.loop.Loopable;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.ui.UI;
import io.houf.dungeoncrawler.ui.impl.MainUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Game extends JPanel implements Loopable, KeyListener {
    public static final Font NORMAL_FONT = new Font("monospaced", Font.BOLD, 16);
    public static final Font BIG_FONT = new Font("monospaced", Font.BOLD, 24);

    private final Loop loop;
    private final List<UI> interfaces;
    private final List<Selectable> selectables;
    private final Pattern keyPattern;

    private int selected = 0;
    private int updates;
    private int frames;

    public Game() {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
        this.setFont(Game.NORMAL_FONT);
        this.setFocusTraversalKeysEnabled(false);

        this.loop = new Loop(this, 25.0d);
        this.interfaces = new ArrayList<>();
        this.selectables = new ArrayList<>();
        this.keyPattern = Pattern.compile("[a-zA-Z0-9 ]");
    }

    public void launch() {
        new Thread(this.loop).start();
    }

    public void quit() {
        this.loop.stop();
    }

    public void openUI(UI ui) {
        this.interfaces.clear();
        this.addUI(ui);
        this.interfaces.forEach(ui2 -> ui2.initialize(this));

        this.selected = 0;
        this.selectables.clear();
        this.selectables.addAll(ui.getSelectables());
        this.updateSelected();
    }

    private void addUI(UI ui) {
        this.interfaces.add(ui);

        ui.getChildren().forEach(this::addUI);
    }

    private void updateSelected() {
        for (var i = 0; i < this.selectables.size(); i++) {
            this.selectables.get(i).setSelected(i == this.selected);
        }
    }

    @Override
    public void start() {
        this.openUI(new MainUI());
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    @Override
    public void update() {
        new ArrayList<>(this.interfaces).forEach(UI::update);
    }

    @Override
    public void render() {
        this.repaint();
    }

    @Override
    public void setLoopData(int updates, int frames) {
        this.updates = updates;
        this.frames = frames;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        new ArrayList<>(this.interfaces).forEach(ui -> ui.render(g2d));

        g2d.setColor(Color.WHITE);
        g2d.drawString(this.updates + " UPS", 10, 20);
        g2d.drawString(this.frames + " FPS", 10, 35);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        var code = e.getKeyCode();
        var min = 0;
        var max = this.selectables.size() - 1;

        if (code == KeyEvent.VK_UP) {
            this.selected = this.selected == min ? max : this.selected - 1;
        } else if (code == KeyEvent.VK_DOWN) {
            this.selected = this.selected == max ? min : this.selected + 1;
        }

        if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_ENTER || code == KeyEvent.VK_TAB || this.keyPattern.matcher(String.valueOf(e.getKeyChar())).matches()) {
            new ArrayList<>(this.interfaces).forEach(ui -> ui.keyPressed(e.getExtendedKeyCode(), e.getKeyChar()));
        }

        this.updateSelected();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
