package logica;

import javax.swing.ImageIcon;

import graficos.Sprite;

public abstract class Obstaculo extends Coordenadas implements Colisionable {
	private static final long serialVersionUID = 1L;
	private static final String PATH = "./imagenes/obstaculos/obstaculos.png";
	protected static Sprite sprite = new Sprite(PATH, 32, 32);
	protected static ImageIcon fondo = sprite.getSprite(1, 0);

//	static {
//		switch (Mapa.getEscenario()) {
//		case NORMAL:
//			sprite.subSprite(0, 0, 7, 2);
//			break;
//		case ESPACIO:
//			sprite.subSprite(0, 2, 7, 2);
//			break;
//		case METAL:
//			sprite.subSprite(0, 4, 7, 2);
//			break;
//		case LADRILLOS:
//			sprite.subSprite(0, 6, 7, 2);
//		}
//		
//		fondo = sprite.getSprite(1, 0);
//	}

	public Obstaculo(double xPos, double yPos, Escenario escenario) {
		super(xPos, yPos);
//		fondo = sprite.getSprite(1, 0);
	}

	public static ImageIcon getFondo() {
		return fondo;
	}

	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

}
