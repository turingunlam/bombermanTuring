package logica;

import static graficos.Sprite.ALTO_TILE;
import static graficos.Sprite.ANCHO_TILE;
import static java.lang.Math.abs;

import javax.swing.ImageIcon;

import graficos.Animacion;
import graficos.Sprite;

public class BombaExplotada extends Bomba {
	private static final long serialVersionUID = 1L;
	private static final String PATH = "./imagenes/bomba/bomba_explotada.png";
	private int tiempoExplosion;
	private int rangoIzq;
	private int rangoDer;
	private int rangoArriba;
	private int rangoAbajo;
	private static final int delayAnimacion = 4;
	private static Sprite sprite;
	private Animacion centro;
	private Animacion izqMedio;
	private Animacion izqPunta;
	private Animacion derMedio;
	private Animacion derPunta;
	private Animacion medio;
	private Animacion arribaPunta;
	private Animacion abajoPunta;

	public BombaExplotada(double xPos, double yPos, int rango, int tiempoDetonacion) {
		super(xPos, yPos, rango, tiempoDetonacion, "BombaExplotada");
		rangoIzq = rangoDer = rangoArriba = rangoAbajo = rango;
		tiempoExplosion = 300;

		sprite = new Sprite(PATH, 32, 32);

		ImageIcon[] centroFrames = { sprite.getSprite(0, 1), sprite.getSprite(1, 1), sprite.getSprite(2, 1),
				sprite.getSprite(3, 1), sprite.getSprite(4, 1), sprite.getSprite(5, 1) };
		centro = new Animacion(centroFrames, Sprite.ANCHO_TILE, Sprite.ALTO_TILE, delayAnimacion);

		ImageIcon[] izqMedioFrames = { sprite.getSprite(0, 5), sprite.getSprite(1, 5), sprite.getSprite(2, 5),
				sprite.getSprite(3, 5), sprite.getSprite(4, 5), sprite.getSprite(5, 5) };
		izqMedio = new Animacion(izqMedioFrames, Sprite.ANCHO_TILE, Sprite.ALTO_TILE, delayAnimacion);

		ImageIcon[] izqPuntaFrames = { sprite.getSprite(6, 5), sprite.getSprite(7, 5), sprite.getSprite(8, 5),
				sprite.getSprite(9, 5), sprite.getSprite(10, 5), sprite.getSprite(11, 5) };
		izqPunta = new Animacion(izqPuntaFrames, Sprite.ANCHO_TILE, Sprite.ALTO_TILE, delayAnimacion);

		ImageIcon[] derMedioFrames = { sprite.getSprite(0, 4), sprite.getSprite(1, 4), sprite.getSprite(2, 4),
				sprite.getSprite(3, 4), sprite.getSprite(4, 4), sprite.getSprite(5, 4) };
		derMedio = new Animacion(derMedioFrames, Sprite.ANCHO_TILE, Sprite.ALTO_TILE, delayAnimacion);

		ImageIcon[] derPuntaFrames = { sprite.getSprite(6, 4), sprite.getSprite(7, 4), sprite.getSprite(8, 4),
				sprite.getSprite(9, 4), sprite.getSprite(10, 4), sprite.getSprite(11, 4) };
		derPunta = new Animacion(derPuntaFrames, Sprite.ANCHO_TILE, Sprite.ALTO_TILE, delayAnimacion);

		ImageIcon[] medioFrames = { sprite.getSprite(0, 2), sprite.getSprite(1, 2), sprite.getSprite(2, 2),
				sprite.getSprite(3, 2), sprite.getSprite(4, 2), sprite.getSprite(5, 2) };
		medio = new Animacion(medioFrames, Sprite.ANCHO_TILE, Sprite.ALTO_TILE, delayAnimacion);

		ImageIcon[] arribaPuntaFrames = { sprite.getSprite(0, 0), sprite.getSprite(1, 0), sprite.getSprite(2, 0),
				sprite.getSprite(3, 0), sprite.getSprite(4, 0), sprite.getSprite(5, 0) };
		arribaPunta = new Animacion(arribaPuntaFrames, Sprite.ANCHO_TILE, Sprite.ALTO_TILE, delayAnimacion);

		ImageIcon[] abajoPuntaFrames = { sprite.getSprite(0, 3), sprite.getSprite(1, 3), sprite.getSprite(2, 3),
				sprite.getSprite(3, 3), sprite.getSprite(4, 3), sprite.getSprite(5, 3) };
		abajoPunta = new Animacion(abajoPuntaFrames, Sprite.ANCHO_TILE, Sprite.ALTO_TILE, delayAnimacion);

		centro.reproducir();
		izqMedio.reproducir();
		izqPunta.reproducir();
		derMedio.reproducir();
		derPunta.reproducir();
		medio.reproducir();
		arribaPunta.reproducir();
		abajoPunta.reproducir();
	}

