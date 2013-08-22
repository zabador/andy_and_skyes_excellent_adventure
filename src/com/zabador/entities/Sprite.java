package com.zabador.entities;

import java.awt.*;
import javax.swing.*;

public class Sprite extends Object {
    private ImageEntity entity;
    protected Point pos;
    protected Point vel;
    protected double rotRate;
    protected int currentState;

    //constructor
    Sprite(JFrame a, Graphics2D g2d) {
        entity = new ImageEntity(a);
        entity.setGraphics(g2d);
        entity.setAlive(false);
        pos = new Point(0, 0);
        vel = new Point(0, 0);
        rotRate = 0.0;
        currentState = 0;
    }

    //load bitmap file
    public void load(String filename) {
        entity.load(filename);
    }

    //perform affine transformations
    public void transform() {
        entity.setX(pos.x);
        entity.setY(pos.y);
        entity.transform();
    }

    //draw the image
    public void draw() {
        entity.g2d.drawImage(entity.getImage(),entity.at,entity.frame);
    }

    //draw bounding rectangle around sprite
    public void drawBounds(Color c) {
        entity.g2d.setColor(c);
        entity.g2d.draw(getBounds());
    }

    //update the position based on velocity
    public void updatePosition() {
        pos.x += vel.x;
        pos.y += vel.y;
    }


    //generic sprite state variable (alive, dead, collided, etc)
    public int state() { return currentState; }
    public void setState(int state) { currentState = state; }

    //returns a bounding rectangle
    public Rectangle getBounds() { return entity.getBounds(); }

    //sprite position
    public Point position() { return pos; }
    public void setPosition(Point pos) { this.pos = pos; }

    //sprite movement velocity
    public Point velocity() { return vel; }
    public void setVelocity(Point vel) { this.vel = vel; }

    //returns the center of the sprite as a Point
    public Point center() {
        int x = (int)entity.getCenterX();
        int y = (int)entity.getCenterY();
        return(new Point(x,y));
    }

    //generic variable for selectively using sprites
    public boolean alive() { return entity.isAlive(); }
    public void setAlive(boolean alive) { entity.setAlive(alive); }

    //returns the source image width/height
    public int imageWidth() { return entity.width(); }
    public int imageHeight() { return entity.height(); }

    //check for collision with a rectangular shape
    public boolean collidesWith(Rectangle rect) {
        return (rect.intersects(getBounds()));
    }
    //check for collision with another sprite
    public boolean collidesWith(Sprite sprite) {
        return (getBounds().intersects(sprite.getBounds()));
    }
    //check for collision with a point
    public boolean collidesWith(Point point) {
        return (getBounds().contains(point.x, point.y));
    }

    public JFrame frame() { return entity.frame; }
    public Graphics2D graphics() { return entity.g2d; }
    public Image image() { return entity.image; }
    public void setImage(Image image) { entity.setImage(image); }

}
