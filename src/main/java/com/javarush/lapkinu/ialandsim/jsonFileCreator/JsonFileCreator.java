package com.javarush.lapkinu.ialandsim.jsonFileCreator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.javarush.lapkinu.ialandsim.config.FilePathConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonFileCreator {

    public static void jsonCreated(Path jsonFilePath, Path propertiesFilePath) {
        Map<String, Integer> animalCountsBase = readFileProperties(propertiesFilePath);
        // Создаем объект ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        // Создаем корневой объект JSON
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode classesArray = objectMapper.createArrayNode();
        // Заполняем массив JSON-объектами
        for (Map.Entry<String, Integer> entry : animalCountsBase.entrySet()) {
            ObjectNode classNode = objectMapper.createObjectNode();
            classNode.put("name", entry.getKey());
            classNode.put("count", entry.getValue());
            classesArray.add(classNode);
        }
        // Добавляем массив в корневой объект
        rootNode.set("classes", classesArray);
        // Записываем JSON в файл
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFilePath.toString()), rootNode);
            System.out.println("Файл "  + FilePathConfig.getJsonPath() + " успешно создан!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> readFileProperties(Path propertiesFilePath) {
        // Создаем Map для хранения названий животных и их количества
        Map<String, Integer> animalCounts = new HashMap<>();
        // Чтение файла и заполнение Map
        try (BufferedReader reader = new BufferedReader(new FileReader(propertiesFilePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Шаблон для поиска информации о количестве животных
                Pattern pattern = Pattern.compile("^(\\w+)\\.count=(\\d+)$");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    // Извлечение названия животного и количества
                    String animalName = matcher.group(1);
                    int count = Integer.parseInt(matcher.group(2));
                    // Добавляем название животного и количество в Map
                    if (count > 0) {
                        // Добавляем название животного и количество в Map
                        animalCounts.put(animalName, count);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Вывод Map для проверки результатов
        //animalCounts.forEach((key, value) -> System.out.println(key + ": " + value));
        return animalCounts;
    }
}