package logica;

import java.awt.image.BufferedImage;
import graficos.Sprite;

public abstract class Obstaculo extends Coordenadas implements Colisionable {
	private static final String PATH = "./imagenes/obstaculos/obstaculos.png";
	protected static final Sprite sprite = new Sprite(PATH, 32, 32);
	protected static BufferedImage fondo;

	static {
		switch (Mapa.getEscenario()) {
			case NORMAL:
				sprite.subSprite(0, 0, 7, 2);
				break;
			case ESPACIO:
				sprite.subSprite(0, 2, 7, 2);
				break;
			case METAL:
				sprite.subSprite(0, 4, 7, 2);
				break;
			case LADRILLOS:
				sprite.subSprite(0, 6, 7, 2);
		}
	}
	
	public Obstaculo(double xPos, double yPos) {
		super(xPos, yPos);
		fondo = sprite.getSprite(1, 0);
	}

	public static BufferedImage getFondo() {
		return fondo;
	}

	public Sprite getSprite() {
		return sprite;
	}

}
