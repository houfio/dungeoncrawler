package io.houf.dungeoncrawler.room;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.impl.PlayerEntity;

public class Floor {
    public final int width;
    public final int height;

    private final Room[][] rooms;

    public Floor(int width, int height) {
        this.width = width;
        this.height = height;
        // Give floor a fixed width and height
        this.rooms = new Room[this.width][this.height];
    }

    public void addRoom(Game game, PlayerEntity player, Room room) {
        // Make sure all rooms have the player instance as entity
        room.addEntity(game, player);

        this.rooms[room.x][room.y] = room;
    }

    public Room getRoom(int x, int y) {
        // Return null if out of bounds to prevent crashes
        if (x < 0 || x >= this.width - 1 || y < 0 || y >= this.height - 1) {
            return null;
        }

        return this.rooms[x][y];
    }

    public void initialize(Game game) {
        for (var i = 0; i < this.width; i++) {
            for (var j = 0; j < this.height; j++) {
                var room = this.rooms[i][j];

                if (room != null) {
                    // Initialize room
                    room.initialize(game);
                }
            }
        }
    }
}
