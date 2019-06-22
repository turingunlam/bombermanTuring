package logica;

import java.io.Serializable;

public enum Movimiento implements Serializable {
	ARRIBA(0, -1), ABAJO(0, 1), DERECHA(1, 0), IZQUIERDA(-1, 0), QUIETO(0, 0);

	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	private Movimiento(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}
}
