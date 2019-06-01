package logica;

public enum TamañoMapa {
	PEQUEÑO(5), MEDIANO(13), GRANDE(19);

	private int tamaño;

	private TamañoMapa(int tamaño) {
		this.tamaño = tamaño;
	}

	public int getTamaño() {
		return tamaño;
	}
}
