package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.ui.impl.CongratulationsUI;

import java.awt.*;

public class StepladderItem extends Item {
    public StepladderItem() {
        super("stepladder");
    }

    @Override
    public Item onUse(Game game) {
        var room = game.getCurrent().currentRoom();

        if (room.x == 2 && room.y == 2) {
            game.getLogger().printLine("You escaped the dungeon through a hole in the ceiling. Congratulations!", Color.WHITE);

            if (game.hasUI) {
                game.openUI(new CongratulationsUI());
            } else {
                game.quit();
            }
        } else {
            game.getLogger().printLine("You tried to use the stepladder, but the ceiling was too low.", Color.ORANGE);
        }

        return this;
    }
}
