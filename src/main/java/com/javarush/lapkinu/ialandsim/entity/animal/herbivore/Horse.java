package com.javarush.lapkinu.ialandsim.entity.animal.herbivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.HORSE;

public class Horse extends Herbivore  {

    public Horse(double weight, int speed) {
        super(HORSE.getId(), weight, speed, HORSE.getImage());
    }
}
