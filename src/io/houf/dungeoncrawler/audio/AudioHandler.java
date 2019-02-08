package io.houf.dungeoncrawler.audio;

import io.houf.dungeoncrawler.Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioHandler {
    private final Game game;
    private final Map<String, Clip> clips;

    public AudioHandler(Game game) {
        this.game = game;
        this.clips = new HashMap<>();
    }

    public void play(String name) {
        if (!this.game.hasUI) {
            return;
        } else if (!this.clips.containsKey(name)) {
            try {
                var stream = AudioSystem.getAudioInputStream(AudioHandler.class.getResourceAsStream("/assets/audio/" + name + ".wav"));

                var clip = AudioSystem.getClip();
                clip.open(stream);

                this.clips.put(name, clip);
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                System.err.println("Unable to load audio effect");
                e.printStackTrace();
            }
        }

        var clip = this.clips.get(name);

        if (clip == null) {
            return;
        } else if (clip.isRunning()) {
            clip.stop();
        }

        clip.setFramePosition(0);
        clip.start();
    }
}
