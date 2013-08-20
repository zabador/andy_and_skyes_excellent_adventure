package com.zabador;

import javax.swing.JFrame;

import com.zabador.graphics_engine.GameFrame;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Andy and Skye's Adventure");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new GameFrame());
        frame.setVisible(true);

    }
}
