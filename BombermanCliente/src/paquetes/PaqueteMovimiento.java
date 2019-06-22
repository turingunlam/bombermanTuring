package paquetes;


import logica.Movimiento;

public class PaqueteMovimiento extends Paquete {
	private static final long serialVersionUID = 1L;
	private Movimiento mov;
	private String nombre;
	
	public PaqueteMovimiento(String nombre, Movimiento mov) {
		super(PaqueteID.Movimiento.getId(), "PaqueteMovimiento");
		this.nombre = nombre;
		this.mov = mov;
	}

	public Movimiento getMov() {
		return mov;
	}

	public void setMov(Movimiento mov) {
		this.mov = mov;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
