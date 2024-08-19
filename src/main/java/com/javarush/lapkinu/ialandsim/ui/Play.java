package com.javarush.lapkinu.ialandsim.ui;

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

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Play {

    private JLabel statisticLabel;

    public void startSimulation(MapManager mapManager, Render render, int width, int height, int delay) {
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

        JFrame frame = new JFrame("Island Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());
        frame.add(render, BorderLayout.CENTER);

        // Создание панели для статистики
        JPanel statsPanel = new JPanel();
        statisticLabel = new JLabel("");
        statsPanel.add(statisticLabel);
        // становить шрифт для статистики
        statisticLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(statsPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        Random random = new Random();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Action> actions = List.of(new Moving(), new Eating(), new Reproduction());
        Thread timerThread = new Thread(new Timer(delay, e -> {
            if (mapManager.getAnimalCount() > 0) {
                List<Entity> animalList = mapManager.getAnimalList();
                Collections.shuffle(animalList);
                for (Entity entity : animalList) {
                    Action action = actions.get(random.nextInt(actions.size()));
                    executor.submit(() -> action.execute(mapManager, entity));
                }
                List<Entity> plantList = mapManager.getAnimalList();
                for (Entity entity : plantList) {
                    if (entity.getClass().getSimpleName().equals("Plants")) {
                        entity.eat();
                    }
                }
                render.animateEntities();
                render.repaint();
                updateStats(mapManager);
            } else {
                ((Timer) e.getSource()).stop();
                executor.shutdown();
            }
        })::start);
        timerThread.start();
    }

    private void updateStats(MapManager mapManager) {
        Map<String, Long> entityCounts = mapManager.getEntityCounts();
        StringBuilder stats = new StringBuilder();
        for (Map.Entry<String, Long> entry : entityCounts.entrySet()) {
            stats.append("| ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
        }
        statisticLabel.setText(stats.toString());
    }

}