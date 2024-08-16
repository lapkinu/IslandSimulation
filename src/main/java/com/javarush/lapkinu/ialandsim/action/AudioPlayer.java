package com.javarush.lapkinu.ialandsim.action;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayer implements Runnable {

    private final String filePath;
    public boolean loop;

    public AudioPlayer(String filePath, boolean loop) {
        this.filePath = filePath;
        this.loop = loop;
    }



    @Override
    public void run() {
        do {
            try (FileInputStream fis = new FileInputStream(filePath)) {
                Player player = new Player(fis);
                player.play();
            } catch (JavaLayerException | IOException e) {
                e.printStackTrace();
            }
        } while (loop);
    }

}
