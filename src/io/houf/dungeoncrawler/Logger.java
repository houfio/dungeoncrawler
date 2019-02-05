package io.houf.dungeoncrawler;

import java.awt.*;

public interface Logger {
    void executeCommand(Game game, String command);

    void printLine(String text, Color color);
}
