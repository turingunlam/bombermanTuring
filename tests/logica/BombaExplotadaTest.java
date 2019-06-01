package logica;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import logica.Bomba;
import logica.BombaExplotada;
import logica.BombaNoExplotada;
import logica.Bomberman;
import logica.Obstaculo;
import logica.ObstaculoRompible;

public class BombaExplotadaTest { 
	private Bomba bExplotada;
	private static Mapa mapa = new Mapa(5, 5, 3, 0.3, Escenario.ESPACIO);

	@Before
	public void setUp() {
		bExplotada = new BombaExplotada(0, 0, 3, 2000);
	}

	@Test
	public void colisioneObstaculoDerecha() {
		Obstaculo obs = new ObstaculoRompible(95, 0);
		assertEquals(true, bExplotada.colisiona(obs));
	}

	@Test
	public void colisioneObstaculoAdelante() {
		Obstaculo obs = new ObstaculoRompible(0, 95);
		assertEquals(true, bExplotada.colisiona(obs));
	}

	@Test
	public void colisioneObstaculoIzq() {
		Obstaculo obs = new ObstaculoRompible(-95, 0);
		assertEquals(true, bExplotada.colisiona(obs));
	}

	@Test
	public void colisioneObstaculoAtras() {
		Obstaculo obs = new ObstaculoRompible(0, -95);
		assertEquals(true, bExplotada.colisiona(obs));
	}

	@Test
	public void noColisioneObstaculoFRDerecha() {
		Obstaculo obs = new ObstaculoRompible(96, 0);
		assertEquals(false, bExplotada.colisiona(obs));
	}

	@Test
	public void noColisioneObstaculoFRAdelante() {
		Obstaculo obs = new ObstaculoRompible(0, 96);
		assertEquals(false, bExplotada.colisiona(obs));
	}

	@Test
	public void noColisioneObstaculoFRIzq() {
		Obstaculo obs = new ObstaculoRompible(-96, 0);
		assertEquals(false, bExplotada.colisiona(obs));
	}

	@Test
	public void noColisioneObstaculoFRAtras() {
		Obstaculo obs = new ObstaculoRompible(0, -96);
		assertEquals(false, bExplotada.colisiona(obs));
	}

}
