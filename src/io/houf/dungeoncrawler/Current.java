package io.houf.dungeoncrawler;

import io.houf.dungeoncrawler.entity.impl.GateEntity;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;
import io.houf.dungeoncrawler.entity.impl.PlayerEntity;
import io.houf.dungeoncrawler.input.Input;
import io.houf.dungeoncrawler.input.InputListener;
import io.houf.dungeoncrawler.item.impl.KeyItem;
import io.houf.dungeoncrawler.item.impl.StepladderItem;
import io.houf.dungeoncrawler.room.Floor;
import io.houf.dungeoncrawler.room.Room;
import io.houf.dungeoncrawler.room.Side;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        Arrays.stream(this.getRooms())
            .forEach(room -> this.floor.addRoom(this.game, this.player, room));

        this.x = 2;
        this.y = 2;

        this.currentRoom().onEnter(this.game);

        this.input.addListener(this);
        new Thread(this.input::start).start();
    }

    public void cleanup() {
        this.input.stop();
    }

    private Room[] getRooms() {
        return new Room[] {
            new Room(1, 0, new Room.Encounter(new ItemEntity(new KeyItem(), 40.0f, 115.0f), 1.0d)),
            new Room(2, 0),
            new Room(4, 0),
            new Room(0, 1),
            new Room(2, 1),
            new Room(3, 1),
            new Room(4, 1),
            new Room(0, 2),
            new Room(1, 2),
            new Room(2, 2, new Room.Encounter[0]),
            new Room(4, 2),
            new Room(5, 2, new Room.Encounter(new GateEntity(190.0f, 115.0f), 1.0d)),
            new Room(2, 3),
            new Room(3, 3, new Room.Encounter(new ItemEntity(new KeyItem(), 190.0f, 40.0f), 1.0d)),
            new Room(0, 4, new Room.Encounter(new GateEntity(115.0f, 40.0f), 1.0d)),
            new Room(2, 4),
            new Room(3, 4),
            new Room(4, 4),
            new Room(5, 4),
            new Room(0, 5),
            new Room(1, 5),
            new Room(2, 5),
            new Room(4, 5, new Room.Encounter(new ItemEntity(new StepladderItem(), 115.0f, 190.0f), 1.0d))
        };
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

        if (game.hasUI) {
            this.game.startAnimation(50, a -> a
                .action(0, () -> {
                    this.player.setX(this.player.getX() + side.x * (114.0f / 25.0f));
                    this.player.setY(this.player.getY() + side.y * (109.0f / 25.0f));
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
                    this.setLocation(xNew, yNew);

                    if (side.horizontal) {
                        this.player.setX(side == Side.EAST ? 0 : 228);
                    } else {
                        this.player.setY(side == Side.SOUTH ? 0 : 218);
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
            this.setLocation(xNew, yNew);
        }

        return true;
    }

    private void setLocation(int x, int y) {
        this.x = x;
        this.y = y;

        this.currentRoom().onEnter(this.game);
    }

    @Override
    public void apply(Game game, String input) {
        game.getLogger().executeCommand(game, input);
    }
}
