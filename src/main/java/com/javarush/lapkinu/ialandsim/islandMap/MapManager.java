package com.javarush.lapkinu.ialandsim.islandMap;

import com.javarush.lapkinu.ialandsim.entity.Entity;

import java.util.*;

public class MapManager {
    private final Cell[][] cells;
    private final Map<Cell, List<Entity>> gridMap;
    private final int width;
    private final int height;

    public  MapManager(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        gridMap = new HashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j);
                gridMap.put(cells[i][j], new ArrayList<>());
            }
        }
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // Метод для добавления животного в определенную ячейку
    public  void addAnimalToCell(Entity entity, int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            gridMap.get(cells[x][y]).add(entity);
        } else {
            System.out.println("Неверные координаты ячейки (" + x + ", " + y + ").");
        }
    }

    // Метод для получения животных в определенной ячейке
    public List<Entity> getAnimalsInCell(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return gridMap.get(cells[x][y]);
        } else {
            System.out.println("Неверные координаты ячейки (" + x + ", " + y + ").");
            return Collections.emptyList();
        }
    }

    // метод для возврата текущей позиции животного на карте
    public  Cell getCell(Entity entity) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (gridMap.get(cells[i][j]).contains(entity)) {
                    return cells[i][j];
                }
            }
        }
        return null;
    }

    public int  getCellX(Entity entity) {
        return getCell(entity).getX();
    }

    public int getCellY(Entity entity) {
        return getCell(entity).getY();
    }

    // метод для удаления животного с карты
    public void  removeAnimal(Entity entity) {
        Cell cell = getCell(entity);
        if (cell != null) {
            gridMap.get(cell).remove(entity);
        }
    }

    // метод для перемещения животного на новую позицию на карте
    public void moveAnimal(Entity entity, int newX, int newY) {
        Cell oldCell = getCell(entity);
        if (oldCell != null) {
            gridMap.get(oldCell).remove(entity);
        }
        addAnimalToCell(entity, newX, newY);
    }

    // Метод для отображения сетки и её животных
    public void displayGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                List<Entity> entities = gridMap.get(cells[i][j]);
                System.out.println("Ячейка " +cells[i][j] + " содержит - " + getAnimalCountInCell(i, j) + " сущностей: " + entities);
            }
        }
        System.out.println("___________________________________________________________");
    }

    // метод для количества всех животных в ячейке
    public int getAnimalCountInCell(int x, int y) {
        return gridMap.get(cells[x][y]).size();
    }

    // метод для определения количества всех животных на карте
    public int getAnimalCount() {
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                count += getAnimalCountInCell(i, j);
            }
        }
        return count;
    }

    public List<Entity> getAnimalList() {
        List<Entity> list = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                list.addAll(gridMap.get(cells[i][j]));
            }
        }
        return list;
    }

}
