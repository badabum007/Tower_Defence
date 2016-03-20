package application;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * �����, ����������� ���������� ��������
 * @author pixxx
 */
public class SpriteAnimation extends Transition{
	private ImageView imageView;
	private int count;
	private int columns;
	private int offsetX;
	private int offsetY;
	private int width;
	private int height;
	
	/**
	 * ������� �������� � �����������
	 * @param imageView - �����������
	 * @param duration - �����������������
	 * @param count
	 * @param columns
	 * @param offsetX
	 * @param offsetY
	 * @param width - ������ �������
	 * @param height - ������ �������
	 */
	public SpriteAnimation(
			ImageView imageView,
			Duration duration,
			int count, int columns,
			int offsetX, int offsetY,
			int width, int height
		){
		this.imageView = imageView;
		this.count = count;
		this.columns = columns;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.width = width;
		this.height = height;
		setCycleDuration(duration);
		setCycleCount(Animation.INDEFINITE);
		setInterpolator(Interpolator.LINEAR);
		this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
	}
	
	public void setOffsetX(int x){
		this.offsetX = x;
	}
	
	public void setOffsetY(int y){
		this.offsetY = y;
	}
	
	protected void interpolate(double f){
		int index = Math.min((int)Math.floor(count*f), count - 1);
		int x = (index%columns)*width + offsetX;
		int y = (index/columns)*height + offsetY;
		imageView.setViewport(new Rectangle2D(x, y, width, height));
	}
}
