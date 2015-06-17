package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Mob extends Rectangle{

	private BufferedImage img;
	
	private String type;
	
	//Coordonnées
	private int x;
	private int y;
	
	private int degats;
	
	public Mob(String type, int x, int y, int largeur, int hauteur){
		super(x, y-hauteur, largeur, hauteur);
		this.type = type;
		
		if(type == "faible")
			degats = 3;
		else if(type == "moyen")
			degats = 4;
		else if(type == "difficile")
			degats = 4;
		else
			degats = 0;
	}
	
	public Mob(String type, int x, int y, BufferedImage img){
		super(x, y-img.getHeight(), img.getWidth(), img.getHeight());
		this.type = type;
		
		if(type == "faible")
			degats = 3;
		else if(type == "moyen")
			degats = 4;
		else if(type == "difficile")
			degats = 4;
		else
			degats = 0;
	}

	public int getDegats() {
		return degats;
	}

	public void setDegats(int degats) {
		this.degats = degats;
	}

	public BufferedImage getImg() {
		return img;
	}

	public String getType() {
		return type;
	}
	
}
