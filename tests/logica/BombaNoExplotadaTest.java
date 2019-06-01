package logica;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import logica.Bomba;
import logica.BombaNoExplotada;
import logica.ObstaculoIrrompible;

public class BombaNoExplotadaTest {
	BombaNoExplotada bNoExplotada;	
	
	@Before
	public void setUp() throws Exception {
		bNoExplotada = new BombaNoExplotada(2, 2, 500, 2);
	}

	@Test
	public void queEsteEscondidoArriba() {
		ObstaculoIrrompible obs = new ObstaculoIrrompible(2, 3);
		Bomba b = new BombaNoExplotada(2, 4, 500, 2);
		assertEquals(true, bNoExplotada.isEscondido(b, obs));
	}

	@Test
	public void queEsteEscondidoAbajo() {
		ObstaculoIrrompible obs = new ObstaculoIrrompible(2, 1);
		Bomba b = new BombaNoExplotada(2, 0, 500, 2);
		assertEquals(true, bNoExplotada.isEscondido(b, obs));
	}

	@Test
	public void queEsteEscondidoIzquierda() {
		ObstaculoIrrompible obs = new ObstaculoIrrompible(3, 2);
		Bomba b = new BombaNoExplotada(4, 2, 500, 2);
		assertEquals(true, bNoExplotada.isEscondido(b, obs));
	}

	@Test
	public void queEsteEscondidoDerecha() {
		ObstaculoIrrompible obs = new ObstaculoIrrompible(1, 2);
		Bomba b = new BombaNoExplotada(0, 2, 500, 2);
		assertEquals(true, bNoExplotada.isEscondido(b, obs));
	}

	@Test
	public void queNoEsteEscondidoAtrasBomba() {
		Bomba b1 = new BombaNoExplotada(2, 0, 500, 2);
		Bomba b2 = new BombaNoExplotada(2, 1, 500, 2);
		assertEquals(false, bNoExplotada.isEscondido(b1, b2));
	}
}