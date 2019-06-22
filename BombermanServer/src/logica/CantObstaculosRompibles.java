package logica;

import java.io.Serializable;

public enum CantObstaculosRompibles implements Serializable {
	NADA(0), POCOS(0.3), MEDIO(0.5), MUCHOS(0.8);

	private double cantidad;

	private CantObstaculosRompibles(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public double getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(double cant) {
		this.cantidad = cant;
	}
}
