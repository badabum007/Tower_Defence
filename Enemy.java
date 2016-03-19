package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Enemy extends Pane{ // Монстр
	ImageView imageView;
	public double posX; // Положение на карте
	public double posY; 
	char PrevBlock = 'N'; // Предыдущий блок, на котором был монстр.
	int Health = 100;
	int width = 32;
	int height = 32;
	int offsetX = 0;
	int offsetY = 0;
	SpriteAnimation animation;
	
	public Enemy(int posX, int posY, int count, int columns){
		Image img = new Image("file:res/enemy.png");
		this.imageView = new ImageView(img);
		this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
		animation = new SpriteAnimation(imageView, Duration.millis(200), count, columns, offsetX, offsetY, width, height);
		this.posX = posX;
		this.posY = posY;
		this.setTranslateX(posX);
		this.setTranslateY(posY);
		getChildren().add(imageView);
		Main.gameRoot.getChildren().add(this);
	}
	
	public void moveX(double x){
		boolean right = true;
		if (x<0) right = false;
		for (int i = 0; i<Math.abs(x); i++){
			if (right){
				this.setTranslateX(this.getTranslateX() + 1);
				posX += 1;
			}
			else {
				this.setTranslateX(this.getTranslateX() - 1);
				posX -= 1;
			}
		}
	}
	
	public void moveY(double y){
		boolean down = true;
		if (y<0) down = false;
		for (int i = 0; i<Math.abs(y); i++){
			if (down){
				this.setTranslateY(this.getTranslateY() + 1);
				posY += 1;
			}
			else{
				this.setTranslateY(this.getTranslateY() - 1);
				posY -= 1;
			}
		}
	}
	
	public void EnemyGoalRiched(){ // Если цель дошла до конца
		Health = 0;
		this.setVisible(false);
		Main.gameRoot.getChildren().remove(this);
		this.animation.stop();
	}
	
	public void GetDamage(int Damage){
		Health = Health - Damage;
		if (Health <= 0){
			this.setVisible(false);
			Main.gameRoot.getChildren().remove(this);
			this.animation.stop();
		}
	}
}
