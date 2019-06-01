package logica;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.HashSet;

import static graficos.Sprite.*;

public class CalculadoraDeColisiones {

	public void chequearColisiones(Mapa mapa) {
		ArrayList<Bomberman> bombermans = new ArrayList<Bomberman>(mapa.getBombermans());
		ArrayList<Bomba> bombas = new ArrayList<Bomba>(mapa.getBombas());
		ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>(mapa.getObstaculos());

		// HashSet NO PERMITE DUPLICADOS. SI DOS BOMBAS COLISIONAN EL MISMO OBSTACULO
		// SOLO SE GUARDARÁ UNA SOLA VEZ EL OBSTACULO.
		HashSet<Colisionable> colisionados = new HashSet<Colisionable>();
		ArrayList<BombaExplotada> bombasExplotadas = new ArrayList<BombaExplotada>();
		ArrayList<Bomba> aBorrar = new ArrayList<Bomba>();

		for (Bomba bomba : bombas) {
			// BOMBA-BOMBA
			if (bomba instanceof BombaExplotada) {
				for (Bomba bomba2 : bombas) {
					if (bomba.colisiona(bomba2)) {
						colisionados.add(bomba2);
					}
				}

				// BOMBA-OBSTACULO
				for (Obstaculo obst : obstaculos) {
					if (bomba.colisiona(obst)) {
						colisionados.add(obst);
					}
				}

				for (Bomberman bm : bombermans) {
					if (bomba.colisiona(bm)) {
						colisionados.add(bm);
					}
				}

				for (Colisionable colisionado : colisionados) {
					if (colisionado instanceof ObstaculoRompible && bomba.colisiona(colisionado)) {
						boolean escondido = false;

						for (Colisionable colisionado2 : colisionados) {
							if (!escondido && bomba.colisiona(colisionado2)) {
								escondido = ((ObstaculoRompible) colisionado).isEscondido(bomba, colisionado2);
							}
						}

						if (!escondido) {
							((ObstaculoRompible) colisionado).romper();
						}
					}

					if (colisionado instanceof BombaNoExplotada && bomba.colisiona(colisionado)) {
						boolean escondido = false;

						for (Colisionable colisionado2 : colisionados) {
							if (!escondido && bomba.colisiona(colisionado2)) {
								escondido = ((BombaNoExplotada) colisionado).isEscondido(bomba, colisionado2);
							}
						}

						if (!escondido) {
							((BombaNoExplotada)colisionado).explotar();
						}
					}
				}
			}
		}

		for (Bomba bomba : bombas) {
			// BOMBA-BOMBERMAN
			for (Bomberman bm : bombermans) {
				if (!bm.isMuerto() && bomba instanceof BombaExplotada && bomba.colisiona(bm)) {
					boolean escondido = false;

					for (Colisionable colisionado : colisionados) {
						if (!escondido && bomba.colisiona(colisionado)) {
							escondido = bm.isEscondido(bomba, colisionado);
						}
					}

					if (!escondido) {
						bm.morir();
					}
				} else if (!bm.isMuerto() && bomba instanceof BombaNoExplotada && bm.colisiona(bomba)) {
					double offsetX = 0;
					double offsetY = 0;

					double colIzquierda = bm.getxPos() - (bomba.getxPos() + ANCHO_TILE);
					double colDerecha = (bm.getxPos() + ANCHO_TILE) - (bomba.getxPos());
					double colArriba = bm.getyPos() - (bomba.getyPos() + ALTO_TILE);
					double colAbajo = (bm.getyPos() + ALTO_TILE) - (bomba.getyPos());

					if (abs(colArriba) > abs(colAbajo)) {
						offsetY = colAbajo;
					} else {
						offsetY = colArriba;
					}

					if (abs(colDerecha) > abs(colIzquierda)) {
						offsetX = colIzquierda;
					} else {
						offsetX = colDerecha;
					}

					if (abs(offsetX) < abs(offsetY)) {
						bm.setxPos(bm.getxPos() - offsetX);
					} else {
						bm.setyPos(bm.getyPos() - offsetY);
					}
				}

			}
		}

		// BOMBERMAN-OBSTACULO
		for (Bomberman bm : bombermans) {
			for (Obstaculo obst : obstaculos) {
				if (!(bm.isMuerto()) && bm.colisiona(obst)) {
					int error = 5;
					
					// GUARDAN CUANTO COLISIONO EN x Y EN y
					double offsetX = 0;
					double offsetY = 0;

					double colIzquierda = bm.getxPos() - (obst.getxPos() + ANCHO_TILE);
					double colDerecha = (bm.getxPos() + ANCHO_TILE) - (obst.getxPos());
					double colArriba = bm.getyPos() - (obst.getyPos() + ALTO_TILE);
					double colAbajo = (bm.getyPos() + ALTO_TILE) - (obst.getyPos());

					if (abs(colArriba) > abs(colAbajo)) {
						offsetY = colAbajo;
					} else {
						offsetY = colArriba;
					}

					if (abs(colDerecha) > abs(colIzquierda)) {
						offsetX = colIzquierda;
					} else {
						offsetX = colDerecha;
					}

//					if (abs(offsetX) < abs(offsetY)) {
//						bm.setxPos(bm.getxPos() - offsetX);
//					} else {
//						bm.setyPos(bm.getyPos() - offsetY);
//					}
					
					if (abs(offsetX) < abs(offsetY) || abs(offsetX) <= error) {
						bm.setxPos(bm.getxPos() - offsetX);
					} 
					
					if(abs(offsetY) < abs(offsetX) || abs(offsetY) <= error){
						bm.setyPos(bm.getyPos() - offsetY);
					}
				}
			}
		}

		mapa.getBombas().removeAll(aBorrar);
		mapa.getBombas().addAll(bombasExplotadas);
	}
}