package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

class MusicContainer{ // Саундтрек
	MediaView mediaView;
	MediaPlayer mediaPlayer;
	public MusicContainer(String path){
		Media media = new Media(new File(path).toURI().toString());
		this.mediaPlayer = new MediaPlayer(media);
		this.mediaView = new MediaView(mediaPlayer);
		this.mediaPlayer.setAutoPlay(false);
		this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}
}
