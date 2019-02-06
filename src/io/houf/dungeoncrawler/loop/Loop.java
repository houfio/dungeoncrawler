package io.houf.dungeoncrawler.loop;

public class Loop implements Runnable {
    private final Loopable loopable;
    private final double ups;

    private boolean running = true;

    public Loop(Loopable loopable, double ups) {
        this.loopable = loopable;
        this.ups = ups;
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        this.loopable.start();

        var lastTime = System.nanoTime();
        var updateDelta = 0.0d;
        var frameDelta = 0.0d;
        var upns = 1000000000.0d / this.ups;
        var fpns = 1000000000.0d / 300.0d;

        while (this.running) {
            var now = System.nanoTime();

            updateDelta += (now - lastTime) / upns;
            frameDelta += (now - lastTime) / fpns;
            lastTime = now;

            if (updateDelta >= 1.0d) {
                this.loopable.update();

                updateDelta--;
            }

            if (frameDelta >= 1.0d) {
                this.loopable.render();

                frameDelta--;
            }
        }

        this.loopable.stop();
    }
}
