package application;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * �����, ����������� ������� ���� ����
 * @author pixxx
 */
class Menu extends StackPane{
	/** ������ � ���� */
	MusicContainer MenuSound;
	
	/**
	 * ����� ����������� ������ � ����
	 * @param path - ���� � ����������
	 */
	public Menu(String path){
		MenuSound = new MusicContainer(path); 
		getChildren().add(MenuSound.mediaView);
	}
	
	/**
	 * ����� ������� ���� � �������� �� ��� �����������
	 */
	public void ShowMenu(){
		MenuSound.mediaPlayer.play();
		MenuBox menuBox;
		/** �������� ������� ���� � �� ���������� � Root */
		MenuItem ButtonStart = new MenuItem("New Game");
		MenuItem ButtonAuto = new MenuItem("Auto Game");
		MenuItem ButtonQuit = new MenuItem("Quit");
		menuBox = new MenuBox("TowerDefence", ButtonStart, ButtonAuto, ButtonQuit);
		getChildren().add(menuBox);
		/** ���������� ������ "New Game" */
		ButtonStart.setOnMouseClicked(event ->{
			ButtonStart.setDisable(true);
			ButtonAuto.setDisable(true);
			menuBox.ChangeText("Pause (Press ESC to continue)");
			MenuSound.mediaPlayer.pause();
			setVisible(false);
			Main.gameRoot.GameMode = "Normal";
			Main.gameRoot.setVisible(true);
			Main.gameRoot.StartGame();
		});
		/** ���������� ������ "Auto Game" */
		ButtonAuto.setOnMouseClicked(event->{
			ButtonAuto.setDisable(true);
			ButtonStart.setDisable(true);
			menuBox.ChangeText("Pause (Press ESC to continue)");
			MenuSound.mediaPlayer.pause();
			setVisible(false);
			Main.gameRoot.GameMode = "Auto";
			Main.gameRoot.setVisible(true);
			Main.gameRoot.StartGame();
		});
		/** ���������� ������ "Quit" */
		ButtonQuit.setOnMouseClicked(event -> System.exit(0));
	}
}

/**
 * �����, ����������� ��������� ��� ������ ����
 * @author pixxx
 */
class MenuBox extends StackPane{
	Text text;
	public MenuBox(String title, MenuItem... items){
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
	
	public void ChangeText(String title){
		text.setText(title);
	}
}

/**
 * �����, ����������� ������ ����
 * @author pixxx
 */
class MenuItem extends StackPane{
	public MenuItem(String name){
		Rectangle bg = new Rectangle(300,24);	
		bg.setVisible(false);
		bg.setOpacity(0.6);
		Text text = new Text(name);
		text.setFill(Color.LIGHTGREY);
		text.setFont(Font.font(20));
		setAlignment(Pos.CENTER);
		getChildren().addAll(bg, text);
		/** ��������� ������ ��� ��������� �� ��� */
		this.setOnMouseEntered(event ->{ 
			bg.setVisible(true);
			text.setFill(Color.LIGHTGRAY);
		});
		/** ��������� ������ ��� ������ ��������� */
		this.setOnMouseExited(event ->{
			bg.setVisible(false);
			text.setFill(Color.LIGHTGRAY);
		});
		/** ��������� ������ ��� ������� */
		this.setOnMousePressed(event ->{
			bg.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
		});
		this.setOnMouseReleased(event ->{
			bg.setFill(Color.BLACK);
			text.setFill(Color.WHITE);
		});
	}
}