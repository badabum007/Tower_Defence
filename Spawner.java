package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Класс, описывающий место появления монстров
 * 
 * @author pixxx
 */
public class Spawner implements Runnable {
  /** Кол-во выходящих монстров(в сумме) */
  int count;

  int startPosX;
  int startPosY;

  /** Кол-во уже выпущенных монстров */
  int iterator;

  /** Список всех созданных этим Spawner-ом монстров */
  ArrayList<Enemy> enemies;

  /**
   * Создает Spawner с заданными параметрами
   * 
   * @param count - Кол-во выходящих монстров
   * @param startPosX - Стартовая точка по X
   * @param startPosY - Стартовая точка по Y
   */
  public Spawner(int count, int startPosX, int startPosY) {
    this.startPosX = startPosX;
    this.startPosY = startPosY;
    this.count = count;
    iterator = 0;
    enemies = new ArrayList<Enemy>();
  }

  /** Создание монстра */
  public void createMonster() {
    enemies.add(new Enemy(startPosX, startPosY, 3, 3));
    iterator++;
  }

  /** Метод, обновляющий местоположение монстров */
  public void update() {
    int blockX, blockY;
    double blockCenterX, blockCenterY;
    for (int j = 0; j < enemies.size(); j++) {
      blockX = (int) (enemies.get(j).posX / Main.BLOCK_SIZE);
      blockY = (int) (enemies.get(j).posY / Main.BLOCK_SIZE);
      String line = LevelData.levels[0][blockY];
      blockCenterX = enemies.get(j).posX + enemies.get(j).width / 2;
      blockCenterY = enemies.get(j).posY + enemies.get(j).height / 2;
      if ((enemies.get(j).prevBlock == 'U')
          && (blockCenterY > blockY * Main.BLOCK_SIZE + Main.BLOCK_SIZE / 2)) {
        enemies.get(j).moveY(-1);
        continue;
      }
      if ((enemies.get(j).prevBlock == 'D')
          && (blockCenterY < blockY * Main.BLOCK_SIZE + Main.BLOCK_SIZE / 2)) {
        enemies.get(j).moveY(1);
        continue;
      }
      if ((enemies.get(j).prevBlock == 'R')
          && (blockCenterX < blockX * Main.BLOCK_SIZE + Main.BLOCK_SIZE / 2)) {
        enemies.get(j).moveX(1);
        continue;
      }
      if ((enemies.get(j).prevBlock == 'L')
          && (blockCenterX > blockX * Main.BLOCK_SIZE + Main.BLOCK_SIZE / 2)) {
        enemies.get(j).moveX(-1);
        continue;
      }
      if (line.charAt(blockX) == 'U') {
        enemies.get(j).animation.play();
        enemies.get(j).animation.setOffsetY(96);
        enemies.get(j).prevBlock = 'U';
        enemies.get(j).moveY(-1);
      }
      if (line.charAt(blockX) == 'D') {
        enemies.get(j).animation.play();
        enemies.get(j).animation.setOffsetY(0);
        enemies.get(j).prevBlock = 'D';
        enemies.get(j).moveY(1);
      }
      if (line.charAt(blockX) == 'R') {
        enemies.get(j).animation.play();
        enemies.get(j).animation.setOffsetY(64);
        enemies.get(j).prevBlock = 'R';
        enemies.get(j).moveX(1);
      }
      if (line.charAt(blockX) == 'L') {
        enemies.get(j).animation.play();
        enemies.get(j).animation.setOffsetY(32);
        enemies.get(j).prevBlock = 'L';
        enemies.get(j).moveX(-1);
      }

      if (line.charAt(blockX) == 'E') {
        enemies.get(j).enemyGoalRiched();
      }
    }
  }

  @Override
  public void run() {
    update();
  }
}
