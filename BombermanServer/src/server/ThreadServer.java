package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gson.RuntimeTypeAdapterFactory;
import logica.Obstaculo;
import logica.ObstaculoIrrompible;
import logica.ObstaculoRompible;
import logica.Partida;
import paquetes.Paquete;
import paquetes.PaqueteExplotarBomba;
import paquetes.PaqueteInicioPartida;
import paquetes.PaqueteManager;
import paquetes.PaqueteMapa;
import paquetes.PaqueteMovimiento;
import paquetes.PaqueteNuevoJugador;
import paquetes.PaquetePonerBomba;

public class ThreadServer extends Thread {
	private Socket socketCliente;
	private static ArrayList<Socket> clientes;
	private DataInputStream in;
	private Partida partida;

	public ThreadServer(Socket socketCliente, ArrayList<Socket> clientes, Partida partida) {
		try {
			this.socketCliente = socketCliente;
			ThreadServer.clientes = clientes;
			this.partida = partida;
			this.in = new DataInputStream(socketCliente.getInputStream());
		} catch (IOException e) {
			System.out.println("Probelmas obteniendo la conexion con un cliente");
		}
	}

	@Override
	public void run() {
		while (socketCliente.isConnected() && !socketCliente.isClosed()) {
			try {
				String lineaPk = in.readUTF();
				Gson gson = crearGson();
				Paquete inputPk = gson.fromJson(lineaPk, Paquete.class);
				PaqueteMapa mapaPk = PaqueteManager.procesarPaquete(inputPk, partida);
				if (mapaPk != null) {
					distribuirClientes(mapaPk);
				}
			} catch (IOException e) {
				System.out.println("No se pudo leer input del cliente.");
			}
		}
	}

	public static void distribuirClientes(Paquete inputPk) {
		for (Socket cliente : clientes) {
			try {
				DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
				out.writeUTF(new Gson().toJson(inputPk));
				out.flush();
			} catch (IOException e) {
				System.out.println("No se pudo enviar paquete al cliente.");
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

		return new GsonBuilder().registerTypeAdapterFactory(adapterPaquete).registerTypeAdapterFactory(adapterObstaculo)
				.create();
	}
}
