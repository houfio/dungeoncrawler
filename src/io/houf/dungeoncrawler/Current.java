package io.houf.dungeoncrawler;

import io.houf.dungeoncrawler.entity.impl.GateEntity;
import io.houf.dungeoncrawler.entity.impl.HoleEntity;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;
import io.houf.dungeoncrawler.entity.impl.PlayerEntity;
import io.houf.dungeoncrawler.input.Input;
import io.houf.dungeoncrawler.input.InputListener;
import io.houf.dungeoncrawler.item.impl.KeyItem;
import io.houf.dungeoncrawler.item.impl.StepladderItem;
import io.houf.dungeoncrawler.room.Encounter;
import io.houf.dungeoncrawler.room.Floor;
import io.houf.dungeoncrawler.room.Room;
import io.houf.dungeoncrawler.room.Side;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Current implements InputListener {
    public final Floor floor;
    public final PlayerEntity player;

    private final Game game;
    private final Input input;

    private int x = 0;
    private int y = 0;

    public Current(Game game) {
        this.floor = new Floor(10, 10);
        this.player = new PlayerEntity();
        this.game = game;
        this.input = new Input(this.game, System.in);
    }

    public void initialize() {
        // Add the rooms to the floor
        Arrays.stream(this.getRooms())
            .forEach(room -> this.floor.addRoom(this.game, this.player, room));
        this.floor.initialize(this.game);

        // Set the start location
        this.setLocation(2, 2);

        // Start listening to console commands
        this.input.addListener(this);
        new Thread(this.input::start).start();
    }

    public void cleanup() {
        // Stop the input listener
        this.input.stop();
    }

    private Room[] getRooms() {
        return new Room[]{
            new Room(1, 0, new Encounter(new ItemEntity(new KeyItem(), 40.0f, 115.0f), 1.0d)),
            new Room(2, 0),
            new Room(4, 0),
            new Room(0, 1),
            new Room(2, 1),
            new Room(3, 1),
            new Room(4, 1),
            new Room(0, 2),
            new Room(1, 2),
            new Room(2, 2, new Encounter(new HoleEntity(), 1.0d)),
            new Room(4, 2),
            new Room(5, 2, new Encounter(new GateEntity(190.0f, 113.0f), 1.0d)),
            new Room(2, 3),
            new Room(3, 3, new Encounter(new ItemEntity(new KeyItem(), 190.0f, 40.0f), 1.0d)),
            new Room(0, 4, new Encounter(new GateEntity(113.0f, 40.0f), 1.0d)),
            new Room(2, 4),
            new Room(3, 4),
            new Room(4, 4),
            new Room(5, 4),
            new Room(0, 5),
            new Room(1, 5),
            new Room(2, 5),
            new Room(4, 5, new Encounter(new ItemEntity(new StepladderItem(), 115.0f, 190.0f), 1.0d))
        };
    }

    public Room currentRoom() {
        // Get room on current location
        return this.floor.getRoom(this.x, this.y);
    }

    public boolean move(Side side) {
        var newRoom = this.currentRoom().getRoomOnSide(side);

        if (newRoom == null) {
            // If there's no room on that side, give up
            return false;
        }

        if (this.game.hasUI) {
            // Start a fancy animation in ui mode
            this.game.startAnimation(50, a -> a
                .action(0, () -> {
                    this.player.move(side.x * (114.0f / 25.0f), side.y * (109.0f / 25.0f));
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
                    this.setLocation(newRoom.x, newRoom.y);

                    if (side.horizontal) {
                        this.player.setLocation(side == Side.EAST ? 0.0f : 228.0f, -1.0f);
                    } else {
                        this.player.setLocation(-1.0f, side == Side.SOUTH ? 0.0f : 218.0f);
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
        } else {
            // Just update the location in console mode
            this.setLocation(newRoom.x, newRoom.y);
        }

        return true;
    }

    public void moveToRandom() {
        var random = new Random();
        var xNew = this.x;
        var yNew = this.y;
        Room room = null;

        // Update location while there's no room in random location or random location is the same as the current location
        while (xNew == this.x || yNew == this.y || room == null) {
            xNew = random.nextInt(this.floor.width);
            yNew = random.nextInt(this.floor.height);
            room = this.floor.getRoom(xNew, yNew);
        }

        // Update location to random one
        this.setLocation(xNew, yNew);
    }

    private void setLocation(int x, int y) {
        this.x = x;
        this.y = y;

        // Call room enter handler
        this.currentRoom().onEnter(this.game);
    }

    @Override
    public void apply(Game game, String input) {
        // forward all input to the command handler
        game.getLogger().executeCommand(game, input);
    }
}
