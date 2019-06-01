package logica;

public enum CantObstaculosRompibles {
	NADA(0), POCOS(0.3), MEDIO(0.5), MUCHOS(0.8);

	private double cantidad;

	private CantObstaculosRompibles(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public double getCantidad() {
		return cantidad;
	}
}
