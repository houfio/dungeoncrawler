package io.houf.dungeoncrawler;

import io.houf.dungeoncrawler.game.Side;
import io.houf.dungeoncrawler.room.Room;
import io.houf.dungeoncrawler.ui.impl.CommandUI;

import java.util.ArrayList;
import java.util.List;

public class Ingame {
    public final List<Room> rooms;

    private final Input input;

    private int currentRoom = 0;

    public Ingame(CommandUI command) {
        this.rooms = new ArrayList<>();
        this.input = new Input(System.in) {
            @Override
            public void handle(String input) {
                command.executeCommand(input);
            }
        };
    }

    public void initialize() {
        var entrance = new Room(this);
        var above = new Room(this);

        entrance.addExit(Side.NORTH, above);

        new Thread(this.input::start).start();
    }

    public void cleanup() {
        this.input.stop();
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
