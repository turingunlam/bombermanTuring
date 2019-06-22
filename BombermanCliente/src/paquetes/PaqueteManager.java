package paquetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import cliente.Cliente;
import logica.Bomba;
import logica.BombaNoExplotada;
import logica.Bomberman;
import logica.Escenario;
import logica.Mapa;
import logica.Obstaculo;
import logica.ObstaculoRompible;
import logica.Partida;
import logica.TamañoMapa;

public class PaqueteManager {
	public static Partida procesarPaquete(Paquete pk, Cliente nombre) {
		if (pk.getId() == PaqueteID.InicioPartida.getId()) {
			TamañoMapa tam = ((PaqueteInicioPartida) pk).getTamMapa();
			Escenario esc = ((PaqueteInicioPartida) pk).getEscenario();

			Mapa mapaAux = new Mapa(tam, esc, ((PaqueteInicioPartida) pk).getCantJugadores(),
					((PaqueteInicioPartida) pk).getBombermans(), ((PaqueteInicioPartida) pk).getObstaculos(),
					((PaqueteInicioPartida) pk).getBombas());

			return new Partida(mapaAux, nombre);
		}

		return null;
	}

	public static PaqueteMapa procesarPaquete(Paquete pk, Partida partida) {
		Mapa mapa = partida.getMapa();

		if (pk.getId() == PaqueteID.Mapa.getId()) {
			actualizarBombas(((PaqueteMapa)pk).getBombas(), mapa);
			actualizarObstaculos(((PaqueteMapa) pk).getObstaculos(), partida, mapa);
			actualizarBombermans(mapa, ((PaqueteMapa) pk).getBombermans());

			return null;
		}

		return null;
	}

	private static void actualizarObstaculos(ArrayList<Obstaculo> obstaculos, Partida partida, Mapa mapa) {
		for (Obstaculo obstaculo : obstaculos) {
			if (!mapa.getObstaculos().contains(obstaculo)) {
				mapa.getObstaculos().add(new ObstaculoRompible(obstaculo.getxPos(), obstaculo.getyPos(),
						partida.getMapa().getEscenario()));
			}
		}
	}

	private static void actualizarBombas(ArrayList<Bomba> bombas, Mapa mapa) {
		for (Bomba bomba : bombas) {
			if (!mapa.getBombas().contains(bomba)) {
				mapa.getBombas().add(new BombaNoExplotada(bomba.getxPos(), bomba.getyPos(), bomba.getTiempoDetonacion(),
						bomba.getRangoMax()));
			}
		}
	}

	private static void actualizarBombermans(Mapa mapa, HashMap<String, Bomberman> bombermans) {
		Iterator<Entry<String, Bomberman>> it = bombermans.entrySet().iterator();

		while (it.hasNext()) {
			Entry<String, Bomberman> actual = it.next();

			mapa.getBombermans().get(actual.getKey()).setMovimiento(actual.getValue().getMovimiento());
		}
	}
}
