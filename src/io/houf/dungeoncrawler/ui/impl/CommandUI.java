package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.Logger;
import io.houf.dungeoncrawler.command.CommandHandler;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

public class CommandUI extends UI implements Selectable, Logger {
    private final StringBuilder command;
    private final CommandHandler handler;
    private final LogUI log;

    private String suggested = "";
    private boolean selected = false;
    private int blink = 0;

    public CommandUI() {
        this.command = new StringBuilder();
        this.handler = new CommandHandler();
        this.log = new LogUI();
    }

    @Override
    public List<UI> getChildren() {
        return Collections.singletonList(
            this.log
        );
    }

    @Override
    public void update(Game game) {
        this.blink++;
    }

    @Override
    public void render(Game game, Graphics2D g) {
        g.setColor(new Color(32, 32, 32));
        g.fillRect(0, 450, 750, 50);

        g.setColor(Color.GRAY);
        g.drawString(this.suggested, 29, 470);

        var text = "> " + this.command;

        if (this.selected && this.blink % 30 > 15) {
            text += "_";
        }

        g.setColor(Color.WHITE);
        g.drawString(text, 10, 470);
    }

    @Override
    public void keyPressed(Game game, int code, char key) {
        if (!this.selected) {
            return;
        }

        if (code == KeyEvent.VK_ENTER) {
            this.executeCommand(game, this.command.toString());

            this.command.setLength(0);

            return;
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            if (this.command.length() > 0) {
                this.command.setLength(this.command.length() - 1);
                this.suggested = this.handler.getSuggested(this.command.toString());
            }

            return;
        } else if (code == KeyEvent.VK_TAB) {
            if (!this.suggested.isEmpty() && this.suggested.length() > this.command.length()) {
                this.command.setLength(0);
                this.command.append(this.suggested);
            }

            return;
        } else if (key == ' ' && this.command.toString().endsWith(" ")) {
            return;
        }

        this.command.append(key);
        this.suggested = this.handler.getSuggested(this.command.toString());
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void executeCommand(Game game, String command) {
        var result = this.handler.handle(game, command);

        this.suggested = "";

        if (result != null) {
            this.log.addLine(result.line, result.color);
        }
    }

    @Override
    public void printLine(String text, Color color) {
        this.log.addLine(text, color);
    }
}
