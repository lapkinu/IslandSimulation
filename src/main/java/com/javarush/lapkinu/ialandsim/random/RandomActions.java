package com.javarush.lapkinu.ialandsim.random;

import java.util.concurrent.ThreadLocalRandom;

public class RandomActions {
    private final int width;
    private final int height;

    public RandomActions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    ThreadLocalRandom tr = ThreadLocalRandom.current();

    public int randomPositionX() {
        return tr.nextInt(0, width);
    }
    public int randomPositionY() {
        return tr.nextInt(0, height);
    }

    public int numberOfCopies() {
        return tr.nextInt(1, 10);
    }
}
