package logica;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimerBomba {
	private Mapa mapa;
	private Timer timer;

	public TimerBomba(Mapa mapa) {
		this.mapa = mapa;
		timer = new Timer();

		// EJECUTA TASK CADA 1000 ms
		timer.scheduleAtFixedRate(new Task(), 0, 500);
	}

	public class Task extends TimerTask {
		@Override
		public void run() {
			ArrayList<Bomba> bombas = new ArrayList<Bomba>(mapa.getBombas());

			for (Bomba bomba : bombas) {
				if (bomba instanceof BombaNoExplotada) {
					bomba.setTiempoDetonacion(bomba.getTiempoDetonacion() - 500);
				}

				else if (bomba instanceof BombaExplotada) {
					((BombaExplotada) bomba).setTiempoExplosion(((BombaExplotada) bomba).getTiempoExplosion() - 500);
				}
			}
		}
	}
}
