package paquetes;

import static graficos.Sprite.ALTO_TILE;
import static graficos.Sprite.ANCHO_TILE;

import java.util.ArrayList;

import logica.Bomba;
import logica.BombaNoExplotada;
import logica.Bomberman;
import logica.Mapa;
import logica.Movimiento;
import logica.Partida;
import server.ThreadServer;

public class PaqueteManager {
	public static PaqueteMapa procesarPaquete(Paquete pk, Partida partida) {
		Mapa mapa = partida.getMapa();

		if (pk.getId() == PaqueteID.NuevoJugador.getId()) {
			mapa.addBomberman(((PaqueteNuevoJugador) pk).getNombre());

			if (mapa.getBombermans().size() >= partida.getMapa().getCantJugadores()) {
				mapa.calcularPosIniBombermans();
				new Thread(partida).start();
				ThreadServer.distribuirClientes(new PaqueteInicioPartida(mapa));
			}

			return null;
		}

		if (pk.getId() == PaqueteID.Movimiento.getId()) {
			String nombre = ((PaqueteMovimiento) pk).getNombre();
			Movimiento mov = ((PaqueteMovimiento) pk).getMov();

			mapa.getBombermans().get(nombre).setMovimiento(mov);

			PaqueteMapa mapaPk = new PaqueteMapa(mapa);
			return mapaPk;
		}

		if (pk.getId() == PaqueteID.PonerBomba.getId()) {
			Bomberman bm = mapa.getBombermans().get(((PaquetePonerBomba) pk).getNombre());
			ArrayList<Bomba> bombas = bm.getBombas();

			for (Bomba bomba : bombas) {
				if (bm.colisiona(bomba)) {
					return new PaqueteMapa(mapa);
				}
			}

			if (bombas.size() < bm.getMaxBombas() && !bm.isPisandoBomba()) {
				int xBomba = (int) bm.getxPos();
				int yBomba = (int) bm.getyPos();

				if ((bm.getxPos() % ANCHO_TILE) >= (ANCHO_TILE) / 2) {
					xBomba = (int) bm.getxPos() + ANCHO_TILE;
				}

				if ((bm.getyPos() % ALTO_TILE) >= (ALTO_TILE) / 2) {
					yBomba = (int) bm.getyPos() + ALTO_TILE;
				}

				bombas.add(new BombaNoExplotada((xBomba / ANCHO_TILE) * ANCHO_TILE, (yBomba / ALTO_TILE) * ALTO_TILE,
						bm.getTiempoBomba(), bm.getRangoBomba()));

				bm.setPisandoBomba();

				return new PaqueteMapa(mapa);
			}

			return new PaqueteMapa(mapa);
		}

		return null;
	}
}
