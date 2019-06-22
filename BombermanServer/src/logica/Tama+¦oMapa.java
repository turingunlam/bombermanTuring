package logica;

import java.io.Serializable;

public enum Tama�oMapa implements Serializable {
	PEQUE�O(5), MEDIANO(13), GRANDE(19);

	private int tama�o;

	private Tama�oMapa(int tama�o) {
		this.tama�o = tama�o;
	}

	public int getTama�o() {
		return tama�o;
	}
}
