package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Personnage extends Rectangle{
	
	private BufferedImage img;
	
	private int vieMax;
	private int vie;
	private String nom;
	
	//Coordonnées
	private int x;
	private int y;
	
	private int degats;
	
	public Personnage(String nom, int x, int y, int largeur, int hauteur){
		super(x, y-hauteur, largeur, hauteur);
		this.nom = nom;
		vieMax = 100;
		vie = vieMax;
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

	public String getNom() {
		return nom;
	}
	
	public int getVieMax(){
		return vieMax;
	}
	
	public void subit(int degats){
		vie -= degats;
	}
	
	public int getVie(){
		return vie;
	}
}
