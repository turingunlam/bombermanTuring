package cliente;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;



public class Cliente {
	private Socket cliente;
	private String nombre;
	private int puerto;
	private String ip;
	private OutputCliente outPutCliente;
	private static final String PATH_PROPERTIES = "../archivos/inicializacion.properties";

	public Cliente(String nombre) {
		try {
			leerProperties();
			cliente = new Socket(ip, puerto);
			this.nombre = nombre;
			ThreadInputCliente threadIn = new ThreadInputCliente(this, nombre);
			threadIn.start();
			outPutCliente = new OutputCliente(this);
		} catch (IOException e) {
			System.out.println(nombre + " no se pudo conectar al server.");
		}
	}

	private void leerProperties() {
		try {
			Properties properties = new Properties();
			InputStream archivoInput = new FileInputStream(PATH_PROPERTIES);
			properties.load(archivoInput);
			puerto = Integer.valueOf(properties.getProperty("puerto"));
			ip = properties.getProperty("ip");
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo encotrar el archivo de properties en " + PATH_PROPERTIES);
		} catch (IOException e) {
			System.out.println("Error al leer del archivo de properties en " + PATH_PROPERTIES);
		}
	}

	public String getNombre() {
		return nombre;
	}

//	public void enviarPaquete(Object pk) {
//		try {
//			out.writeUTF(new Gson().toJson(pk));
//			out.flush();
//		} catch (IOException e) {
//			System.out.println("No se pudo enviar el paquete del cliente " + nombre);
//		}
//	}

//	public Object recibirPaquete() {
//		try {
//			Gson gson = new Gson();
//			String linea = in.readUTF();
//			return gson.fromJson(linea, Object.class);
//		} catch (IOException e) {
//			System.out.println("No se pudo recibir ningun paquete.");
//		}
//
//		return null;
//	}

	public void cerrarSocket() {
		try {
			cliente.close();
		} catch (IOException e) {
			System.out.println("No se pudo cerrar el socket.");
		}
	}

	public boolean isSocketCerrado() {
		return cliente.isClosed();
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}

	public OutputCliente getOutPutCliente() {
		return outPutCliente;
	}

	public void setOutPutCliente(OutputCliente outPutCliente) {
		this.outPutCliente = outPutCliente;
	}

	public static void main(String[] args) {
		Cliente cliente = new Cliente("fasasasd");
	}

}
