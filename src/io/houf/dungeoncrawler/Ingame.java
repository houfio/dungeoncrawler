package io.houf.dungeoncrawler;

import io.houf.dungeoncrawler.room.Side;
import io.houf.dungeoncrawler.room.Floor;
import io.houf.dungeoncrawler.room.Room;
import io.houf.dungeoncrawler.ui.impl.CommandUI;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ingame {
    public final Floor floor;

    private final Game game;
    private final Input input;

    private int x = 0;
    private int y = 0;

    public Ingame(Game game, CommandUI command) {
        this.floor = new Floor(10, 10);
        this.input = new Input(System.in) {
            @Override
            public void handle(String input) {
                command.executeCommand(input);
            }
        };
        this.game = game;
    }

    public void initialize() {
        var room0 = new Room(this.game, 0, 0);
        var room1 = new Room(this.game, 0, 1);

        this.floor.addRoom(room0);
        this.floor.addRoom(room1);

        new Thread(this.input::start).start();
    }

    public void cleanup() {
        this.input.stop();
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
            .at(0, g -> {
                g.setColor(new Color(0, 0, 0, 55));
                g.fillRect(75, 75, 300, 300);
            })
            .at(10, g -> {
                g.setColor(new Color(0, 0, 0, 155));
                g.fillRect(75, 75, 300, 300);
            })
            .at(20, g -> {
                g.setColor(new Color(0, 0, 0, 255));
                g.fillRect(75, 75, 300, 300);
            })
            .cb(20, a1 -> {
                this.x = xNew;
                this.y = yNew;
            })
            .at(30, g -> {
                g.setColor(new Color(0, 0, 0, 155));
                g.fillRect(75, 75, 300, 300);
            })
            .at(40, g -> {
                g.setColor(new Color(0, 0, 0, 55));
                g.fillRect(75, 75, 300, 300);
            }));

        return true;
    }
}
