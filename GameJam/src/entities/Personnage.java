package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Personnage extends Rectangle{
	
	private BufferedImage left_fixe;
	private BufferedImage left_move1;
	private BufferedImage left_move2;
	
	private BufferedImage right_fixe;
	private BufferedImage right_move1;
	private BufferedImage right_move2;
	
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
		
		File f = null;
		try{
			
			f = new File("img/Tera/left_fixe.png");
			left_fixe = ImageIO.read(f);
			f = new File("img/Tera/left_move1.png");
			left_move1 = ImageIO.read(f);
			f = new File("img/Tera/left_move2.png");
			left_move2 = ImageIO.read(f);
			
			f = new File("img/Tera/right_fixe.png");
			right_fixe = ImageIO.read(f);
			f = new File("img/Tera/right_move1.png");
			right_move1 = ImageIO.read(f);
			f = new File("img/Tera/right_move2.png");
			right_move2 = ImageIO.read(f);
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}

	public int getDegats() {
		return degats;
	}

	public void setDegats(int degats) {
		this.degats = degats;
	}

	public BufferedImage getLeft_fixe() {
		return left_fixe;
	}

	public BufferedImage getLeft_move1() {
		return left_move1;
	}

	public BufferedImage getLeft_move2() {
		return left_move2;
	}

	public BufferedImage getRight_fixe() {
		return right_fixe;
	}

	public BufferedImage getRight_move1() {
		return right_move1;
	}

	public BufferedImage getRight_move2() {
		return right_move2;
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

	public boolean isAlive() {
		// TODO Auto-generated method stub
		return vie>0;
	}
}
