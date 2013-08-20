package com.zabador.entities;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Hero extends Entity {

    public Hero(int x, int y) {
        super(x,y);
    }

    public void update() {

    }

    public void draw(Graphics2D g2d) {

        g2d.drawImage(getPlayerImage(), x, y, null);
    }

    public Image getPlayerImage() {

        ImageIcon ic = new ImageIcon("images/player.png");
        return ic.getImage();
    }
}
