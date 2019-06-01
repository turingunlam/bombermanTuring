package logica;

public enum Escenario {
	NORMAL(1), ESPACIO(2), METAL(2), LADRILLOS(3);

	private int escenario;

	private Escenario(int escenario) {
		this.escenario = escenario;
	}
	
	public int getEscenario() {
		return escenario;
	}
}
