package io.houf.dungeoncrawler.room;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Side {
    NORTH(0, -1, false),
    EAST(1, 0, true),
    SOUTH(0, 1, false),
    WEST(-1, 0, true);

    public final int x;
    public final int y;
    public final boolean horizontal;

    Side(int x, int y, boolean horizontal) {
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
    }

    public static List<String> getSides() {
        return Arrays.stream(Side.values())
            .map(side -> side.name().toLowerCase())
            .collect(Collectors.toList());
    }
}
