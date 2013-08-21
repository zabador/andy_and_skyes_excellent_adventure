package com.zabador.graphics_engine;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Timer;

import javax.swing.JPanel;

import com.zabador.entities.Hero;

public class GameFrame extends JPanel implements Runnable, ActionListener{

    Thread gameLoop;
    Hero hero;

    public GameFrame() {
        setFocusable(true);

        hero = new Hero(100,100);
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        hero.draw(g2d);

    }

    public void start() {
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void run() {
        //acquire the current thread
        Thread t = Thread.currentThread();
        //keep going as long as the thread is alive
        while (t == gameLoop) {
            try {
                //target framerate is 50 fps
                Thread.sleep(20);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        System.out.println("key pressed");
        hero.update();
    }

}
