package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import logica.CantObstaculosRompibles;
import logica.Escenario;
import logica.Mapa;
import logica.Partida;
import logica.TamañoMapa;

public class Server {
	private static ServerSocket serverSocket;
	private int puerto;
	private ArrayList<Socket> clientes;
	private static final String PATH_PROPERTIES="../archivos/inicializacion.properties";
	public Server(int cantClientes) {
		try {
			leerProperties();
			clientes = new ArrayList<Socket>();
			Server.serverSocket = new ServerSocket(puerto);
			Mapa mapa = new Mapa(TamañoMapa.MEDIANO, cantClientes, CantObstaculosRompibles.MEDIO, Escenario.ESPACIO);
			Partida partida = new Partida(mapa);
			for (int i = 0; i < cantClientes; i++) {
				Socket socketCliente = serverSocket.accept();
				clientes.add(socketCliente);
				ThreadServer serverThread = new ThreadServer(socketCliente, clientes, partida);
				serverThread.start();
			}
		} catch (IOException e) {
			System.out.println("No se pudo crear el server.");
		}
	}

	private void leerProperties() {
		try {
			Properties properties = new Properties();
			InputStream archivoInput = new FileInputStream(PATH_PROPERTIES);
			properties.load(archivoInput);
			puerto = Integer.valueOf(properties.getProperty("puerto"));
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo encotrar el archivo de properties en " + PATH_PROPERTIES);
		} catch (IOException e) {
			System.out.println("Error al leer del archivo de properties en " + PATH_PROPERTIES );
		}
	}

	public static void main(String[] args) {
		Server server = new Server(4);
	}
}
