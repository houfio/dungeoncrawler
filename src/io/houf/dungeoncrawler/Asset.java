package io.houf.dungeoncrawler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Asset {
    private static final Map<String, BufferedImage> ASSETS = new HashMap<>();

    public static BufferedImage read(String asset) {
        if (Asset.ASSETS.containsKey(asset)) {
            // If asset already read return it
            return Asset.ASSETS.get(asset);
        }

        try {
            // Load the image
            var loaded = ImageIO.read(Asset.class.getResourceAsStream("/assets/" + asset + ".png"));

            // Put it in the map
            Asset.ASSETS.put(asset, loaded);

            return loaded;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage rotate(BufferedImage image, double degrees) {
        var angle = Math.toRadians(degrees);
        var sin = Math.abs(Math.sin(angle));
        var cos = Math.abs(Math.cos(angle));
        var width = image.getWidth();
        var height = image.getHeight();
        // Calculate new image width and height
        var widthNew = (int) Math.floor(width * cos + height * sin);
        var heightNew = (int) Math.floor(height * cos + width * sin);
        var configuration = Asset.getDefaultConfiguration();
        // Create new empty image with new width and height
        var result = configuration.createCompatibleImage(widthNew, heightNew, Transparency.TRANSLUCENT);
        var g = result.createGraphics();

        g.translate((widthNew - width) / 2.0d, (heightNew - height) / 2.0d);
        g.rotate(angle, width / 2.0d, height / 2.0d);
        // Draw old image on the new empty one, but rotated
        g.drawRenderedImage(image, null);
        g.dispose();

        return result;
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        var environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var device = environment.getDefaultScreenDevice();

        return device.getDefaultConfiguration();
    }
}
