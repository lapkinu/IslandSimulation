package com.javarush.lapkinu.ialandsim.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathConfig {

    private static final Path DEFAULT_JSON_FILE_NAME = Paths.get("src/main/resources/Entity.json");
    private static final Path DEFAULT_PROPERTIES_FILE_NAME = Paths.get("src/main/resources/Entity.properties");
    private static final String AUDIO_FILE_PATH = "src/main/resources/vmz.mp3";

    public static Path getJsonPath() {
        return DEFAULT_JSON_FILE_NAME;
    }

    public static Path getPropertiesPath() {
        return DEFAULT_PROPERTIES_FILE_NAME;
    }

    public static String getAudioPath() {
        return AUDIO_FILE_PATH;
    }
}