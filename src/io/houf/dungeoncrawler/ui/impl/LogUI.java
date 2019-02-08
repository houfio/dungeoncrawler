package io.houf.dungeoncrawler.ui.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogUI extends UI {
    private final List<Line> lines;

    public LogUI() {
        this.lines = new ArrayList<>();
    }

    @Override
    public void render(Game game, Graphics2D g) {
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
            var maxLines = 25;

            if (offset > maxLines) {
                break;
            }

            var line = lines.get(i);

            g.setColor(new Color(line.color.getRed(), line.color.getGreen(), line.color.getBlue(), i == 0 ? 255 : 155));

            for (var text : line.text) {
                g.drawString(text, 465, 58 + offset * 15);

                if (++offset > maxLines) {
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

            var maxWidth = 29;

            if (added.length() > maxWidth || last.endsWith("\n")) {
                lines.add(word);

                continue;
            }

            lines.set(lines.size() - 1, added);
        }

        this.lines.add(new Line(lines, color));

        System.out.println(line);
    }

    private class Line {
        private final List<String> text;
        private final Color color;

        private Line(List<String> text, Color color) {
            this.text = text;
            this.color = color;
        }
    }
}
