package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import application.SList;

/**
 * Класс реализует обработку и вывод статистики
 * 
 * @author pixxx
 *
 */
public class Statistic {
  /** Статистика по каждой клетке */
  int[][] statisticNumbers;

  /** Оттенок зеленого */
  int greenColor;

  /** Общее кол-во башен */
  int sum;

  /** Scala Class содержащий статистику */
  SList listScala;

  /**
   * Инициализация данных
   */
  public Statistic() {
    String line = LevelData.levels[0][0];
    listScala = new SList();
    greenColor = 0;
    sum = 0;
    statisticNumbers = new int[LevelData.levels[0].length][line.length()];
    getStatisticNumbers();
  }

  /**
   * Метод считывает статистические данные о клетках и записывает их в массив
   */
  public void getStatisticNumbers() {
    int coordX, coordY, max = 0;
    String line = LevelData.levels[0][0];
    File[] saveFiles = FileWork.getSaveList();
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
    for (int i = 0; i < statisticNumbers.length; i++) {
      for (int j = 0; j < statisticNumbers[i].length; j++) {
        listScala.addEl(statisticNumbers[i][j]);
      }
    }
    System.out.println("statisticNumbers[7][4]");
    // System.out.println(listScala.getElem(7, 4, statisticNumbers[0].length));
    Main.gameRoot.createMap(true);
    if (max != 0) {
      int maxRGB = 255;
      greenColor = (int) (maxRGB / max);
    }
  }

  /**
   * Метод реализует вывод статистики на экран
   */
  public void showStatistic() {
    final int ROUND = 100;
    VBox vbox = new VBox();
    double temp;
    String line;
    List<Object> arrlist = new ArrayList<Object>();
    arrlist = listScala.ret();
    for (int i = 0; i < statisticNumbers.length; i++) {
      HBox hbox = new HBox();
      for (int j = 0; j < statisticNumbers[i].length; j++) {
        line = LevelData.levels[0][i];
        temp = (double) arrlist.get(i * statisticNumbers[i].length + j) / sum * ROUND;
        temp *= ROUND;
        int tempValue = (int) temp;
        temp = (double) tempValue / ROUND;
        Text boxText = new Text(Double.toString(temp) + "%");
        Rectangle bg = new Rectangle(Main.BLOCK_SIZE, Main.BLOCK_SIZE);
        bg.setFill(Color.rgb(0, greenColor * statisticNumbers[i][j], 0));
        bg.setOpacity(0.3);
        StackPane sPane = new StackPane();
        sPane.getChildren().add(bg);
        if (line.charAt(j) == '0') {
          sPane.getChildren().add(boxText);
        } else {
          bg.setOpacity(0.1);
        }
        hbox.getChildren().add(sPane);
      }
      vbox.getChildren().add(hbox);
    }
    Main.menu.getChildren().addAll(vbox);
    Main.scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        Main.menu.getChildren().clear();
        Main.menu.showMenu();
      }
    });
  }
}
