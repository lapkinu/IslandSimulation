package com.javarush.lapkinu.ialandsim.ui;

import com.javarush.lapkinu.ialandsim.entity.Entity;
import com.javarush.lapkinu.ialandsim.islandMap.MapManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Render extends JPanel implements ActionListener {
    private final Image backgroundImage;
    private final MapManager mapManager;
    private final int cellWidth;
    private final int cellHeight;
    private final Timer animationTimer;

    public Render(MapManager mapManager, int frameWidth, int frameHeight) {
        this.mapManager = mapManager;
        this.cellWidth = frameWidth / mapManager.getWidth();
        this.cellHeight = frameHeight / mapManager.getHeight();
        backgroundImage = new ImageIcon("src/main/resources/img/field/Field.png").getImage();
        Timer timer = new Timer(1000, this);
        timer.start();
        animationTimer = new Timer(1, e -> animateEntities());
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        for (Entity entity : mapManager.getAnimalList()) {
            int x = (int) (entity.getCurrentX() * cellWidth);
            int y = (int) (entity.getCurrentY() * cellHeight);
            g.drawImage(entity.getImage(), x, y, cellWidth, cellHeight, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mapManager.updateAnimalPositions();
    }

    private void animateEntities() {
        for (Entity entity : mapManager.getAnimalList()) {
            double currentX = entity.getCurrentX();
            double currentY = entity.getCurrentY();
            double endX = entity.getEndX();
            double endY = entity.getEndY();
            double deltaX = (endX - currentX) * 0.08; // Adjust the factor for smoother animation
            double deltaY = (endY - currentY) * 0.08; // Adjust the factor for smoother animation
            entity.setCurrentX(currentX + deltaX);
            entity.setCurrentY(currentY + deltaY);
        }
        repaint();
    }
}