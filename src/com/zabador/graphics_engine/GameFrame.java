package com.zabador.graphics_engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameFrame extends JPanel {

    public GameFrame() {
        setFocusable(true);
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawString("Hello World", 150, 150);

        ImageIcon ic = new ImageIcon("images/player.png");
        Image i = ic.getImage();

        g2d.drawImage(i, 500, 200, null);
    }

}
