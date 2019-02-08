package io.houf.dungeoncrawler.command;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.ui.LogLine;

public interface Command {
    String getName();

    Argument<?>[] getArguments();

    LogLine execute(Game game, ArgumentMap arguments);
}
