package logica;

import java.awt.image.BufferedImage;

import graficos.Animacion;

public class ObstaculoIrrompible extends Obstaculo {
	private Animacion animacionActual;
	private Animacion quieto;

	public ObstaculoIrrompible(double xPos, double yPos) {
		super(xPos, yPos);

		BufferedImage[] quietoFrames = { sprite.getSprite(0, 0) };
		quieto = new Animacion(quietoFrames, 32, 32, 10);
		
		animacionActual = quieto;
	}

	@Override
	public boolean colisiona(Colisionable c) {
		return false;
	}
	
	public Animacion getAnimacionActual() {
		return animacionActual;
	}
}
