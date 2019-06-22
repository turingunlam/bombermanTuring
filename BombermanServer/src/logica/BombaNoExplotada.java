package logica;

import javax.swing.ImageIcon;

import graficos.Animacion;
import graficos.Sprite;

public class BombaNoExplotada extends Bomba {
	private static final long serialVersionUID = 1L;
	private static final String PATH = "./imagenes/bomba/bomba_no_explotada.png";
	private boolean explotar;
	private static Sprite sprite;
	private Animacion animacionActual;
	private Animacion quieta;

	public BombaNoExplotada(double xPos, double yPos, int tiempoDetonacion, int rango) {
		super(xPos, yPos, rango, tiempoDetonacion, "BombaNoExplotada");
		
		sprite = new Sprite(PATH, 32, 32);

		ImageIcon[] quietaFrames = { sprite.getSprite(0, 0), sprite.getSprite(1, 0), sprite.getSprite(2, 0), sprite.getSprite(3, 0) };
		quieta = new Animacion(quietaFrames, 32, 32, 10);

		animacionActual = quieta;
		animacionActual.reproducir();
	}

	public void actualizar() {
		animacionActual.actualizar();
	}
	
	public void explotar() {
		explotar = true;
	}

	public boolean isExplotada() {
		return explotar;
	}

	public boolean isEscondido(Bomba bomba, Colisionable obst) {
		if (!(obst instanceof Bomba)) {
			if (((Coordenadas) obst).getPos().compararX(bomba.getPos()) > 0
					&& this.getPos().compararX(((Coordenadas) obst).getPos()) > 0) {
				return true;
			}

			if (((Coordenadas) obst).getPos().compararX(bomba.getPos()) < 0
					&& this.getPos().compararX(((Coordenadas) obst).getPos()) < 0) {
				return true;
			}

			if (((Coordenadas) obst).getPos().compararY(bomba.getPos()) > 0
					&& this.getPos().compararY(((Coordenadas) obst).getPos()) > 0) {
				return true;
			}

			if (((Coordenadas) obst).getPos().compararY(bomba.getPos()) < 0
					&& this.getPos().compararY(((Coordenadas) obst).getPos()) < 0) {
				return true;
			}
		}
		return false;
	}

	public Animacion getAnimacionActual() {
		return animacionActual;
	}
}
