package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Porte extends Rectangle{
	
	private String type;
	
	//Coordonnées
	private int initX;
	private int initY;

	private boolean enabled;
	
	private int degats;
	
	public Porte(String type, int x, int y, int largeur, int hauteur){
		super(x, (y-hauteur), largeur, hauteur);
		initX = x;
		initY = y;
		this.enabled = true;
		this.type = type;
	}

	public Porte(String type, int x, int y, int largeur, int hauteur, boolean enabled){
		this(type, x, y, largeur, hauteur);
		this.setEnabled(enabled);
	}

	public void setEnabled(boolean isEnabled){
		this.enabled = isEnabled;
	}

	public boolean isEnabled(){
		return this.enabled;
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

	public int getInitX() {
		return initX;
	}

	public int getInitY() {
		return initY;
	}
	
}
