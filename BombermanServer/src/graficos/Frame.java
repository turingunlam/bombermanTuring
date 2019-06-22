package graficos;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Frame implements Serializable {
	private static final long serialVersionUID = 1L;
	private ImageIcon img;
	
	public Frame(ImageIcon img) {
		this.img = img;
	}
	
	public ImageIcon getFrame() {
		return img;
	}
}
