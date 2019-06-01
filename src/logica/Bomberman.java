package logica;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import graficos.Animacion;
import graficos.Sprite;

import static graficos.Sprite.*;

public class Bomberman extends Coordenadas implements Colisionable {
	private static final String PATH = "./imagenes/bomberman/bomberman.png";
	private Animacion quieto;
	private Animacion caminarAbajo;
	private Animacion caminarArriba;
	private Animacion caminarDerecha;
	private Animacion caminarIzquierda;
	private Animacion animacionActual;
	private Animacion morir;
	private BufferedImage icono;
	private int contadorAnimacionMorir;
	private Movimiento movimiento;
	private double velocidad;
	private double puntaje;
	private static int nro = 1;
	private int nroBm;
	private int tiempoBomba;
	private int rangoBomba;
	private int maxBombas;
	private boolean muerto;
	private boolean eliminar;
	private boolean pisandoBomba;
	private String nombre;
	private ArrayList<Bomba> bombas;
	private Sprite sprite;

	public Bomberman(double xPos, double yPos, String nombre, int nroJugador) {
		super(xPos, yPos);
		this.nroBm = nro++;
		this.velocidad = 1;
		this.tiempoBomba = 3000; // 2 seg
		this.puntaje = 0;
		this.muerto = false;
		this.eliminar = false;
		this.pisandoBomba = false;
		this.movimiento = Movimiento.QUIETO;
		this.contadorAnimacionMorir = 12;
		
		if (nombre.isEmpty() || nombre.trim().length() == 0) {
			this.nombre = "Bomberman";
		} else {
			this.nombre = nombre;
		}

		this.bombas = new ArrayList<Bomba>();
		this.maxBombas = 3;
		this.rangoBomba = 3;

		switch (nroJugador) {
		case 0:
			this.sprite = new Sprite(PATH, 32, 49);
			sprite.subSprite(0, 0, 10, 2);
			break;
		case 1:
			this.sprite = new Sprite(PATH, 32, 49);
			sprite.subSprite(0, 2, 10, 2);
			break;
		case 2:
			this.sprite = new Sprite(PATH, 32, 49);
			sprite.subSprite(0, 4, 10, 2);
			break;
		case 3:
			this.sprite = new Sprite(PATH, 32, 49);
			sprite.subSprite(0, 6, 10, 2);
			break;
		default:
			this.sprite = new Sprite(PATH, 32, 49);
			break;
		}

		icono = sprite.getImg().getSubimage(128, 49, 32, 32);
		
		BufferedImage[] quietoFrames = { sprite.getSprite(4, 1) };
		quieto = new Animacion(quietoFrames, 32, 49, 10);

		BufferedImage[] caminarAbajoFrames = { sprite.getSprite(3, 1), sprite.getSprite(4, 1), sprite.getSprite(5, 1),
				sprite.getSprite(6, 1) };
		caminarAbajo = new Animacion(caminarAbajoFrames, 32, 49, 5);

		BufferedImage[] caminarArribaFrames = { sprite.getSprite(0, 0), sprite.getSprite(1, 0), sprite.getSprite(2, 0),
				sprite.getSprite(3, 0) };
		caminarArriba = new Animacion(caminarArribaFrames, 32, 49, 5);

		BufferedImage[] caminarDerechaFrames = { sprite.getSprite(7, 1), sprite.getSprite(8, 1),
				sprite.getSprite(9, 1), };
		caminarDerecha = new Animacion(caminarDerechaFrames, 32, 49, 5);

		BufferedImage[] caminarIzquierdaFrames = { sprite.getSprite(0, 1), sprite.getSprite(1, 1),
				sprite.getSprite(2, 1) };
		caminarIzquierda = new Animacion(caminarIzquierdaFrames, 32, 49, 5);

		BufferedImage[] morirFrames = { sprite.getSprite(4, 0), sprite.getSprite(4, 0), sprite.getSprite(5, 0),
				sprite.getSprite(7, 0), sprite.getSprite(8, 0), sprite.getSprite(9, 0) };
		morir = new Animacion(morirFrames, 32, 49, 4);
		
		animacionActual = quieto;
	}

	public void mover() {
		this.setPos(desplazar(movimiento.x() * this.velocidad, movimiento.y() * this.velocidad));
	}

