package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Obstacle extends Rectangle{
	
	public Obstacle(int x, int y, int largeur, int hauteur){
		super(x, y-hauteur, largeur, hauteur);
	}
	
	public Obstacle(int x, int y, BufferedImage img){
		super(x, y-img.getHeight(), img.getWidth(), img.getHeight());
	}
	
}
