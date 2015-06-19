package levels;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.ZoneView;

import entities.Mob;
import entities.Obstacle;
import entities.Personnage;
import entities.Porte;

public class Level {

	protected final int initSol = 423;
	protected int sol = 423;
	protected BufferedImage bg;
	protected int posBg;
	protected Personnage hero;
	protected List<Mob> listeMob;
	protected List<Obstacle> listeObstacle;
	protected Porte inPorte;
	protected Porte outPorte;
	protected String nomLevel;
	
	public Level(String nomLevel, Personnage p){
		this.nomLevel = nomLevel;
		hero = p;
		listeMob = new ArrayList<Mob>();
		listeObstacle = new ArrayList<Obstacle>();
	}
	
	/**
	 * Met à jour la position des élément du niveau (en fonction du background)
	 */
	public void update(){
		for(Mob mob: listeMob){
			mob.setScrollInitX((int)mob.getInitX()+posBg);

			if(mob.isAlive()){
				if(mob.isPlayerNear(hero)){
					System.out.println("Chase");
					mob.move(hero);
				}else{
					System.out.println("Ronde");
					mob.ronde();
				}
			}else if(mob.isMovable() && !mob.isAlive()){
				mob.ronde();
			}
		}
		for(Obstacle obstacle: listeObstacle){
			obstacle.setLocation((int)(obstacle.getInitX())+posBg, (int)obstacle.getY());
		}

		inPorte.setLocation((int)(inPorte.getInitX())+posBg, (int)inPorte.getY());
		outPorte.setLocation((int)(outPorte.getInitX())+posBg, (int)outPorte.getY());
	}
	
	public boolean intersectPorteDown(){
		if(hero.intersects(inPorte)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean intersectPorteUp(){
		if(hero.intersects(outPorte)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean doMobsIntersects(){
		if(listeMobIntersects().isEmpty())
			return false;
		else
			return true;
	}
	
	public List<Mob> listeMobIntersects(){
		List<Mob> mobs = new ArrayList<Mob>();
		for(Mob mob: getListeMob()){
			if(hero.intersects(mob)){
				mobs.add(mob);
			}
		}
		return mobs;
	}
	
	public boolean doObstaclesIntersects(){
		if(listeObstaclesIntersects().isEmpty())
			return false;
		else
			return true;
	}
	
	public List<Obstacle> listeObstaclesIntersects(){
		List<Obstacle> obstacles = new ArrayList<Obstacle>();
		for(Obstacle obstacle: getListeObstacle()){
			if(hero.intersects(obstacle)){
				obstacles.add(obstacle);
			}
		}
		return obstacles;
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

	public void setPosBg(int posBg) {
		this.posBg = posBg;
	}
	
	public Personnage getHero(){
		return hero;
	}
	
	public String getNomLevel(){
		return nomLevel;
	}

	public void setSol(int sol) {
		// TODO Auto-generated method stub
		this.sol = sol;
	}
	
	public int getInitSol() {
		return initSol;
	}
	
}
