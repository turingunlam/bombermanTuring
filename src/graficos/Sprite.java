package graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	public static final int ANCHO_TILE = 32;
	public static final int ALTO_TILE = 32;
	private int anchoImg;
	private int altoImg;
	private int anchoFrame;
	private int altoFrame;
	private BufferedImage sprite;

	public Sprite(String path, int anchoFrame, int altoFrame) {
		try {
			sprite = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.anchoImg = sprite.getWidth();
		this.altoImg = sprite.getHeight();
		this.anchoFrame = anchoFrame;
		this.altoFrame = altoFrame;
	}

	public BufferedImage getSprite (int x, int y) {
		return sprite.getSubimage(x * anchoFrame, y * altoFrame, anchoFrame , altoFrame );
	}
	
	public void subSprite(int x, int y, int w, int h) {
		sprite = sprite.getSubimage(x * anchoFrame, y * altoFrame, w * anchoFrame, h * altoFrame);
	}
	
	public BufferedImage getImg() {
		return sprite;
	}

	public int getAnchoImg() {
		return anchoImg;
	}

	public int getAltoImg() {
		return altoImg;
	}
}