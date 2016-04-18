package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * ����� ��������� ������� � ��� �������� � �������� �� ��� �����������
 * 
 * @author pixxx
 */
public class Enemy extends Pane {
  ImageView imageView;

  /** ��������� ������� */
  public double posX;
  public double posY;

  /** ���������� ����, �� ������� ��� ������ (���������� ��� ����������� ������������) */
  char PrevBlock = 'N';

  int Health = 100;
  int width = 32;
  int height = 32;
  int offsetX = 0;
  int offsetY = 0;
  SpriteAnimation animation;

  /**
   * ������� ������� � ��������� �����������
   * 
   * @param posX - ��������� ������� �� X
   * @param posY - ��������� ������� �� Y
   * @param count - �������� ��� ���������� ��������
   * @param columns - �������� ��� ���������� ��������
   */
  public Enemy(int posX, int posY, int count, int columns) {
    Image img = new Image(getClass().getResourceAsStream("enemy.png"));
    this.imageView = new ImageView(img);
    this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    animation = new SpriteAnimation(imageView, Duration.millis(200), count, columns, offsetX,
        offsetY, width, height);
    this.posX = posX;
    this.posY = posY;
    this.setTranslateX(posX);
    this.setTranslateY(posY);
    getChildren().add(imageView);
    Main.gameRoot.getChildren().add(this);
  }

  /**
   * �����, ���������� �� ������������ ������� �� X
   * 
   * @param x - �� ������� �������� �������
   */
  public void moveX(double x) {
    boolean right = true;
    if (x < 0)
      right = false;
    for (int i = 0; i < Math.abs(x); i++) {
      if (right) {
        this.setTranslateX(this.getTranslateX() + 1);
        posX += 1;
      } else {
        this.setTranslateX(this.getTranslateX() - 1);
        posX -= 1;
      }
    }
  }

  /**
   * �����, ���������� �� ������������ ������� �� Y
   * 
   * @param y - �� ������� �������� �������
   */
  public void moveY(double y) {
    boolean down = true;
    if (y < 0)
      down = false;
    for (int i = 0; i < Math.abs(y); i++) {
      if (down) {
        this.setTranslateY(this.getTranslateY() + 1);
        posY += 1;
      } else {
        this.setTranslateY(this.getTranslateY() - 1);
        posY -= 1;
      }
    }
  }

  /**
   * �����, ��������� ���� � �����, ���� ��� ����� �� �����
   */
  public void EnemyGoalRiched() {
    Health = 0;
    this.setVisible(false);
    Main.gameRoot.getChildren().remove(this);
    this.animation.stop();
  }

  /**
   * ����� �������� �� ��������� ����� �� �����
   * 
   * @param Damage - ���-�� ����������� �����
   */
  public void GetDamage(int Damage) {
    Health = Health - Damage;
    if (Health <= 0) {
      this.setVisible(false);
      Main.gameRoot.getChildren().remove(this);
      this.animation.stop();
    }
  }
}
