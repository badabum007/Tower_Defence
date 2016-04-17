package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Класс описывает монстра и его движение и отвечает за его отображение
 * 
 * @author pixxx
 */
public class Enemy extends Pane {
  ImageView imageView;

  /** Положение монстра */
  public double posX;
  public double posY;

  /** Предыдущий блок, на котором был монстр (необходимо для правильного передвижения) */
  char PrevBlock = 'N';

  int Health = 100;
  int width = 32;
  int height = 32;
  int offsetX = 0;
  int offsetY = 0;
  SpriteAnimation animation;

  /**
   * Создает монстра с заданными параметрами
   * 
   * @param posX - Начальная позиция по X
   * @param posY - Начальная позиция по Y
   * @param count - Параметр для спрайтовой анимации
   * @param columns - Параметр для спрайтовой анимации
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
   * Метод, отвечающий за передвижение монстра по X
   * 
   * @param x - На сколько сдвигать монстра
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
   * Метод, отвечающий за передвижение монстра по Y
   * 
   * @param y - На сколько сдвигать монстра
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
   * Метод, убирающий цель с карты, если она дошла до конца
   */
  public void EnemyGoalRiched() {
    Health = 0;
    this.setVisible(false);
    Main.gameRoot.getChildren().remove(this);
    this.animation.stop();
  }

  /**
   * Метод отвечает за получение урона от вышек
   * 
   * @param Damage - Кол-во полученного урона
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
