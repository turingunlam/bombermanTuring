package graficos;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cliente.Cliente;
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

	public VentanaGrafica(Mapa mapa, Cliente cliente) {
		super("Bomberman v2.5");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, (mapa.getAncho() + 2) * ANCHO_TILE + 14, (mapa.getAlto() + 2) * ALTO_TILE + 38);
		setLocationRelativeTo(null);

		this.mapa = mapa;

		contentPaneMapa = new JPanelGraficoPrincipal(mapa);
		setContentPane(contentPaneMapa);

		inManager = new InputManager(mapa, cliente);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				confirmacionCierreVentana();
			}
		});

		addKeyListener(inManager);

		setVisible(true);
	}
	
	public VentanaGrafica(Escenario tipoMapa, TamañoMapa tamMapa, CantObstaculosRompibles cantObstRompibles,
			Integer cantJugadores, Cliente cliente) {
		super("Bomberman v2.5");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, (tamMapa.getTamaño() + 2) * ANCHO_TILE + 14, (tamMapa.getTamaño() + 2) * ALTO_TILE + 38);
		setLocationRelativeTo(null);

		mapa = new Mapa(tamMapa, cantJugadores, cantObstRompibles, tipoMapa);

		contentPaneMapa = new JPanelGraficoPrincipal(mapa);
		setContentPane(contentPaneMapa);

		inManager = new InputManager(mapa, cliente);

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
