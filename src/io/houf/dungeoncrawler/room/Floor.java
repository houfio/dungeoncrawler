package io.houf.dungeoncrawler.room;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.PlayerEntity;

public class Floor {
    private final int width;
    private final int height;
    private final Room[][] rooms;

    public Floor(int width, int height) {
        this.width = width;
        this.height = height;
        this.rooms = new Room[this.width][this.height];
    }

    public void addRoom(Game game, PlayerEntity player, Room room) {
        room.addEntity(game, player);

        this.rooms[room.x][room.y] = room;
    }

    public Room getRoom(int x, int y) {
        if (x < 0 || x >= this.width - 1 || y < 0 || y >= this.height - 1) {
            return null;
        }

        return this.rooms[x][y];
    }
}
