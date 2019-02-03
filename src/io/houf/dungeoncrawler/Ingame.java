package io.houf.dungeoncrawler;

import io.houf.dungeoncrawler.game.Side;
import io.houf.dungeoncrawler.room.Room;

import java.util.ArrayList;
import java.util.List;

public class Ingame {
    public final List<Room> rooms;

    private int currentRoom = 0;

    public Ingame() {
        this.rooms = new ArrayList<>();
    }

    public void initialize() {
        var entrance = new Room(this);
        var above = new Room(this);

        entrance.addExit(Side.NORTH, above);
    }

    public Room currentRoom() {
        return this.rooms.get(this.currentRoom);
    }

    public boolean move(Side side) {
        var current = this.rooms.get(this.currentRoom);
        var next = current.exits.get(side);

        if (next == null) {
            return false;
        }

        this.currentRoom = this.rooms.indexOf(next);

        return true;
    }
}
