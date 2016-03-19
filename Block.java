package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Block extends Pane{
	ImageView block;
	Image img_grass = new Image("file:res/grass.jpg"); // Текстура травы
	Image img_tree = new Image("file:res/tree.png"); // Текстура дерева 
	Image img_road = new Image("file:res/road.jpg"); // Текстура дороги
	public enum BlockType{
		Grass, Tree, Road;
	}
	
	public Block(BlockType type, int x, int y){
		block = new ImageView();
		block.setFitHeight(Main.BLOCK_SIZE);
		block.setFitWidth(Main.BLOCK_SIZE);
		setTranslateX(x);
		setTranslateY(y);
		switch(type){
		case Tree: 
			block.setImage(img_tree);
			break;
		case Grass:
			block.setImage(img_grass);
			Line line1 = new Line(x, y, x+Main.BLOCK_SIZE, y);
			Line line2 = new Line(x+Main.BLOCK_SIZE, y, x+Main.BLOCK_SIZE, y + Main.BLOCK_SIZE);
			Line line3 = new Line(x + Main.BLOCK_SIZE, y + Main.BLOCK_SIZE, x, y + Main.BLOCK_SIZE);
			Line line4 = new Line(x, y + Main.BLOCK_SIZE, x, y);
			this.setOnMouseEntered(event->{
				Main.gameRoot.getChildren().addAll(line1, line2, line3, line4);
			});
			this.setOnMouseExited(event->{
				Main.gameRoot.getChildren().removeAll(line1, line2, line3, line4);
			});
			if (Main.gameRoot.GameMode == "Normal"){
					this.setOnMouseClicked(event->{
						Tower tower = new Tower(x, y, 150);
						Main.gameRoot.Towers.add(tower);
					});
			}
			break;
		case Road:
			block.setImage(img_road);
			break;
		}
		getChildren().add(block);
		Main.gameRoot.getChildren().add(this);
	}
}
