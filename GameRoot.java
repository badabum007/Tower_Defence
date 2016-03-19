package application;
//
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameRoot extends Pane {
	MusicContainer GameSound;
	Spawner[] Spawn = new Spawner[2];
	ArrayList<Tower> Towers;
	static String GameMode; // ������ ����: ��������������(Auto) ��� ������(Normal);
	
	public GameRoot(String path){
		Towers = new ArrayList<Tower>();
		this.setVisible(false);
		GameSound = new MusicContainer(path); // �������, ������
		GameSound.mediaPlayer.setVolume(0.4);
		getChildren().add(GameSound.mediaView);
	}
	
	public void StartGame(){
		CreateMap();
		GameSound.mediaPlayer.play();
		Spawn[0] = new Spawner(10, 8*Main.BLOCK_SIZE + 9, 0); // �������� ������ (���-�� ��������� �����, ��������� ����������)
		Spawn[1] = new Spawner(10, 11*Main.BLOCK_SIZE + 9, 0);
		
		/* ----------------- ������ ------------------------ */
		final LongProperty CheckForShootTimer = new SimpleLongProperty();
		final LongProperty FrameTimer = new SimpleLongProperty(0);
		AnimationTimer timer = new AnimationTimer(){
			long EveryTick = 0;
			@Override
			public void handle(long now){
				EveryTick++;
				if (EveryTick>55){ // 55 ����� ~= 1 ������� 
					EveryTick = 0;
					if (Spawn[0].iterator < Spawn[0].count) Spawn[0].CreateMonster();
					if (Spawn[1].iterator < Spawn[1].count) Spawn[1].CreateMonster();
				}
				
				if (now / 100000000 != CheckForShootTimer.get()){ // ������ 0.1� ���������, ��� ����� ����������
					CheckForShooting();
					for (int i = 0; i<Towers.size(); i++){
						Towers.get(i).TimeToShoot-= 0.1;
					}
				}
				if (now / 10000000 != FrameTimer.get()){ // ���������� ��������������
					Spawn[0].update();
					Spawn[1].update();
				}
				FrameTimer.set(now / 10000000);
				CheckForShootTimer.set(now / 100000000);
			}
		};
		timer.start();
		
		FadeTransition FT_Menu = new FadeTransition(Duration.seconds(1), Main.menu); // ������� ��������� � ��������� ����
		FT_Menu.setFromValue(0);
		FT_Menu.setToValue(1);
		FadeTransition FT_FromGame = new FadeTransition(Duration.seconds(0.5), this);
		FT_FromGame.setFromValue(0);
		FT_FromGame.setToValue(1);
		/* ��� ������� �� ESC ��� ����*/
		Main.scene.setOnKeyPressed(event ->{
			if ((event.getCode() == KeyCode.ESCAPE) && (!Main.menu.isVisible())){
				 // ���������
				timer.stop();
				this.GameSound.mediaPlayer.pause();
				Main.menu.MenuSound.mediaPlayer.play();
				FT_Menu.play();
				this.setVisible(false);
				Main.menu.setVisible(true);
			}	/* ��� ������� �� ESC � ���� */
			else if ((event.getCode() == KeyCode.ESCAPE) && (Main.menu.isVisible())){
				 // ���������
				timer.start();
				FT_FromGame.play();
				this.setVisible(true);
				Main.menu.setVisible(false);
				this.GameSound.mediaPlayer.play();
				Main.menu.MenuSound.mediaPlayer.pause();
			}
		});
	}
	
	public void CreateMap(){ // ���������� �����
		for (int i = 0;  i<LevelData.levels[0].length; i++){
			String line = LevelData.levels[0][i];
			for (int j = 0; j<line.length(); j++){
				switch(line.charAt(j)){ 
				case 'T':
					Block tree = new Block(Block.BlockType.Tree, j*Main.BLOCK_SIZE, i*Main.BLOCK_SIZE);
					break;
				case '0':
					Block grass = new Block(Block.BlockType.Grass, j*Main.BLOCK_SIZE, i*Main.BLOCK_SIZE);
					break;
				default:
					Block road = new Block(Block.BlockType.Road, j*Main.BLOCK_SIZE, i*Main.BLOCK_SIZE);
					break;
				}
			}
		}
	}
	
	public void CheckForShooting(){ // ���� ��� ����� �������� � �� ����
		int SpawnersCount = 2;
		for (int i=0; i<SpawnersCount; i++){ // ���� �� �������
			for (int j=0; j<Spawn[i].enemies.size(); j++){ // ���� �� ��������
				if (Spawn[i].enemies.get(j).Health <= 0){
					Spawn[i].enemies.remove(j);
					//Spawn[i].iterator--;
					continue;
				}
				for (int k=0; k<Towers.size(); k++){ // ���� �� ������
					double EnemyPosX = Spawn[i].enemies.get(j).getTranslateX();
					double EnemyPosY = Spawn[i].enemies.get(j).getTranslateY();
					double TowerPosX = Towers.get(k).getTranslateX();
					double TowerPosY = Towers.get(k).getTranslateY();
					if (Towers.get(k).TimeToShoot <= 0){ // Cooldown ������?
						if (Math.pow(Math.pow(EnemyPosX - TowerPosX, 2) + Math.pow(EnemyPosY - TowerPosY, 2),0.5) < Towers.get(k).attackRange){ //������������?
							Towers.get(k).TimeToShoot = Towers.get(k).ShootCooldown; // ������ �� Cooldown
							Towers.get(k).Shots.add(new Shot(Spawn[i].enemies.get(j), Towers.get(k).posX + Main.BLOCK_SIZE/2, Towers.get(k).posY));
						}
					}
				}
			}
		}
	}
}
