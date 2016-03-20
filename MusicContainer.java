package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * �����  ��� �������� � �������� ������ � ������
 * @author pixxx
 */
class MusicContainer{
	MediaView mediaView;
	MediaPlayer mediaPlayer;
	
	/**
	 * �����, ���������������� ������ � ������������
	 * @param path - ���� � ����������
	 */
	public MusicContainer(String path){
		Media media = new Media(getClass().getResource(path).toString());
		this.mediaPlayer = new MediaPlayer(media);
		this.mediaView = new MediaView(mediaPlayer);
		this.mediaPlayer.setAutoPlay(false);
		this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}
}
