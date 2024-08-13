package com.javarush.lapkinu.ialandsim.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathConfig {

    private static final Path DEFAULT_JSON_FILE_NAME = Paths.get("Entity.json");
    private static final Path DEFAULT_PROPERTIES_FILE_NAME = Paths.get("Entity.properties");

    public static Path getJsonPath() {
        return DEFAULT_JSON_FILE_NAME;
    }

    public static Path getPropertiesPath() {
        return DEFAULT_PROPERTIES_FILE_NAME;
    }
}