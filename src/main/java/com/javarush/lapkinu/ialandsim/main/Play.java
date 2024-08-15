package com.javarush.lapkinu.ialandsim.main;

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
            entity.setStartX(x); entity.setStartY(y); entity.setEndX(x); entity.setEndY(y);
        }
        mapManager.displayGrid();
        System.out.println("______________ ^ инициализация сущностей на карте ^ ____________________\n");

        JFrame frame = new JFrame("Island Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.add(render);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Timer(1000, e -> {
            if (mapManager.getAnimalCount() > 0) {
                for (Entity entity : mapManager.getAnimalList()) {
                    Random random = new Random();
                    int randomInt = random.nextInt(1,5);
                    if (randomInt == 2) {
                        // переместить сущность
                        entity.updatePosition(mapManager.getWidth(), mapManager.getHeight());
                        mapManager.moveAnimal(entity, (int) entity.getEndX(), (int) entity.getEndY());
                        if (entity.getWeight() <= (entity.getWeightMax() * 0.15)) {
                            mapManager.removeAnimal(entity);
                        } else {
                            //entity.hunger();
                        }
                    } /*else if (randomInt == 2) {
                        // удалить сущность
                        mapManager.removeAnimal(entity);
                    } else if (randomInt == 3) {
                        // создать новую сущность
                        Entity newEntity = EntityFactory.createEntity(entity);
                        int x = mapManager.getCellX(entity);
                        int y = mapManager.getCellY(entity);
                        mapManager.addAnimalToCell(newEntity, x, y);
                        newEntity.setStartX(x); newEntity.setStartY(y); newEntity.setEndX(x); newEntity.setEndY(y);
                    }*/
                }
                //mapManager.displayGrid();
                render.animateEntities();
                render.repaint();
            }
        }).start();
    }
}