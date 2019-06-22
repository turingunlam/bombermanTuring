package paquetes;

import java.util.ArrayList;
import java.util.HashMap;

import logica.Bomba;
import logica.Bomberman;
import logica.CantObstaculosRompibles;
import logica.Escenario;
import logica.Mapa;
import logica.Obstaculo;
import logica.TamañoMapa;

public class PaqueteInicioPartida extends Paquete {
	private static final long serialVersionUID = 1L;
	private Escenario escenario;
	private CantObstaculosRompibles cantObstaculosR;
	private TamañoMapa tamMapa;
	private HashMap<String, Bomberman> bombermans;
	private ArrayList<Bomba> bombas;
	private ArrayList<Obstaculo> obstaculos;
	private int cantJugadores;
	
	public PaqueteInicioPartida(Mapa mapa) {
		super(PaqueteID.InicioPartida.getId(), "PaqueteInicioPartida");
		this.escenario = mapa.getEscenario();
		this.cantObstaculosR = mapa.getCantObstaculosR();
		this.tamMapa = mapa.getTamMapa();
		this.bombermans = mapa.getBombermans();
		this.bombas = mapa.getBombas();
		this.obstaculos = mapa.getObstaculos();
		this.cantJugadores = mapa.getCantJugadores();
	}

	public int getCantJugadores() {
		return cantJugadores;
	}

	public void setCantJugadores(int cantJugadores) {
		this.cantJugadores = cantJugadores;
	}

	public Escenario getEscenario() {
		return escenario;
	}

	public void setEscenario(Escenario escenario) {
		this.escenario = escenario;
	}

	public CantObstaculosRompibles getCantObstaculosR() {
		return cantObstaculosR;
	}

	public void setCantObstaculosR(CantObstaculosRompibles cantObstaculosR) {
		this.cantObstaculosR = cantObstaculosR;
	}

	public TamañoMapa getTamMapa() {
		return tamMapa;
	}

	public void setTamMapa(TamañoMapa tamMapa) {
		this.tamMapa = tamMapa;
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
