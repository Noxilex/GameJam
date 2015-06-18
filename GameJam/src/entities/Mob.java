package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Panel;

public class Mob extends Rectangle{

	private BufferedImage img;
	
	private String type;
	
	//Coordonnées
	private int iniTx;
	private int iniTy;
	
	//Deplacement
	private int xDep = 0;
	
	private int degats;
	
	public Mob(String type, int x, int y, int largeur, int hauteur){
		super(x, y-hauteur, largeur, hauteur);
		iniTx = x;
		iniTy = y;
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
		iniTx = x;
		iniTy = y;
		
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

	public int getInitX() {
		return iniTx;
	}

	public int getInitY() {
		return iniTy;
	}
	
	public int getMove(){
		return xDep;
	}
	
	public void move(Personnage p){
		if(getX() < p.getX()){
			xDep += Panel.vitesse/2;
		}else{
			xDep -= Panel.vitesse/2;
		}
	}
}
