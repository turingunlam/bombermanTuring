
package graficos;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import logica.Bomba;
import logica.BombaExplotada;
import logica.BombaNoExplotada;
import logica.Bomberman;
import logica.Mapa;
import logica.Obstaculo;
import logica.ObstaculoIrrompible;
import logica.ObstaculoRompible;

import static graficos.Sprite.*;

@SuppressWarnings("serial")
public class JPanelGraficoPrincipal extends JPanel {
	private Mapa mapa;

	public JPanelGraficoPrincipal(Mapa mapa) {
		this.mapa = mapa;
	}

	public void paint(Graphics g) {
		super.paint(g);
		paintFondo(g);
		paintBombas(g);
		paintObstaculos(g);
		paintBombermans(g);
	}

	private void paintFondo(Graphics g) {
		for (int i = 0; i < mapa.getAlto(); i++) {
			for (int j = 0; j < mapa.getAncho(); j++) {
				g.drawImage(Obstaculo.getFondo(), i * ANCHO_TILE, j * ALTO_TILE, null);
			}
		}
	}

	private void paintBombermans(Graphics g) {
		for (Bomberman bomberman : mapa.getBombermans()) {
			g.drawImage(bomberman.getAnimacionActual().getFrame(), (int) bomberman.getxPos(),
					(int) bomberman.getyPos() - 18, bomberman.getAnimacionActual().getAnchoFrame(),
					bomberman.getAnimacionActual().getAltoFrame(), null);
		}
	}

	private void paintBombas(Graphics g) {
		ArrayList<Bomba> bombas = new ArrayList<Bomba>(mapa.getBombas());

		for (Bomba bomba : bombas) {
			if (bomba instanceof BombaExplotada) {
				paintBombaExplotada(g, (BombaExplotada) bomba);
			} else {
				g.drawImage(((BombaNoExplotada) bomba).getAnimacionActual().getFrame(), (int) bomba.getxPos(),
						(int) bomba.getyPos(), ANCHO_TILE, ALTO_TILE, null);
			}
		}
	}

	private void paintBombaExplotada(Graphics g, BombaExplotada bomba) {
		// CENTRO DE LA EXPLOSION
		g.drawImage(((BombaExplotada) bomba).getCentro().getFrame(), (int) bomba.getxPos(), (int) bomba.getyPos(),
				ANCHO_TILE, ALTO_TILE, null);

		// IZQUIERDA DE LA EXPLOSION
		for (int i = 1; i < bomba.getRangoIzq() - 1; i++) {
			g.drawImage(((BombaExplotada) bomba).getIzqMedio().getFrame(), (int) bomba.getxPos() - i * ANCHO_TILE,
					(int) bomba.getyPos(), ANCHO_TILE, ALTO_TILE, null);
		}

		// PUNTA IZQUIERDA DE LA EXPLOSION
		if (!(bomba.getRangoIzq() < bomba.getRangoMax())) {
			g.drawImage(((BombaExplotada) bomba).getIzqPunta().getFrame(),
					(int) bomba.getxPos() - (bomba.getRangoIzq() - 1) * ANCHO_TILE, (int) bomba.getyPos(), ANCHO_TILE,
					ALTO_TILE, null);
		}

		// DERECHA DE LA EXPLOSION
		for (int i = 1; i < bomba.getRangoDer() - 1; i++) {
			g.drawImage(((BombaExplotada) bomba).getDerMedio().getFrame(), (int) bomba.getxPos() + i * ANCHO_TILE,
					(int) bomba.getyPos(), ANCHO_TILE, ALTO_TILE, null);
		}

		// PUNTA DERECHA DE LA EXPLOSION
		if (!(bomba.getRangoDer() < bomba.getRangoMax())) {
			g.drawImage(((BombaExplotada) bomba).getDerPunta().getFrame(),
					(int) bomba.getxPos() + (bomba.getRangoDer() - 1) * ANCHO_TILE, (int) bomba.getyPos(), ANCHO_TILE,
					ALTO_TILE, null);
		}
		// ARRIBA DE LA EXPLOSION
		for (int i = 1; i < bomba.getRangoArriba() - 1; i++) {
			g.drawImage(((BombaExplotada) bomba).getMedio().getFrame(), (int) bomba.getxPos(),
					(int) bomba.getyPos() - i * ALTO_TILE, ANCHO_TILE, ALTO_TILE, null);
		}

		// PUNTA ARRIBA DE LA EXPLOSION
		if (!(bomba.getRangoArriba() < bomba.getRangoMax())) {
			g.drawImage(((BombaExplotada) bomba).getArribaPunta().getFrame(), (int) bomba.getxPos(),
					(int) bomba.getyPos() - (bomba.getRangoArriba() - 1) * ALTO_TILE, ANCHO_TILE, ALTO_TILE, null);
		}

		// ABAJO DE LA EXPLOSION
		for (int i = 1; i < bomba.getRangoAbajo() - 1; i++) {
			g.drawImage(((BombaExplotada) bomba).getMedio().getFrame(), (int) bomba.getxPos(),
					(int) bomba.getyPos() + i * ALTO_TILE, ANCHO_TILE, ALTO_TILE, null);
		}

		// PUNTA ABAJO DE LA EXPLOSION
		if (!(bomba.getRangoAbajo() < bomba.getRangoMax())) {
			g.drawImage(((BombaExplotada) bomba).getAbajoPunta().getFrame(), (int) bomba.getxPos(),
					(int) bomba.getyPos() + (bomba.getRangoAbajo() - 1) * ALTO_TILE, ANCHO_TILE, ALTO_TILE, null);
		}
	}

	private void paintObstaculos(Graphics g) {
		ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>(mapa.getObstaculos());

		for (Obstaculo obstaculo : obstaculos) {
			if (obstaculo instanceof ObstaculoIrrompible) {
				g.drawImage(((ObstaculoIrrompible) obstaculo).getAnimacionActual().getFrame(),
						(int) ((ObstaculoIrrompible) obstaculo).getxPos(),
						(int) ((ObstaculoIrrompible) obstaculo).getyPos(), ANCHO_TILE, ALTO_TILE, null);
			} else {
				g.drawImage(((ObstaculoRompible) obstaculo).getAnimacionActual().getFrame(),
						(int) ((ObstaculoRompible) obstaculo).getxPos(),
						(int) ((ObstaculoRompible) obstaculo).getyPos(), ANCHO_TILE, ALTO_TILE, null);
			}
		}
	}

	public Mapa getMapa() {
		return mapa;
	}
}
