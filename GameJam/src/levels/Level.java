package levels;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import entities.Mob;
import entities.Obstacle;
import entities.Personnage;
import entities.Porte;

public class Level {

	protected int sol = 420;
	protected BufferedImage bg;
	protected int posBg;
	//Fait
	protected Personnage hero;
	//
	protected List<Mob> listeMob;
	protected List<Obstacle> listeObstacle;
	protected Porte inPorte;
	protected Porte outPorte;
	
	public Level(Personnage p){
		hero = p;
		listeMob = new ArrayList<Mob>();
		listeObstacle = new ArrayList<Obstacle>();
	}
	
	public BufferedImage getBackground(){
		return bg;
	}
	
	public List<Mob> getListeMob(){
		return listeMob;
	}
	
	public List<Obstacle> getListeObstacle(){
		return listeObstacle;
	}
	
	public Porte getInPorte(){
		return inPorte;
	}
	
	public Porte getOutPorte(){
		return outPorte;
	}
	
	public int getSol(){
		return sol;
	}
	
	public int getPosBg() {
		return posBg;
	}

	public void setPosImg(int posBg) {
		this.posBg = posBg;
	}
	
	public Personnage getHero(){
		return hero;
	}
	
}
