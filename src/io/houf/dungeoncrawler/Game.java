package io.houf.dungeoncrawler;

import io.houf.dungeoncrawler.loop.Loop;
import io.houf.dungeoncrawler.loop.Loopable;
import io.houf.dungeoncrawler.ui.Animation;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.ui.UI;
import io.houf.dungeoncrawler.ui.impl.GameUI;
import io.houf.dungeoncrawler.ui.impl.MainUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Game extends JPanel implements Loopable, KeyListener {
    public static final Font NORMAL_FONT = new Font("monospaced", Font.BOLD, 16);
    public static final Font BIG_FONT = new Font("monospaced", Font.BOLD, 24);

    public final boolean hasUI;

    private final Loop loop;
    private final List<UI> interfaces;
    private final List<Selectable> selectables;
    private final List<Animation> animations;

    private Logger logger;
    private Current current;
    private int selected = 0;

    public Game(boolean hasUI) {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
        this.setFont(Game.NORMAL_FONT);
        this.setFocusTraversalKeysEnabled(false);

        this.hasUI = hasUI;
        this.loop = new Loop(this, 25.0d);
        this.interfaces = new ArrayList<>();
        this.selectables = new ArrayList<>();
        this.animations = new ArrayList<>();
    }

    public void launch() {
        new Thread(this.loop).start();
    }

    public void quit() {
        this.loop.stop();
    }

    public void openUI(UI ui) {
        this.interfaces.forEach(i -> i.cleanup(this));

        this.interfaces.clear();
        this.addUI(ui);

        this.selected = 0;
        this.selectables.clear();
        this.selectables.addAll(ui.getSelectables());
        this.updateSelected();
    }

    private void addUI(UI ui) {
        ui.initialize(this);
        this.interfaces.add(ui);

        ui.getChildren().forEach(this::addUI);
    }

    private void updateSelected() {
        for (var i = 0; i < this.selectables.size(); i++) {
            this.selectables.get(i).setSelected(i == this.selected);
        }
    }

    public void startAnimation(int length, Function<Animation, Animation> builder) {
        this.animations.add(builder.apply(new Animation(length)).callback(length, this.animations::remove));
    }

    @Override
    public void start() {
        this.openUI(this.hasUI ? new MainUI() : new GameUI());
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    @Override
    public void update() {
        new ArrayList<>(this.interfaces).forEach(ui -> ui.update(this));
        new ArrayList<>(this.animations).forEach(Animation::update);
    }

    @Override
    public void render() {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        new ArrayList<>(this.interfaces).forEach(ui -> ui.render(this, g2d));
        new ArrayList<>(this.animations).forEach(animation -> animation.render(g2d));
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

        new ArrayList<>(this.interfaces).forEach(ui -> ui.keyPressed(this, e.getExtendedKeyCode(), e.getKeyChar()));

        this.updateSelected();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void initialize(Logger logger) {
        this.logger = logger;
        this.current = new Current(this);

        this.current.initialize();
    }

    public Logger getLogger() {
        return this.logger;
    }

    public Current getCurrent() {
        return this.current;
    }
}
