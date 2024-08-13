package com.javarush.lapkinu.ialandsim.entity.animal.herbivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.DEER;

public class Deer extends Herbivore  {

    public Deer(double weight, int speed) {
        super(DEER.getId(), weight, speed, DEER.getImage());
    }
}
