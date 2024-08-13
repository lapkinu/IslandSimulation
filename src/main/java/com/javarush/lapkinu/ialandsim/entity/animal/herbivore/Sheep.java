package com.javarush.lapkinu.ialandsim.entity.animal.herbivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.SHEEP;

public class Sheep extends Herbivore  {

    public Sheep(double weight, int speed) {
        super(SHEEP.getId(), weight, speed, SHEEP.getImage());
    }
}
