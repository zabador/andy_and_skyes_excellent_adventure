package com.zabador.game.entities;

public class Warp {

    private String name;
    private int mapX;
    private int mapY;

    /**
     *
     */
    public Warp(String name, int mapX, int mapY) {
        this.name = name;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the mapX
     */
    public int getMapX() {
        return mapX;
    }

    /**
     * @param mapX the mapX to set
     */
    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    /**
     * @return the mapY
     */
    public int getMapY() {
        return mapY;
    }

    /**
     * @param mapY the mapY to set
     */
    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

}
