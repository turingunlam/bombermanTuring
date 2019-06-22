package paquetes;

import java.io.Serializable;

public class Paquete implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String type;
	protected int id;

	public Paquete() {

	}

	protected Paquete(int id, String type) {
		this.id = id;
		this.type = type;
	}

	public int getId() {
		return id;
	}
}
