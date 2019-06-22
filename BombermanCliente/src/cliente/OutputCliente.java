package cliente;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import paquetes.PaqueteNuevoJugador;

public class OutputCliente {
	private Cliente cliente;
	private Socket socketCliente;
	private DataOutputStream out;

	public OutputCliente(Cliente cliente) {
		try {
			this.cliente = cliente;
			socketCliente = cliente.getCliente();
			out = new DataOutputStream(socketCliente.getOutputStream());
			enviarPaquete(new PaqueteNuevoJugador(cliente.getNombre()));
		} catch (IOException e) {
			System.out.println("No se pudo obtener stream de entrada del cliente");
		}
	}
	
	public void enviarPaquete(Object pk) {
		try {
			out.writeUTF(new Gson().toJson(pk));
			out.flush();
		} catch (IOException e) {
			System.out.println("No se pudo enviar el paquete del cliente ");
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
