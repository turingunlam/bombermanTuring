package logica;

import static logica.Movimiento.ABAJO;
import static logica.Movimiento.ARRIBA;
import static logica.Movimiento.DERECHA;
import static logica.Movimiento.IZQUIERDA;
import static logica.Movimiento.QUIETO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cliente.Cliente;
import paquetes.PaqueteMovimiento;
import paquetes.PaquetePonerBomba;

public class InputManager implements KeyListener {
	private Cliente cliente;
	private Mapa mapa;

	public InputManager(Mapa mapa, Cliente cliente) {
		this.mapa = mapa;
		this.cliente = cliente;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		Bomberman bm = mapa.getBombermans().get(cliente.getNombre());

		if (code == KeyEvent.VK_UP && bm.getMovimiento() != Movimiento.ARRIBA) {
			PaqueteMovimiento pk = new PaqueteMovimiento(cliente.getNombre(), ARRIBA);
			cliente.getOutPutCliente().enviarPaquete(pk);
			return;
		}

		if (code == KeyEvent.VK_DOWN && bm.getMovimiento() != Movimiento.ABAJO) {
			PaqueteMovimiento pk = new PaqueteMovimiento(cliente.getNombre(), ABAJO);
			cliente.getOutPutCliente().enviarPaquete(pk);
			return;
		}

		if (code == KeyEvent.VK_RIGHT && bm.getMovimiento() != Movimiento.DERECHA) {
			PaqueteMovimiento pk = new PaqueteMovimiento(cliente.getNombre(), DERECHA);
			cliente.getOutPutCliente().enviarPaquete(pk);
			return;
		}

		if (code == KeyEvent.VK_LEFT && bm.getMovimiento() != Movimiento.IZQUIERDA) {
			PaqueteMovimiento pk = new PaqueteMovimiento(cliente.getNombre(), IZQUIERDA);
			cliente.getOutPutCliente().enviarPaquete(pk);
			return;
		}

		if (code == KeyEvent.VK_SPACE) {
			PaquetePonerBomba pk = new PaquetePonerBomba(bm.getxPos(), bm.getyPos(), cliente.getNombre());
			cliente.getOutPutCliente().enviarPaquete(pk);
			return;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		Bomberman bm = mapa.getBombermans().get(cliente.getNombre());
		
		if (code == KeyEvent.VK_UP && bm.getMovimiento().equals(ARRIBA)) {
			PaqueteMovimiento pk = new PaqueteMovimiento(cliente.getNombre(), QUIETO);
			cliente.getOutPutCliente().enviarPaquete(pk);
			return;
		}

		if (code == KeyEvent.VK_DOWN && bm.getMovimiento().equals(ABAJO)) {
			PaqueteMovimiento pk = new PaqueteMovimiento(cliente.getNombre(), QUIETO);
			cliente.getOutPutCliente().enviarPaquete(pk);
			return;
		}

		if (code == KeyEvent.VK_RIGHT && bm.getMovimiento().equals(DERECHA)) {
			PaqueteMovimiento pk = new PaqueteMovimiento(cliente.getNombre(), QUIETO);
			cliente.getOutPutCliente().enviarPaquete(pk);
			return;
		}

		if (code == KeyEvent.VK_LEFT && bm.getMovimiento().equals(IZQUIERDA)) {
			PaqueteMovimiento pk = new PaqueteMovimiento(cliente.getNombre(), QUIETO);
			cliente.getOutPutCliente().enviarPaquete(pk);
			return;
		}
	}
}
