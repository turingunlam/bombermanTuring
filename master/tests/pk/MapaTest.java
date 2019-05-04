package pk;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MapaTest {
	private Mapa m;
	private Bomberman bm;
	private Bomba bomba;
	private ObstaculoIrrompible obstI;
	private ObstaculoRompible obstR;;

	@Before
	public void setUp() {
		m = new Mapa(9, 7, 1, 0.6);
	}

	@Test
	public void queValideCantJugadores() {
		m = new Mapa(5, 5, 5, 0);
		assertEquals(4, m.getBombermans().size());
	}

	@Test
	public void queNoAgreugueMasBms() {
		m = new Mapa(5, 5, 4, 0);
		bm = new Bomberman(5, 5, "");
		m.setBomberman(bm);
		assertEquals(4, m.getBombermans().size());
	}

	@Test
	public void queValidePorcentajeObstaculosRMayorA1() {
		m = new Mapa(5, 5, 0, 1.1);
		assertEquals(0.6, m.getPorcentajeObstR(), 0);
	}

	@Test
	public void queValidePorcentajeObstaculosRMenorA0() {
		m = new Mapa(5, 5, 0, -1);
		assertEquals(0.6, m.getPorcentajeObstR(), 0);
	}

	@Test
	public void queValideAnchoMapa() {
		m = new Mapa(2, 5, 0, 0);
		assertEquals(7, m.getAncho());
	}
	
	@Test
	public void queValideAltoMapa() {
		m = new Mapa(5, 2, 0, 0);
		assertEquals(7, m.getAlto());
	}

	@Test
	public void calculoPosIniBms() {
		m = new Mapa(5, 5, 4, 0);
		Coordenadas coordBm1 = new Coordenadas(1.5, 1.5);
		Coordenadas coordBm2 = new Coordenadas(1.5, 5.5);
		Coordenadas coordBm3 = new Coordenadas(5.5, 1.5);
		Coordenadas coordBm4 = new Coordenadas(5.5, 5.5);
		assertEquals(coordBm1, m.getBombermans().get(0).getPos());
		assertEquals(coordBm2, m.getBombermans().get(1).getPos());
		assertEquals(coordBm3, m.getBombermans().get(2).getPos());
		assertEquals(coordBm4, m.getBombermans().get(3).getPos());
	}

	@Test
	public void queCreeMapaConObstaculosIrrompibles() {
		m = new Mapa(5, 5, 0, 0);
		ArrayList<Coordenadas> coordObst = new ArrayList<Coordenadas>();
		for (int i = 0; i <= 6; i++) {
			coordObst.add(new Coordenadas(i, 0));
			coordObst.add(new Coordenadas(i, 6));
		}
		for (int i = 1; i <= 5; i++) {
			coordObst.add(new Coordenadas(0, i));
			coordObst.add(new Coordenadas(6, i));
		}
		coordObst.add(new Coordenadas(2, 2));
		coordObst.add(new Coordenadas(2, 4));
		coordObst.add(new Coordenadas(4, 2));
		coordObst.add(new Coordenadas(4, 4));
		int i = 0;
		for (Obstaculo obs : m.getObstaculos()) {
			assertEquals(coordObst.get(i), obs.getPos());
			i++;
		}
	}

	@Test
	public void queCreeMapaConObstaculosRompibles() {
		m = new Mapa(8, 8, 0, 1);
		ArrayList<Coordenadas> coordObst = new ArrayList<Coordenadas>();
		for (int i = 3; i < 7; i++) {
			coordObst.add(new Coordenadas(i, 1));
		}
		coordObst.add(new Coordenadas(3, 2));
		coordObst.add(new Coordenadas(5, 2));
		for (int i = 1; i < 9; i++) {
			coordObst.add(new Coordenadas(i, 3));
		}
		coordObst.add(new Coordenadas(1, 4));
		coordObst.add(new Coordenadas(3, 4));
		coordObst.add(new Coordenadas(5, 4));
		coordObst.add(new Coordenadas(7, 4));
		coordObst.add(new Coordenadas(8, 4));
		for (int i = 1; i < 9; i++) {
			coordObst.add(new Coordenadas(i, 5));
		}
		coordObst.add(new Coordenadas(1, 6));
		coordObst.add(new Coordenadas(3, 6));
		coordObst.add(new Coordenadas(5, 6));
		coordObst.add(new Coordenadas(7, 6));
		coordObst.add(new Coordenadas(8, 6));
		for (int i = 7; i < 9; i++) {
			for (int j = 3; j < 7; j++) {
				coordObst.add(new Coordenadas(j, i));
			}
		}
		for (int i = 0; i < 40; i++) {
			assertEquals(coordObst.get(i), m.getObstaculos().get(i + 45).getPos());
		}
	}

	@Test
	public void queQuiteBmMuertos() {
		m = new Mapa(9, 7, 0, 0);
		bm = new Bomberman(0, 0, "");
		Bomberman bm2 = new Bomberman(0, 0, "");
		m.setBomberman(bm);
		m.setBomberman(bm2);
		assertEquals(2, m.getBombermans().size());
		bm2.morir();
		m.quitarBmMuertos();
		assertEquals(1, m.getBombermans().size());
	}

	@Test
	public void queQuiteObstaculosRotos() {
		m = new Mapa(9, 7, 1, 0);
		bomba = new Bomba(5, 3, 2000, 3);
		obstI = new ObstaculoIrrompible(5, 4);
		obstR = new ObstaculoRompible(5, 2);
		ObstaculoRompible obstR2 = new ObstaculoRompible(7, 3);
		ObstaculoRompible obstR3 = new ObstaculoRompible(6, 3);
		ObstaculoRompible obstR4 = new ObstaculoRompible(4, 3);
		m.setBomba(bomba);
		m.setObstaculo(obstI);
		m.setObstaculo(obstR);
		m.setObstaculo(obstR2);
		m.setObstaculo(obstR3);
		m.setObstaculo(obstR4);
		assertEquals(4, m.getCantObstaculosR());
		bomba.romper();
		m.chequearColisiones();
		m.quitarObstaculosRotos();
		assertEquals(1, m.getCantObstaculosR());
	}

	@Test
	public void colisionBombermanObstaculoI() {
		m = new Mapa(9, 7, 0, 0);
		bm = new Bomberman(3.9, 5.4, "");
		obstI = new ObstaculoIrrompible(3, 5);
		m.setBomberman(bm);
		m.setObstaculo(obstI);
		assertEquals(true, bm.colisiona(obstI));
		m.chequearColisiones();
		assertEquals(4, bm.getPos().getxPos(), 0);
		assertEquals(5.4, bm.getPos().getyPos(), 0);
	}

	@Test
	public void colisionBombermanObstaculoR() {
		m = new Mapa(9, 7, 0, 0);
		bm = new Bomberman(3.6, 5.1, "");
		obstR = new ObstaculoRompible(3, 5);
		m.setBomberman(bm);
		m.setObstaculo(obstR);
		assertEquals(true, bm.colisiona(obstR));
		m.chequearColisiones();
		assertEquals(3.6, bm.getPos().getxPos(), 0);
		assertEquals(5, bm.getPos().getyPos(), 0);
	}

	@Test
	public void colisionBombaDetonadaBomberman() {
		bm = new Bomberman(4, 5, "");
		bomba = new Bomba(4, 6, 2000, 3);
		bomba.romper();
		assertEquals(true, bomba.colisiona(bm));
	}

	@Test
	public void colisionBombaNoDetonadaBomberman() {
		bm = new Bomberman(4, 5, "");
		bomba = new Bomba(4, 6, 2000, 3);
		assertEquals(false, bomba.colisiona(bm));
	}

	@Test
	public void colisionBombaBombaDetonada() {
		Bomba bomba2 = new Bomba(4, 5, 2000, 4);
		bomba = new Bomba(7, 5, 2000, 4);
		bomba2.romper();
		assertEquals(true, bomba2.colisiona(bomba));
	}

	@Test
	public void colisionBombaBombaNoDetonada() {
		Bomba bomba2 = new Bomba(4, 5, 2000, 4);
		bomba = new Bomba(7, 5, 2000, 4);
		assertEquals(false, bomba2.colisiona(bomba));
	}

	@Test
	public void colisionBombaBombaBomba() {
		Bomba bomba2 = new Bomba(3, 3, 2000, 3);
		Bomba bomba3 = new Bomba(4, 3, 2000, 3);
		bomba = new Bomba(5, 3, 2000, 3);
		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomba(bomba3);
		assertEquals(false, bomba2.isRoto());
		assertEquals(false, bomba3.isRoto());
		bomba.romper();
		m.chequearColisiones();
		assertEquals(true, bomba2.isRoto());
		assertEquals(true, bomba3.isRoto());
	}

	@Test
	public void colisionBombermanEscondido() {
		m = new Mapa(9, 7, 0, 0);
		bm = new Bomberman(7, 4, "");
		Bomberman bm2 = new Bomberman(3, 4, "");
		Bomberman bm3 = new Bomberman(5, 2, "");
		Bomberman bm4 = new Bomberman(5, 5, "");
		bomba = new Bomba(5, 4, 2000, 3);
		obstI = new ObstaculoIrrompible(5, 3);
		// UBICO LOS ELEMENTOS EN EL MAPA
		m.setBomberman(bm);
		m.setBomberman(bm2);
		m.setBomberman(bm3);
		m.setBomberman(bm4);
		m.setObstaculo(obstI);
		m.setBomba(bomba);
		assertEquals(4, m.getBombermans().size());
		bomba.romper();
		m.chequearColisiones();
		m.quitarBmMuertos();
		assertEquals(3, m.getBombermans().size());
	}

	@Test
	public void colisionBombaBombaBomberman() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new Bomba(5, 3, 2000, 3);
		Bomba bomba2 = new Bomba(7, 3, 2000, 3);
		bm = new Bomberman(7, 1, "");
		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomberman(bm);
		assertEquals(false, bomba2.isRoto());
		assertEquals(1, m.getBombermans().size());
		bomba.romper();
		m.chequearColisiones();
		m.quitarBmMuertos();
		assertEquals(true, bomba2.isRoto());
		assertEquals(0, m.getBombermans().size());
	}

	@Test
	public void colisionBombaBombaBmEscondido() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new Bomba(5, 3, 2000, 3);
		Bomba bomba2 = new Bomba(7, 3, 2000, 3);
		bm = new Bomberman(7, 5, "");
		obstR = new ObstaculoRompible(7, 4);
		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomberman(bm);
		m.setObstaculo(obstR);
		assertEquals(false, bomba2.isRoto());
		assertEquals(1, m.getBombermans().size());
		bomba.romper();
		m.chequearColisiones();
		m.quitarBmMuertos();
		assertEquals(true, bomba2.isRoto());
		assertEquals(1, m.getBombermans().size());
	}

	@Test
	public void queChequeeExplosionesConColisionados() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new Bomba(5, 3, 2000, 3);
		Bomba bomba2 = new Bomba(6, 1, 2000, 3);
		bm = new Bomberman(4, 1, "");
		obstR = new ObstaculoRompible(5, 2);
		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomberman(bm);
		m.setObstaculo(obstR);
		assertEquals(1, m.getBombermans().size());
		bomba.romper();
		bomba2.romper();
		m.chequearColisiones();
		m.quitarBmMuertos();
		assertEquals(0, m.getBombermans().size());
	}

	@Test
	public void colisionBombaBombaEscondida() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new Bomba(5, 4, 2000, 3);
		Bomba bomba2 = new Bomba(5, 2, 2000, 3);
		Bomba bomba3 = new Bomba(5, 6, 2000, 3);
		Bomba bomba4 = new Bomba(3, 4, 2000, 3);
		Bomba bomba5 = new Bomba(7, 4, 2000, 3);
		obstR = new ObstaculoRompible(5, 3);
		ObstaculoRompible obstR2 = new ObstaculoRompible(5, 5);
		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomba(bomba3);
		m.setBomba(bomba4);
		m.setBomba(bomba5);
		m.setObstaculo(obstR);
		m.setObstaculo(obstR2);
		assertEquals(false, bomba2.isRoto());
		assertEquals(false, bomba3.isRoto());
		assertEquals(false, bomba4.isRoto());
		assertEquals(false, bomba5.isRoto());
		bomba.romper();
		m.chequearColisiones();
		assertEquals(false, bomba2.isRoto());
		assertEquals(false, bomba3.isRoto());
		assertEquals(false, bomba4.isRoto());
		assertEquals(false, bomba5.isRoto());
	}

	@Test
	public void colisionBmBmEscondido() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new Bomba(5, 3, 2000, 3);
		bm = new Bomberman(6, 3, "");
		Bomberman bm2 = new Bomberman(7, 3, "");
		m.setBomba(bomba);
		m.setBomberman(bm);
		m.setBomberman(bm2);
		assertEquals(false, bm.isMuerto());
		assertEquals(false, bm2.isMuerto());
		bomba.romper();
		m.chequearColisiones();
		assertEquals(true, bm.isMuerto());
		assertEquals(false, bm2.isMuerto());
	}

	@Test
	public void colisionBmObstaculoEscondido() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new Bomba(5, 3, 2000, 3);
		bm = new Bomberman(6, 3, "");
		obstR = new ObstaculoRompible(7, 3);
		m.setBomba(bomba);
		m.setBomberman(bm);
		m.setObstaculo(obstR);
		assertEquals(false, bm.isMuerto());
		assertEquals(false, obstR.isRoto());
		bomba.romper();
		m.chequearColisiones();
		assertEquals(true, bm.isMuerto());
		assertEquals(false, obstR.isRoto());
	}

	@Test
	public void colisionBmBombaEscondida() {
		m = new Mapa(9, 7, 0, 0);
		bomba = new Bomba(5, 3, 2000, 3);
		bm = new Bomberman(6, 3, "");
		Bomba bomba2 = new Bomba(7, 3, 2000, 3);
		m.setBomba(bomba);
		m.setBomba(bomba2);
		m.setBomberman(bm);
		assertEquals(false, bm.isMuerto());
		assertEquals(false, bomba2.isRoto());
		bomba.romper();
		m.chequearColisiones();
		assertEquals(true, bm.isMuerto());
		assertEquals(false, bomba2.isRoto());
	}

	@Test
	public void queQuiteBombasExplotadas() {
		Coordenadas coord = new Coordenadas(3, 3);
		m = new Mapa(9, 7, 0, 0);
		bm = new Bomberman(1, 1, "");
		m.setBomberman(bm);
		bm.ponerBomba();
		bm.getBombas().get(0).romper();
		bm.setPos(coord);
		bm.ponerBomba();
		bm.getBombas().get(1).romper();
		coord.setxPos(5);
		coord.setyPos(5);
		bm.setPos(coord);
		bm.ponerBomba();
		assertEquals(3, bm.getBombas().size());
		m.quitarBombasExplotadas();
		assertEquals(1, bm.getBombas().size());
	}
}