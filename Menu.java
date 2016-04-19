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
 * �����, ����������� ������� ���� ����
 * 
 * @author pixxx
 */
class Menu extends StackPane {
  /** ������ � ���� */
  MusicContainer MenuSound;
  
  /** ������ ������ ��� �������� */
  ListView<String> listView;

  /**
   * ����� ����������� ������ � ����
   * 
   * @param path - ���� � ����������
   */
  public Menu(String path) {
    MenuSound = new MusicContainer(path);
    getChildren().add(MenuSound.mediaView);
  }

  /**
   * ����� ������� ���� � �������� �� ��� �����������
   */
  public void ShowMenu() {
    MenuSound.mediaPlayer.play();
    MenuBox menuBox;
    /** �������� ������� ���� � �� ���������� � Root */
    VBox vbox = new VBox(10);
    MenuItem ButtonStart = new MenuItem("New Game");
    MenuItem ButtonAuto = new MenuItem("Auto Game");
    MenuItem ButtonSaveGame = new MenuItem("Save");
    MenuItem ButtonLoadGame = new MenuItem("Load");
    MenuItem ButtonLoad = new MenuItem("Load");
    MenuItem ButtonJavaSort = new MenuItem("JavaSort");
    MenuItem ButtonScalaSort = new MenuItem("ScalaSort");
    MenuItem ButtonBack = new MenuItem("Back");
    ButtonSaveGame.setDisable(true);
    MenuItem ButtonQuit = new MenuItem("Quit");
    menuBox = new MenuBox("TowerDefence", ButtonStart, ButtonAuto, ButtonLoadGame, ButtonSaveGame, ButtonQuit);
    getChildren().add(menuBox);
    /** ���������� ������ "New Game" */
    ButtonStart.setOnMouseClicked(event -> {
      ButtonSaveGame.setDisable(false);
      menuBox.ChangeText("Pause (Press ESC to continue)");
      Main.gameRoot.GameSound.mediaPlayer.stop();
      MenuSound.mediaPlayer.pause();
      setVisible(false);
      Main.gameRoot.GameMode = "Normal";
      Main.gameRoot.setVisible(true);
      Main.gameRoot.StartGame();
    });
    /** ���������� ������ "Auto Game" */
    ButtonAuto.setOnMouseClicked(event -> {
      ButtonSaveGame.setDisable(false);
      menuBox.ChangeText("Pause (Press ESC to continue)");
      Main.gameRoot.GameSound.mediaPlayer.stop();
      MenuSound.mediaPlayer.pause();
      setVisible(false);
      Main.gameRoot.GameMode = "Auto";
      Main.gameRoot.setVisible(true);
      Main.gameRoot.StartGame();
    });
    /** ���������� ������ "Load" */
    ButtonLoadGame.setOnMouseClicked(event -> {
      listView = new ListView<>();
      File[] saveFiles = Main.fileWork.GetSaveList();
      for (int i = 0; i<saveFiles.length; i++){
        listView.getItems().add(saveFiles[i].getName());
      }
      listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      Text text = new Text("Choose load file");
      text.setFont(new Font(40));
      text.setFill(Color.WHITE);
      vbox.setAlignment(Pos.CENTER);
      vbox.getChildren().addAll(text);
      vbox.getChildren().addAll(listView);
      vbox.getChildren().addAll(ButtonLoad, ButtonJavaSort, ButtonScalaSort, ButtonBack);
      setAlignment(Pos.CENTER);
      getChildren().addAll(vbox);
    });
    /** ���������� "Back" � ������� "Load" */
    ButtonBack.setOnMouseClicked(event -> {
      vbox.getChildren().clear();
      getChildren().remove(vbox);
    });
    /** ���������� "Load" � ������� "Load" */
    ButtonLoad.setOnMouseClicked(event ->{
      String choise;
      choise = listView.getSelectionModel().getSelectedItem();
      if (choise != null){
        ButtonSaveGame.setDisable(true);
        Main.gameRoot.GameMode = "RePlay";
        vbox.getChildren().clear();
        getChildren().remove(vbox);
        Main.fileWork.LoadFile = choise;
        MenuSound.mediaPlayer.pause();
        Main.gameRoot.GameSound.mediaPlayer.stop();
        setVisible(false);
        if (Main.gameRoot.GameMode != "Auto")
          Main.gameRoot.GameMode = "RePlay";
        Main.gameRoot.setVisible(true);
        Main.gameRoot.StartGame();
      }
    });
    /** ���������� "JavaSort" � ������� "Load" */
    ButtonJavaSort.setOnMouseClicked(event ->{
      long timeJava = System.currentTimeMillis();
      listView.getItems().clear();
      File[] sortedFiles = Main.fileWork.GetSortedJavaList();
      for (int i = 0; i<10000; i++){
        sortedFiles = Main.fileWork.GetSortedJavaList();
      }
      for (int i = 0; i<sortedFiles.length; i++){
        listView.getItems().add(sortedFiles[i].getName());
      }
      timeJava = System.currentTimeMillis() - timeJava;
      System.out.println("Java: " + timeJava);
    });
    /** ���������� "ScalaSort" � ������� "Load" */
    ButtonScalaSort.setOnMouseClicked(event ->{
      long timeScala = System.currentTimeMillis();
      listView.getItems().clear();
      File[] sortedFiles = Main.fileWork.GetSortedScalaList();
      for (int i = 0; i<10000; i++){
        sortedFiles = Main.fileWork.GetSortedScalaList();
      }
      for (int i = 0; i<sortedFiles.length; i++){
        listView.getItems().add(sortedFiles[i].getName());
      }
      timeScala = System.currentTimeMillis() - timeScala;
      System.out.println("Scala: " + timeScala);
    });
    /** ���������� ������ "Save" */
    ButtonSaveGame.setOnMouseClicked(event ->{
      try {
        Main.fileWork.CreateSave();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    /** ���������� ������ "Quit" */
    ButtonQuit.setOnMouseClicked(event -> {
      System.exit(0);
    });
  }
}


/**
 * �����, ����������� ��������� ��� ������ ����
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
 * �����, ����������� ������ ����
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
    /** ��������� ������ ��� ��������� �� ��� */
    this.setOnMouseEntered(event -> {
      bg.setVisible(true);
      text.setFill(Color.LIGHTGRAY);
    });
    /** ��������� ������ ��� ������ ��������� */
    this.setOnMouseExited(event -> {
      bg.setVisible(false);
      text.setFill(Color.LIGHTGRAY);
    });
    /** ��������� ������ ��� ������� */
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
