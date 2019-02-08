package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.ui.impl.LogUI;
import io.houf.dungeoncrawler.validator.impl.BackpackValidator;

public class UseCommand implements Command {
    private final Argument<Item> item = new Argument<>("item", "The item to use", true, new BackpackValidator());

    @Override
    public String getName() {
        return "use";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[]{
            this.item
        };
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        var player = game.getCurrent().player;
        var item = arguments.get(this.item);
        var index = player.items.indexOf(item);
        var replacement = item.onUse(game);

        if (replacement == null) {
            player.items.remove(index);
        } else {
            player.items.set(index, replacement);
        }

        return null;
    }
}
