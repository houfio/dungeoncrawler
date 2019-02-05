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
        this.x = 1;
        this.y = 1;

        var room0 = new Room(this.game, 1, 1);
        var room1 = new Room(this.game, 0, 1);
        var room2 = new Room(this.game, 1, 0);
        var room3 = new Room(this.game, 2, 1);
        var room4 = new Room(this.game, 1, 2);

        this.floor.addRoom(room0);
        this.floor.addRoom(room1);
        this.floor.addRoom(room2);
        this.floor.addRoom(room3);
        this.floor.addRoom(room4);
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
