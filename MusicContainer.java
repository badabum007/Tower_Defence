package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * Класс  для создания и хранения звуков и музыки
 * @author pixxx
 */
class MusicContainer{
	MediaView mediaView;
	MediaPlayer mediaPlayer;
	
	/**
	 * Метод, подготавливающий музыку к произведению
	 * @param path - Путь к аудиофайлу
	 */
	public MusicContainer(String path){
		Media media = new Media(getClass().getResource(path).toString());
		this.mediaPlayer = new MediaPlayer(media);
		this.mediaView = new MediaView(mediaPlayer);
		this.mediaPlayer.setAutoPlay(false);
		this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}
}
