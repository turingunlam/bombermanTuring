package pk;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BombermanTest {
	private Bomberman bm;

	@Before
	public void setUp() {
		bm = new Bomberman(0, 0, "Pj");
	}

	@Test
	public void queValideNombreCadenaNula() {
		Bomberman bm1 = new Bomberman(0, 0, ""); 
		assertEquals("Bomberman", bm1.getNombre());
	}
	
	@Test
	public void queValideNombreEspacio() {
		Bomberman bm1 = new Bomberman(0, 0, "   "); 
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
		bm.mover(Bomberman.getAbajo());
		bm.mover(Bomberman.getAbajo());
		bm.mover(Bomberman.getAbajo());
		bm.mover(Bomberman.getArriba());
		bm.mover(Bomberman.getDerecha());
		bm.mover(Bomberman.getIzquierda());
		bm.mover(Bomberman.getDerecha());
		assertEquals(0.1, bm.getPos().getxPos(), 0.1);
		assertEquals(0.2, bm.getPos().getyPos(), 0.1);
	}

	@Test
	public void quePongaBomba() {
		bm.getPos().setxPos(10);
		bm.getPos().setyPos(15);
		bm.ponerBomba();
		assertEquals(10, bm.getBombas().get(0).getPos().getxPos(), 0);
		assertEquals(15, bm.getBombas().get(0).getPos().getyPos(), 0);
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
		assertEquals(1, bm.getRangoBomba());
	}

	@Test
	public void colisionObstaculoR() {
		bm.getPos().setxPos(0.3);
		bm.getPos().setyPos(0.5);
		ObstaculoRompible obs = new ObstaculoRompible(0, 0);
		assertEquals(true, bm.colisiona(obs));
	}

	@Test
	public void noColisionObstaculo() {
		ObstaculoRompible obs = new ObstaculoRompible(1, 0);
		assertEquals(false, bm.colisiona(obs));
	}

	@Test
	public void queNoPongaBombasMaximas() {
		bm.ponerBomba();
		bm.ponerBomba();
		assertEquals(true, bm.ponerBomba());
		assertEquals(false, bm.ponerBomba());
	}
	
	@Test
	public void queValideCambioBombas() {
		ArrayList<Bomba> bombas= new ArrayList<Bomba>();
		for ( int i = 0; i < 5; i++) {
			bombas.add(new Bomba(0, 0, 2000, 1));
		}
		bm.setBombas(bombas);
		assertNotSame(bombas, bm.getBombas());
	}
	
}