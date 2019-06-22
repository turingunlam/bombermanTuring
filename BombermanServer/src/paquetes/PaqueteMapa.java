package paquetes;

import java.util.ArrayList;
import java.util.HashMap;

import logica.Bomba;
import logica.Bomberman;
import logica.Mapa;
import logica.Obstaculo;

public class PaqueteMapa extends Paquete {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Bomberman> bombermans;
	private ArrayList<Bomba> bombas;
	private ArrayList<Obstaculo> obstaculos;

	public PaqueteMapa(Mapa mapa) {
		super(PaqueteID.Mapa.getId(), "PaqueteMapa");
		bombermans = mapa.getBombermans();
		bombas = mapa.getBombas();
		obstaculos = mapa.getObstaculos();
	}

	public HashMap<String, Bomberman> getBombermans() {
		return bombermans;
	}

	public void setBombermans(HashMap<String, Bomberman> bombermans) {
		this.bombermans = bombermans;
	}

	public ArrayList<Bomba> getBombas() {
		return bombas;
	}

	public void setBombas(ArrayList<Bomba> bombas) {
		this.bombas = bombas;
	}

	public ArrayList<Obstaculo> getObstaculos() {
		return obstaculos;
	}

	public void setObstaculos(ArrayList<Obstaculo> obstaculos) {
		this.obstaculos = obstaculos;
	}
	
}
