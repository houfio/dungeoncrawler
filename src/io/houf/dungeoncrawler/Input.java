package io.houf.dungeoncrawler;

import java.io.InputStream;
import java.util.Scanner;

public abstract class Input {
    private final InputStream input;

    private boolean running = true;

    public Input(InputStream input) {
        this.input = input;
    }

    public void start() {
        try (var scanner = new Scanner(this.input)) {
            while (scanner.hasNext()) {
                var input = scanner.nextLine();

                if (!this.running) {
                    break;
                }

                if (input.isEmpty()) {
                    continue;
                }

                this.handle(input);
            }
        }
    }

    public void stop() {
        this.running = false;
    }

    public abstract void handle(String input);
}
