package com.javarush.lapkinu.ialandsim.factory;

import com.javarush.lapkinu.ialandsim.animalTable.EntityProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.lapkinu.ialandsim.entity.Entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.*;

public class EntityFactory {

    private static final Map<String, String> mapClassInfo = new HashMap<>();

    static {
        for (EntityProperties entity: EntityProperties.values()) {
            mapClassInfo.put(entity.getValueName(),entity.getPath());
        }
    }

    public static List<Entity> createAnimals(Path jsonFifePath, Path propertiesFilePath) {
        List<Entity> entities = new ArrayList<>();
        Properties properties = new Properties();
        try {
            // Загрузка параметров из файла свойств
            properties.load(new FileInputStream(propertiesFilePath.toFile()));
            // Загрузка JSON-файла
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(jsonFifePath.toString()));
            JsonNode classesArray = rootNode.get("classes");
            for (JsonNode classNode : classesArray) {

                String className = classNode.get("name").asText();
                int count = classNode.get("count").asInt();

                // Получение параметров конструктора из файла свойств
                double weight = Double.parseDouble(properties.getProperty(className + ".weight"));
                int speed = Integer.parseInt(properties.getProperty(className + ".speed"));

                // Получение полного имени класса из словаря
                String fullClassName = mapClassInfo.get(className);
                if (fullClassName == null) {
                    throw new RuntimeException("Class not found for name: " + className);
                }
                // создание объектов
                Class<?> clazz = Class.forName(fullClassName);
                Constructor<?> constructor = clazz.getConstructor(double.class, int.class);
                for (int i = 0; i < count; i++) {
                    Entity entity = (Entity) constructor.newInstance(weight, speed);
                    entities.add(entity);
                }
            }
            /*JOptionPane.showMessageDialog(null, "\n\nВсе сущности созданны и готовы есть друг друга, размножатьстя" +
                    " и бегать по полю!\n\n" + "                                           вы точно этого хотите ?!!!" );*/
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return entities;
    }

    public static Entity createEntity(Entity entity) {
        Entity newEntity = null;
        try {
            Class<?> clazz = entity.getClass();
            Constructor<?> constructor = clazz.getConstructor(double.class, int.class);
            newEntity = (Entity) constructor.newInstance(entity.getWeight(), entity.getSpeed());
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return newEntity;
    }

}
