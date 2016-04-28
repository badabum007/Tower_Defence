package application;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Класс, описывающий главное меню игры
 * 
 * @author pixxx
 */
class Menu extends StackPane {
  /** Музыка в меню */
  MusicContainer menuSound;

  /** Список файлов для загрузки */
  ListView<String> listView;

  /**
   * Метод добавляющий музыку в меню
   * 
   * @param path - Путь к аудиофайлу
   */
  public Menu(String path) {
    menuSound = new MusicContainer(path);
    getChildren().add(menuSound.mediaView);
  }

  /**
   * Метод создает меню и отвечает за его отображение
   */
  public void showMenu() {
    menuSound.mediaPlayer.play();
    MenuBox menuBox;
    /** Создание пунктов меню и их добавление в Root */
    VBox vbox = new VBox(10);
    MenuItem buttonStart = new MenuItem("New Game");
    MenuItem buttonAuto = new MenuItem("Auto Game");
    MenuItem buttonSaveGame = new MenuItem("Save");
    MenuItem buttonLoadGame = new MenuItem("Load");
    MenuItem buttonStatistic = new MenuItem("Statistic");
    MenuItem buttonLoad = new MenuItem("Load");
    MenuItem generateSaves = new MenuItem("Generate Saves");
    MenuItem buttonJavaSort = new MenuItem("JavaSort");
    MenuItem buttonScalaSort = new MenuItem("ScalaSort");
    MenuItem buttonBack = new MenuItem("Back");
    buttonSaveGame.setDisable(true);
    MenuItem buttonQuit = new MenuItem("Quit");
    menuBox = new MenuBox("TowerDefence", buttonStart, buttonAuto, buttonLoadGame, buttonSaveGame,
        buttonStatistic, buttonQuit);
    getChildren().add(menuBox);
    /** Обработчик кнопки "New Game" */
    buttonStart.setOnMouseClicked(event -> {
      buttonSaveGame.setDisable(false);
      menuBox.changeText("Pause (Press ESC to continue)");
      Main.gameRoot.gameSound.mediaPlayer.stop();
      menuSound.mediaPlayer.pause();
      setVisible(false);
      Main.gameRoot.gameMode = "Normal";
      Main.gameRoot.setVisible(true);
      Main.gameRoot.startGame();
    });
    /** Обработчик кнопки "Auto Game" */
    buttonAuto.setOnMouseClicked(event -> {
      buttonSaveGame.setDisable(false);
      menuBox.changeText("Pause (Press ESC to continue)");
      Main.gameRoot.gameSound.mediaPlayer.stop();
      menuSound.mediaPlayer.pause();
      setVisible(false);
      Main.gameRoot.gameMode = "Auto";
      Main.gameRoot.setVisible(true);
      Main.gameRoot.startGame();
    });
    /** Обработчик кнопки "Load" */
    buttonLoadGame.setOnMouseClicked(event -> {
      listView = new ListView<>();
      File[] saveFiles = Main.fileWork.getSaveList();
      for (int i = 0; i < saveFiles.length; i++) {
        listView.getItems().add(saveFiles[i].getName());
      }
      listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      Text text = new Text("Choose load file");
      text.setFont(new Font(40));
      text.setFill(Color.WHITE);
      vbox.setAlignment(Pos.CENTER);
      vbox.getChildren().addAll(text);
      vbox.getChildren().addAll(listView);
      vbox.getChildren().addAll(buttonLoad, buttonJavaSort, buttonScalaSort, generateSaves, buttonBack);
      setAlignment(Pos.CENTER);
      getChildren().addAll(vbox);
    });
    /** Обработчик "Back" в подменю "Load" */
    buttonBack.setOnMouseClicked(event -> {
      vbox.getChildren().clear();
      getChildren().remove(vbox);
    });
    /** Обработчик "Generate Saves" в подменю "Load"*/
    generateSaves.setOnMouseClicked(event ->{
      try {
        Main.fileWork.generateRandomFiles();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    /** Обработчик "Load" в подменю "Load" */
    buttonLoad.setOnMouseClicked(event -> {
      String choise;
      choise = listView.getSelectionModel().getSelectedItem();
      if (choise != null) {
        buttonSaveGame.setDisable(true);
        Main.gameRoot.gameMode = "RePlay";
        menuBox.changeText("Pause (Press ESC to continue)");
        vbox.getChildren().clear();
        getChildren().remove(vbox);
        Main.fileWork.loadFile = choise;
        menuSound.mediaPlayer.pause();
        Main.gameRoot.gameSound.mediaPlayer.stop();
        setVisible(false);
        if (Main.gameRoot.gameMode != "Auto")
          Main.gameRoot.gameMode = "RePlay";
        Main.gameRoot.setVisible(true);
        Main.gameRoot.startGame();
      }
    });
    /** Обработчик "JavaSort" в подменю "Load" */
    buttonJavaSort.setOnMouseClicked(event -> {
      long timeJava = System.currentTimeMillis();
      listView.getItems().clear();
      File[] sortedFiles = Main.fileWork.getSortedJavaList();
      for (int i = 0; i < 10; i++) {
        sortedFiles = Main.fileWork.getSortedJavaList();
      }
      for (int i = 0; i < sortedFiles.length; i++) {
        listView.getItems().add(sortedFiles[i].getName());
      }
      timeJava = System.currentTimeMillis() - timeJava;
      System.out.println("Java: " + timeJava);
    });
    /** Обработчик "ScalaSort" в подменб "Load" */
    buttonScalaSort.setOnMouseClicked(event -> {
      long timeScala = System.currentTimeMillis();
      listView.getItems().clear();
      File[] sortedFiles = Main.fileWork.getSortedScalaList();
      for (int i = 0; i < 10; i++) {
        sortedFiles = Main.fileWork.getSortedScalaList();
      }
      for (int i = 0; i < sortedFiles.length; i++) {
        listView.getItems().add(sortedFiles[i].getName());
      }
      timeScala = System.currentTimeMillis() - timeScala;
      System.out.println("Scala: " + timeScala);
    });
    /** Обработчик кнопки "Save" */
    buttonSaveGame.setOnMouseClicked(event -> {
      try {
        Main.fileWork.createSave();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    /** Обработчик кнопки Statistic */
    buttonStatistic.setOnMouseClicked(event->{
      Statistic stats = new Statistic();
      stats.showStatistic();
    });
    /** Обработчик кнопки "Quit" */
    buttonQuit.setOnMouseClicked(event -> {
      System.exit(0);
    });
  }
}


/**
 * Класс, описывающий контейнер для кнопок меню
 * 
 * @author pixxx
 */
class MenuBox extends StackPane {
  Text text;

  public MenuBox(String title, MenuItem... items) {
    Rectangle bg = new Rectangle(Main.RESOLUTION_X, Main.RESOLUTION_Y);
    bg.setOpacity(0.7);
    text = new Text(title);
    text.setFont(new Font(40));
    text.setFill(Color.WHITE);
    VBox vbox = new VBox();
    vbox.setAlignment(Pos.CENTER);
    vbox.getChildren().addAll(text);
    vbox.getChildren().addAll(items);
    setAlignment(Pos.CENTER);
    getChildren().addAll(bg, vbox);
  }

  public void changeText(String title) {
    text.setText(title);
  }
}


/**
 * Класс, описывающий кнопки меню
 * 
 * @author pixxx
 */
class MenuItem extends StackPane {
  public MenuItem(String name) {
    Rectangle bg = new Rectangle(300, 24);
    bg.setVisible(false);
    bg.setOpacity(0.6);
    Text text = new Text(name);
    text.setFill(Color.LIGHTGREY);
    text.setFont(Font.font(20));
    setAlignment(Pos.CENTER);
    getChildren().addAll(bg, text);
    /** Поведение кнопки при наведении на нее */
    this.setOnMouseEntered(event -> {
      bg.setVisible(true);
      text.setFill(Color.LIGHTGRAY);
    });
    /** Поведение кнопки при снятии наведения */
    this.setOnMouseExited(event -> {
      bg.setVisible(false);
      text.setFill(Color.LIGHTGRAY);
    });
    /** Поведение кнопки при нажатии */
    this.setOnMousePressed(event -> {
      bg.setFill(Color.WHITE);
      text.setFill(Color.BLACK);
    });
    this.setOnMouseReleased(event -> {
      bg.setFill(Color.BLACK);
      text.setFill(Color.WHITE);
    });
  }
}
