package logica;

import static java.lang.Math.*;
import java.util.ArrayList;

import logica.Bomba;
import logica.BombaExplotada;
import logica.BombaNoExplotada;
import logica.Bomberman;
import static graficos.Sprite.*;

public class Mapa {
	private int ancho;
	private int alto;
	private int cantObstaculosR;
	private double porcentajeObstR;
	private boolean[][] mapa;
	private static Escenario escenario;
	private ArrayList<Bomberman> bombermans;
	private ArrayList<Bomba> bombas;
	private ArrayList<Obstaculo> obstaculos;
	private final static int CANT_MAX_BMS = 4;
	private CalculadoraDeColisiones calcColisiones;

	public Mapa(int ancho, int alto, int cantJugadores, double porcentajeObstR, Escenario escenario) {
		if (cantJugadores > CANT_MAX_BMS) {
			cantJugadores = 4;
		}
		if (porcentajeObstR > 1 || porcentajeObstR < 0) {
			porcentajeObstR = 0.6;
		}
		if (ancho < 5 || alto < 5) {
			ancho = 5;
			alto = 5;
		}
		this.ancho = ancho + 2;
		this.alto = alto + 2;
		this.cantObstaculosR = 0;
		this.porcentajeObstR = porcentajeObstR;
		this.bombas = new ArrayList<Bomba>();
		this.bombermans = new ArrayList<Bomberman>();
		this.obstaculos = new ArrayList<Obstaculo>();
		this.mapa = new boolean[this.alto][this.ancho];
		Mapa.escenario = escenario;
		calcColisiones = new CalculadoraDeColisiones();

		for (int i = 0; i < cantJugadores; i++) {
			this.bombermans.add(new Bomberman(0, 0, "", i));
		}

		calcularObstaculosIrrompibles();
		calcularPosIniBombermans();
		calcularObstaculosRompibles();
	}

	private void calcularObstaculosIrrompibles() {
		// AGREGO LAS PAREDES EXTERIORES EN EL ANCHO
		for (int i = 0; i < this.ancho; i++) {
			this.obstaculos.add(new ObstaculoIrrompible(i * ANCHO_TILE, 0));
			this.obstaculos.add(new ObstaculoIrrompible(i * ANCHO_TILE, (this.alto - 1) * ALTO_TILE));
			this.mapa[0][i] = true;
			this.mapa[this.alto - 1][i] = true;
		}

		// AGREGO LAS PAREDES EXTERIORES EN EL ALTO
		for (int j = 1; j < this.alto - 1; j++) {
			this.obstaculos.add(new ObstaculoIrrompible(0, j * ALTO_TILE));
			this.obstaculos.add(new ObstaculoIrrompible((this.ancho - 1) * ANCHO_TILE, j * ALTO_TILE));
			this.mapa[j][0] = true;
			this.mapa[j][this.ancho - 1] = true;
		}

		// AGREGO LAS PAREDES INTERIORES
		for (int i = 2; i < this.alto - 2; i += 2) {
			for (int j = 2; j < this.ancho - 2; j += 2) {
				this.obstaculos.add(new ObstaculoIrrompible(j * ANCHO_TILE, i * ALTO_TILE));
				this.mapa[i][j] = true;
			}
		}
	}

	private void calcularPosIniBombermans() {
		for (int i = 0; i < this.bombermans.size(); i++) {
			switch (i) {
			case 0:
				this.bombermans.get(0).setPos(new Coordenadas(1 * ANCHO_TILE, 1 * ALTO_TILE));
				this.mapa[1][1] = true;
				break;
			case 1:
				this.bombermans.get(1).setPos(new Coordenadas((this.ancho - 2) * ANCHO_TILE, 1 * ALTO_TILE));
				this.mapa[1][this.ancho - 2] = true;
				break;
			case 2:
				this.bombermans.get(2).setPos(new Coordenadas(1 * ANCHO_TILE, (this.alto - 2) * ALTO_TILE));
				this.mapa[this.alto - 2][1] = true;
				break;
			case 3:
				this.bombermans.get(3)
						.setPos(new Coordenadas((this.ancho - 2) * ANCHO_TILE, (this.alto - 2) * ALTO_TILE));
				this.mapa[this.alto - 2][this.ancho - 2] = true;
				break;
			default:
				break;
			}
		}

	}

	private void calcularObstaculosRompibles() {
		double random;
		for (int i = 1; i <= 2; i++) {
			for (int j = 3; j < this.ancho - 3; j++) {
				random = random();

				if (random <= this.porcentajeObstR && !mapa[i][j]) {
					this.obstaculos.add(new ObstaculoRompible(j * ANCHO_TILE, i * ALTO_TILE));
					this.mapa[i][j] = true;
					this.cantObstaculosR++;
				}
			}
		}
		for (int i = 3; i < this.alto - 3; i++) {
			for (int j = 1; j < this.ancho - 1; j++) {
				random = random();

				if (random <= this.porcentajeObstR && !mapa[i][j]) {
					this.obstaculos.add(new ObstaculoRompible(j * ANCHO_TILE, i * ALTO_TILE));
					this.mapa[i][j] = true;
					this.cantObstaculosR++;
				}
			}
		}

		for (int i = this.alto - 3; i <= this.alto - 2; i++) {
			for (int j = 3; j < this.ancho - 3; j++) {
				random = random();

				if (random <= this.porcentajeObstR && !mapa[i][j]) {
					this.obstaculos.add(new ObstaculoRompible(j * ANCHO_TILE, i * ALTO_TILE));
					this.mapa[i][j] = true;
					this.cantObstaculosR++;
				}
			}
		}
	}

