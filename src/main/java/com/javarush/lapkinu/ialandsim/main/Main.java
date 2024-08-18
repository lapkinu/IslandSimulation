package com.javarush.lapkinu.ialandsim.main;

import com.javarush.lapkinu.ialandsim.ui.UiProperties;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UiProperties::new);
    }
}
