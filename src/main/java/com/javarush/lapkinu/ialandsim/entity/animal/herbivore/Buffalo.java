package com.javarush.lapkinu.ialandsim.entity.animal.herbivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.BUFFALO;

public class Buffalo extends Herbivore  {

    public Buffalo(double weight, int speed) {
        super(BUFFALO.getId(), weight, speed, BUFFALO.getImage());
    }
}
