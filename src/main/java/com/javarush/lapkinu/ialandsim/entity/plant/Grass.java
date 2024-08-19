package com.javarush.lapkinu.ialandsim.entity.plant;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.GRASS;

public class Grass extends Plants {

    public Grass(double weight, int speed) {
        super(GRASS.getId(), weight, speed, GRASS.getImage());
    }
}
