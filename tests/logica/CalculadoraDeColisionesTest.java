package logica;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CalculadoraDeColisionesTest {
	
	private CalculadoraDeColisiones calcu;
	private  Mapa m;
	private Bomberman bm;
	private Bomba bomba;
	private ObstaculoIrrompible obstI;
	private ObstaculoRompible obstR;;
	
	@Before
	public void  setUp() {
		calcu = new CalculadoraDeColisiones();
		m =  new Mapa(9, 7, 0, 0,Escenario.ESPACIO);
	}
/*
	@Test
	public void colisionBombermanObstaculoI() {
		bm = new Bomberman(3.9, 5.4, "Pj",1);
		obstI = new ObstaculoIrrompible(3, 5);
		m.setBomberman(bm);
		m.setObstaculo(obstI);
		calcu.chequearColisiones(m);
		assertEquals(4, bm.getPos().getxPos(), 0);
		assertEquals(5.4, bm.getPos().getyPos(), 0);
	}

	@Test
	public void colisionBombermanObstaculoR() {
		m = new Mapa(9, 7, 0, 0);
		bm = new Bomberman(3.6, 5.1, "");
		obstR = new ObstaculoRompible(3, 5);
		m.setBomberman(bm, 1);
		m.setObstaculo(obstR);
		assertEquals(true, bm.colisiona(obstR));
		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());
		assertEquals(3.6, bm.getPos().getxPos(), 0);
		assertEquals(5, bm.getPos().getyPos(), 0);
	}

	@Test
	public void colisionBombaDetonadaBomberman() {
		bm = new Bomberman(4, 5, "");
		bomba = new BombaExplotada(4, 6, 2000, 3);
		assertEquals(true, bomba.colisiona(bm));
	}

	@Test
	public void colisionBombaNoDetonadaBomberman() {
		bm = new Bomberman(4, 5, "");
		bomba = new BombaNoExplotada(4, 6, 2000, 3);
		assertEquals(false, bomba.colisiona(bm));
	}

	@Test
	public void colisionBombaBombaDetonada() {
		Bomba bomba2 = new BombaExplotada(4, 5, 2000, 4);
		bomba = new BombaNoExplotada(7, 5, 2000, 4);
		assertEquals(true, bomba2.colisiona(bomba));
	}

	@Test
	public void colisionBombaBombaNoDetonada() {
		Bomba bomba2 = new BombaNoExplotada(4, 5, 2000, 4);
		bomba = new BombaNoExplotada(7, 5, 2000, 4);
		assertEquals(false, bomba2.colisiona(bomba));
	}

	@Test
	public void colisionBombaBombaBomba() {
		Bomba bomba2 = new BombaNoExplotada(3, 3, 2000, 3);
		Bomba bomba3 = new BombaNoExplotada(4, 3, 2000, 3);
		bomba = new BombaExplotada(5, 3, 2000, 3);
		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomba(bomba3);

		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());

		for (Bomba bomba : m.getBombas()) {
			assertEquals(true, bomba instanceof BombaExplotada);
		}
	}

	@Test
	public void colisionBombermanEscondido() {
		m = new Mapa(9, 7, 0, 0);
		bm = new Bomberman(7, 4, "");
		Bomberman bm2 = new Bomberman(3, 4, "");
		Bomberman bm3 = new Bomberman(5, 2, "");
		Bomberman bm4 = new Bomberman(5, 5, "");
		bomba = new BombaExplotada(5, 4, 2000, 3);
		obstI = new ObstaculoIrrompible(5, 3);
		// UBICO LOS ELEMENTOS EN EL MAPA
		m.setBomberman(bm, 1);
		m.setBomberman(bm2, 1);
		m.setBomberman(bm3, 1);
		m.setBomberman(bm4, 1);
		m.setObstaculo(obstI);
		m.setBomba(bomba);
		assertEquals(4, m.getBombermans().size());
		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());
		m.quitarBmMuertos();
		assertEquals(3, m.getBombermans().size());
	}

	@Test
	public void colisionBombaBombaBomberman() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new BombaExplotada(5, 3, 2000, 3);
		Bomba bomba2 = new BombaNoExplotada(7, 3, 2000, 3);
		bm = new Bomberman(7, 1, "");

		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomberman(bm, 1);

		assertEquals(1, m.getBombermans().size());

		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());
		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());

		m.quitarBmMuertos();

		assertEquals(0, m.getBombermans().size());

		for (Bomba bomba : m.getBombas()) {
			assertEquals(true, bomba instanceof BombaExplotada);
		}
	}

	@Test
	public void colisionBombaBombaBmEscondido() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new BombaExplotada(5, 3, 2000, 3);
		Bomba bomba2 = new BombaNoExplotada(7, 3, 2000, 3);
		bm = new Bomberman(7, 5, "");
		obstR = new ObstaculoRompible(7, 4);

		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomberman(bm, 1);
		m.setObstaculo(obstR);

		assertEquals(1, m.getBombermans().size());

		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());
		m.quitarBmMuertos();

		assertEquals(1, m.getBombermans().size());
	}

	@Test
	public void queChequeeExplosionesConColisionados() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new BombaExplotada(5, 3, 2000, 3);
		Bomba bomba2 = new BombaExplotada(6, 1, 2000, 3);
		bm = new Bomberman(4, 1, "");
		obstR = new ObstaculoRompible(5, 2);
		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomberman(bm, 1);
		m.setObstaculo(obstR);
		assertEquals(1, m.getBombermans().size());
		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());
		m.quitarBmMuertos();
		assertEquals(0, m.getBombermans().size());
	}

	@Test
	public void colisionBombaBombaEscondida() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new BombaExplotada(5, 4, 2000, 3);
		Bomba bomba2 = new BombaNoExplotada(5, 2, 2000, 3);
		Bomba bomba3 = new BombaNoExplotada(5, 6, 2000, 3);
		Bomba bomba4 = new BombaNoExplotada(3, 4, 2000, 3);
		Bomba bomba5 = new BombaNoExplotada(7, 4, 2000, 3);
		obstR = new ObstaculoRompible(5, 3);
		ObstaculoRompible obstR2 = new ObstaculoRompible(5, 5);

		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomba(bomba3);
		m.setBomba(bomba4);
		m.setBomba(bomba5);
		m.setObstaculo(obstR);
		m.setObstaculo(obstR2);

		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());

		assertEquals(true, m.getBombas().get(0) instanceof BombaExplotada);

		for (int i = 1; i < m.getBombas().size() - 1; i++) {
			assertEquals(true, m.getBombas().get(i) instanceof BombaNoExplotada);
		}
	}

	@Test
	public void colisionBmBmEscondido() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new BombaExplotada(5, 3, 2000, 3);
		bm = new Bomberman(6, 3, "");
		Bomberman bm2 = new Bomberman(7, 3, "");
		m.setBomba(bomba);
		m.setBomberman(bm, 1);
		m.setBomberman(bm2, 1);
		assertEquals(false, bm.isMuerto());
		assertEquals(false, bm2.isMuerto());
		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());
		assertEquals(true, bm.isMuerto());
		assertEquals(false, bm2.isMuerto());
	}

	@Test
	public void colisionBmObstaculoEscondido() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new BombaExplotada(5, 3, 2000, 3);
		bm = new Bomberman(6, 3, "");
		obstR = new ObstaculoRompible(7, 3);
		m.setBomba(bomba);
		m.setBomberman(bm, 1);
		m.setObstaculo(obstR);
		assertEquals(false, bm.isMuerto());
		assertEquals(false, obstR.isRoto());
		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());
		assertEquals(true, bm.isMuerto());
		assertEquals(false, obstR.isRoto());
	}

	@Test
	public void colisionBmBombaEscondida() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new BombaExplotada(5, 3, 2000, 3);
		bm = new Bomberman(6, 3, "");
		Bomba bomba2 = new BombaNoExplotada(7, 3, 2000, 3);
		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomberman(bm, 1);
		assertEquals(false, bm.isMuerto());
		assertNotNull(bomba2);
		m.getCalcColisiones().chequearColisiones(m.getBombermans(), m.getBombas(), m.getObstaculos());
		assertEquals(true, bm.isMuerto());
		assertNotNull(bomba2);
	}
*/
}
