package io.houf.dungeoncrawler.room;

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
}
