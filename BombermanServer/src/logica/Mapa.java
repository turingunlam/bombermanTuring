package logica;

import static graficos.Sprite.ALTO_TILE;
import static graficos.Sprite.ANCHO_TILE;
import static java.lang.Math.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Mapa {
	private int ancho;
	private int alto;
	private CantObstaculosRompibles cantObstaculosR;
	private double porcentajeObstR;
	private boolean[][] mapa;
	private Escenario escenario;
	private TamañoMapa tamMapa;
	private static HashMap<String, Bomberman> bombermans;
	private static ArrayList<Bomba> bombas;
	private static ArrayList<Obstaculo> obstaculos;
	private final static int CANT_MAX_BMS = 4;
	private CalculadoraDeColisiones calcColisiones;
	private int cantJugadores;

	public Mapa(TamañoMapa tamMapa, int cantJugadores, CantObstaculosRompibles porcentajeObstR, Escenario escenario) {
		if (cantJugadores > CANT_MAX_BMS) {
			cantJugadores = 4;
		}
		if (porcentajeObstR.getCantidad() > 1 || porcentajeObstR.getCantidad() < 0) {
			porcentajeObstR.setCantidad(0.6);
		}

		this.cantJugadores = cantJugadores;
		this.tamMapa = tamMapa;
		this.cantObstaculosR = porcentajeObstR;
		this.ancho = tamMapa.getTamaño() + 2;
		this.alto = tamMapa.getTamaño() + 2;
		this.porcentajeObstR = porcentajeObstR.getCantidad();
		Mapa.bombas = new ArrayList<Bomba>();
		Mapa.bombermans = new HashMap<String, Bomberman>();
		Mapa.obstaculos = new ArrayList<Obstaculo>();
		this.mapa = new boolean[this.alto][this.ancho];
		this.escenario = escenario;
		calcColisiones = new CalculadoraDeColisiones();

		calcularObstaculosIrrompibles();
		calcularObstaculosRompibles();
	}

	public Mapa(TamañoMapa tam, Escenario esc, int cantJugadores, HashMap<String, Bomberman> bombermans,
			ArrayList<Obstaculo> obstaculos, ArrayList<Bomba> bombas) {
		this.cantJugadores = cantJugadores;
		Mapa.bombermans = new HashMap<String, Bomberman>();

		iniciarBombermans(bombermans);

		this.tamMapa = tam;
		this.ancho = tam.getTamaño() + 2;
		this.alto = tam.getTamaño() + 2;
		Mapa.bombas = bombas;

		Mapa.obstaculos = new ArrayList<Obstaculo>();
		iniciarObstaculos(esc, obstaculos);
		
		this.mapa = new boolean[this.alto][this.ancho];
		this.escenario = esc;

		calcularObstaculosIrrompibles();
	}

	public static void iniciarObstaculos(Escenario esc, ArrayList<Obstaculo> obstaculos) {
		for(Obstaculo obstaculo : obstaculos) {
			if(obstaculo instanceof ObstaculoRompible) {
				Mapa.obstaculos.add(new ObstaculoRompible(obstaculo.getxPos(), obstaculo.getyPos(), esc));
			}
		}
	}

	private static void iniciarBombermans(HashMap<String, Bomberman> bombermans) {
		Iterator<Entry<String, Bomberman>> it = bombermans.entrySet().iterator();

		while (it.hasNext()) {
			Entry<String, Bomberman> actual = it.next();
			Bomberman aux = new Bomberman(actual.getValue().getxPos(), actual.getValue().getyPos(),
					actual.getValue().getNombre());
			Mapa.bombermans.put(actual.getValue().getNombre(), aux);
		}
	}

	private void calcularObstaculosIrrompibles() {
		// AGREGO LAS PAREDES EXTERIORES EN EL ANCHO
		for (int i = 0; i < this.ancho; i++) {
			Mapa.obstaculos.add(new ObstaculoIrrompible(i * ANCHO_TILE, 0, escenario));
			Mapa.obstaculos.add(new ObstaculoIrrompible(i * ANCHO_TILE, (this.alto - 1) * ALTO_TILE, escenario));
			this.mapa[0][i] = true;
			this.mapa[this.alto - 1][i] = true;
		}

		// AGREGO LAS PAREDES EXTERIORES EN EL ALTO
		for (int j = 1; j < this.alto - 1; j++) {
			Mapa.obstaculos.add(new ObstaculoIrrompible(0, j * ALTO_TILE, escenario));
			Mapa.obstaculos.add(new ObstaculoIrrompible((this.ancho - 1) * ANCHO_TILE, j * ALTO_TILE, escenario));
			this.mapa[j][0] = true;
			this.mapa[j][this.ancho - 1] = true;
		}

		// AGREGO LAS PAREDES INTERIORES
		for (int i = 2; i < this.alto - 2; i += 2) {
			for (int j = 2; j < this.ancho - 2; j += 2) {
				Mapa.obstaculos.add(new ObstaculoIrrompible(j * ANCHO_TILE, i * ALTO_TILE, escenario));
				this.mapa[i][j] = true;
			}
		}
	}

	public void calcularPosIniBombermans() {
		ArrayList<Bomberman> bms = new ArrayList<Bomberman>(bombermans.values());

		for (int i = 0; i < bms.size(); i++) {
			switch (i) {
			case 0:
				bms.get(0).setPos(new Coordenadas(1 * ANCHO_TILE, 1 * ALTO_TILE));
				this.mapa[1][1] = true;
				break;
			case 1:
				bms.get(1).setPos(new Coordenadas((this.ancho - 2) * ANCHO_TILE, 1 * ALTO_TILE));
				this.mapa[1][this.ancho - 2] = true;
				break;
			case 2:
				bms.get(2).setPos(new Coordenadas(1 * ANCHO_TILE, (this.alto - 2) * ALTO_TILE));
				this.mapa[this.alto - 2][1] = true;
				break;
			case 3:
				bms.get(3).setPos(new Coordenadas((this.ancho - 2) * ANCHO_TILE, (this.alto - 2) * ALTO_TILE));
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
					Mapa.obstaculos.add(new ObstaculoRompible(j * ANCHO_TILE, i * ALTO_TILE, escenario));
					this.mapa[i][j] = true;
				}
			}
		}
		for (int i = 3; i < this.alto - 3; i++) {
			for (int j = 1; j < this.ancho - 1; j++) {
				random = random();

				if (random <= this.porcentajeObstR && !mapa[i][j]) {
					Mapa.obstaculos.add(new ObstaculoRompible(j * ANCHO_TILE, i * ALTO_TILE, escenario));
					this.mapa[i][j] = true;
				}
			}
		}

		for (int i = this.alto - 3; i <= this.alto - 2; i++) {
			for (int j = 3; j < this.ancho - 3; j++) {
				random = random();

				if (random <= this.porcentajeObstR && !mapa[i][j]) {
					Mapa.obstaculos.add(new ObstaculoRompible(j * ANCHO_TILE, i * ALTO_TILE, escenario));
					this.mapa[i][j] = true;
				}
			}
		}
	}

	public void quitarBmMuertos() {
		Iterator<Entry<String, Bomberman>> it = bombermans.entrySet().iterator();

		while (it.hasNext()) {
			Bomberman bm = it.next().getValue();

			if (bm.isEliminado()) {
				it.remove();
			}
		}
	}

	public void quitarObstaculosRotos() {
		ArrayList<Obstaculo> aBorrar = new ArrayList<Obstaculo>();
		ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>(Mapa.obstaculos);

		for (Obstaculo o : obstaculos) {
			if (o instanceof ObstaculoRompible) {
				if (((ObstaculoRompible) o).isEliminado()) {
					aBorrar.add(o);
				}
			}
		}

		Mapa.obstaculos.removeAll(aBorrar);
	}

	public void quitarBombasExplotadas() {
		ArrayList<Bomba> aBorrar = new ArrayList<Bomba>();
		ArrayList<Bomba> bombas = new ArrayList<Bomba>(Mapa.bombas);

		for (Bomba bomba : bombas) {
			if (bomba instanceof BombaExplotada && ((BombaExplotada) bomba).getTiempoExplosion() <= 0) {
				aBorrar.add(bomba);
			}
		}

		Mapa.bombas.removeAll(aBorrar);
	}

	public void explotarBombas() {
		ArrayList<Bomba> aBorrar = new ArrayList<Bomba>();
		ArrayList<Bomba> aAgregar = new ArrayList<Bomba>();

		Iterator<Entry<String, Bomberman>> it = bombermans.entrySet().iterator();

		while (it.hasNext()) {
			Bomberman bm = it.next().getValue();
			ArrayList<Bomba> bombasAux = new ArrayList<Bomba>(bm.getBombas());
			aAgregar.clear();
			aBorrar.clear();

			for (Bomba bomba : bombasAux) {
				if ((bomba instanceof BombaNoExplotada
						&& (((BombaNoExplotada) bomba).isExplotada() || bomba.getTiempoDetonacion() <= 0))
						|| bm.isMuerto()) {
					BombaExplotada nuevaBomba = new BombaExplotada(bomba.getxPos(), bomba.getyPos(),
							bomba.getRangoMax(), bomba.getTiempoDetonacion());

					if (!Mapa.bombas.contains(nuevaBomba)) {
						aAgregar.add(nuevaBomba);
						aBorrar.add(bomba);
					}
				}
			}
			bm.getBombas().removeAll(aBorrar);
			Mapa.bombas.removeAll(aBorrar);
			Mapa.bombas.addAll(aAgregar);
		}
	}

	public void addBombas() {
		ArrayList<Bomba> aBorrar = new ArrayList<Bomba>();

		Iterator<Entry<String, Bomberman>> it = bombermans.entrySet().iterator();

		while (it.hasNext()) {
			Bomberman bm = it.next().getValue();
			aBorrar.clear();

			for (Bomba bomba : bm.getBombas()) {
				boolean colisiona = false;

				Iterator<Entry<String, Bomberman>> it2 = bombermans.entrySet().iterator();
				while (it.hasNext()) {
					Bomberman bm2 = it2.next().getValue();
					if (!bm.equals(bm2) && bm2.intersecciona(bomba)) {
						colisiona = true;
					}
				}

				if (colisiona) {
					aBorrar.add(bomba);
				} else if (!Mapa.bombas.contains(bomba)) {
					Mapa.bombas.add(bomba);
				}
			}

			bm.getBombas().removeAll(aBorrar);
		}

	}

	public void actualizar() {
		Iterator<Entry<String, Bomberman>> it = bombermans.entrySet().iterator();

		while (it.hasNext()) {
			Bomberman bm = it.next().getValue();
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

		CalculadoraDeColisiones.chequearColisiones(this);
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

	public CantObstaculosRompibles getCantObstaculosR() {
		return cantObstaculosR;
	}

	public HashMap<String, Bomberman> getBombermans() {
		return bombermans;
	}

	public ArrayList<Bomba> getBombas() {
		return bombas;
	}

	public void setBomba(Bomba bomba) {
		Mapa.bombas.add(bomba);
	}

	public Escenario getEscenario() {
		return escenario;
	}

	public ArrayList<Obstaculo> getObstaculos() {
		return obstaculos;
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

	public void addBomberman(String nombre) {
		Mapa.bombermans.put(nombre, new Bomberman(0, 0, nombre));
	}

	public void setBombas(ArrayList<Bomba> bombas) {
		Mapa.bombas = bombas;
	}

	public void setBombermans(HashMap<String, Bomberman> bombermans) {
		Mapa.bombermans = bombermans;
	}

	public void setObstaculos(ArrayList<Obstaculo> obstaculos) {
		Mapa.obstaculos = obstaculos;
	}

	public TamañoMapa getTamMapa() {
		return tamMapa;
	}

	public void setTamMapa(TamañoMapa tamMapa) {
		this.tamMapa = tamMapa;
	}

	public int getCantJugadores() {
		return cantJugadores;
	}

	public void setCantJugadores(int cantJugadores) {
		this.cantJugadores = cantJugadores;
	}
}