	public void actualizar() {
		centro.actualizar();
		izqMedio.actualizar();
		izqPunta.actualizar();
		derMedio.actualizar();
		derPunta.actualizar();
		medio.actualizar();
		arribaPunta.actualizar();
		abajoPunta.actualizar();
	}

	@Override
	public boolean colisiona(Colisionable c) {
		if (c != this) {
			if (intersecciona(c)) {
				return true;
			}

			return false;
		}

		return false;
	}

	public boolean intersecciona(Colisionable c) {
		if (this.equals(c)) {
			return false;
		}

		if (getxPos() - ANCHO_TILE * (rangoIzq - 1) < ((Coordenadas) c).getxPos() + ANCHO_TILE) {
			if (getxPos() + ANCHO_TILE * rangoDer > ((Coordenadas) c).getxPos()) {
				if (getyPos() < ((Coordenadas) c).getyPos() + ANCHO_TILE) {
					if (getyPos() + ALTO_TILE > ((Coordenadas) c).getyPos()) {
						if (!(c instanceof Bomberman)) {
							int resta = (int) (getxPos() - ((Coordenadas) c).getxPos());

							if (resta == 0) {
								rangoIzq = rangoDer = rangoArriba = rangoAbajo = 1;
							}
							if (resta > 0) {
								int nuevoRango = abs(Math.round(resta / ALTO_TILE)) + 1;

								if (nuevoRango < rangoIzq) {
									rangoIzq = nuevoRango;
								}
							} else {
								int nuevoRango = abs(Math.round(resta / ALTO_TILE)) + 1;

								if (nuevoRango < rangoDer) {
									rangoDer = nuevoRango;
								}
							}
						}
						return true;
					}
				}
			}
		}

		if (getyPos() - ALTO_TILE * (rangoArriba - 1) < ((Coordenadas) c).getyPos() + ALTO_TILE) {
			if (getyPos() + ALTO_TILE * rangoAbajo > ((Coordenadas) c).getyPos()) {
				if (getxPos() < ((Coordenadas) c).getxPos() + ANCHO_TILE) {
					if (getxPos() + ALTO_TILE > ((Coordenadas) c).getxPos()) {
						if (!(c instanceof Bomberman)) {
							int resta = (int) (getyPos() - ((Coordenadas) c).getyPos());

							if (resta == 0) {
								rangoIzq = rangoDer = rangoArriba = rangoAbajo = 1;
							} else if (resta > 0) {
								int nuevoRango = abs(Math.round(resta / ALTO_TILE)) + 1;

								if (nuevoRango < rangoArriba) {
									rangoArriba = nuevoRango;
								}
							} else {
								int nuevoRango = abs(Math.round(resta / ALTO_TILE)) + 1;

								if (nuevoRango < rangoAbajo) {
									rangoAbajo = nuevoRango;
								}
							}
						}
						return true;
					}
				}
			}
		}

		return false;
	}

	public int getTiempoExplosion() {
		return tiempoExplosion;
	}

	public void setTiempoExplosion(int tiempoExplosion) {
		this.tiempoExplosion = tiempoExplosion;
	}

	public int getRangoIzq() {
		return rangoIzq;
	}

	public int getRangoDer() {
		return rangoDer;
	}

	public int getRangoArriba() {
		return rangoArriba;
	}

	public int getRangoAbajo() {
		return rangoAbajo;
	}

	public Animacion getCentro() {
		return centro;
	}

	public Animacion getIzqMedio() {
		return izqMedio;
	}

	public Animacion getIzqPunta() {
		return izqPunta;
	}

	public Animacion getDerMedio() {
		return derMedio;
	}

	public Animacion getDerPunta() {
		return derPunta;
	}

	public Animacion getMedio() {
		return medio;
	}

	public Animacion getArribaPunta() {
		return arribaPunta;
	}

	public Animacion getAbajoPunta() {
		return abajoPunta;
	}

}
