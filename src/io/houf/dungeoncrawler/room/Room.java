package io.houf.dungeoncrawler.room;

import io.houf.dungeoncrawler.Ingame;
import io.houf.dungeoncrawler.game.Side;

import java.util.HashMap;
import java.util.Map;

public class Room {
    public final Map<Side, Room> exits;

    public Room(Ingame ingame) {
        this.exits = new HashMap<>();

        ingame.rooms.add(this);
    }

    public void addExit(Side side, Room room) {
        this.addExit(side, room, true);
    }

    public void addExit(Side side, Room room, boolean add) {
        this.exits.put(side, room);

        if (add) {
            room.addExit(side.opposite(), this, false);
        }
    }
}
