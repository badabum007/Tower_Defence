package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * ����� �������� �������� ����������, �����, Root-�
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
	static Pane All = new Pane();
	static Scene scene;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		menu = new Menu("menu.mp3");
		gameRoot = new GameRoot("wave1.mp3");
		scene = new Scene(All, RESOLUTION_X, RESOLUTION_Y);
		All.getChildren().addAll(menu, gameRoot);
		menu.ShowMenu();
		primaryStage.setTitle("TowerDefence");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
