package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Класс описывает блок размером Main.BLOCK_SIZE
 * и отвечает за его отображение
 * @author pixxx
 */
public class Block extends Pane{
	ImageView block;
	
	/** Текстура травы */
	Image img_grass = new Image(getClass().getResourceAsStream("grass.jpg"));
	
	/** Текстура дерева */
	Image img_tree = new Image(getClass().getResourceAsStream("tree.png")); 
	
	/** Текстура дороги */
	Image img_road = new Image(getClass().getResourceAsStream("road.jpg"));
	
	public enum BlockType{
		Grass, Tree, Road;
	}
	
	/** Создает блок с заданными параметрами
	 *  @param type - Тип блока(Трава, дерево, дорога)
	 *  @param x - Координата X
	 *  @param y - Координата Y 
	 */
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
			Line line3 = new Line(x + Main.BLOCK_SIZE, y + Main.BLOCK_SIZE, 
								  x, y + Main.BLOCK_SIZE);
			Line line4 = new Line(x, y + Main.BLOCK_SIZE, x, y);
			/** Выделение блока при наведении */
			this.setOnMouseEntered(event->{
				Main.gameRoot.getChildren().addAll(line1, line2, line3, line4);
			});
			/** Снятие выделения с блока */
			this.setOnMouseExited(event->{
				Main.gameRoot.getChildren().removeAll(line1, line2, line3, line4);
			});
			/** Ставить Tower по клику на блок */
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
		/** Добавление блока на Root */
		getChildren().add(block);
		Main.gameRoot.getChildren().add(this);
	}
}
