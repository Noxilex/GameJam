package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Obstacle extends Rectangle{
	
	private int iniTx;
	private int iniTy;
	
	public Obstacle(int x, int y, int largeur, int hauteur){
		super(x, y-hauteur, largeur, hauteur);
		iniTx = x;
		iniTy = y;
	}
	
	public Obstacle(int x, int y, BufferedImage img){
		super(x, y-img.getHeight(), img.getWidth(), img.getHeight());
		iniTx = x;
		iniTy = y;
	}
	
	public int getInitX() {
		return iniTx;
	}

	public int getInitY() {
		return iniTy;
	}
	
}
