package paquetes;

public enum PaqueteID {
	ExplotarBomba(1), InicioPartida(2), Mapa(3), Movimiento(4), NuevoJugador(5), PonerBomba(6);

	private int id;

	private PaqueteID(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
