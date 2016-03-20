package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Класс, описывающий место появления монстров
 * @author pixxx
 */
public class Spawner{
	/** Кол-во выходящих монстров(в сумме) */
	int count;
	
	int startPosX;
	int startPosY;
	
	/** Кол-во уже выпущенных монстров*/
	int iterator;
	
	/** Список всех созданных этим Spawner-ом монстров */
	ArrayList<Enemy> enemies;
	
	/**
	 * Создает Spawner с заданными параметрами
	 * @param count - Кол-во выходящих монстров
	 * @param startPosX - Стартовая точка по X
	 * @param startPosY - Стартовая точка по Y
	 */
	public Spawner(int count, int startPosX, int startPosY){
		this.startPosX = startPosX;
		this.startPosY = startPosY;
		this.count = count;
		iterator = 0;
		enemies = new ArrayList<Enemy>();
	}
	
	/** Создание монстра */
	public void CreateMonster(){
		enemies.add(new Enemy(startPosX, startPosY, 3, 3));
		iterator++;
	}
	
	/** Метод, обновляющий местоположение монстров */
	public void update(){
		int BlockX, BlockY;
		double BlockCenterX, BlockCenterY;
			for (int j=0; j<enemies.size(); j++){
				BlockX = (int)(enemies.get(j).posX / Main.BLOCK_SIZE);
				BlockY = (int)(enemies.get(j).posY / Main.BLOCK_SIZE);
				String line = LevelData.levels[0][BlockY];
				BlockCenterX = enemies.get(j).posX + enemies.get(j).width/2; 
				BlockCenterY = enemies.get(j).posY + enemies.get(j).height/2;
				if ((enemies.get(j).PrevBlock=='U')&&
						(BlockCenterY>BlockY * Main.BLOCK_SIZE + Main.BLOCK_SIZE / 2)){
					enemies.get(j).moveY(-1);
					continue;
				}
				if ((enemies.get(j).PrevBlock=='D')&&
						(BlockCenterY<BlockY * Main.BLOCK_SIZE + Main.BLOCK_SIZE / 2)){
					enemies.get(j).moveY(1);
					continue;
				}
				if ((enemies.get(j).PrevBlock=='R')&&
						(BlockCenterX<BlockX * Main.BLOCK_SIZE + Main.BLOCK_SIZE / 2)){
					enemies.get(j).moveX(1);
					continue;
				}
				if ((enemies.get(j).PrevBlock=='L')&&
						(BlockCenterX>BlockX * Main.BLOCK_SIZE + Main.BLOCK_SIZE / 2)){
					enemies.get(j).moveX(-1);
					continue;
				}
				if (line.charAt(BlockX)=='U'){
					enemies.get(j).animation.play();
					enemies.get(j).animation.setOffsetY(96);
					enemies.get(j).PrevBlock = 'U';
					enemies.get(j).moveY(-1);
				}
				if (line.charAt(BlockX)=='D'){
					enemies.get(j).animation.play();
					enemies.get(j).animation.setOffsetY(0);
					enemies.get(j).PrevBlock = 'D';
					enemies.get(j).moveY(1);
				}	
				if (line.charAt(BlockX)=='R'){
					enemies.get(j).animation.play();
					enemies.get(j).animation.setOffsetY(64);
					enemies.get(j).PrevBlock = 'R';
					enemies.get(j).moveX(1);
				}
				if (line.charAt(BlockX)=='L') {
					enemies.get(j).animation.play();
					enemies.get(j).animation.setOffsetY(32);
					enemies.get(j).PrevBlock = 'L';
					enemies.get(j).moveX(-1);
				}
				
				if (line.charAt(BlockX)=='E'){
					enemies.get(j).EnemyGoalRiched();
				}
			}
		}
}
