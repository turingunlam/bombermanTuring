package paquetes;

public class PaqueteNuevoJugador extends Paquete {
	private static final long serialVersionUID = 1L;
	String nombre;

	public PaqueteNuevoJugador(String nombre) {
		super(PaqueteID.NuevoJugador.getId(), "PaqueteNuevoJugador");
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
