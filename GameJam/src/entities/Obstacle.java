package entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Obstacle extends Rectangle{
	
	private int iniTx;
	private int iniTy;
	
	private BufferedImage poutre;
	
	public Obstacle(int x, int y, int largeur, int hauteur){
		super(x, y, largeur, hauteur);
		
		File f = null;
		try{
			
			f = new File("img/poutre.png");
			poutre = ImageIO.read(f);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
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

	public Image getImg() {
		// TODO Auto-generated method stub
		return poutre;
	}
	
}
