package com.javarush.lapkinu.ialandsim.propertyEditor;

import com.javarush.lapkinu.ialandsim.animalTable.EntityProperties;
import com.javarush.lapkinu.ialandsim.config.FilePathConfig;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;


public class PropertyEditor extends JFrame {
    private static final String[] CLASS_NAMES = EntityProperties.getEntity();
    private static final String[] PARAM_NAMES = {"count", "weight", "speed"};
    private final JTextField[][] textFields;
    private final Properties properties;
    Path pathPropertiesFile = FilePathConfig.getPropertiesPath();

    public PropertyEditor() {
        setTitle("Property Editor");
        setSize(300, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(CLASS_NAMES.length * PARAM_NAMES.length + 1, 2));

        properties = new Properties();
        loadDefaultProperties();

        textFields = new JTextField[CLASS_NAMES.length][PARAM_NAMES.length];
        for (int i = 0; i < CLASS_NAMES.length; i++) {
            for (int j = 0; j < PARAM_NAMES.length; j++) {
                String propertyName = CLASS_NAMES[i] + "." + PARAM_NAMES[j];
                add(new JLabel(CLASS_NAMES[i] + " " + PARAM_NAMES[j] + ":"));
                textFields[i][j] = new JTextField(properties.getProperty(propertyName, "0"));
                add(textFields[i][j]);
            }
        }
        JButton saveButton = new JButton("Save Properties");
        saveButton.addActionListener(e -> saveProperties());
        add(saveButton);
        add(new JLabel());
    }

    private void loadDefaultProperties() {
        try (FileInputStream fis = new FileInputStream(pathPropertiesFile.toFile())) {
            properties.load(fis);
        } catch (IOException e) {
            // Если файл не найден или не загружен, подтягиваются стандартные значения
            for (EntityProperties entity : EntityProperties.values()) {
                String name = entity.getValueName();
                for (int j = 0; j < PARAM_NAMES.length ; j++) {
                   Number value;
                   if (j == 0) {
                       value = entity.getCount();
                   } else if (j == 1){
                       value = entity.getWeight();
                   } else {
                       value = entity.getSpeed();
                   }
                    properties.setProperty(name + "." + PARAM_NAMES[j], String.valueOf(value));
                }
            }
        }
    }

    private void saveProperties() {
        for (int i = 0; i < CLASS_NAMES.length; i++) {
            for (int j = 0; j < PARAM_NAMES.length; j++) {
                String propertyName = CLASS_NAMES[i] + "." + PARAM_NAMES[j];
                properties.setProperty(propertyName, textFields[i][j].getText());
            }
        }
        try (FileOutputStream fos = new FileOutputStream(pathPropertiesFile.toFile())) {
            properties.store(fos, "Entity.properties");
            JOptionPane.showMessageDialog(this, "Properties saved successfully!");
            dispose();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving properties: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PropertyEditor().setVisible(true));
    }

}