	public void actualizar() {
		if(!isMuerto()) {
			mover();
		}

		if(isMuerto()) {
			animacionActual = morir;
			
			velocidad = 2.1;
			
			if(contadorAnimacionMorir > 0) {
				movimiento = Movimiento.ARRIBA;
				contadorAnimacionMorir--;
			} else {
				movimiento = Movimiento.ABAJO;
			}
			
			mover();
			
			
			if(animacionActual.termino()) {
				eliminar = true;
			}
		} else if (movimiento.equals(Movimiento.ARRIBA)) {
			animacionActual = caminarArriba;
		} else if (movimiento.equals(Movimiento.ABAJO)) {
			animacionActual = caminarAbajo;
		} else if (movimiento.equals(Movimiento.DERECHA)) {
			animacionActual = caminarDerecha;
		} else if (movimiento.equals(Movimiento.IZQUIERDA)) {
			animacionActual = caminarIzquierda;
		} else if (movimiento.equals(Movimiento.QUIETO)) {
			animacionActual = quieto;
			caminarArriba.reset();
			caminarAbajo.reset();
			caminarDerecha.reset();
			caminarIzquierda.reset();
		}

		animacionActual.reproducir();
		animacionActual.actualizar();
	}

	public boolean ponerBomba() {
		for (Bomba bomba : bombas) {
			if (this.colisiona(bomba)) {
				return false;
			}
		}

		if (bombas.size() < maxBombas && !pisandoBomba) {
			int xBomba = (int) getxPos();
			int yBomba = (int) getyPos();

			if ((getxPos() % ANCHO_TILE) >= (ANCHO_TILE) / 2) {
				xBomba = (int) getxPos() + ANCHO_TILE;
			}

			if ((getyPos() % ALTO_TILE) >= (ALTO_TILE) / 2) {
				yBomba = (int) getyPos() + ALTO_TILE;
			}

			bombas.add(new BombaNoExplotada((xBomba / ANCHO_TILE) * ANCHO_TILE, (yBomba / ALTO_TILE) * ALTO_TILE,
					tiempoBomba, rangoBomba));

			pisandoBomba = true;

			return true;
		}

		return false;
	}

	@Override
	public boolean colisiona(Colisionable c) {
		if(bombas.size() < 1) {
			pisandoBomba = false;
		}
		
		if (pisandoBomba && c.equals(bombas.get(bombas.size() - 1))) {
			if (!this.intersecciona(c)) {
				pisandoBomba = false;
			}

			return false;
		}

		if (this.intersecciona(c)) {
			return true;
		}

		return false;
	}

	public boolean intersecciona(Colisionable c) {
		if (getxPos() < ((Coordenadas) c).getxPos() + ANCHO_TILE) {
			if (getxPos() + ANCHO_TILE > ((Coordenadas) c).getxPos()) {
				if (getyPos() < ((Coordenadas) c).getyPos() + ANCHO_TILE) {
					if (getyPos() + ALTO_TILE > ((Coordenadas) c).getyPos()) {
						return true;
					}
				}
			}
		}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + nroBm;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bomberman other = (Bomberman) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nroBm != other.nroBm)
			return false;
		return true;
	}

	public void morir() {
		this.muerto = true;
	}

	public boolean isMuerto() {
		return muerto;
	}

	public double getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(double puntaje) {
		this.puntaje = puntaje;
	}

	public int getTiempoBomba() {
		return tiempoBomba;
	}

	public void setTiempoBomba(int tiempoBomba) {
		this.tiempoBomba = tiempoBomba;
	}

	public int getRangoBomba() {
		return rangoBomba;
	}

	public void setRangoBomba(int rangoBomba) {
		if (rangoBomba >= 0) {
			this.rangoBomba = rangoBomba;
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (!nombre.isEmpty() && nombre.trim().length() != 0) {
			this.nombre = nombre;
		}
	}

	public ArrayList<Bomba> getBombas() {
		return bombas;
	}

	public void setBombas(ArrayList<Bomba> bombas) {
		if (bombas.size() <= this.maxBombas) {
			this.bombas = bombas;
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Animacion getAnimacionActual() {
		return animacionActual;
	}

	public void setAnimacionActual(Animacion anim) {
		animacionActual = anim;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}
	
	public void eliminar() {
		this.eliminar= true;
	}
	
	public boolean isEliminado() {
		return eliminar;
	}
	
	public BufferedImage getIcono() {
		return icono;
	}
}