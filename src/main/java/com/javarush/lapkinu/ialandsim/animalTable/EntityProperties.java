package com.javarush.lapkinu.ialandsim.animalTable;

import com.javarush.lapkinu.ialandsim.entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public enum EntityProperties {

    WOLF("Wolf", 0, 0,  50, 3, "com.javarush.lapkinu.ialandsim.entity.animal.predator.Wolf", "src/main/resources/img/icon/wolf.png"),
    BOA("Boa", 0, 0,  15, 1, "com.javarush.lapkinu.ialandsim.entity.animal.predator.Boa", "src/main/resources/img/icon/boa.png"),
    FOX("Fox", 0,  0, 8, 2, "com.javarush.lapkinu.ialandsim.entity.animal.predator.Fox", "src/main/resources/img/icon/fox.png"),
    BEAR("Bear", 0, 0, 500, 2, "com.javarush.lapkinu.ialandsim.entity.animal.omnivore.Bear", "src/main/resources/img/icon/bear.png"),
    EAGLE("Eagle", 0, 0, 6, 3, "com.javarush.lapkinu.ialandsim.entity.animal.predator.Eagle", "src/main/resources/img/icon/eagle.png"),
    HORSE("Horse", 0, 0, 400, 4, "com.javarush.lapkinu.ialandsim.entity.animal.herbivore.Horse", "src/main/resources/img/icon/horse.png"),
    DEER("Deer", 0,0, 300, 4, "com.javarush.lapkinu.ialandsim.entity.animal.herbivore.Deer", "src/main/resources/img/icon/deer.png"),
    RABBIT("Rabbit", 0, 0, 2, 2, "com.javarush.lapkinu.ialandsim.entity.animal.herbivore.Rabbit", "src/main/resources/img/icon/rabbit.png"),
    MOUSE("Mouse", 0, 0, 0.05, 1, "com.javarush.lapkinu.ialandsim.entity.animal.herbivore.Mouse", "src/main/resources/img/icon/mouse.png"),
    GOAT("Goat", 0, 0, 60, 3, "com.javarush.lapkinu.ialandsim.entity.animal.herbivore.Goat", "src/main/resources/img/icon/goat.png"),
    SHEEP("Sheep", 0, 0, 70, 3, "com.javarush.lapkinu.ialandsim.entity.animal.herbivore.Sheep", "src/main/resources/img/icon/sheep.png"),
    BOAR("Boar", 0, 0, 400, 2, "com.javarush.lapkinu.ialandsim.entity.animal.omnivore.Boar", "src/main/resources/img/icon/boar.png"),
    BUFFALO("Buffalo", 0, 0, 700, 3, "com.javarush.lapkinu.ialandsim.entity.animal.herbivore.Buffalo", "src/main/resources/img/icon/buffalo.png"),
    DUCK("Duck", 0, 0, 1, 4, "com.javarush.lapkinu.ialandsim.entity.animal.omnivore.Duck", "src/main/resources/img/icon/duck.png"),
    CATERPILLAR("Caterpillar", 0, 0, 0.01, 0, "com.javarush.lapkinu.ialandsim.entity.animal.herbivore.Caterpillar","src/main/resources/img/icon/caterpillar.png"),
    GRASS("Grass", 0, 0, 1, 0, "com.javarush.lapkinu.ialandsim.entity.plant.Grass", "src/main/resources/img/icon/plant.png");

    private final String name;
    private int id;
    private final int count;
    private final double weight;
    private final int speed;
    private final String path;

    private static final int[][] animalTable = {
            // WOLF, BOA, FOX, BEAR, EAGLE, HORSE, DEER, RABBIT, MOUSE, GOAT, SHEEP, BOAR, BUFFALO, DUCK, CATERPILLAR, PLANTS,
            {-1, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},    // WOLF
            {0, -1, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},         // BOA
            {0, 0, -1, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},         // FOX
            {0, 80, 0, -1, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},   // BEAR
            {0, 0, 10, 0, -1, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},         // EAGLE
            {0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},           // HORSE
            {0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 100},           // DEER
            {0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 100},           // RABBIT
            {0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 90, 100},          // MOUSE
            {0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 100},           // GOAT
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 100},           // SHEEP
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, -1, 0, 0, 90, 100},         // BOAR
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 100},           // BUFFALO
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 90, 100},          // DUCK
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 100},           // CATERPILLAR
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1}              // PLANTS
    };
    final Image image;

    EntityProperties(String name, int id, int count, double weight, int speed, String path, String icon) {
        this.name = name;
        this.id = id;
        this.count = count;
        this.weight = weight;
        this.speed = speed;
        this.path = path;
        this.image =  new ImageIcon(icon).getImage();
    }

    public String getValueName() {
        return name;
    }

    public int getId() {
        return ++id;
    }

    public int getCount() {
        return count;
    }

    public double getWeight() {
        return weight;
    }

    public int getSpeed() {
        return speed;
    }

    public String getPath() {
        return path;
    }

    public Image getImage() {
        return image;
    }

    public static int valueTable(EntityProperties animal1, EntityProperties animal2) {
        int index1 = animal1.ordinal();
        int index2 = animal2.ordinal();
        return animalTable[index1][index2];
    }

    public static String[] getEntity() {
        String[] animals = new String[EntityProperties.values().length];
        for (EntityProperties animal : EntityProperties.values()) {
            animals[animal.ordinal()] = animal.getValueName();
        }
        return animals;
    }

    // метод принимает Entity и лист Entity и возвращает Entity с наибольшим значением в таблице animalTable
    public static Entity getBestAnimalTable(Entity entity, List<Entity> listEntity) {
        int bestValue = 0;
        for (Entity entity1 : listEntity) {
            int value = valueTable(formEntity(entity), formEntity(entity1));
            if (value > bestValue) {
                bestValue = value;
                entity = entity1;
            }
        }
        return entity;

    }

    // метод принимает Entity и возращает EntityProperties (WOLF, BOA, FOX, BEAR...)
    public static EntityProperties formEntity(Entity entity) {
        for (EntityProperties entityProperties : values()) {
            if (entityProperties.getPath().equals(entity.getClass().getName())) {
                return entityProperties;
            }
        }
        return null;
    }

}
