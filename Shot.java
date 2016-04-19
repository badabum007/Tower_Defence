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
 * �����, ����������� ������� �����
 * 
 * @author pixxx
 */
public class Shot extends Circle {
  /** ���� �������� */
  Enemy target;

  /** ��������� ���������� �������� */
  double startX;
  double startY;

  /** ���� �������� � ��� �������� */
  Path shotPath;
  PathTransition animation;

  /**
   * �����, ��������� ������� � ��������� �����������
   * 
   * @param Target - ���� ��������
   * @param startX - ��������� ���������� X
   * @param startY - ��������� ���������� Y
   */
  public Shot(Enemy target, double startX, double startY) {
    this.target = target;
    this.startX = startX;
    this.startY = startY;
    this.setCenterX(startX);
    this.setCenterY(startY);
    this.setRadius(5);
    Main.gameRoot.getChildren().add(this);
    /** ������� ���� �������� - ������ � ����; ������� ��� �������� */
    shotPath = new Path(new MoveTo(startX, startY));
    shotPath.getElements()
        .add(new LineTo(target.posX + target.width / 2, target.posY + target.height / 2));
    animation = new PathTransition(Duration.millis(200), shotPath, this);
    animation.play();
    /** ��� ��������� � ���� */
    animation.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        PathTransition finishedAnimation = (PathTransition) actionEvent.getSource();
        Shot finishedShot = (Shot) finishedAnimation.getNode();
        /** �������� �������� �� Root-� � ��������� ����� ����� */
        finishedShot.setVisible(false);
        target.getDamage(20);
        Main.gameRoot.getChildren().remove(finishedShot);
      }
    });
  }
}
