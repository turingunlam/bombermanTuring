package logica;

import java.io.Serializable;

public interface Colisionable extends Serializable {
	public boolean colisiona(Colisionable c);
}
