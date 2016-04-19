package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Класс, описывающий движок игры
 * 
 * @author pixxx
 */
public class GameRoot extends Pane {
  /** Маестро, музыку! */
  MusicContainer GameSound;

  /** Имеющиеся на карте Спаунеры */
  Spawner[] Spawn = new Spawner[2];

  /** Вышки, добавленные на карту */
  ArrayList<Tower> Towers;

  /** Режим игры: автоматический(Auto) или обычный(Normal) */
  static String GameMode;

  /** Бот для автоматического режима */
  Bot bot;

  /** Файл для RePlay */
  File savingFile;

  /** Момент времени постановки вышки */
  long timeOfTower;

  /** Хранение аргументов из файла */
  long argumentsFromFile[][];

  /** Количество строк в файле */
  int maxCount = 0;

  /**
   * Метод подготавливает Root и музыку
   * 
   * @param path - Путь к аудиофайлу
   */
  public GameRoot(String path) {
    this.setVisible(false);
    GameSound = new MusicContainer(path); // Маестро, музыку
    GameSound.mediaPlayer.setVolume(0.4);
    getChildren().add(GameSound.mediaView);
  }

  /**
   * Метод, реализующий логику игры
   */
  public void StartGame() {
    timeOfTower = 0;
    Towers = new ArrayList<Tower>();
    CreateMap();
    GameSound.mediaPlayer.play();
    Spawn[0] = new Spawner(30, 8 * Main.BLOCK_SIZE + 9, 0);
    Spawn[1] = new Spawner(30, 11 * Main.BLOCK_SIZE + 9, 0);
    Main.fileWork.CreateTempFile("positions.txt");

    if (GameMode == "Auto") {
      bot = new Bot();
    }
    // Чтение аргументов из файла и их запись в массив
    if (GameMode == "RePlay") {
      int counter = 0;
      try {
        String[] args = new String[3];
        BufferedReader reader = new BufferedReader(new FileReader(Main.fileWork.LoadFile));
        String line;
        maxCount = 0;
        // Считаем количество строк в файле для выделения памяти
        while ((line = reader.readLine()) != null){
          maxCount++;
        }
        reader.close();
        reader = new BufferedReader(new FileReader(Main.fileWork.LoadFile));
        argumentsFromFile = new long[maxCount][3];
        while ((line = reader.readLine()) != null) {
          args = line.split(" ");
          for (int i = 0; i < 3; i++) {
            argumentsFromFile[counter][i] = Integer.parseInt(args[i]);
          }
          counter++;
        } ;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    /** Описание таймера */
    final LongProperty CheckForShootTimer = new SimpleLongProperty();
    final LongProperty FrameTimer = new SimpleLongProperty(0);
    AnimationTimer timer = new AnimationTimer() {
      long EveryTick = 0;
      long EveryTickForBot = 0;
      // Счетчик для аргументов из файла
      int counter = 0;

      @Override
      public void handle(long now) {
        timeOfTower++;
        EveryTick++;
        // 55 тиков ~= 1 сек
        if (EveryTick > 55) {
          EveryTick = 0;
          if (Spawn[0].iterator < Spawn[0].count)
            Spawn[0].CreateMonster();
          if (Spawn[1].iterator < Spawn[1].count)
            Spawn[1].CreateMonster();
        }
        // Установка вышек в соответствии с временем постройки
        if (GameMode == "RePlay") {
          if (counter < maxCount) {
            if (timeOfTower > argumentsFromFile[counter][2]) {
              Tower tower =
                  new Tower(argumentsFromFile[counter][0], argumentsFromFile[counter][1], 150);
              Main.gameRoot.Towers.add(tower);
              counter++;
            }
          }
        }
        if (GameMode == "Auto") {
          EveryTickForBot++;
          // 165 тиков ~= 3 сек
          if (EveryTickForBot > 165) {
            EveryTickForBot = 0;
            if (bot.Iterator < bot.Count)
              bot.createTower();
          }
        }
        // Проверка на выстрелы вышек с интервалом 0.1
        if (now / 100000000 != CheckForShootTimer.get()) {
          CheckForShooting();
          if (Main.connectionType == "Client") {
            Main.client.recieve();
          }
          if (Main.connectionType == "Server") {
            Main.server.sendEmptyString();
          }
          // Уменьшение Cooldown-а каждой вышки
          for (int i = 0; i < Towers.size(); i++) {
            Towers.get(i).TimeToShoot -= 0.1;
          }
        }
        // Обновление местоположения монстров с интервалом 0.01 сек
        if (now / 10000000 != FrameTimer.get()) {
          Thread t1 = new Thread(Spawn[0]);
          Thread t2 = new Thread(Spawn[1]);
          t1.start();
          try {
            t1.join();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          t2.start();
          try {
            t2.join();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        FrameTimer.set(now / 10000000);
        CheckForShootTimer.set(now / 100000000);
      }
    };
    timer.start();
    // Описание анимации плавного появления и исчезновения главного меню
    FadeTransition FT_Menu = new FadeTransition(Duration.seconds(1), Main.menu);
    FT_Menu.setFromValue(0);
    FT_Menu.setToValue(1);
    FadeTransition FT_FromGame = new FadeTransition(Duration.seconds(0.5), this);
    FT_FromGame.setFromValue(0);
    FT_FromGame.setToValue(1);
    // Обработчик нажатия ESC в процессе игры
    Main.scene.setOnKeyPressed(event -> {
      if ((event.getCode() == KeyCode.ESCAPE) && (!Main.menu.isVisible())) {
        timer.stop();
        this.GameSound.mediaPlayer.pause();
        Main.menu.MenuSound.mediaPlayer.play();
        FT_Menu.play();
        this.setVisible(false);
        Main.menu.setVisible(true);
      }
      // Обработчик нажатия ESC в процессе игры
      else if ((event.getCode() == KeyCode.ESCAPE) && (Main.menu.isVisible())) {
        // Затухание
        timer.start();
        FT_FromGame.play();
        this.setVisible(true);
        Main.menu.setVisible(false);
        this.GameSound.mediaPlayer.play();
        Main.menu.MenuSound.mediaPlayer.pause();
      }
    });
  }

  /**
   * Метод прорисовывает карту по матрице
   * 
   * @see LevelData
   */
  public void CreateMap() {
    for (int i = 0; i < LevelData.levels[0].length; i++) {
      String line = LevelData.levels[0][i];
      for (int j = 0; j < line.length(); j++) {
        switch (line.charAt(j)) {
          case 'T':
            Block tree = new Block(Block.BlockType.Tree, j * Main.BLOCK_SIZE, i * Main.BLOCK_SIZE);
            break;
          case '0':
            Block grass =
                new Block(Block.BlockType.Grass, j * Main.BLOCK_SIZE, i * Main.BLOCK_SIZE);
            break;
          default:
            Block road = new Block(Block.BlockType.Road, j * Main.BLOCK_SIZE, i * Main.BLOCK_SIZE);
            break;
        }
      }
    }
  }

  /** Метод, реализующий поиск целей и генерации выстрела */
  public void CheckForShooting() {
    int SpawnersCount = 2;
    for (int i = 0; i < SpawnersCount; i++) {
      for (int j = 0; j < Spawn[i].enemies.size(); j++) {
        if (Spawn[i].enemies.get(j).Health <= 0) {
          Spawn[i].enemies.remove(j);
          continue;
        }
        for (int k = 0; k < Towers.size(); k++) {
          double EnemyPosX = Spawn[i].enemies.get(j).getTranslateX();
          double EnemyPosY = Spawn[i].enemies.get(j).getTranslateY();
          double TowerPosX = Towers.get(k).getTranslateX();
          double TowerPosY = Towers.get(k).getTranslateY();
          // Условие проверки на Cooldown
          if (Towers.get(k).TimeToShoot <= 0) {
            // Проверка на дальность выстрела
            if (Math.pow(Math.pow(EnemyPosX - TowerPosX, 2) + Math.pow(EnemyPosY - TowerPosY, 2),
                0.5) < Towers.get(k).attackRange) {
              // Установка Cooldown
              Towers.get(k).TimeToShoot = Towers.get(k).ShootCooldown;
              // Создание выстрела
              Towers.get(k).Shots = new Shot(Spawn[i].enemies.get(j),
                  Towers.get(k).posX + Main.BLOCK_SIZE / 2, Towers.get(k).posY);
            }
          }
        }
      }
    }
  }
}
