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
 * Класс, описывающий вышку
 * 
 * @author pixxx
 */
public class Tower extends Pane {
  int damage;
  ImageView imageView;

  /** Cooldown на выстрел вышки */
  double shootCooldown;

  /** Сколько времени осталось до выстрела */
  double timeToShoot = 0;

  /** Позиция вышки, радиус атаки, ее размеры и производимый выстрел */
  double posX;
  double posY;
  double attackRange;
  int height;
  int width;
  Shot shots;

  /**
   * Метод, создающий объект вышки с заданными параметрами
   * 
   * @param posX - Местоположение по X
   * @param posY - Местоположение по Y
   * @param attackRange - Радиус атаки
   */
  public Tower(double posX, double posY, double attackRange) {
    width = Main.BLOCK_SIZE;
    height = Main.BLOCK_SIZE;
    shootCooldown = 0.5;
    this.attackRange = attackRange;
    this.posX = posX;
    this.posY = posY;
    Image img = new Image(getClass().getResourceAsStream("Tower.png"), width, height, false, true);
    this.imageView = new ImageView(img);
    this.imageView.setViewport(new Rectangle2D(0, 0, width, height));
    this.setTranslateX(posX);
    this.setTranslateY(posY);
    getChildren().add(imageView);
    Main.gameRoot.getChildren().add(this);
    /** Создание кольца, отображающего радиус атаки */
    Circle attackRangeCircle =
        new Circle(posX + Main.BLOCK_SIZE / 2, posY + Main.BLOCK_SIZE / 2, attackRange);
    attackRangeCircle.setOpacity(0.1);
    Main.gameRoot.getChildren().add(attackRangeCircle);
    attackRangeCircle.setVisible(false);
    /** Отображение кольца радиуса атаки при наведении на вышку */
    this.setOnMouseEntered(event -> {
      attackRangeCircle.setVisible(true);
    });
    this.setOnMouseExited(event -> {
      attackRangeCircle.setMouseTransparent(true);
      attackRangeCircle.setVisible(false);
    });
  }
}
