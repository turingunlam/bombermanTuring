package logica;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import logica.Bomba;
import logica.BombaNoExplotada;
import logica.Bomberman;
import logica.Movimiento;
import logica.ObstaculoRompible;

public class BombermanTest {
	private Bomberman bm;
	private static Mapa m = new Mapa(7, 7, 3, 0.4, Escenario.ESPACIO);
	
	@Before
	public void setUp() {
		bm = new Bomberman(0, 0, "Pj",1);
	}

	@Test
	public void queValideNombreCadenaNula() {
		Bomberman bm1 = new Bomberman(0, 0, "",1);
		assertEquals("Bomberman", bm1.getNombre());
	}

	@Test
	public void queValideNombreEspacio() {
		Bomberman bm1 = new Bomberman(0, 0, "   ",1);
		assertEquals("Bomberman", bm1.getNombre());
	}

	@Test
	public void queNoCambieNombreCadenaNula() {
		bm.setNombre("");
		assertEquals("Pj", bm.getNombre());
	}

	@Test
	public void queNoCambieNombreEspacios() {
		bm.setNombre("   ");
		assertEquals("Pj", bm.getNombre());
	}

	@Test
	public void queSeMueva() {
		bm.setMovimiento(Movimiento.ABAJO);
		bm.mover();
		bm.setMovimiento(Movimiento.ABAJO);
		bm.mover();
		bm.setMovimiento(Movimiento.ABAJO);
		bm.mover();
		bm.setMovimiento(Movimiento.ARRIBA);
		bm.mover();
		bm.setMovimiento(Movimiento.DERECHA);
		bm.mover();
		bm.setMovimiento(Movimiento.IZQUIERDA);
		bm.mover();
		bm.setMovimiento(Movimiento.DERECHA);
		bm.mover();
		assertEquals(1, bm.getPos().getxPos(), 0.1);
		assertEquals(2, bm.getPos().getyPos(), 0.1);
	}

	@Test
	public void quePongaBomba() {
		assertEquals(true, bm.ponerBomba());
	}

	@Test
	public void queMuera() {
		assertEquals(false, bm.isMuerto());
		bm.morir();
		assertEquals(true, bm.isMuerto());
	}

	@Test
	public void queNoCambieRangoBombaNegativas() {
		bm.setRangoBomba(-3);
		assertEquals(3, bm.getRangoBomba());
	}

	@Test
	public void queNoPongaBombasMaximas() {
		bm.ponerBomba();
		bm.setPos(new Coordenadas(100,-100));
		bm.ponerBomba();
		bm.setPos(new Coordenadas(100,100));
		assertEquals(true, bm.ponerBomba());
		bm.setPos(new Coordenadas(-100,-100));
		assertEquals(false, bm.ponerBomba());
	}
	
	@Test
	public void queNoPongaBombasSiColisiona() {
		bm.setPos(new Coordenadas(100,-100));
		bm.ponerBomba();
		assertEquals(false, bm.ponerBomba());
	}

	@Test
	public void queValideCambioBombas() {
		ArrayList<Bomba> bombas = new ArrayList<Bomba>();
		for (int i = 0; i < 5; i++) {
			bombas.add(new BombaNoExplotada(0, 0, 2000, 1));
		}
		bm.setBombas(bombas);
		assertNotSame(bombas, bm.getBombas());
	}
	
	@Test
	public void colisionObjetoDerecha() {
		ObstaculoRompible obs = new ObstaculoRompible(31, 0);
		assertEquals(true, bm.colisiona(obs));
	}
	
	@Test
	public void colisionObjetoIzquierda() {
		ObstaculoRompible obs = new ObstaculoRompible(-31, 0);
		assertEquals(true, bm.colisiona(obs));
	}
	
	@Test
	public void colisionObjetoArriba() {
		ObstaculoRompible obs = new ObstaculoRompible(0, -31);
		assertEquals(true, bm.colisiona(obs));
	}
	
	@Test
	public void noColisionObjetoDerecha() {
		ObstaculoRompible obs = new ObstaculoRompible(32, 0);
		assertEquals(false, bm.colisiona(obs));
	}
	
	@Test
	public void noColisionObjetoIzquierda() {
		ObstaculoRompible obs = new ObstaculoRompible(-32, 0);
		assertEquals(false, bm.colisiona(obs));
	}
	
	@Test
	public void noColisionObjetoArriba() {
		ObstaculoRompible obs = new ObstaculoRompible(0, -32);
		assertEquals(false, bm.colisiona(obs));
	}
	
	@Test
	public void noColisionObjetoAbajo() {
		ObstaculoRompible obs = new ObstaculoRompible(0, -32);
		assertEquals(false, bm.colisiona(obs));
	}
	
	
	@Test
	public void isEscondidoDerecha() {
		ObstaculoRompible obst = new ObstaculoRompible(-1, 0);
		BombaNoExplotada bomba = new BombaNoExplotada(-2, 0, 500, 2);
		assertEquals(true, bm.isEscondido(bomba, obst));
	}

	@Test
	public void isEscondidoIzquierda() {
		ObstaculoRompible obst = new ObstaculoRompible(1, 0);
		BombaNoExplotada bomba = new BombaNoExplotada(2, 0, 500, 2);
		assertEquals(true, bm.isEscondido(bomba, obst));
	}

	@Test
	public void isEscondidoArriba() {
		ObstaculoRompible obst = new ObstaculoRompible(0, 1);
		BombaNoExplotada bomba = new BombaNoExplotada(0, 2, 500, 2);
		assertEquals(true, bm.isEscondido(bomba, obst));
	}

	@Test
	public void isEscondidoAbajo() {
		ObstaculoRompible obst = new ObstaculoRompible(0, -1);
		BombaNoExplotada bomba = new BombaNoExplotada(0, -2, 500, 2);
		assertEquals(true, bm.isEscondido(bomba, obst));
	}

	@Test
	public void isNoEscondidoAtrasBomba() {
		BombaNoExplotada obst = new BombaNoExplotada(0, 1, 500, 2);
		BombaNoExplotada bomba = new BombaNoExplotada(0, 2, 500, 2);
		assertEquals(false, bm.isEscondido(bomba, obst));
	}
}