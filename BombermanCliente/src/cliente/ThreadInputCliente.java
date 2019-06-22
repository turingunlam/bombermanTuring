package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gson.RuntimeTypeAdapterFactory;
import logica.Bomba;
import logica.BombaExplotada;
import logica.BombaNoExplotada;
import logica.Obstaculo;
import logica.ObstaculoIrrompible;
import logica.ObstaculoRompible;
import logica.Partida;
import paquetes.Paquete;
import paquetes.PaqueteExplotarBomba;
import paquetes.PaqueteID;
import paquetes.PaqueteInicioPartida;
import paquetes.PaqueteManager;
import paquetes.PaqueteMapa;
import paquetes.PaqueteMovimiento;
import paquetes.PaqueteNuevoJugador;
import paquetes.PaquetePonerBomba;

public class ThreadInputCliente extends Thread {
	private Socket socketCliente;
	private Cliente cliente;
	private DataInputStream in;
	private Partida partida;

	public ThreadInputCliente(Cliente cliente, String nombre) {
		try {
			this.cliente = cliente;
			socketCliente = cliente.getCliente();
			in = new DataInputStream(socketCliente.getInputStream());
		} catch (IOException e) {
			System.out.println("No se pudo obtener stream de entrada del cliente " + cliente.getNombre());
		}
	}

	@Override
	public void run() {
		while (socketCliente.isConnected() && !socketCliente.isClosed()) {
			try {
				Paquete inputPk;
				String lineaPk = in.readUTF();

				Gson gson = crearGson();

				inputPk = gson.fromJson(lineaPk, Paquete.class);
				if (inputPk.getId() == PaqueteID.InicioPartida.getId()) {
					partida = PaqueteManager.procesarPaquete(inputPk, cliente);
				} else {
					PaqueteManager.procesarPaquete((PaqueteMapa) inputPk, partida);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("No se pudo leer paquete del server.");
			}

		}
	}

	private Gson crearGson() {
		RuntimeTypeAdapterFactory<Paquete> adapterPaquete = RuntimeTypeAdapterFactory.of(Paquete.class, "type")
				.registerSubtype(PaqueteMapa.class, "PaqueteMapa")
				.registerSubtype(PaqueteInicioPartida.class, "PaqueteInicioPartida")
				.registerSubtype(PaqueteExplotarBomba.class, "PaqueteExplotarBomba")
				.registerSubtype(PaqueteMovimiento.class, "PaqueteMovimiento")
				.registerSubtype(PaqueteNuevoJugador.class, "PaqueteNuevoJugador")
				.registerSubtype(PaquetePonerBomba.class, "PaquetePonerBomba");

		RuntimeTypeAdapterFactory<Obstaculo> adapterObstaculo = RuntimeTypeAdapterFactory.of(Obstaculo.class, "type")
				.registerSubtype(ObstaculoIrrompible.class, "ObstaculoIrrompible")
				.registerSubtype(ObstaculoRompible.class, "ObstaculoRompible");

		RuntimeTypeAdapterFactory<Bomba> adapterBomba = RuntimeTypeAdapterFactory.of(Bomba.class, "type")
				.registerSubtype(BombaNoExplotada.class, "BombaNoExplotada")
				.registerSubtype(BombaExplotada.class, "BombaExplotada");

		return new GsonBuilder().registerTypeAdapterFactory(adapterPaquete).registerTypeAdapterFactory(adapterObstaculo)
				.registerTypeAdapterFactory(adapterBomba).create();
	}
}
