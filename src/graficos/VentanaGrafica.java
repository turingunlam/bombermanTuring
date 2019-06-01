package graficos;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logica.CantObstaculosRompibles;
import logica.Escenario;
import logica.InputManager;
import logica.Mapa;
import logica.TamañoMapa;
import static graficos.Sprite.*;

@SuppressWarnings("serial")
public class VentanaGrafica extends JFrame {
	private JPanelGraficoPrincipal contentPaneMapa;
	private InputManager inManager;
	private Mapa mapa;

	public VentanaGrafica(Escenario tipoMapa, TamañoMapa tamMapa, CantObstaculosRompibles cantObstRompibles,
			Integer cantJugadores) {
		super("Bomberman v2.5");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, (tamMapa.getTamaño() + 2) * ANCHO_TILE + 14, (tamMapa.getTamaño() + 2) * ALTO_TILE + 38);
		setLocationRelativeTo(null);

		mapa = new Mapa(tamMapa.getTamaño(), tamMapa.getTamaño(), cantJugadores, cantObstRompibles.getCantidad(),
				tipoMapa);

		contentPaneMapa = new JPanelGraficoPrincipal(mapa);
		setContentPane(contentPaneMapa);

		inManager = new InputManager(contentPaneMapa.getMapa());

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				confirmacionCierreVentana();
			}
		});

		addKeyListener(inManager);

		setVisible(true);
	}

	private void confirmacionCierreVentana() {
		if (JOptionPane.showConfirmDialog(rootPane, "¿Desea salir?", "Salir",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public Mapa getMapa() {
		return mapa;
	}

	public InputManager getInManager() {
		return inManager;
	}
}
