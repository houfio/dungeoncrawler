package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogUI extends UI {
    public static final int MAX_WIDTH = 29;
    public static final int MAX_LINES = 25;

    private final List<LogLine> lines;

    public LogUI() {
        this.lines = new ArrayList<>();
    }

    @Override
    public void initialize(Game game) {
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(450, 0, 300, 450);

        g.setColor(Color.WHITE);
        g.setFont(Game.BIG_FONT);
        g.drawString("Game Log", 465, 30);
        g.setFont(Game.NORMAL_FONT);

        var offset = 0;
        var lines = new ArrayList<>(this.lines);

        Collections.reverse(lines);

        for (var i = 0; i < lines.size(); i++) {
            if (offset > LogUI.MAX_LINES) {
                break;
            }

            var line = lines.get(i);

            g.setColor(new Color(line.color.getRed(), line.color.getGreen(), line.color.getBlue(), i == 0 ? 255 : 155));

            for (var text : line.text) {
                g.drawString(text, 465, 58 + offset * 15);

                if (++offset > LogUI.MAX_LINES) {
                    break;
                }
            }
        }
    }

    public void addLine(String line, Color color) {
        var lines = new ArrayList<String>();
        var words = line.split(" ");

        lines.add("$");

        for (var word : words) {
            var last = lines.get(lines.size() - 1);
            var added = last + " " + word;

            if (added.length() > LogUI.MAX_WIDTH || last.endsWith("\n")) {
                lines.add(word);

                continue;
            }

            lines.set(lines.size() - 1, added);
        }

        this.lines.add(new LogLine(lines, color));
    }

    public static class RawLogLine {
        public final String line;
        public final Color color;

        public RawLogLine(String line, Color color) {
            this.line = line;
            this.color = color;
        }
    }

    class LogLine {
        public final List<String> text;
        public final Color color;

        LogLine(List<String> text, Color color) {
            this.text = text;
            this.color = color;
        }
    }
}
