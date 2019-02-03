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
        var delta = 0.0d;
        var upns = 1000000000.0d / this.ups;
        var timer = System.currentTimeMillis();

        var updates = 0;
        var frames = 0;

        while (this.running) {
            var now = System.nanoTime();

            delta += (now - lastTime) / upns;
            lastTime = now;
            frames++;

            if (delta >= 1.0) {
                this.loopable.update();

                updates++;
                delta--;
            }

            this.loopable.render();

            if (System.currentTimeMillis() - timer > 1000.0d) {
                timer += 1000.0d;

                this.loopable.setLoopData(updates, frames);

                updates = 0;
                frames = 0;
            }
        }

        this.loopable.stop();
    }
}
