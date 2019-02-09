package io.houf.dungeoncrawler.input;

import io.houf.dungeoncrawler.Game;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Input {
    private final Game game;
    private final InputStream input;
    private final List<InputListener> listeners;

    private boolean listening;

    public Input(Game game, InputStream input) {
        this.game = game;
        this.input = input;
        this.listeners = new ArrayList<>();
    }

    public void addListener(InputListener listener) {
        this.listeners.add(listener);
    }

    public void start() {
        this.listening = true;

        try (var scanner = new Scanner(this.input)) {
            while (scanner.hasNext()) {
                var input = scanner.nextLine().trim();

                if (!this.listening) {
                    // Break the loop if stopped
                    break;
                } else if (input.isEmpty()) {
                    // Ignore empty input
                    continue;
                }

                this.listeners.forEach(listener -> listener.apply(this.game, input));
            }
        }
    }

    public void stop() {
        this.listening = false;
    }
}
