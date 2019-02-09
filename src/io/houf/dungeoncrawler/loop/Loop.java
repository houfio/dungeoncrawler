package io.houf.dungeoncrawler.loop;

public class Loop implements Runnable {
    private final Loopable loopable;
    private final double ups;
    private final double fps;

    private boolean running = true;

    public Loop(Loopable loopable, double ups, double fps) {
        this.loopable = loopable;
        this.ups = ups;
        this.fps = fps;
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        this.loopable.start();

        var last = System.nanoTime();
        var updateDelta = 0.0d;
        var frameDelta = 0.0d;
        var upns = 1000000000.0d / this.ups;
        var fpns = 1000000000.0d / this.fps;

        while (this.running) {
            var now = System.nanoTime();

            // Add delta between last loop and this one divided by target delta in ns
            updateDelta += (now - last) / upns;
            frameDelta += (now - last) / fpns;
            // Update last loop time
            last = now;

            // Lock the updates so you don't walk faster on better systems, looking at you Bethesda
            if (updateDelta >= 1.0d) {
                this.loopable.update();

                updateDelta--;
            }

            // Lock the frames, otherwise the game uses up all system resources to produce almost a million frames per second...
            if (frameDelta >= 1.0d) {
                this.loopable.render();

                frameDelta--;
            }
        }

        this.loopable.stop();
    }
}
