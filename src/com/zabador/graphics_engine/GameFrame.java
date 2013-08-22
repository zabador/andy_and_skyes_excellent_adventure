package com.zabador.graphics_engine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.net.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements Runnable, KeyListener {
    Image image;
    Thread gameloop;
    Random rand = new Random();

    public GameFrame() {
        super("Our game");
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit tk = Toolkit.getDefaultToolkit();
        image = tk.getImage(getURL("/images/player.png"));

        gameloop = new Thread(this);
        gameloop.start();
    }

    private URL getURL(String filename) {
        URL url = null;
        try {
            url = this.getClass().getResource(filename);
        } catch (Exception e) {
        }
        return url;
    }

    public void run() {
        Thread t = Thread.currentThread();
        while (t == gameloop) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 100, 100, this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
