package application;

import java.io.File;

import javafx.geometry.Pos;
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
  MusicContainer MenuSound;

  /**
   * Метод добавляющий музыку в меню
   * 
   * @param path - Путь к аудиофайлу
   */
  public Menu(String path) {
    MenuSound = new MusicContainer(path);
    getChildren().add(MenuSound.mediaView);
  }

  /**
   * Метод создает меню и отвечает за его отображение
   */
  public void ShowMenu() {
    MenuSound.mediaPlayer.play();
    MenuBox menuBox;
    /** Создание пунктов меню и их добавление в Root */
    MenuItem ButtonStart = new MenuItem("New Game");
    MenuItem ButtonAuto = new MenuItem("Auto Game");
    MenuItem ButtonRePlay = new MenuItem("Re-Play");
    ButtonRePlay.setDisable(true);
    MenuItem ButtonQuit = new MenuItem("Quit");
    menuBox = new MenuBox("TowerDefence", ButtonStart, ButtonAuto, ButtonRePlay, ButtonQuit);
    getChildren().add(menuBox);
    /** Обработчик кнопки "New Game" */
    ButtonStart.setOnMouseClicked(event -> {
      ButtonStart.setDisable(true);
      ButtonAuto.setDisable(true);
      ButtonRePlay.setDisable(false);
      menuBox.ChangeText("Pause (Press ESC to continue)");
      MenuSound.mediaPlayer.pause();
      setVisible(false);
      Main.gameRoot.GameMode = "Normal";
      Main.gameRoot.setVisible(true);
      Main.gameRoot.StartGame();
    });
    /** Обработчик кнопки "Auto Game" */
    ButtonAuto.setOnMouseClicked(event -> {
      ButtonAuto.setDisable(true);
      ButtonStart.setDisable(true);
      ButtonRePlay.setDisable(false);
      menuBox.ChangeText("Pause (Press ESC to continue)");
      MenuSound.mediaPlayer.pause();
      setVisible(false);
      Main.gameRoot.GameMode = "Auto";
      Main.gameRoot.setVisible(true);
      Main.gameRoot.StartGame();
    });
    /** Обработчик кнопки "Re-Play" */
    ButtonRePlay.setOnMouseClicked(event -> {
      ButtonRePlay.setDisable(true);
      MenuSound.mediaPlayer.pause();
      Main.gameRoot.GameSound.mediaPlayer.stop();
      setVisible(false);
      if (Main.gameRoot.GameMode != "Auto")
        Main.gameRoot.GameMode = "RePlay";
      Main.gameRoot.setVisible(true);
      Main.gameRoot.StartGame();
    });
    /** Обработчик кнопки "Quit" */
    ButtonQuit.setOnMouseClicked(event -> {
      if (Main.gameRoot.savingFile != null)
        Main.gameRoot.savingFile.delete();
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

  public void ChangeText(String title) {
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
