package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.Logger;
import io.houf.dungeoncrawler.command.CommandHandler;
import io.houf.dungeoncrawler.ui.Selectable;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandUI extends UI implements Selectable, Logger {
    private final StringBuilder command;
    private final CommandHandler handler;
    private final LogUI log;
    private final List<String> history;

    private String suggested = "";
    private boolean selected = false;
    private int blink = 0;
    private int back = 0;

    public CommandUI() {
        this.command = new StringBuilder();
        this.handler = new CommandHandler();
        this.log = new LogUI();
        this.history = new ArrayList<>();
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
            if (this.command.toString().trim().length() > 0) {
                var str = this.command.toString();

                this.executeCommand(game, str);

                if (this.history.size() == 0 || !this.history.get(0).equals(str)) {
                    this.history.add(0, str);
                }

                this.command.setLength(0);
                this.back = 0;
            }
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            if (this.command.length() > 0) {
                this.command.setLength(this.command.length() - 1);
                this.updateSuggested(game);
            }
        } else if (code == KeyEvent.VK_TAB) {
            if (!this.suggested.isEmpty() && this.suggested.length() > this.command.length()) {
                this.command.setLength(0);
                this.command.append(this.suggested);
            }
        } else if (code == KeyEvent.VK_UP) {
            if (this.back < this.history.size()) {
                this.back++;

                this.command.setLength(0);
                this.command.append(this.history.get(this.back - 1));

                this.updateSuggested(game);
            }
        } else if (code == KeyEvent.VK_DOWN) {
            if (this.back > 0) {
                this.back--;
                this.command.setLength(0);

                if (this.back > 0) {
                    this.command.append(this.history.get(this.back - 1));
                }

                this.updateSuggested(game);
            }
        } else if ((key != ' ' || !this.command.toString().endsWith(" ")) && this.isPrintableChar(key)) {
            this.command.append(key);
            this.updateSuggested(game);
        }
    }

    private boolean isPrintableChar(char c) {
        var block = Character.UnicodeBlock.of(c);

        return !Character.isISOControl(c) && c != KeyEvent.CHAR_UNDEFINED && block != null && block != Character.UnicodeBlock.SPECIALS;
    }

    private void updateSuggested(Game game) {
        this.suggested = this.handler.getSuggested(game, this.command.toString());
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void executeCommand(Game game, String command) {
        var result = this.handler.handle(game, command.toLowerCase());

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
