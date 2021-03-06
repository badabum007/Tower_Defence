package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * ����� �������� �������� ����������, �����, Root-�
 * 
 * @author pixxx
 * @version 1.0 Alpha
 */
public class Main extends Application {
  /** ������ ����� � �������� � ������� ���� */
  public static final int BLOCK_SIZE = 50;
  public static final int RESOLUTION_X = 1000;
  public static final int RESOLUTION_Y = 800;

  /** ����� � Root-� */
  static GameRoot gameRoot;
  static Menu menu;
  static Pane all = new Pane();
  static Scene scene;
  static FileWork fileWork;

  static Server server;
  static Client client;

  /** Type of connection: Server or Client */
  static String connectionType;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    connectionType = "Single";
    if (connectionType == "Server") {
      server = new Server();
    }
    if (connectionType == "Client") {
      client = new Client();
    }
    fileWork = new FileWork();
    menu = new Menu("menu.mp3");
    gameRoot = new GameRoot("wave1.mp3");
    scene = new Scene(all, RESOLUTION_X, RESOLUTION_Y);
    all.getChildren().addAll(menu, gameRoot);
    menu.showMenu();
    primaryStage.setTitle(connectionType);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
