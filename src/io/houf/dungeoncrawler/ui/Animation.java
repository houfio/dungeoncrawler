package io.houf.dungeoncrawler.ui;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Animation {
    public final int length;

    private final Map<Integer, Consumer<Graphics2D>> keyframes;
    private final Map<Integer, Runnable> actions;
    private final Map<Integer, Consumer<Animation>> callbacks;

    private int frame = 0;

    public Animation(int length) {
        this.length = length;
        this.keyframes = new LinkedHashMap<>();
        this.actions = new LinkedHashMap<>();
        this.callbacks = new HashMap<>();
    }

    public Animation keyframe(int frame, Consumer<Graphics2D> renderer) {
        this.keyframes.put(frame, renderer);

        return this;
    }

    public Animation action(int frame, Runnable action) {
        this.actions.put(frame, action);

        return this;
    }

    public Animation callback(int frame, Consumer<Animation> callback) {
        this.callbacks.put(frame, callback);

        return this;
    }

    public void update() {
        this.actions.entrySet().stream()
            .filter(entry -> this.frame > entry.getKey())
            .reduce((a, b) -> b)
            .ifPresent(entry -> entry.getValue().run());
        this.callbacks.entrySet().stream()
            .filter(entry -> entry.getKey() == this.frame)
            .findFirst()
            .ifPresent(entry -> entry.getValue().accept(this));

        this.frame++;
    }

    public void render(Graphics2D g) {
        this.keyframes.entrySet().stream()
            .filter(entry -> this.frame > entry.getKey())
            .reduce((a, b) -> b)
            .ifPresent(entry -> entry.getValue().accept(g));
    }
}
