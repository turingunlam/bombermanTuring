package paquetes;


import logica.Coordenadas;

public class PaqueteExplotarBomba extends Paquete {
	private static final long serialVersionUID = 1L;
	private Coordenadas coordenadas;
	
	public PaqueteExplotarBomba(int x, int y) {
		super(PaqueteID.ExplotarBomba.getId(), "PaqueteExplotarBomba");
		coordenadas = new Coordenadas(x, y);
	}

	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}
}
