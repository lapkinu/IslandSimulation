package com.javarush.lapkinu.ialandsim.ui;

import com.javarush.lapkinu.ialandsim.islandMap.MapManager;
import com.javarush.lapkinu.ialandsim.entity.Entity;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {
    private final Image backgroundImage;
    private final MapManager mapManager;
    private final int cellWidth;
    private final int cellHeight;
    private final double smoothSimulation;

    public Render(MapManager mapManager, int frameWidth, int frameHeight, double smoothSimulation) {
        this.mapManager = mapManager;
        this.cellWidth = frameWidth / mapManager.getWidth();
        this.cellHeight = frameHeight / mapManager.getHeight();
        this.smoothSimulation = smoothSimulation;
        backgroundImage = new ImageIcon("src/main/resources/img/field/Field.png").getImage();

        // Таймер для анимации объектов
        Timer animationTimer = new Timer(10, e -> {
            animateEntities();
            repaint();
        });
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        for (Entity entity : mapManager.getAnimalList()) {
            int x = (int) (entity.getCurrentX() * cellWidth);
            int y = (int) (entity.getCurrentY() * cellHeight);
            g.drawImage(entity.getImage(), x, y , cellWidth, cellHeight, this);
        }
    }

    public void animateEntities() {
        for (Entity entity : mapManager.getAnimalList()) {
            double currentX = entity.getCurrentX();
            double currentY = entity.getCurrentY();
            double endX = entity.getEndX();
            double endY = entity.getEndY();
            double deltaX = (endX - currentX) * (0.1 / smoothSimulation);
            double deltaY = (endY - currentY) * (0.1 / smoothSimulation);
            entity.setCurrentX(currentX + deltaX);
            entity.setCurrentY(currentY + deltaY);
        }
    }

}