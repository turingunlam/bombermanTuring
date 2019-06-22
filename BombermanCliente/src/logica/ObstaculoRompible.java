package logica;

import javax.swing.ImageIcon;

import graficos.Animacion;
import graficos.Sprite;

public class ObstaculoRompible extends Obstaculo {
	private static final long serialVersionUID = 1L;
	private boolean roto;
	private boolean eliminar;
	private Animacion animacionActual;
	private Animacion quieto;
	private Animacion romper;

	public ObstaculoRompible(double xPos, double yPos, Escenario escenario) {
		super(xPos, yPos, escenario);
		this.roto = false;
		this.eliminar = false;

		ImageIcon[] quietoFrames = { sprite.getSprite(0, 1) };
		quieto = new Animacion(quietoFrames, 32, 32, 10);

		ImageIcon[] romperFrames = { sprite.getSprite(1, 1), sprite.getSprite(2, 1), sprite.getSprite(3, 1),
				sprite.getSprite(4, 1), sprite.getSprite(5, 1), sprite.getSprite(6, 1) };
		romper = new Animacion(romperFrames, Sprite.ANCHO_TILE, Sprite.ALTO_TILE, 5);
		
		animacionActual = quieto;
	}

	public void actualizar() {
		if (isRoto()) {
			animacionActual = romper;
			if (animacionActual.termino()) {
				eliminar = true;
				//return;
			}
		}

		animacionActual.reproducir();
		animacionActual.actualizar();
	}

	@Override
	public boolean colisiona(Colisionable c) {
		return false;
	}

	public final boolean isEscondido(Bomba bomba, Colisionable obst) {
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

	public void romper() {
		this.roto = true;
	}

	public boolean isRoto() {
		return this.roto;
	}
	
	public void eliminar() {
		this.eliminar= true;
	}
	
	public boolean isEliminado() {
		return this.eliminar;
	}

	public Animacion getAnimacionActual() {
		return animacionActual;
	}
}