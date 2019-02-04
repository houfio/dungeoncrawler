package io.houf.dungeoncrawler.room;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Side {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    public final int x;
    public final int y;

    Side(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static List<String> getSides() {
        return Arrays.stream(Side.values())
            .map(side -> side.name().toLowerCase())
            .collect(Collectors.toList());
    }

    public Side opposite() {
        var values = Arrays.asList(Side.values());

        return values.get((values.indexOf(this) + 2) % values.size());
    }
}
