package application;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * Класс, реализующий magicButton
 * 
 * @author pixxx
 *
 */
public class MagicButton {
  MediaView mediaView;
  Group root;
  static boolean isPlaying = false;

  public MagicButton(String path) {
    root = new Group();
    isPlaying = true;
    Media media = new Media(getClass().getResource(path).toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaView = new MediaView(mediaPlayer);
    root.getChildren().add(mediaView);
    mediaView.setFitWidth(Main.RESOLUTION_X);
    mediaView.setFitHeight(Main.RESOLUTION_Y);
    Main.scene.setOnKeyPressed(event -> {
      if ((event.getCode() == KeyCode.ESCAPE) && (isPlaying)) {
        isPlaying = false;
        mediaPlayer.stop();
        Main.menu.getChildren().remove(root);
        Main.menu.menuBox.changeText("TowerDefence");
        Main.menu.menuSound.mediaPlayer.play();
      }
    });
  }
}
