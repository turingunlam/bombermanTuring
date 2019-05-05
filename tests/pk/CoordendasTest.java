package pk;

import static org.junit.Assert.*;
import org.junit.Test;

public class CoordendasTest {
	Coordenadas coord;

	@Test
	public void queValideCoordenadasNegativas() {
		coord = new Coordenadas(-2, -2);
		assertEquals(0, coord.getxPos(), 0);
		assertEquals(0, coord.getyPos(), 0);
	}
	
	@Test
	public void queNoCambieXNegativa() {
		coord = new Coordenadas(2, 4);
		coord.setxPos(-2);
		assertEquals(2, coord.getxPos(), 0);
	}

	@Test
	public void queNoCambieYNegativa() {
		coord = new Coordenadas(2, 4);
		coord.setyPos(-2);
		assertEquals(4, coord.getyPos(), 0);
	}
}
