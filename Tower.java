package application;

import java.util.ArrayList;

import com.sun.prism.paint.Color;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * �����, ����������� �����
 * @author pixxx
 */
public class Tower extends Pane {
	int Damage;
	ImageView imageView;
	
	/** Cooldown �� ������� ����� */
	double ShootCooldown;
	
	/** ������� ������� �������� �� �������� */
	double TimeToShoot = 0;
	
	/** ������� �����, ������ �����, �� ������� � ������������ ������� */
	double posX;
	double posY;
	double attackRange;
	int height;
	int width;
	Shot Shots;
	
	/**
	 * �����, ��������� ������ ����� � ��������� �����������
	 * @param posX - �������������� �� X
	 * @param posY - �������������� �� Y
	 * @param attackRange - ������ �����
	 */
	public Tower(double posX, double posY, double attackRange){
		width = Main.BLOCK_SIZE;
		height = Main.BLOCK_SIZE;
		ShootCooldown = 0.5;
		this.attackRange = attackRange;
		this.posX = posX; this.posY = posY;
		Image img = new Image(getClass().getResourceAsStream("Tower.png"),
				width, height, false, true);
		this.imageView = new ImageView(img);
		this.imageView.setViewport(new Rectangle2D(0, 0, width, height));
		this.setTranslateX(posX);
		this.setTranslateY(posY);
		getChildren().add(imageView);
		Main.gameRoot.getChildren().add(this);
		// �������� ������, ������������� ������ �����
		Circle AttackRangeCircle = new Circle(posX + Main.BLOCK_SIZE/2, 
				posY + Main.BLOCK_SIZE/2, attackRange);
		AttackRangeCircle.setOpacity(0.1);
		Main.gameRoot.getChildren().add(AttackRangeCircle);
		AttackRangeCircle.setVisible(false);
		// ����������� ������ ������� ����� ��� ��������� �� �����
		this.setOnMouseEntered(event->{
			AttackRangeCircle.setVisible(true);
		});
		this.setOnMouseExited(event->{
			AttackRangeCircle.setMouseTransparent(true);
			AttackRangeCircle.setVisible(false);
		});
	}
}
