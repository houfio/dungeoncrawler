package io.houf.dungeoncrawler.entity.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.ui.Sprite;
import io.houf.dungeoncrawler.ui.impl.CongratulationsUI;

public class HoleEntity extends Entity {
    public HoleEntity() {
        super(new Sprite("entity/hole", 128, 100), 160, 25, 128, 128);
    }

    public void escape(Game game) {
        if (game.hasUI) {
            game.openUI(new CongratulationsUI());
        } else {
            game.quit();
        }
    }
}
