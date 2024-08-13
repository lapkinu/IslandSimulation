package com.javarush.lapkinu.ialandsim.entity.animal.herbivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.CATERPILLAR;

public class Caterpillar extends Herbivore  {

    public Caterpillar(double weight, int speed) {
        super(CATERPILLAR.getId(), weight, speed, CATERPILLAR.getImage());
    }
}
