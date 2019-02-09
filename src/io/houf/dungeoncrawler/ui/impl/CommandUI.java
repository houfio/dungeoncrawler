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
            // Add blinking cursor to text
            text += "_";
        }

        g.setColor(Color.WHITE);
        g.drawString(text, 10, 470);
    }

    @Override
    public void keyPressed(Game game, int code, char key) {
        if (!this.selected) {
            // Don't bother when not selected
            return;
        }

        if (code == KeyEvent.VK_ENTER) {
            // Don't submit if no command is given
            if (this.command.toString().trim().length() > 0) {
                var str = this.command.toString();

                // Execute the given command
                this.executeCommand(game, str);

                if (this.history.size() == 0 || !this.history.get(0).equals(str)) {
                    // Add command to history if not the same as last command
                    this.history.add(0, str);
                }

                // Reset command
                this.command.setLength(0);
                // Reset history index
                this.back = 0;
            }
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            if (this.command.length() > 0) {
                // Remove last character from command
                this.command.setLength(this.command.length() - 1);
                this.updateSuggested(game);
            }
        } else if (code == KeyEvent.VK_TAB) {
            if (!this.suggested.isEmpty() && this.suggested.length() >= this.command.length()) {
                // Update command to suggested if at least the same length (otherwise you may reset the command if there's no suggestion)
                this.command.setLength(0);
                this.command.append(this.suggested).append(" ");
            }
        } else if (code == KeyEvent.VK_UP) {
            if (this.back < this.history.size()) {
                // Increment history index
                this.back++;

                //Set command to history entry
                this.command.setLength(0);
                this.command.append(this.history.get(this.back - 1));

                this.updateSuggested(game);
            }
        } else if (code == KeyEvent.VK_DOWN) {
            if (this.back > 0) {
                // Decrement history index
                this.back--;
                this.command.setLength(0);

                if (this.back > 0) {
                    // If bigger than zero get history entry
                    this.command.append(this.history.get(this.back - 1));
                }

                this.updateSuggested(game);
            }
        } else if ((key != ' ' || !this.command.toString().endsWith(" ")) && this.isPrintableChar(key)) {
            // Append typed character
            this.command.append(key);
            this.updateSuggested(game);
        }
    }

    private boolean isPrintableChar(char c) {
        // Get unicode block from character
        var block = Character.UnicodeBlock.of(c);

        // Make sure typed key is not undefined nor a special character
        return !Character.isISOControl(c) && c != KeyEvent.CHAR_UNDEFINED && block != null && block != Character.UnicodeBlock.SPECIALS;
    }

    private void updateSuggested(Game game) {
        // Update command suggestion
        this.suggested = this.handler.getSuggested(game, this.command.toString());
    }

    @Override
    public void setSelected(Game game, boolean selected) {
        this.selected = selected;
    }

    @Override
    public void executeCommand(Game game, String command) {
        // Make sure capitalization doesn't matter in command
        var result = this.handler.handle(game, command.toLowerCase());

        // Reset suggestion
        this.suggested = "";

        if (result != null) {
            // Print command result if not null
            this.log.addLine(result.line, result.color);
        }
    }

    @Override
    public void printLine(String text, Color color) {
        this.log.addLine(text, color);
    }
}
