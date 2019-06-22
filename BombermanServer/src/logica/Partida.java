package logica;

public class Partida implements Runnable {
	@SuppressWarnings("unused")
	private static final int FPS_MAX = 60; // CANTIDAD MAXIMA DE FRAMES POR SEGUNDO
	private static final double TIEMPO_FRAME = 1000 / 60; // TIEMPO DE CADA FRAME
	private static final int FRAMES_OMITIDOS_MAX = 5; // CANTIDAD MAXIMA DE FRAMES OMITIDOS SIN RENDERIZAR
	private Mapa mapa;
	
	@SuppressWarnings("unused")
	private TimerBomba timerBomba;

	public Partida(Mapa mapa) {
		this.mapa = mapa;
		timerBomba = new TimerBomba(mapa);
//		new Thread(this).start();
	}

	@Override
	public void run() {
		int frames_omitidos;
		long tiempo_comienzo;
		long tiempo_sleep;
		long dif_tiempo;

		while (mapa.getBombermans().size() >= 1) {
			frames_omitidos = 0;
			tiempo_comienzo = System.currentTimeMillis();

			mapa.actualizar();

			dif_tiempo = System.currentTimeMillis() - tiempo_comienzo;

			// SI SE ACTUALIZO Y RENDERIZO MAS RAPIDO QUE EL TIEMPO DE FRAME, "PARAMOS" EL
			// THREAD HASTA EL PROXIMO CICLO
			tiempo_sleep = (long) (TIEMPO_FRAME - dif_tiempo);

			if (tiempo_sleep > 0) {
				try {
					Thread.sleep(tiempo_sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// SI SE TARDO MAS QUE EL TIEMPO DE FRAME PARA ACTUALIZAR Y RENDERIZAR, HAY QUE
			// ACTUALIZAR Y RECUPERAR LOS FRAMES "PERDIDOS"
			// SI LOS FRAMES PERDIDOS FUERON MAS QUE LOS FRAMES_OMITIDOS_MAX, ALGUNOS FRAMES
			// NO SE RECUPERAN (SE RECUPERAN HASTA UN MAXIMO DE 5 FRAMES)
			while (tiempo_sleep < 0 && frames_omitidos < FRAMES_OMITIDOS_MAX) {
				mapa.actualizar();
				tiempo_sleep += TIEMPO_FRAME;
				frames_omitidos++;
			}
		}
	}

	public static void main(String[] args) {
//		Mapa mapa = null;
//		Thread juego = new Thread(new Partida(mapa));
//		juego.start();
	}

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
}
