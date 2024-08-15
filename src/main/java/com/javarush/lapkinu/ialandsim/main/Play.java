package com.javarush.lapkinu.ialandsim.main;

import com.javarush.lapkinu.ialandsim.action.Eating;
import com.javarush.lapkinu.ialandsim.action.Moving;
import com.javarush.lapkinu.ialandsim.action.Reproduction;
import com.javarush.lapkinu.ialandsim.config.FilePathConfig;
import com.javarush.lapkinu.ialandsim.entity.Entity;
import com.javarush.lapkinu.ialandsim.factory.EntityFactory;
import com.javarush.lapkinu.ialandsim.islandMap.MapManager;
import com.javarush.lapkinu.ialandsim.jsonFileCreator.JsonFileCreator;
import com.javarush.lapkinu.ialandsim.random.RandomActions;
import com.javarush.lapkinu.ialandsim.ui.*;

import javax.swing.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Play {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UiProperties::new);
    }

    public static void startSimulation(MapManager mapManager, Render render, int width, int height) {
        Path propertiesFilePath = FilePathConfig.getPropertiesPath();
        Path jsonFilePath = FilePathConfig.getJsonPath();
        RandomActions randomActions = new RandomActions(mapManager.getWidth(), mapManager.getHeight());
        JsonFileCreator.jsonCreated(jsonFilePath, propertiesFilePath);
        List<Entity> listEntity = EntityFactory.createAnimals(jsonFilePath, propertiesFilePath);
        for (Entity entity : listEntity) {
            int x = randomActions.randomPositionX();
            int y = randomActions.randomPositionY();
            mapManager.addAnimalToCell(entity, x, y);
            entity.setStartX(x);
            entity.setStartY(y);
            entity.setEndX(x);
            entity.setEndY(y);
        }
        mapManager.displayGrid();
        System.out.println("______________ ^ инициализация сущностей на карте ^ ____________________\n");

        JFrame frame = new JFrame("Island Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.add(render);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        ExecutorService boardExecutor = Executors.newFixedThreadPool(5);

        new Timer(1000, e -> {
            if (mapManager.getAnimalCount() > 0) {
                for (Entity entity : mapManager.getAnimalList()) {
                    Random random = new Random();
                    int randomInt = random.nextInt(1, 4);
                    if (randomInt == 1) {
                        // переместить сущность
                        boardExecutor.submit(new Moving(mapManager, entity));
                    } else if (randomInt == 2) {
                        // попытка съесть другую сущность
                        int cellX = mapManager.getCellX(entity);
                        int cellY = mapManager.getCellY(entity);
                        if (cellX != -1 && cellY != -1) {
                            boardExecutor.submit(new Eating(mapManager, cellX, cellY));
                        }
                    } else if (randomInt == 3) {
                        // создать новую сущность
                        if (Math.random() > 0.8) {
                            int cellX = mapManager.getCellX(entity);
                            int cellY = mapManager.getCellY(entity);
                            if (cellX != -1 && cellY != -1) {
                                boardExecutor.submit(new Reproduction(mapManager, cellX, cellY));
                            }
                        }
                    }
                }
                //mapManager.displayGrid();
                render.animateEntities();
                render.repaint();
            } else {
                ((Timer) e.getSource()).stop();
                boardExecutor.shutdown();
            }
        }).start();
    }
}