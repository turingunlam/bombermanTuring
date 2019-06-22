package logica;

public abstract class Bomba extends Coordenadas implements Colisionable {
	private static final long serialVersionUID = 1L;
	protected int rangoMax;
	protected int tiempoDetonacion;
	protected String type;

	public Bomba(double xPos, double yPos, int rango, int tiempoDetonacion, String type) {
		super(xPos, yPos);
		this.tiempoDetonacion = tiempoDetonacion;
		this.rangoMax = rango;
		this.type = type;
	}

	@Override
	public boolean colisiona(Colisionable c) {
		return false;
	}

	public int getRangoMax() {
		return rangoMax;
	}

	public int getTiempoDetonacion() {
		return tiempoDetonacion;
	}

	public void setTiempoDetonacion(int tiempoDetonacion) {
		this.tiempoDetonacion = tiempoDetonacion;
	}
}