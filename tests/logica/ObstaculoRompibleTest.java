package logica;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ObstaculoRompibleTest {
	private ObstaculoRompible obstaculoR;
	private static Mapa m = new Mapa(7, 7, 3, 0.4, Escenario.ESPACIO);

	@Before
	public void setUp() throws Exception {
		obstaculoR = new ObstaculoRompible(0, 0);
	}

	@Test
	public void isEscondidoDerecha() { 
		ObstaculoRompible obst = new ObstaculoRompible(-1, 0);
		BombaNoExplotada bomba = new BombaNoExplotada(-2, 0, 500, 2);
		assertEquals(true, obstaculoR.isEscondido(bomba, obst));
	}

	@Test
	public void isEscondidoIzquierda() {
		ObstaculoRompible obst = new ObstaculoRompible(1, 0);
		BombaNoExplotada bomba = new BombaNoExplotada(2, 0, 500, 2);
		assertEquals(true, obstaculoR.isEscondido(bomba, obst));
	}

	@Test
	public void isEscondidoArriba() {
		ObstaculoRompible obst = new ObstaculoRompible(0, 1);
		BombaNoExplotada bomba = new BombaNoExplotada(0, 2, 500, 2);
		assertEquals(true, obstaculoR.isEscondido(bomba, obst));
	}

	@Test
	public void isEscondidoAbajo() {
		ObstaculoRompible obst = new ObstaculoRompible(0, -1);
		BombaNoExplotada bomba = new BombaNoExplotada(0, -2, 500, 2);
		assertEquals(true, obstaculoR.isEscondido(bomba, obst));
	}

	@Test
	public void isNoEscondidoAtrasBomba() {
		BombaNoExplotada obst = new BombaNoExplotada(0, -1, 500, 2);
		BombaNoExplotada bomba = new BombaNoExplotada(0, -2, 500, 2);
		assertEquals(false, obstaculoR.isEscondido(bomba, obst));
	}

}
