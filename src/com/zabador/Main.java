package com.zabador;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

import com.zabador.entities.Hero;

public class Main extends Applet implements Runnable, KeyListener {

    //the main thread becomes the game loop
    Thread gameloop;

    //use this as a double buffer
    BufferedImage backbuffer;

    //the main drawing object for the back buffer
    Graphics2D g2d;

    Hero hero;

    //create the asteroid array

    //create the identity transform (0,0)
    AffineTransform identity = new AffineTransform();

    public void init() {
        //create the back buffer for smooth graphics
        backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();

        hero = new Hero(100,100);

        //start the user input listener
        addKeyListener(this);
    }

    public void update(Graphics g) {
        //start off transforms at identity
        g2d.setTransform(identity);

        //erase the background
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, getSize().width, getSize().height);

        //repaint the applet window
        paint(g);
    }


    public void paint(Graphics g) {

        hero.draw((Graphics2D)g);
    }

    public void start() {

		//create the gameloop thread for real-time updates
        gameloop = new Thread(this);
        gameloop.start();
    }

    public void run() {

        //acquire the current thread
        Thread t = Thread.currentThread();

        //keep going as long as the thread is alive
        while (t == gameloop) {

            try {
                //update the game loop
                gameUpdate();

                //target framerate is 50 fps
                Thread.sleep(20);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
    /*****************************************************
     * thread stop event
     *****************************************************/
    public void stop() {

		//kill the gameloop thread
        gameloop = null;
    }

    /*****************************************************
     * move and animate the objects in the game
     *****************************************************/
    private void gameUpdate() {
        hero.update();
    }

    public void keyReleased(KeyEvent k) { 
    }
    public void keyTyped(KeyEvent k) { }
    public void keyPressed(KeyEvent k) {
        System.out.println("pressed");
        int keyCode = k.getKeyCode();

        switch (keyCode) {

        case KeyEvent.VK_W:
            hero.setVelY(-2);
            break;

        case KeyEvent.VK_S:
            hero.setVelY(2);
            break;

        case KeyEvent.VK_D:
            hero.setVelX(2);
            break;

        case KeyEvent.VK_A:
            hero.setVelX(-2);
            break;
        }
    }
}
