package graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Sprite implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int ANCHO_TILE = 32;
	public static final int ALTO_TILE = 32;
	private int anchoImg;
	private int altoImg;
	private int anchoFrame;
	private int altoFrame;
	private String path;
	private ImageIcon sprite;

	public Sprite(String path, int anchoFrame, int altoFrame) {
		sprite = new ImageIcon(path);

		this.path = path;
		this.anchoImg = sprite.getIconWidth();
		this.altoImg = sprite.getIconHeight();
		this.anchoFrame = anchoFrame;
		this.altoFrame = altoFrame;
	}

	public ImageIcon getSprite(int x, int y) {
		BufferedImage aux = null;
		try {
			aux = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageIcon res = new ImageIcon(aux.getSubimage(x * anchoFrame, y * altoFrame, anchoFrame, altoFrame));
		
		return res;
	}

	public ImageIcon getSprite(int x, int y, int w, int h) {
		BufferedImage aux = null;
		try {
			aux = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageIcon res = new ImageIcon(aux.getSubimage(x * anchoFrame, y * altoFrame, anchoFrame, altoFrame));
		
		return res;
	}
	
	public void subSprite(int x, int y, int w, int h) {
		BufferedImage aux = null;
		try {
			aux = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sprite = new ImageIcon(aux.getSubimage(x * anchoFrame, y * altoFrame, w * anchoFrame, h * altoFrame));
	}

	public int getAnchoImg() {
		return anchoImg;
	}

	public int getAltoImg() {
		return altoImg;
	}
}