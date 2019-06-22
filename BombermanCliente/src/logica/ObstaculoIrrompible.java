package logica;

import javax.swing.ImageIcon;

import graficos.Animacion;

public class ObstaculoIrrompible extends Obstaculo {
	private static final long serialVersionUID = 1L;
	private static Animacion animacionActual;
	private static Animacion quieto;

	public ObstaculoIrrompible(double xPos, double yPos, Escenario escenario) {
		super(xPos, yPos, escenario);

		ImageIcon[] quietoFrames = { sprite.getSprite(0, 0) };
		quieto = new Animacion(quietoFrames, 32, 32, 10);

		animacionActual = quieto;
	}

	@Override
	public boolean colisiona(Colisionable c) {
		return false;
	}

	public static Animacion getAnimacionActual() {
		return animacionActual;
	}
}
