package io.houf.dungeoncrawler;

import io.houf.dungeoncrawler.room.Side;
import io.houf.dungeoncrawler.room.Floor;
import io.houf.dungeoncrawler.room.Room;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ingame {
    public final Floor floor;

    private final Game game;

    private int x = 0;
    private int y = 0;

    public Ingame(Game game) {
        this.floor = new Floor(10, 10);
        this.game = game;
    }

    public void initialize() {
        var room0 = new Room(0, 0);
        var room1 = new Room(0, 2);
        var room2 = new Room(0, 4);
        var room3 = new Room(1, 0);
        var room4 = new Room(1, 2);
        var room5 = new Room(1, 3);
        var room6 = new Room(1, 4);
        var room7 = new Room(2, 0);
        var room8 = new Room(2, 1);
        var room9 = new Room(2, 2);
        var room10 = new Room(2, 4);
        var room11 = new Room(2, 5);
        var room12 = new Room(3, 2);
        var room13 = new Room(3, 3);
        var room14 = new Room(4, 0);
        var room15 = new Room(4, 2);
        var room16 = new Room(4, 3);
        var room17 = new Room(4, 4);
        var room18 = new Room(4, 5);
        var room19 = new Room(5, 0);
        var room20 = new Room(5, 1);
        var room21 = new Room(5, 2);
        var room22 = new Room(5, 4);

        this.floor.addRoom(room0);
        this.floor.addRoom(room1);
        this.floor.addRoom(room2);
        this.floor.addRoom(room3);
        this.floor.addRoom(room4);
        this.floor.addRoom(room5);
        this.floor.addRoom(room6);
        this.floor.addRoom(room7);
        this.floor.addRoom(room8);
        this.floor.addRoom(room9);
        this.floor.addRoom(room10);
        this.floor.addRoom(room11);
        this.floor.addRoom(room12);
        this.floor.addRoom(room13);
        this.floor.addRoom(room14);
        this.floor.addRoom(room15);
        this.floor.addRoom(room16);
        this.floor.addRoom(room17);
        this.floor.addRoom(room18);
        this.floor.addRoom(room19);
        this.floor.addRoom(room20);
        this.floor.addRoom(room21);
        this.floor.addRoom(room22);

        this.x = room9.x;
        this.y = room9.y;

        this.currentRoom().onEnter(this.game);
    }

    public Room currentRoom() {
        return this.floor.getRoom(this.x, this.y);
    }

    public List<Side> getDoors() {
        return Arrays.stream(Side.values())
            .filter(side -> this.floor.getRoom(this.x + side.x, this.y + side.y) != null)
            .collect(Collectors.toList());
    }

    public boolean move(Side side) {
        var xNew = this.x + side.x;
        var yNew = this.y + side.y;
        var newRoom = this.floor.getRoom(xNew, yNew);

        if (newRoom == null) {
            return false;
        }

        this.game.startAnimation(50, a -> a
            .action(0, () -> {
                this.currentRoom().player.x += side.x * (114.0d / 25.0d);
                this.currentRoom().player.y += side.y * (109.0d / 25.0d);
            })
            .keyframe(0, g -> {
                g.setColor(new Color(0, 0, 0, 55));
                g.fillRect(75, 75, 300, 300);
            })
            .keyframe(10, g -> {
                g.setColor(new Color(0, 0, 0, 155));
                g.fillRect(75, 75, 300, 300);
            })
            .keyframe(20, g -> {
                g.setColor(new Color(0, 0, 0, 255));
                g.fillRect(75, 75, 300, 300);
            })
            .callback(25, a1 -> {
                this.x = xNew;
                this.y = yNew;

                if (side.horizontal) {
                    this.currentRoom().player.x = side == Side.EAST ? 0 : 228;
                } else {
                    this.currentRoom().player.y = side == Side.SOUTH ? 0 : 218;
                }

                this.currentRoom().onEnter(this.game);
            })
            .keyframe(30, g -> {
                g.setColor(new Color(0, 0, 0, 155));
                g.fillRect(75, 75, 300, 300);
            })
            .keyframe(40, g -> {
                g.setColor(new Color(0, 0, 0, 55));
                g.fillRect(75, 75, 300, 300);
            }));

        return true;
    }
}
