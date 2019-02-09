package io.houf.dungeoncrawler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MoveListener extends MouseAdapter {
    private final JFrame frame;

    private Point pressPosition = null;

    public MoveListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.pressPosition = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.pressPosition = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        var current = e.getLocationOnScreen();

        // Update the frame location
        this.frame.setLocation(current.x - this.pressPosition.x, current.y - this.pressPosition.y);
    }
}
