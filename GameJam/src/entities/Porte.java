package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Porte extends Rectangle{
	
	private String type;
	
	//Coordonnées
	private int x;
	private int y;
	
	private int degats;
	
	public Porte(String type, int x, int y, int largeur, int hauteur){
		super(x, (y-hauteur), largeur, hauteur);
		this.type = type;
	}

	public int getDegats() {
		return degats;
	}

	public void setDegats(int degats) {
		this.degats = degats;
	}

	public String getType() {
		return type;
	}
	
}