	public void quitarBmMuertos() {
		ArrayList<Bomberman> aBorrar = new ArrayList<Bomberman>();

		for (Bomberman b : bombermans) {
			if (b.isEliminado()) {
				aBorrar.add(b);
			}
		}

		bombermans.removeAll(aBorrar);
	}

	public void quitarObstaculosRotos() {
		ArrayList<Obstaculo> aBorrar = new ArrayList<Obstaculo>();
		ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>(this.obstaculos);

		for (Obstaculo o : obstaculos) {
			if (o instanceof ObstaculoRompible) {
				if (((ObstaculoRompible) o).isEliminado()) {
					aBorrar.add(o);
					cantObstaculosR--;
				}
			}
		}

		this.obstaculos.removeAll(aBorrar);
	}

	public void quitarBombasExplotadas() {
		ArrayList<Bomba> aBorrar = new ArrayList<Bomba>();
		ArrayList<Bomba> bombas = new ArrayList<Bomba>(this.bombas);

		for (Bomba bomba : bombas) {
			if (bomba instanceof BombaExplotada && ((BombaExplotada) bomba).getTiempoExplosion() <= 0) {
				aBorrar.add(bomba);
			}
		}

		this.bombas.removeAll(aBorrar);
	}

	public void explotarBombas() {
		ArrayList<Bomba> aBorrar = new ArrayList<Bomba>();
		ArrayList<Bomba> aAgregar = new ArrayList<Bomba>();

		for (Bomberman bm : bombermans) {
			aAgregar.clear();
			aBorrar.clear();
			
			for (Bomba bomba : bm.getBombas()) {
				if (bomba instanceof BombaNoExplotada
						&& (((BombaNoExplotada) bomba).isExplotada() || bomba.getTiempoDetonacion() <= 0)) {
					BombaExplotada nuevaBomba = new BombaExplotada(bomba.getxPos(), bomba.getyPos(),
							bomba.getRangoMax(), bomba.getTiempoDetonacion());

					if (!this.bombas.contains(nuevaBomba)) {
						aAgregar.add(nuevaBomba);
						aBorrar.add(bomba);
					}
				}
			}

			bm.getBombas().removeAll(aBorrar);
			this.bombas.removeAll(aBorrar);
			this.bombas.addAll(aAgregar);
		}
	}

	public void addBombas() {
		ArrayList<Bomba> aBorrar = new ArrayList<Bomba>();
		
		for (Bomberman bm : bombermans) {
			aBorrar.clear();
			
			for (Bomba bomba : bm.getBombas()) {
				boolean colisiona = false;

				for (Bomberman bm2 : bombermans) {
					if (!bm.equals(bm2) && bm2.intersecciona(bomba)) {
						colisiona = true;
					}
				}
				
				if(colisiona) {
					aBorrar.add(bomba);
				} else if (!this.bombas.contains(bomba)) {
					this.bombas.add(bomba);
				}
			}
			
			bm.getBombas().removeAll(aBorrar);
		}
		
	}

	public void actualizar() {
		for (Bomberman bm : bombermans) {
			bm.actualizar();
		}

		for (Bomba bomba : bombas) {
			if (bomba instanceof BombaExplotada) {
				((BombaExplotada) bomba).actualizar();
			} else {
				((BombaNoExplotada) bomba).actualizar();
			}
		}

		for (Obstaculo obst : obstaculos) {
			if (obst instanceof ObstaculoRompible && ((ObstaculoRompible) obst).isRoto()) {
				((ObstaculoRompible) obst).actualizar();
			}
		}

		calcColisiones.chequearColisiones(this);
		addBombas();
		explotarBombas();
		quitarBmMuertos();
		quitarObstaculosRotos();
		quitarBombasExplotadas();
	}

	public void mostrarMapa() {
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
				if (!mapa[i][j]) {
					System.out.print("  ");
				} else {
					System.out.print(mapa[i][j] + " ");
				}
			}

			System.out.println();
		}
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public boolean[][] getMapa() {
		return this.mapa;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getCantObstaculosR() {
		return cantObstaculosR;
	}

	public void setCantObstaculosR(int cantObstaculosR) {
		this.cantObstaculosR = cantObstaculosR;
	}

	public ArrayList<Bomberman> getBombermans() {
		return bombermans;
	}

	public ArrayList<Bomba> getBombas() {
		return bombas;
	}

	public void setBomba(Bomba bomba) {
		this.bombas.add(bomba);
	}

	public static Escenario getEscenario() {
		return escenario;
	}

	public ArrayList<Obstaculo> getObstaculos() {
		return obstaculos;
	}

	public void setObstaculo(Obstaculo obstaculo) {
		if (obstaculo instanceof ObstaculoRompible) {
			cantObstaculosR++;
		}

		this.obstaculos.add(obstaculo);
	}

	public double getPorcentajeObstR() {
		return porcentajeObstR;
	}

	public void setPorcentajeObstR(double porcentajeObstR) {
		this.porcentajeObstR = porcentajeObstR;
	}

	public CalculadoraDeColisiones getCalcColisiones() {
		return calcColisiones;
	}

	public void setCalcColisiones(CalculadoraDeColisiones calcColisiones) {
		this.calcColisiones = calcColisiones;
	}
}
