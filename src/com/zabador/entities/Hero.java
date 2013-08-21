package com.zabador.entities;

import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import java.net.URL;

public class Hero extends Entity {

    private int velX = 0, velY = 0;

    public Hero(int x, int y) {
        super(x,y);
    }

    public void update() {

        x += velX;
        y += velY;

    }

    public void setVelX(int x) {
        System.out.println("moving x");
        velX = x;
    }

    public void setVelY(int y) {
        velY = y;
    }


    public void draw(Graphics2D g2d) {

        g2d.drawImage(getPlayerImage(), x, y, null);
    }

    public Image getPlayerImage() {

        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(getURL("/images/player.png"));
    }

    private URL getURL(String filename) {
        URL url = null;
        try {
            url = this.getClass().getResource(filename);
        }catch(Exception e) {

        }
        return url;
    }

}
