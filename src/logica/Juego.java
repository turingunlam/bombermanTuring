package logica;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import graficos.MenuPrincipal;
import graficos.VentanaGrafica;

public class Juego implements Runnable {
	@SuppressWarnings("unused")
	private static final int FPS_MAX = 60; // CANTIDAD MAXIMA DE FRAMES POR SEGUNDO
	private static final double TIEMPO_FRAME = 1000 / 60; // TIEMPO DE CADA FRAME
	private static final int FRAMES_OMITIDOS_MAX = 5; // CANTIDAD MAXIMA DE FRAMES OMITIDOS SIN RENDERIZAR

	@SuppressWarnings("unused")
	private TimerBomba timerBomba;
	private VentanaGrafica ventanaPrincipal;
	private MenuPrincipal menu;

	public Juego() {
		menu = new MenuPrincipal();
		ventanaPrincipal = menu.getVentana();
		timerBomba = new TimerBomba(ventanaPrincipal.getMapa());
	}

	@Override
	public void run() {
		int frames_omitidos;
		long tiempo_comienzo;
		long tiempo_sleep;
		long dif_tiempo;

		while (ventanaPrincipal.getMapa().getBombermans().size() > 1) {
			frames_omitidos = 0;
			tiempo_comienzo = System.currentTimeMillis();

			ventanaPrincipal.getMapa().actualizar();
			ventanaPrincipal.getContentPane().repaint();

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
				ventanaPrincipal.getMapa().actualizar();
				tiempo_sleep += TIEMPO_FRAME;
				frames_omitidos++;
			}
		}

		JOptionPane.showMessageDialog(ventanaPrincipal,"¡GANASTE!", "Fin de la partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ventanaPrincipal.getMapa().getBombermans().get(0).getIcono()));
		ventanaPrincipal.dispose();
	}

	public static void main(String[] args) {
		Runnable juego = new Juego();
		new Thread(juego).start();
	}
}
