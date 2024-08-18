package com.javarush.lapkinu.ialandsim.main;

import com.javarush.lapkinu.ialandsim.action.Action;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Play {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UiProperties::new);
    }

    public static void startSimulation(MapManager mapManager, Render render, int width, int height, int delay) {
        Path propertiesFilePath = FilePathConfig.getPropertiesPath();
        Path jsonFilePath = FilePathConfig.getJsonPath();
        RandomActions randomActions = new RandomActions(mapManager.getWidth(), mapManager.getHeight());
        JsonFileCreator.jsonCreated(jsonFilePath, propertiesFilePath);
        List<Entity> listEntity = EntityFactory.createAnimals(jsonFilePath, propertiesFilePath);
        Collections.shuffle(listEntity);
        for (Entity entity : listEntity) {
            int x = randomActions.randomPositionX();
            int y = randomActions.randomPositionY();
            mapManager.addAnimalToCell(entity, x, y);
            entity.setStartX(x); entity.setStartY(y);
            entity.setEndX(x); entity.setEndY(y);
        }
        mapManager.displayGrid();
        System.out.println("______________ ^ инициализация сущностей на карте ^ ____________________\n");
        JOptionPane.showMessageDialog(null, "\n\nВсе сущности созданны и готовы есть друг друга, размножатьстя" +
                    " и бегать по полю!\n\n" + "                                           вы точно этого хотите ?!!!" );

        JFrame frame = new JFrame("Island Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.add(render);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread timerThread = new Thread(() -> {
            Random random = new Random();
            ExecutorService executor = Executors.newFixedThreadPool(8);
            List<Action> actions = List.of(new Moving(), new Eating(), new Reproduction());
            new Timer(delay, e -> {
                if (mapManager.getAnimalCount() > 0) {
                    List<Entity> animalList = mapManager.getAnimalList();
                    Collections.shuffle(animalList);
                    for (Entity entity : animalList) {
                        Action action = actions.get(random.nextInt(actions.size()));
                        executor.submit(() -> action.execute(mapManager, entity));
                    }
                    render.animateEntities();
                    render.repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                    executor.shutdown();
                }
            }).start();
        });

        timerThread.start();
    }

}