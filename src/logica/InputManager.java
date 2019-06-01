package logica;

import static logica.Movimiento.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener{
	private Mapa mapa;

	public InputManager(Mapa mapa) {
		this.mapa = mapa;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// BOMBERMAN 1
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					mapa.getBombermans().get(0).setMovimiento(ARRIBA);
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					mapa.getBombermans().get(0).setMovimiento(ABAJO);
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					mapa.getBombermans().get(0).setMovimiento(DERECHA);
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					mapa.getBombermans().get(0).setMovimiento(IZQUIERDA);
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					mapa.getBombermans().get(0).ponerBomba();
					return;
				}

				// BOMBERMAN 2
				if (e.getKeyCode() == KeyEvent.VK_W) {
					mapa.getBombermans().get(1).setMovimiento(ARRIBA);
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_S) {
					mapa.getBombermans().get(1).setMovimiento(ABAJO);
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_D) {
					mapa.getBombermans().get(1).setMovimiento(DERECHA);
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_A) {
					mapa.getBombermans().get(1).setMovimiento(IZQUIERDA);
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_B) {
					mapa.getBombermans().get(1).ponerBomba();
					return;
				}
				
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		
		// JUGADOR 1
		
		if(code == KeyEvent.VK_UP && mapa.getBombermans().get(0).getMovimiento().equals(ARRIBA)) {
			mapa.getBombermans().get(0).setMovimiento(QUIETO);
			return;
		}
		
		if(code == KeyEvent.VK_DOWN && mapa.getBombermans().get(0).getMovimiento().equals(ABAJO)) {
			mapa.getBombermans().get(0).setMovimiento(QUIETO);
			return;
		}
		
		if(code == KeyEvent.VK_RIGHT && mapa.getBombermans().get(0).getMovimiento().equals(DERECHA)) {
			mapa.getBombermans().get(0).setMovimiento(QUIETO);
			return;
		}
		
		if(code == KeyEvent.VK_LEFT && mapa.getBombermans().get(0).getMovimiento().equals(IZQUIERDA)) {
			mapa.getBombermans().get(0).setMovimiento(QUIETO);
			return;
		}
		
		
		// JUGADOR 2
		
		if(code == KeyEvent.VK_W && mapa.getBombermans().get(1).getMovimiento().equals(ARRIBA)) {
			mapa.getBombermans().get(1).setMovimiento(QUIETO);
			return;
		}
		
		if(code == KeyEvent.VK_S && mapa.getBombermans().get(1).getMovimiento().equals(ABAJO)) {
			mapa.getBombermans().get(1).setMovimiento(QUIETO);
			return;
		}
		
		if(code == KeyEvent.VK_D && mapa.getBombermans().get(1).getMovimiento().equals(DERECHA)) {
			mapa.getBombermans().get(1).setMovimiento(QUIETO);
			return;
		}
		
		if(code == KeyEvent.VK_A && mapa.getBombermans().get(1).getMovimiento().equals(IZQUIERDA)) {
			mapa.getBombermans().get(1).setMovimiento(QUIETO);
			return;
		}
	}
}
