package io.houf.dungeoncrawler.command;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.impl.LogUI;

public interface Command {
    String getName();

    Argument<?>[] getArguments();

    LogUI.RawLogLine execute(Game game, ArgumentMap arguments);
}
