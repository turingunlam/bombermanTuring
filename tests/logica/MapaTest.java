package logica;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import static graficos.Sprite.*;

public class MapaTest {
	private Mapa m;
	private Bomberman bm;
	private Bomba bomba;
	private ObstaculoIrrompible obstI;
	private ObstaculoRompible obstR;;

	@Before
	public void setUp() {
		m = new Mapa(9, 7, 1, 0, Escenario.ESPACIO);
	}

	@Test
	public void queValideCantJugadores() {
		m = new Mapa(5, 5, 5, 0, Escenario.ESPACIO);
		assertEquals(4, m.getBombermans().size());
	}

	@Test
	public void queValidePorcentajeObstaculosRMayorA1() {
		m = new Mapa(5, 5, 0, 1.1, Escenario.ESPACIO);
		assertEquals(0.6, m.getPorcentajeObstR(), 0);
	}

	@Test
	public void queValidePorcentajeObstaculosRMenorA0() {
		m = new Mapa(5, 5, 0, -1, Escenario.ESPACIO);
		assertEquals(0.6, m.getPorcentajeObstR(), 0);
	}

	@Test
	public void queValideAnchoMapa() {
		m = new Mapa(2, 5, 0, 0, Escenario.ESPACIO);
		assertEquals(7, m.getAncho());
	}

	@Test
	public void queValideAltoMapa() {
		m = new Mapa(5, 2, 0, 0, Escenario.ESPACIO);
		assertEquals(7, m.getAlto());
	}

	@Test
	public void calculoPosIniBms() {
		m = new Mapa(5, 5, 4, 0, Escenario.ESPACIO);
		Coordenadas coordBm1 = new Coordenadas(32, 32);
		Coordenadas coordBm2 = new Coordenadas(160, 32);
		Coordenadas coordBm3 = new Coordenadas(32, 160);
		Coordenadas coordBm4 = new Coordenadas(160, 160);
		assertEquals(coordBm1, m.getBombermans().get(0).getPos());
		assertEquals(coordBm2, m.getBombermans().get(1).getPos());
		assertEquals(coordBm3, m.getBombermans().get(2).getPos());
		assertEquals(coordBm4, m.getBombermans().get(3).getPos());
	}

	@Test
	public void queCreeMapaConObstaculosIrrompibles() {
		m = new Mapa(5, 5, 0, 0, Escenario.ESPACIO);
		ArrayList<Coordenadas> coordObst = new ArrayList<Coordenadas>();
		for (int i = 0; i <= 6; i++) {
			coordObst.add(new Coordenadas(i * ANCHO_TILE, 0));
			coordObst.add(new Coordenadas(i * ANCHO_TILE, 6 * ALTO_TILE));
		}
		for (int i = 1; i <= 5; i++) {
			coordObst.add(new Coordenadas(0, i * ALTO_TILE));
			coordObst.add(new Coordenadas(6 * ANCHO_TILE, i * ALTO_TILE));
		}
		coordObst.add(new Coordenadas(2 * ANCHO_TILE, 2 * ALTO_TILE));
		coordObst.add(new Coordenadas(4 * ANCHO_TILE, 2 * ALTO_TILE));
		coordObst.add(new Coordenadas(2 * ALTO_TILE, 4 * ANCHO_TILE));
		coordObst.add(new Coordenadas(4 * ANCHO_TILE, 4 * ALTO_TILE));
		int i = 0;
		for (Obstaculo obs : m.getObstaculos()) {
			assertEquals(coordObst.get(i), obs.getPos());
			i++;
		}
	}

	@Test
	public void queCreeMapaConObstaculosRompibles() {
		m = new Mapa(8, 8, 0, 1, Escenario.ESPACIO);
		ArrayList<Coordenadas> coordObst = new ArrayList<Coordenadas>();
		for (int i = 3; i < 7; i++) {
			coordObst.add(new Coordenadas(i * 32, 1 * 32));
		}
		coordObst.add(new Coordenadas(3 * ANCHO_TILE, 2 * ALTO_TILE));
		coordObst.add(new Coordenadas(5 * ANCHO_TILE, 2 * ALTO_TILE));
		for (int i = 1; i < 9; i++) {
			coordObst.add(new Coordenadas(i * ANCHO_TILE, 3 * ALTO_TILE));
		}
		coordObst.add(new Coordenadas(1 * ANCHO_TILE, 4 * ALTO_TILE));
		coordObst.add(new Coordenadas(3 * ANCHO_TILE, 4 * ALTO_TILE));
		coordObst.add(new Coordenadas(5 * ANCHO_TILE, 4 * ALTO_TILE));
		coordObst.add(new Coordenadas(7 * ANCHO_TILE, 4 * ALTO_TILE));
		coordObst.add(new Coordenadas(8 * ANCHO_TILE, 4 * ALTO_TILE));
		for (int i = 1; i < 9; i++) {
			coordObst.add(new Coordenadas(i * ANCHO_TILE, 5 * ALTO_TILE));
		}
		coordObst.add(new Coordenadas(1 * ANCHO_TILE, 6 * ALTO_TILE));
		coordObst.add(new Coordenadas(3 * ANCHO_TILE, 6 * ALTO_TILE));
		coordObst.add(new Coordenadas(5 * ANCHO_TILE, 6 * ALTO_TILE));
		coordObst.add(new Coordenadas(7 * ANCHO_TILE, 6 * ALTO_TILE));
		coordObst.add(new Coordenadas(8 * ANCHO_TILE, 6 * ALTO_TILE));
		for (int i = 7; i < 9; i++) {
			for (int j = 3; j < 7; j++) {
				coordObst.add(new Coordenadas(j * ANCHO_TILE, i * 32));
			}
		}
		for (int i = 0; i < 40; i++) {
			assertEquals(coordObst.get(i), m.getObstaculos().get(i + 45).getPos());
		}
	}

	@Test
	public void queQuiteBmMuertos() {
		m = new Mapa(9, 7, 2, 0, Escenario.ESPACIO);
		assertEquals(2, m.getBombermans().size());
		m.getBombermans().get(1).eliminar();
		m.quitarBmMuertos();
		assertEquals(1, m.getBombermans().size());
	}

	@Test
	public void queQuiteObstaculosRotos() {
		m = new Mapa(9, 7, 0, 0, Escenario.ESPACIO);
		obstR = new ObstaculoRompible(5, 2);
		m.setObstaculo(obstR);
		assertEquals(1, m.getCantObstaculosR());
		obstR.eliminar();
		m.quitarObstaculosRotos();
		assertEquals(0, m.getCantObstaculosR());
	} 

	@Test
	public void queQuiteBombasExplotadas() {
		m = new Mapa(9, 7, 0, 0, Escenario.ESPACIO);
		BombaExplotada bomba = new BombaExplotada(5, 2, 500, 3);
		bomba.setTiempoExplosion(0);
		m.setBomba(bomba);
		assertEquals(1, m.getBombas().size());
		m.quitarBombasExplotadas();
		assertEquals(0, m.getBombas().size());
	}
}