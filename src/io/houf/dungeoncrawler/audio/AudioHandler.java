package io.houf.dungeoncrawler.audio;

import io.houf.dungeoncrawler.Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioHandler {
    private final Map<String, Clip> clips;

    private boolean enabled;

    public AudioHandler(Game game) {
        this.clips = new HashMap<>();

        // Disabled in console mode, also impossible to enable
        this.enabled = game.hasUI;
    }

    public void play(String name) {
        if (!this.enabled) {
            // Don't do anything if audio is disabled
            return;
        } else if (!this.clips.containsKey(name)) {
            try {
                // Get stream from wav file
                var stream = AudioSystem.getAudioInputStream(new BufferedInputStream(AudioHandler.class.getResourceAsStream("/assets/audio/" + name + ".wav")));
                var clip = AudioSystem.getClip();

                // Open the stream in the created clip
                clip.open(stream);

                // Save clip for later reference
                this.clips.put(name, clip);
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException | NullPointerException e) {
                // Give message when something breaks
                System.err.println("Unable to load audio effect");
                e.printStackTrace();
            }
        }

        var clip = this.clips.get(name);

        if (clip == null) {
            // If something happened while loading the clip, don't play it
            return;
        } else if (clip.isRunning()) {
            clip.stop();
        }

        // Replay clip from beginning
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
