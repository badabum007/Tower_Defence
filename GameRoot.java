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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * �����, ����������� ������ ����
 * 
 * @author pixxx
 */
public class GameRoot extends Pane {
  /** �������, ������! */
  MusicContainer gameSound;

  /** ��������� �� ����� �������� */
  Spawner[] spawn = new Spawner[2];

  /** �����, ����������� �� ����� */
  ArrayList<Tower> towers;

  /** ����� ����: ��������������(Auto) ��� �������(Normal) */
  static String gameMode;

  /** ��� ��� ��������������� ������ */
  Bot bot;

  /** ���� ��� RePlay */
  File savingFile;

  /** ������ ������� ���������� ����� */
  long timeOfTower;

  /** �������� ���������� �� ����� */
  long argumentsFromFile[][];

  /** ���������� ����� � ����� */
  int maxCount = 0;

  /**
   * ����� �������������� Root � ������
   * 
   * @param path - ���� � ����������
   */
  public GameRoot(String path) {
    this.setVisible(false);
    /** �������� ������ */
    gameSound = new MusicContainer(path);
    gameSound.mediaPlayer.setVolume(0.4);
    getChildren().add(gameSound.mediaView);
  }

  /**
   * �����, ����������� ������ ����
   */
  public void startGame() {
    timeOfTower = 0;
    towers = new ArrayList<Tower>();
    createMap(false);
    gameSound.mediaPlayer.play();
    spawn[0] = new Spawner(30, 8 * Main.BLOCK_SIZE + 9, 0);
    spawn[1] = new Spawner(30, 11 * Main.BLOCK_SIZE + 9, 0);
    Main.fileWork.createTempFile("positions.txt");

    if (gameMode == "Auto") {
      bot = new Bot();
    }
    /** ������ ���������� �� ����� � �� ������ � ������ */
    if (gameMode == "RePlay") {
      int counter = 0;
      try {
        String[] args = new String[3];
        BufferedReader reader = new BufferedReader(new FileReader(Main.fileWork.loadFile));
        String line;
        maxCount = 0;
        /** ������� ���������� ����� � ����� ��� ��������� ������ */
        while ((line = reader.readLine()) != null) {
          maxCount++;
        }
        reader.close();
        reader = new BufferedReader(new FileReader(Main.fileWork.loadFile));
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
    /** �������� ������� */
    final LongProperty checkForShootTimer = new SimpleLongProperty();
    final LongProperty frameTimer = new SimpleLongProperty(0);
    AnimationTimer timer = new AnimationTimer() {
      long everyTick = 0;
      long everyTickForBot = 0;
      /** ������� ��� ���������� �� ����� */
      int counter = 0;

      @Override
      public void handle(long now) {
        timeOfTower++;
        everyTick++;
        /** 55 ����� ~= 1 ��� */
        if (everyTick > 55) {
          everyTick = 0;
          if (spawn[0].iterator < spawn[0].count)
            spawn[0].createMonster();
          if (spawn[1].iterator < spawn[1].count)
            spawn[1].createMonster();
        }
        /** ��������� ����� � ������������ � �������� ��������� */
        if (gameMode == "RePlay") {
          if (counter < maxCount) {
            if (timeOfTower > argumentsFromFile[counter][2]) {
              Tower tower =
                  new Tower(argumentsFromFile[counter][0], argumentsFromFile[counter][1], 150);
              Main.gameRoot.towers.add(tower);
              counter++;
            }
          }
        }
        if (gameMode == "Auto") {
          everyTickForBot++;
          /** 165 ����� ~= 3 ��� */
          if (everyTickForBot > 165) {
            everyTickForBot = 0;
            if (bot.iterator < bot.count)
              bot.createTower();
          }
        }
        /** �������� �� �������� ����� � ���������� 0.1 */
        if (now / 100000000 != checkForShootTimer.get()) {
          checkForShooting();
          if (Main.connectionType == "Client") {
            Main.client.recieve();
          }
          if (Main.connectionType == "Server") {
            Main.server.sendEmptyString();
          }
          /** ���������� Cooldown-� ������ ����� */
          for (int i = 0; i < towers.size(); i++) {
            towers.get(i).timeToShoot -= 0.1;
          }
        }
        /** ���������� �������������� �������� � ���������� 0.01 ��� */
        if (now / 10000000 != frameTimer.get()) {
          Thread thread_1 = new Thread(spawn[0]);
          Thread thread_2 = new Thread(spawn[1]);
          thread_1.start();
          try {
            thread_1.join();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          thread_2.start();
          try {
            thread_2.join();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        frameTimer.set(now / 10000000);
        checkForShootTimer.set(now / 100000000);
      }
    };
    timer.start();
    // �������� �������� �������� ��������� � ������������ �������� ����
    FadeTransition ftMenu = new FadeTransition(Duration.seconds(1), Main.menu);
    ftMenu.setFromValue(0);
    ftMenu.setToValue(1);
    FadeTransition ftFromGame = new FadeTransition(Duration.seconds(0.5), this);
    ftFromGame.setFromValue(0);
    ftFromGame.setToValue(1);
    /** ���������� ������� ESC � �������� ���� */
    Main.scene.setOnKeyPressed(event -> {
      if ((event.getCode() == KeyCode.ESCAPE) && (!Main.menu.isVisible())) {
        timer.stop();
        this.gameSound.mediaPlayer.pause();
        Main.menu.menuSound.mediaPlayer.play();
        ftMenu.play();
        this.setVisible(false);
        Main.menu.setVisible(true);
      }
      /** ���������� ������� ESC � �������� ���� */
      else if ((event.getCode() == KeyCode.ESCAPE) && (Main.menu.isVisible())) {
        /** ��������� */
        timer.start();
        ftFromGame.play();
        this.setVisible(true);
        Main.menu.setVisible(false);
        this.gameSound.mediaPlayer.play();
        Main.menu.menuSound.mediaPlayer.pause();
      }
    });
  }

  /**
   * ����� ������������� ����� �� �������
   * 
   * @see LevelData
   */
  public void createMap(boolean isStats) {
    for (int i = 0; i < LevelData.levels[0].length; i++) {
      String line = LevelData.levels[0][i];
      for (int j = 0; j < line.length(); j++) {
        switch (line.charAt(j)) {
          case 'T':
            Block tree =
                new Block(Block.BlockType.Tree, j * Main.BLOCK_SIZE, i * Main.BLOCK_SIZE, isStats);
            break;
          case '0':
            Block grass =
                new Block(Block.BlockType.Grass, j * Main.BLOCK_SIZE, i * Main.BLOCK_SIZE, isStats);
            break;
          default:
            Block road =
                new Block(Block.BlockType.Road, j * Main.BLOCK_SIZE, i * Main.BLOCK_SIZE, isStats);
            break;
        }
      }
    }
  }

  /**
   * �����, ��������� ����� � ������� �����������
   */
  /*public void createStatisticMap() {
    File[] saveFiles = FileWork.getSaveList();
    String line = LevelData.levels[0][0];
    int[][] statisticNumbers = new int[LevelData.levels[0].length][line.length()];
    int coordX, coordY, max = 0, greenColor = 0, sum = 0;
    for (int i = 0; i < saveFiles.length; i++) {
      try {
        String[] args = new String[3];
        BufferedReader reader = new BufferedReader(new FileReader(saveFiles[i]));
        while ((line = reader.readLine()) != null) {
          args = line.split(" ");
          coordY = Integer.parseInt(args[0]) / Main.BLOCK_SIZE;
          coordX = Integer.parseInt(args[1]) / Main.BLOCK_SIZE;
          statisticNumbers[coordX][coordY]++;
          sum++;
          if (statisticNumbers[coordX][coordY] > max) {
            max = statisticNumbers[coordX][coordY];
          }
        } ;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    createMap(true);
    if (max != 0) {
      greenColor = (int) (255 / max);
    }
  
  }*/

  /** �����, ����������� ����� ����� � ��������� �������� */
  public void checkForShooting() {
    int spawnersCount = 2;
    for (int i = 0; i < spawnersCount; i++) {
      for (int j = 0; j < spawn[i].enemies.size(); j++) {
        if (spawn[i].enemies.get(j).health <= 0) {
          spawn[i].enemies.remove(j);
          continue;
        }
        for (int k = 0; k < towers.size(); k++) {
          double EnemyPosX = spawn[i].enemies.get(j).getTranslateX();
          double EnemyPosY = spawn[i].enemies.get(j).getTranslateY();
          double TowerPosX = towers.get(k).getTranslateX();
          double TowerPosY = towers.get(k).getTranslateY();
          /** ������� �������� �� Cooldown */
          if (towers.get(k).timeToShoot <= 0) {
            /** �������� �� ��������� �������� */
            if (Math.pow(Math.pow(EnemyPosX - TowerPosX, 2) + Math.pow(EnemyPosY - TowerPosY, 2),
                0.5) < towers.get(k).attackRange) {
              /** ��������� Cooldown */
              towers.get(k).timeToShoot = towers.get(k).shootCooldown;
              /** �������� �������� */
              towers.get(k).shots = new Shot(spawn[i].enemies.get(j),
                  towers.get(k).posX + Main.BLOCK_SIZE / 2, towers.get(k).posY);
            }
          }
        }
      }
    }
  }
}
