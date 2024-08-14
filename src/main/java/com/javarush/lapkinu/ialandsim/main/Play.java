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

public class Play {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UiProperties::new);
    }

    public static void startSimulation(MapManager mapManager, Render render) {
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
        frame.setSize(1920, 1080);
        frame.add(render);
        frame.setVisible(true);

        new Timer(1000, e -> {
            if (mapManager.getAnimalCount() > 0) {
                for (Entity entity : listEntity) {
                    entity.updatePosition(mapManager.getWidth(), mapManager.getHeight());
                    mapManager.moveAnimal(entity, (int) entity.getEndX(), (int) entity.getEndY());
                    if (entity.getWeight() <= (entity.getWeightMax() * 0.15)) {
                        mapManager.removeAnimal(entity);
                    } else {
                        entity.hunger();
                    }
                }
                mapManager.displayGrid();
                render.animateEntities();
                render.repaint();
                System.out.println("___________________________________________________________");
            }
        }).start();
    }
}