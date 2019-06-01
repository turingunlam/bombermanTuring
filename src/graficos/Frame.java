package graficos;

import java.awt.image.BufferedImage;

public class Frame {
	private BufferedImage img;
	
	public Frame(BufferedImage img) {
		this.img = img;
	}
	
	public BufferedImage getFrame() {
		return img;
	}
}
