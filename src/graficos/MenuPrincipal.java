package graficos;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.CantObstaculosRompibles;
import logica.Escenario;
import logica.TamañoMapa;

import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MenuPrincipal extends JDialog {

	public Escenario tipoMapa;
	public TamañoMapa tamMapa;
	public CantObstaculosRompibles cantObst;

	private JButton btnJugar;

	private JPanel contentPane;

	private VentanaGrafica ventana;

	public MenuPrincipal() {
		setModal(true);
		setResizable(false);
		setAlwaysOnTop(true);
		setBounds(100, 100, 329, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 149, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblTipoMapa = new JLabel("Tipo Mapa");
		GridBagConstraints gbc_lblTipoMapa = new GridBagConstraints();
		gbc_lblTipoMapa.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoMapa.gridx = 1;
		gbc_lblTipoMapa.gridy = 1;
		panel.add(lblTipoMapa, gbc_lblTipoMapa);

		JComboBox<Escenario> cmbTipoMapa = new JComboBox<>();
		cmbTipoMapa.setToolTipText("Seleccionar...");
		cmbTipoMapa.setModel(new DefaultComboBoxModel<>(Escenario.values()));
		GridBagConstraints gbc_cmbTipoMapa = new GridBagConstraints();
		gbc_cmbTipoMapa.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTipoMapa.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTipoMapa.gridx = 2;
		gbc_cmbTipoMapa.gridy = 1;
		panel.add(cmbTipoMapa, gbc_cmbTipoMapa);

		JLabel lblTamaoMapa = new JLabel("Tama\u00F1o Mapa");
		GridBagConstraints gbc_lblTamaoMapa = new GridBagConstraints();
		gbc_lblTamaoMapa.insets = new Insets(0, 0, 5, 5);
		gbc_lblTamaoMapa.gridx = 1;
		gbc_lblTamaoMapa.gridy = 2;
		panel.add(lblTamaoMapa, gbc_lblTamaoMapa);

		JComboBox<TamañoMapa> cmbTamMapa = new JComboBox<>();
		cmbTamMapa.setToolTipText("Seleccionar...");
		cmbTamMapa.setModel(new DefaultComboBoxModel<>(TamañoMapa.values()));
		GridBagConstraints gbc_cmbTamMapa = new GridBagConstraints();
		gbc_cmbTamMapa.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTamMapa.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTamMapa.gridx = 2;
		gbc_cmbTamMapa.gridy = 2;
		panel.add(cmbTamMapa, gbc_cmbTamMapa);

		JLabel lblCantidadObstculos = new JLabel("Cantidad Obst\u00E1culos");
		GridBagConstraints gbc_lblCantidadObstculos = new GridBagConstraints();
		gbc_lblCantidadObstculos.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantidadObstculos.gridx = 1;
		gbc_lblCantidadObstculos.gridy = 3;
		panel.add(lblCantidadObstculos, gbc_lblCantidadObstculos);

		JComboBox<CantObstaculosRompibles> cmbCantObst = new JComboBox<>();
		cmbCantObst.setToolTipText("Seleccionar...");
		cmbCantObst.setModel(new DefaultComboBoxModel<>(CantObstaculosRompibles.values()));
		GridBagConstraints gbc_cmbCantObst = new GridBagConstraints();
		gbc_cmbCantObst.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCantObst.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCantObst.gridx = 2;
		gbc_cmbCantObst.gridy = 3;
		panel.add(cmbCantObst, gbc_cmbCantObst);

		JLabel lblCantidadJugadores = new JLabel("Cantidad Jugadores");
		GridBagConstraints gbc_lblCantidadJugadores = new GridBagConstraints();
		gbc_lblCantidadJugadores.anchor = GridBagConstraints.EAST;
		gbc_lblCantidadJugadores.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantidadJugadores.gridx = 1;
		gbc_lblCantidadJugadores.gridy = 4;
		panel.add(lblCantidadJugadores, gbc_lblCantidadJugadores);

		JComboBox<String> cmbCantJugadores = new JComboBox<>();
		cmbCantJugadores.setToolTipText("Seleccionar...");
		cmbCantJugadores.setModel(new DefaultComboBoxModel<>(new String[] { "2", "3", "4" }));
		GridBagConstraints gbc_cmbCantJugadores = new GridBagConstraints();
		gbc_cmbCantJugadores.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCantJugadores.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCantJugadores.gridx = 2;
		gbc_cmbCantJugadores.gridy = 4;
		panel.add(cmbCantJugadores, gbc_cmbCantJugadores);

		btnJugar = new JButton("Jugar!");

		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Escenario tipoEscenario = ((Escenario) cmbTipoMapa.getSelectedItem());
				TamañoMapa tamMapa = ((TamañoMapa) cmbTamMapa.getSelectedItem());
				CantObstaculosRompibles cantObstRompibles = ((CantObstaculosRompibles) cmbCantObst.getSelectedItem());
				Integer cantJugadores = ((Integer.parseInt((String) cmbCantJugadores.getSelectedItem())));

				dispose();
				crearVentana(tipoEscenario, tamMapa, cantObstRompibles, cantJugadores);
			}
		});

		GridBagConstraints gbc_btnJugar = new GridBagConstraints();
		gbc_btnJugar.insets = new Insets(0, 0, 5, 0);
		gbc_btnJugar.gridwidth = 5;
		gbc_btnJugar.gridx = 0;
		gbc_btnJugar.gridy = 5;
		panel.add(btnJugar, gbc_btnJugar);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void crearVentana(Escenario tipoEscenario, TamañoMapa tamMapa, CantObstaculosRompibles cantObstRompibles,
			Integer cantJugadores) {
		ventana = new VentanaGrafica(tipoEscenario, tamMapa, cantObstRompibles, cantJugadores);		
	}
	
	public VentanaGrafica getVentana() {
		return ventana;
	}
}
