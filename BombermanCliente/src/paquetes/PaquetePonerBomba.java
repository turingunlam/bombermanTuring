package paquetes;

import logica.Coordenadas;

public class PaquetePonerBomba extends Paquete {
	private static final long serialVersionUID = 1L;
	private Coordenadas coordenadas;
	private String nombre;
	
	public PaquetePonerBomba(double x, double y, String nombre) {
		super(PaqueteID.PonerBomba.getId(), "PaquetePonerBomba");
		coordenadas = new Coordenadas(x, y);
		this.nombre = nombre;
	}

	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
