package io.houf.dungeoncrawler.game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Side {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static List<String> getSides() {
        return Arrays.stream(Side.values())
            .map(side -> side.name().toLowerCase())
            .collect(Collectors.toList());
    }
}
