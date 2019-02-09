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

    private int tick = 0;

    public Animation(int length) {
        this.length = length;
        this.keyframes = new LinkedHashMap<>();
        this.actions = new LinkedHashMap<>();
        this.callbacks = new HashMap<>();
    }

    public Animation keyframe(int tick, Consumer<Graphics2D> renderer) {
        // Called every frame following the tick
        this.keyframes.put(tick, renderer);

        return this;
    }

    public Animation action(int tick, Runnable action) {
        // Called every update following the tick
        this.actions.put(tick, action);

        return this;
    }

    public Animation callback(int tick, Consumer<Animation> callback) {
        // Called once on tick
        this.callbacks.put(tick, callback);

        return this;
    }

    public void update() {
        this.actions.entrySet().stream()
            .filter(entry -> this.tick > entry.getKey())
            .reduce((a, b) -> b)
            .ifPresent(entry -> entry.getValue().run());
        this.callbacks.entrySet().stream()
            .filter(entry -> entry.getKey() == this.tick)
            .findFirst()
            .ifPresent(entry -> entry.getValue().accept(this));

        // Increment animation tick
        this.tick++;
    }

    public void render(Graphics2D g) {
        this.keyframes.entrySet().stream()
            .filter(entry -> this.tick > entry.getKey())
            .reduce((a, b) -> b)
            .ifPresent(entry -> entry.getValue().accept(g));
    }
}
