package com.javarush.lapkinu.ialandsim.entity.animal.herbivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.RABBIT;

public class Rabbit extends Herbivore  {

    public Rabbit(double weight, int speed) {
        super(RABBIT.getId(), weight, speed, RABBIT.getImage());
    }
}
