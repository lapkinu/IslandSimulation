package com.javarush.lapkinu.ialandsim.ui;

import com.javarush.lapkinu.ialandsim.feature.AudioPlayer;
import com.javarush.lapkinu.ialandsim.animalTable.EntityProperties;
import com.javarush.lapkinu.ialandsim.config.FilePathConfig;
import com.javarush.lapkinu.ialandsim.islandMap.MapManager;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class SetProperties {
    private static final String[] COLUMN_NAMES = {"Entity", "Quantity", "Weight", "Speed"};
    private static final Properties properties = new Properties();
    private static final Path pathPropertiesFile = FilePathConfig.getPropertiesPath();
    private static JTextField heightField;
    private static JTextField widthField;
    private int frameWidth;
    private int frameHeight;
    private double smoothSimulation;
    private int delay;
    private AudioPlayer player;
    private Thread audioThread;
    private boolean isPlaying = false;

    public SetProperties() {
        JFrame frame = new JFrame("Config Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 530);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        JLabel heightLabel = new JLabel("    heightField:");
        heightField = new JTextField(5);
        JLabel widthLabel = new JLabel("    widthField:");
        widthField = new JTextField(5);

        heightField.setText("30");
        widthField.setText("25");

        inputPanel.add(heightLabel);
        inputPanel.add(heightField);
        inputPanel.add(widthLabel);
        inputPanel.add(widthField);


        JLabel windowSizeLabel = new JLabel("  Screen size:");
        inputPanel.add(windowSizeLabel);
        String[] windowSizes = { "640x480", "720x480",
                "800x600", "1024x768", "1280x720", "1560x900", "1920x1080", "2560x1440", "2560x1600", "3840x2160"};
        JComboBox<String> windowSizeComboBox = new JComboBox<>(windowSizes);
        windowSizeComboBox.setSelectedItem("1560x900"); // Set default value
        windowSizeComboBox.addActionListener(e -> {
            String selectedSize = (String) windowSizeComboBox.getSelectedItem();
            if (selectedSize != null) {
                String[] size = selectedSize.split("x");
                if (size.length == 2) {
                    this.frameWidth = Integer.parseInt(size[0]);
                    this.frameHeight = Integer.parseInt(size[1]);
                }
            }
        });
        // Вызов обработчика событий для установки значений по умолчанию
        windowSizeComboBox.getActionListeners()[0].actionPerformed(null);
        inputPanel.add(windowSizeComboBox);

        JLabel smoothSimulationLabel = new JLabel("  Smooth:");
        inputPanel.add(smoothSimulationLabel);
        String[] smoothSimulationVol = { "1", "2",
                "3", "4", "5", "6", "7", "8", "9", "10"};
        JComboBox<String> smoothSimulationComboBox = new JComboBox<>(smoothSimulationVol);
        smoothSimulationComboBox.setSelectedItem("3"); // Set default value
        smoothSimulationComboBox.addActionListener(e -> {
            String selectedVol = (String) smoothSimulationComboBox.getSelectedItem();
            if (selectedVol != null) {
                this.smoothSimulation = Double.parseDouble(selectedVol);
            }
        });
        // Вызов обработчика событий для установки значений по умолчанию
        smoothSimulationComboBox.getActionListeners()[0].actionPerformed(null);
        inputPanel.add(smoothSimulationComboBox);


        JLabel delayLabel = new JLabel("  Delay ms:");
        inputPanel.add(delayLabel);
        String[] daleyVol = {"100", "200", "300", "400", "500", "600", "700", "800", "900", "1000"};
        JComboBox<String> delayComboBox = new JComboBox<>(daleyVol);
        delayComboBox.setSelectedItem("1000"); // Set default value
        delayComboBox.addActionListener(e -> {
            String selectedVol = (String) delayComboBox.getSelectedItem();
            if (selectedVol != null) {
                this.delay = Integer.parseInt(selectedVol);
            }
        });
        // Вызов обработчика событий для установки значений по умолчанию
        delayComboBox.getActionListeners()[0].actionPerformed(null);
        inputPanel.add(delayComboBox);


        JCheckBox audioCheckBox = new JCheckBox("  Feature");
        audioCheckBox.setSelected(false);
        audioCheckBox.addActionListener(e -> {
            if (audioCheckBox.isSelected() && !isPlaying) {
                startAudioPlayer();
            } else if (!audioCheckBox.isSelected() && isPlaying) {
                stopAudioPlayer();
            }
        });
        inputPanel.add(audioCheckBox);

        // Загрузка данных из properties файла
        loadProperties();

        // Создание модели таблицы
        DefaultTableModel model = new DefaultTableModel(getDataFromProperties(), COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Первый столбец не редактируемый
            }
        };

        // Создание таблицы
        JTable table = new JTable(model);
        table.setGridColor(Color.BLACK); // Установка ��вета границ
        table.setShowGrid(true); // Включение отображения границ

        // Добавление поведения для замены текста
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        // Настройка редактора ячеек для замены текста
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        DefaultCellEditor editor = new DefaultCellEditor(textField) {
            @Override
            public boolean stopCellEditing() {
                textField.selectAll(); // Выделение текста при начале редактирования
                return super.stopCellEditing();
            }
        };
        table.setDefaultEditor(String.class, editor);

        // Создание централизованного рендерера
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // Центрирование по горизонтали
        centerRenderer.setVerticalAlignment(JLabel.CENTER); // Центрирование по вертикали

        // Применение рендерера ко всем столбцам
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Создание рендерера для первого столбца с измененным шрифтом
        DefaultTableCellRenderer firstColumnRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setFont(new Font("Arial", Font.BOLD, 16)); // Установка шрифта
                return component;
            }
        };

        // Применение рендерера к первому столбцу
        table.getColumnModel().getColumn(0).setCellRenderer(firstColumnRenderer);

        // Настройка выделения
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(false);

        // Установка выравнивания для заголовков
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER); // Центрирование заголовков по горизонтали
        headerRenderer.setVerticalAlignment(JLabel.CENTER); // Центрирование заголовков по вертикали

        // Установка ширины столбцов
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        Font font = new Font("Arial", Font.PLAIN, 16);
        table.setFont(font);
        table.setRowHeight(24); // Установка высоты строки

        // Установка шрифта для заголовков
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Добавление слушателя для предотвращения выделения текста в редакторе ячейки
        table.getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                SwingUtilities.invokeLater(() -> {
                    int row = table.getEditingRow();
                    int column = table.getEditingColumn();
                    if (row != -1 && column != -1) {
                        table.editCellAt(row, column);
                        JTextField editor = (JTextField) table.getEditorComponent();
                        editor.selectAll();
                    }
                });
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // Добавление границы вокруг таблицы
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 15));

        // Создание панели и добавление компонентов
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Добавление кнопки сохранения и запуска симуляции
        JButton saveButton = new JButton("*** \uD83D\uDC07 START SIMULATION \uD83D\uDC07 ***");
        saveButton.addActionListener(e -> {
            Thread thread = new Thread(() -> {
                System.out.println("Start simulation new thread" + Thread.currentThread().getName());
            saveProperties(model);
            MapManager mapManager = new MapManager(getWidthField(), getHeightField());
            Render render = new Render(mapManager, frameWidth, frameHeight, smoothSimulation); // 1920, 1080
            Play play = new Play();
            play.startSimulation(mapManager, render, frameWidth, frameHeight, delay);
            });
            thread.start();
        });


        panel.add(saveButton, BorderLayout.SOUTH);

        // Добавление панели с текстовыми полями в верхнюю часть окна
        frame.add(inputPanel, BorderLayout.NORTH);

        // Добавление панели в окно
        frame.add(panel, BorderLayout.CENTER);

        // Обновление интерфейса
        frame.revalidate();
        frame.repaint();

        // Отображение окна
        frame.setVisible(true);
    }

    private void startAudioPlayer() {
        player = new AudioPlayer(FilePathConfig.getAudioPath(), true);
        audioThread = new Thread(player);
        audioThread.start();
        isPlaying = true;
    }

    private void stopAudioPlayer() {
        if (player != null) {
            player.stop();
            try {
                audioThread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            player = null;
            audioThread = null;
            isPlaying = false;
        }
    }

    public static int getHeightField() {
        return Integer.parseInt(heightField.getText());
    }

    public static int getWidthField() {
        return Integer.parseInt(widthField.getText());
    }


    // Метод для загрузки данных из properties файла
    private static void loadProperties() {
        try (FileInputStream fis = new FileInputStream(pathPropertiesFile.toFile())) {
            properties.load(fis);
        } catch (IOException e) {
            // Если файл не найден или не загружен, подтягиваются стандартные значения
            for (EntityProperties entity : EntityProperties.values()) {
                String name = entity.getValueName();
                properties.setProperty(name + ".count", String.valueOf(entity.getCount()));
                properties.setProperty(name + ".weight", String.valueOf(entity.getWeight()));
                properties.setProperty(name + ".speed", String.valueOf(entity.getSpeed()));
            }
        }
    }

    // Метод для получения данных из properties
    private static Object[][] getDataFromProperties() {
        Object[][] data = new Object[EntityProperties.values().length][COLUMN_NAMES.length];
        int index = 0;
        for (EntityProperties entity : EntityProperties.values()) {
            String name = entity.getValueName();
            data[index][0] = name;
            data[index][1] = properties.getProperty(name + ".count", "0");
            data[index][2] = properties.getProperty(name + ".weight", "0");
            data[index][3] = properties.getProperty(name + ".speed", "0");
            index++;
        }
        return data;
    }

    // Метод для сохранения данных в properties файл
    private static void saveProperties(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String name = (String) model.getValueAt(i, 0);
            properties.setProperty(name + ".count", (String) model.getValueAt(i, 1));
            properties.setProperty(name + ".weight", (String) model.getValueAt(i, 2));
            properties.setProperty(name + ".speed", (String) model.getValueAt(i, 3));
        }
        try (FileOutputStream fos = new FileOutputStream(pathPropertiesFile.toFile())) {
            properties.store(fos, "Entity properties");
            System.out.println("Файл " + FilePathConfig.getPropertiesPath() + " успешно создан!");
            //JOptionPane.showMessageDialog(null, "Properties saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving properties: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}