package graficos;

import java.awt.image.BufferedImage;

public class Animacion {
	private int cantFrames;
	private int delay;
	private int contadorTicks;
	private int direccion;
	private int frameActual;
	private int anchoFrame;
	private int altoFrame;
	private boolean reproduciendo;
	private Frame[] frames;
	
	public Animacion(BufferedImage[] frames, int anchoFrame, int altoFrame, int delay) {
		cantFrames = frames.length;
		contadorTicks = 0;
		frameActual = 0;
		reproduciendo = false;
		direccion = 1;
		this.anchoFrame = anchoFrame;
		this.altoFrame = altoFrame;
		this.delay = delay;
		
		this.frames = new Frame[frames.length];
		for(int i = 0; i < frames.length; i++) {
			this.frames[i] = new Frame(frames[i]);
		}
	}
	
	public int getAnchoFrame() {
		return anchoFrame;
	}

	public int getAltoFrame() {
		return altoFrame;
	}

	public void frenar() {
		reproduciendo = false;
	}
	
	public void reproducir() {
		reproduciendo = true;
	}
	
	public void reset() {
		contadorTicks = 0;
		frameActual = 0;
		frenar();
	}
	
	public void actualizar() {
		if(!reproduciendo) {
			return;
		}
		
		contadorTicks++;
		
		if(contadorTicks > delay) {
			contadorTicks = 0;
			frameActual += direccion;

			if(frameActual >= cantFrames) {
				frameActual = 0;
			} else if(frameActual < 0) {
				frameActual = cantFrames - 1;
			}
		}		
	}
	
	public boolean termino() {
		return frameActual == cantFrames - 1;
	}
	
	public BufferedImage getFrame() {
		return frames[frameActual].getFrame();
	}
	
}
