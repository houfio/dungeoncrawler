package io.houf.dungeoncrawler.loop;

public interface Loopable {
    void start();

    void stop();

    void update();

    void render();

    void setLoopData(int updates, int frames);
}
