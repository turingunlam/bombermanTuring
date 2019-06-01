package logica;

public abstract class Bomba extends Coordenadas implements Colisionable {
	protected int rangoMax;
	protected int tiempoDetonacion;
	
	public Bomba(double xPos, double yPos, int rango, int tiempoDetonacion) {
		super(xPos, yPos);
		this.tiempoDetonacion = tiempoDetonacion;
		this.rangoMax = rango;
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