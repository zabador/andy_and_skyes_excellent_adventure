package com.zabador.graphics_engine;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Timer;

import javax.swing.JPanel;

import com.zabador.entities.Hero;

public class GameFrame extends JPanel implements ActionListener{

    Timer mainTimer;
    Hero hero;

    public GameFrame() {
        setFocusable(true);

        hero = new Hero(100,100);

    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawString("Hello World", 150, 150);
        hero.draw(g2d);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

}
