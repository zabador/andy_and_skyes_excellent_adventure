package com.zabador.game.entities;

import java.util.ArrayList;

public class Warp {

    private String name;
    private String file;
    private int mapX;
    private int mapY;
    private ArrayList<String> portals;

    /**
     *
     */
    public Warp(String name, String file, int mapX, int mapY, ArrayList<String> portals) {
        this.name = name;
        this.file = file;
        this.mapX = mapX;
        this.mapY = mapY;
        this.portals = portals;
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
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
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

    /**
     * @return the portals
     */
    public ArrayList<String> getPortals() {
        return portals;
    }

    /**
     * @param portals the portals to set
     */
    public void setPortals(ArrayList<String> portals) {
        this.portals = portals;
    }
}
