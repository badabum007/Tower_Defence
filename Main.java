package application;
	
import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	public static final int BLOCK_SIZE = 50; // Размер блока
	public static final int RESOLUTION_X = 1000;
	public static final int RESOLUTION_Y = 800;
	static GameRoot gameRoot;
	static Menu menu;
	static Pane All = new Pane();
	static Scene scene;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		menu = new Menu("res/menu.mp3");
		gameRoot = new GameRoot("res/wave1.mp3");
		scene = new Scene(All, RESOLUTION_X, RESOLUTION_Y);
		All.getChildren().addAll(menu, gameRoot);
		menu.ShowMenu();
		primaryStage.setTitle("TowerDefence");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
