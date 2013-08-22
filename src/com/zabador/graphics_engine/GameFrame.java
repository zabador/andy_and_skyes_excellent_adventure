package com.zabador.graphics_engine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import javax.swing.*;

import com.zabador.entities.Hero;
import com.zabador.entities.ImageEntity;

import java.util.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements Runnable, KeyListener {
    int screenWidth = 800;
    int screenHeight = 600;

    static int SPRITE_NORMAL = 0;
    static int SPRITE_COLLIDED = 1;

    //double buffer objects
    BufferedImage backbuffer;
    Graphics2D g2d;

    Hero hero;
    ImageEntity background;
    Thread gameloop;
    Random rand = new Random();

    boolean keyUP,keyDOWN,keyLEFT,keyRIGHT;

    public GameFrame() {
        super("Our game");
        setSize(screenWidth, screenHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create the back buffer for smooth graphics
        backbuffer = new BufferedImage(screenWidth, screenHeight,
                BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();

        addKeyListener(this);

        //load the background
        background = new ImageEntity(this);
        background.load("/images/bluespace.png");

        //load the asteroid sprite
        hero = new Hero(this, g2d);
        hero.load("/images/player.png");
        Point point = new Point(0, 0);
        hero.setPosition(point);

        gameloop = new Thread(this);
        gameloop.start();
    }

    public void run() {
        Thread t = Thread.currentThread();
        while (t == gameloop) {
            try {
                Thread.sleep(20);
                gameUpdate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //draw the background
            g2d.drawImage(background.getImage(), 0, 0, screenWidth - 1,
                    screenHeight - 1, this);

            hero.transform();
            hero.draw();

            repaint();
        }
    }

    private void gameUpdate() {
        checkInput();
        updateHero();
    }

    private void updateHero() {
        hero.updatePosition();
        hero.setState(SPRITE_NORMAL);
    }

    public void paint(Graphics g) {
        //draw the back buffer to the screen
        g.drawImage(backbuffer, 0, 0, this);
    }

    public void checkInput() {
        if (keyUP) {
            hero.setVelocity(new Point(0,-2));
        }
        else if (keyDOWN) {
            hero.setVelocity(new Point(0,2));
        }
        else if (keyLEFT) {
            hero.setVelocity(new Point(-2,0));
        }
        else if (keyRIGHT) {
            hero.setVelocity(new Point(2,0));
        }
        else hero.setVelocity(new Point(0,0));
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Keypressed");
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                keyUP = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                keyDOWN = true;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                keyLEFT = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                keyRIGHT = true;
                break;
        }

    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                keyUP = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                keyDOWN = false;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                keyLEFT = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                keyRIGHT = false;
                break;
        }


    }

    public void keyTyped(KeyEvent e) {
    }
}
