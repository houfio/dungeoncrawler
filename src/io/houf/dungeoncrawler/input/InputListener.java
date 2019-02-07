package io.houf.dungeoncrawler.input;

import io.houf.dungeoncrawler.Game;

@FunctionalInterface
public interface InputListener {
    void apply(Game game, String input);
}
