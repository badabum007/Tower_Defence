package application;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Класс, описывающий выстрел вышки
 * 
 * @author pixxx
 */
public class Shot extends Circle {
  /** Цель выстрела */
  Enemy target;

  /** Стартовые координаты выстрела */
  double startX;
  double startY;

  /** Путь выстрела и его анимация */
  Path shotPath;
  PathTransition animation;

  /**
   * Метод, создающий выстрел с заданными параметрами
   * 
   * @param Target - Цель выстрела
   * @param startX - Стартовая координата X
   * @param startY - Стартовая координата Y
   */
  public Shot(Enemy target, double startX, double startY) {
    this.target = target;
    this.startX = startX;
    this.startY = startY;
    this.setCenterX(startX);
    this.setCenterY(startY);
    this.setRadius(5);
    Main.gameRoot.getChildren().add(this);
    /** Задание пути выстрела - откуда и куда; Задание его анимации */
    shotPath = new Path(new MoveTo(startX, startY));
    shotPath.getElements()
        .add(new LineTo(target.posX + target.width / 2, target.posY + target.height / 2));
    animation = new PathTransition(Duration.millis(200), shotPath, this);
    animation.play();
    /** При попадании в цель */
    animation.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        PathTransition finishedAnimation = (PathTransition) actionEvent.getSource();
        Shot finishedShot = (Shot) finishedAnimation.getNode();
        /** Удаление выстрела из Root-а и получение целью урона */
        finishedShot.setVisible(false);
        target.getDamage(20);
        Main.gameRoot.getChildren().remove(finishedShot);
      }
    });
  }
}
