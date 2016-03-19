package application;
//
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Shot extends Circle {
	Enemy Target;
	double startX;
	double startY;
	Path ShotPath;
	PathTransition animation;
	
	public Shot(Enemy Target, double startX, double startY){
		this.Target = Target;
		this.startX = startX;
		this.startY = startY;
		this.setCenterX(startX);
		this.setCenterY(startY);
		this.setRadius(5);
		Main.gameRoot.getChildren().add(this);
		
		ShotPath = new Path(new MoveTo(startX, startY));
		ShotPath.getElements().add(new LineTo(Target.posX + Target.width/2, Target.posY + Target.height/2));
		animation = new PathTransition(Duration.millis(200), ShotPath, this);
		animation.play();
		
		animation.setOnFinished(new EventHandler<ActionEvent>(){
			 @Override
             public void handle(ActionEvent actionEvent) {
				 PathTransition finishedAnimation = (PathTransition) actionEvent.getSource();
				 Shot finishedShot = (Shot) finishedAnimation.getNode();
				 
				 finishedShot.setVisible(false);
				 Target.GetDamage(20);
				 Main.gameRoot.getChildren().remove(finishedShot);
			 }
		});
	}
}
