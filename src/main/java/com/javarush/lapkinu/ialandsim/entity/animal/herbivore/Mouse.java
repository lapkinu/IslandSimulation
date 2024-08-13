package com.javarush.lapkinu.ialandsim.entity.animal.herbivore;


import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.MOUSE;

public class Mouse extends Herbivore  {

    public Mouse(double weight, int speed) {
        super(MOUSE.getId(), weight, speed, MOUSE.getImage());
    }
}
