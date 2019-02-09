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
    private final Map<String, Clip> clips;

    private boolean enabled;

    public AudioHandler(Game game) {
        this.clips = new HashMap<>();

        this.enabled = game.hasUI;
    }

    public void play(String name) {
        if (!this.enabled) {
            return;
        } else if (!this.clips.containsKey(name)) {
            try {
                var stream = AudioSystem.getAudioInputStream(AudioHandler.class.getResourceAsStream("/assets/audio/" + name + ".wav"));

                var clip = AudioSystem.getClip();
                clip.open(stream);

                this.clips.put(name, clip);
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException | NullPointerException e) {
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

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public boolean enabled() {
        return this.enabled;
    }
}
