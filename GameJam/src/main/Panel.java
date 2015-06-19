package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import levels.Level;
import levels.Level1;
import levels.Level2;
import levels.Level3;
import levels.Level4;
import miscellanous.SonAmbiance;
import miscellanous.SonCoup;
import miscellanous.SonFight;
import miscellanous.SonSaut;
import entities.Mob;
import entities.Obstacle;
import entities.Personnage;



@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable{

	private BufferedImage bg1;

	/*
	private BufferedImage up_fixe;
	private BufferedImage up_move1;
	private BufferedImage up_move2;
	private BufferedImage down_fixe;
	private BufferedImage down_move1;
	private BufferedImage down_move2;
	*/
	
	private int etat = 0;
	
	private boolean droite;
	private boolean gauche;
	private boolean haut;
	private boolean bas;
	private boolean enAir = false;
	private boolean tombe = false;
	
	private SonFight sf = new SonFight();
	private SonSaut s = new SonSaut();
	private SonAmbiance sa = new SonAmbiance();
	
	private int taillePersonnage = 90;
	private int hauteurSaut = taillePersonnage+10;
	
	private int cpt = 0;
	private int imgActuelle = 0;
	private int hauteurPersonnage = 0;
	private int sol = 335;
	private int xInit = 700/2-taillePersonnage/2;
	
	private int x = xInit;
	private int y = sol;
	
	private int posYInitSaut;
	
	private Personnage p;
	
	private Level lvlActuel;
	private Level level1;
	private Level level2;
	private Level level3;
	private Level level4;
	
	private int limiteDroite = -1300;
	private int limiteGauche = 0;
	
	private boolean running = false;
	
	private Thread thread;
	
	public static final int FPS = 60;
	public static int vitesse = 300/FPS;
	
	private KeyMap m = new KeyMap();

	private boolean etageMonte;

	private boolean etageDescendu;
	
	public Panel(String nomJoueur){

		File f = null;
		try{
			f = new File("img/bg1.png");
			bg1 = ImageIO.read(f);
		}catch(Exception e){
			e.printStackTrace();
		}
		setPreferredSize(new Dimension(700, 500));
		addKeyListener(m);
		
		p= new Personnage(nomJoueur, xInit, sol, taillePersonnage/2, taillePersonnage);
		
		start();

	}
	
	public void addNotify(){
		
		super.addNotify();
		
		requestFocus();
	}
	
	private void resetLevels(){
		level1 = new Level1(p);
		level2 = new Level2(p);
		level3 = new Level3(p);
		level4 = new Level4(p);
		lvlActuel = level1;
	}
	
	private void start(){
		etat = 1;
		resetLevels();
		running = true;
		sa.jouerEnBoucle();
		thread = new Thread(this);
		thread.start();
	}
	
	private void handleControl(){
		droite = m.isDroite();
		gauche = m.isGauche();
		haut = m.isHaut();
		bas = m.isBas();
	}
	
	/**
	 * Met à jour le contenu des niveaux (Level)
	 */
	private void update(){
		if(etageMonte || etageDescendu){
			switch(etat){
				case(1):
					lvlActuel = level1;
					break;
				case(2):
					lvlActuel = level2;
					break;
				case(3):
					lvlActuel = level3;
					break;
				case(4):
					lvlActuel = level4;
					limiteDroite=0;
					limiteGauche=-1300;
					break;
				default:
					lvlActuel = level1;
			}
			if(etageMonte)
				lvlActuel.getHero().setLocation((int)(lvlActuel.getInPorte().getInitX()+lvlActuel.getInPorte().getWidth()+1), (int)lvlActuel.getInPorte().getY());
			else{
				lvlActuel.getHero().setLocation((int)(lvlActuel.getOutPorte().getX()-lvlActuel.getHero().getWidth()-1), (int)lvlActuel.getOutPorte().getY());
			}
			etageMonte = false;
			etageDescendu = false;
		}
		

		
		//On verifie que le carre n'a pas atteint le sol
		if(lvlActuel.getHero().getY()+lvlActuel.getHero().getHeight() >= lvlActuel.getSol()) {
			enAir = false;
			tombe = false;
			hauteurPersonnage = 0;
		}
			
			
		if(enAir && tombe) {
			lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY()+6);
		}
		
		if(lvlActuel.doObstaclesIntersects()){
			
			for(Obstacle obstacle : lvlActuel.listeObstaclesIntersects()){
				//Si le perso est au dessus de la plateforme, il marche dessus
				if((inside(lvlActuel.getHero().getX(), obstacle.getX(), obstacle.getX()+obstacle.getWidth())
							|| inside(lvlActuel.getHero().getX()+lvlActuel.getHero().getWidth(), obstacle.getX(), obstacle.getX()+obstacle.getWidth())
							|| (lvlActuel.getHero().getX() < obstacle.getX() && (lvlActuel.getHero().getX()+lvlActuel.getHero().getWidth()) > obstacle.getX()+obstacle.getWidth())) 
							&& lvlActuel.getHero().getY()<=obstacle.getY()-obstacle.getHeight()){
						lvlActuel.setSol((int)(obstacle.getY()));
				}else if(obstacle.getX()+obstacle.getWidth()/2>=lvlActuel.getHero().getX()+lvlActuel.getHero().getWidth()){
					lvlActuel.getHero().setLocation((int)(obstacle.getX()-lvlActuel.getHero().getWidth()-1), (int)lvlActuel.getHero().getY());
				}else if(obstacle.getX()+obstacle.getWidth()/2<lvlActuel.getHero().getX()){
					lvlActuel.getHero().setLocation((int)(obstacle.getX()+obstacle.getWidth()+1), (int)lvlActuel.getHero().getY());
				}
			}
		}else if(lvlActuel.getSol() != lvlActuel.getInitSol()){
			lvlActuel.setSol(lvlActuel.getInitSol());
			enAir = true;
		}
		//Droite et gauche sont en dehors du else pour permettre le air-movement
		
		if(gauche){
			if(lvlActuel.getPosBg() <= limiteGauche && (int)lvlActuel.getHero().getX() <= xInit-1)
				lvlActuel.setPosBg(lvlActuel.getPosBg()+vitesse);
			else if(lvlActuel.getHero().getX() > 0)
				lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX()-vitesse, (int)lvlActuel.getHero().getY());
			//x+=vitesse;
		}
			
		else if(droite){
			if(lvlActuel.getPosBg() >= limiteDroite && (int)lvlActuel.getHero().getX()>= xInit+1) 
				lvlActuel.setPosBg(lvlActuel.getPosBg()-vitesse);
			else if(lvlActuel.getHero().getX() < 700-45)
				lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX()+vitesse, (int)lvlActuel.getHero().getY());
		}
		
		if(!(enAir || tombe))
			posYInitSaut = (int)lvlActuel.getHero().getY();
		
		if(haut && posYInitSaut-hauteurPersonnage > posYInitSaut-hauteurSaut && !tombe){
			enAir = true;
			hauteurPersonnage += 6;
			lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY()-6);
		}else{
			tombe = true;
		}
		
		if(lvlActuel.doMobsIntersects()){
			//new SonCoup().jouer();
			
			for(Mob mob : lvlActuel.getListeMob()){
				lvlActuel.getHero().subit(mob.getDegats());
				if(mob.getX() < lvlActuel.getHero().getX()){
					lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX()+vitesse*mob.getDegats(), (int)lvlActuel.getHero().getY()-vitesse*mob.getDegats());
					enAir = true;
					tombe = true;
				}
				else{
					lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX()-vitesse*mob.getDegats(), (int)lvlActuel.getHero().getY()-vitesse*mob.getDegats());
					enAir = true;
					tombe = true;
				}
			}
		}
		
		//On peut appuyer sur bas pour redescendre au sol
		if(bas){
			enAir = true;
			tombe = true;
			
			if(lvlActuel.getHero().getY()<lvlActuel.getInitSol()-lvlActuel.getHero().getHeight())
				lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX(), (int)(lvlActuel.getHero().getY()+vitesse));
			
			//Si l'on appuie sur bas lorsqu'on est sur l'escalier de gauche, on descend d'un étage
			if(lvlActuel.intersectPorteDown() && etat > 1){
				//TODO Position d'arrivée dans le niveau
				etat--;
				etageDescendu = true;
				System.out.println("Porte Down");
			}
			//Si l'on appuie sur bas lorsqu'on est sur l'escalier de droite, on monte d'un étage
			if(lvlActuel.intersectPorteUp() && etat < 4){
				etat++;
				etageMonte = true;
				System.out.println("Porte Up");
			}
		}
		lvlActuel.update();
	}
	
	private boolean inside(double nb, double first, double last){
		return nb > first && nb < last;
	}
	
	private void render(){
		this.repaint();
	}
	
	public void playSound(){
		if(keyDown(KeyEvent.VK_UP) && level1.getHero().getY()+level1.getHero().getHeight() >= level1.getSol()-9 && !tombe){
			s.jouer();
		}
	}
	
	private boolean moving(){
		if(droite || gauche){
			return true;
		}else{
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	private boolean keyDown(){
		if(droite || gauche || haut || bas){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean keyDown(int nomTouche){
		if(nomTouche == KeyEvent.VK_LEFT){
			if(gauche)
				return true;
			else
				return false;
		} else if(nomTouche == KeyEvent.VK_RIGHT){
			if(droite)
				return true;
			else
				return false;
		} else if(nomTouche == KeyEvent.VK_UP){
			if(haut)
				return true;
			else
				return false;
		} else if(nomTouche == KeyEvent.VK_DOWN){
			if(bas)
				return true;
			else
				return false;
		} else{
			return false;
		}
	}

	/**
	 * Affiche le contenu des niveaux puis le personnage
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
					
		if(lvlActuel.getHero().isAlive()){
			//Dessin du background
			if(lvlActuel == level4) 
				g.drawImage(lvlActuel.getBackground(), 0, 0, 700, 500, null, null);
			else
				g.drawImage(lvlActuel.getBackground(), lvlActuel.getPosBg(), 0, 2000, 500, null, null);
					
			//Dessin du nom de l'étage
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), 70);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman", 1, 30));
			g.drawString(lvlActuel.getNomLevel(), getWidth()/3*2, 35);
			
			//Dessin des PVs
			g.setColor(Color.WHITE);
			g.drawRect(30-1, 25-1, 2*lvlActuel.getHero().getVieMax()+1, 20+2);
			g.setColor(Color.RED);
			g.fillRect(30, 25, 2*lvlActuel.getHero().getVie(), 20);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman", 1, 15));
			g.drawString(lvlActuel.getHero().getVie() + "/" + lvlActuel.getHero().getVieMax(), 30, 20);
			
			//Dessin des obstacles : Carres Bleus
			for(Obstacle o : lvlActuel.getListeObstacle()){
				g.setColor(Color.BLUE);
				g.fillRect((int)o.getX(), (int)o.getY(), (int)o.getWidth(), (int)o.getHeight());
			}
							
			//Dessin des mobs : Carres Rouges
			for(Mob m : lvlActuel.getListeMob()){
				g.setColor(Color.RED);
				g.fillRect((int)m.getX(), (int)m.getY(), (int)m.getWidth(), (int)m.getHeight());
				g.drawImage(m.getImg(), (int)m.getX()-(int)m.getWidth()/2, (int)m.getY()-(int)m.getHeight()/2, (int)m.getWidth()*2, (int)m.getHeight()*2, null, null);
			}
			
			//Dessin des portes : Carres Noir (vides)
			g.setColor(Color.BLACK);
			g.drawRect((int)lvlActuel.getInPorte().getX(), (int)lvlActuel.getInPorte().getY(), (int)lvlActuel.getInPorte().getWidth(), (int)lvlActuel.getInPorte().getHeight());
			g.drawRect((int)lvlActuel.getOutPorte().getX(), (int)lvlActuel.getOutPorte().getY(), (int)lvlActuel.getOutPorte().getWidth(), (int)lvlActuel.getOutPorte().getHeight());
							
			//g.drawRect(325+level1.getPosBg(), level1.getSol(), 120, 140);
							
			//g.fillRect(100, 420, 50, 5);
				
			if(moving()){
				cpt = (cpt+1)%11;
				if(cpt == 10)
					imgActuelle = (imgActuelle+1)%4;
				}else{
					imgActuelle = 0;
				}
				
				int lastKey = m.getLastDirection();
				
				if(lastKey == KeyEvent.VK_RIGHT){
					
				if(!enAir){
					if(imgActuelle == 0 || imgActuelle == 2){
						g.drawImage(lvlActuel.getHero().getRight_fixe(), (int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
					}else if(imgActuelle == 1){
						g.drawImage(lvlActuel.getHero().getRight_move1(), (int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
					}else if(imgActuelle == 3){	
						g.drawImage(lvlActuel.getHero().getRight_move2(), (int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
					}
				}else{
					//TODO Image de Saut droite
					g.drawImage(lvlActuel.getHero().getRight_move1(),(int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
				}
						
			} else if(lastKey == KeyEvent.VK_LEFT){
				if(!enAir){
					if(imgActuelle == 0 || imgActuelle == 2){
						g.drawImage(lvlActuel.getHero().getLeft_fixe(), (int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
					}else if(imgActuelle == 1){
						g.drawImage(lvlActuel.getHero().getLeft_move1(),(int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
					}else if(imgActuelle == 3){	
						g.drawImage(lvlActuel.getHero().getLeft_move2(),(int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
					}
				}else{
					//TODO Image de Saut gauche
						
						g.drawImage(lvlActuel.getHero().getLeft_move1(),(int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
				}
			}
		}else{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman", 1, 40));
			g.drawString("GAME OVER", getWidth()/4+50, getHeight()/2);
		}
	}
		/*g.setColor(Color.WHITE);
		g.fillRect(x, y, 50, 50);*/
	
	@Override
	public void run() {

		while(running){
			if(lvlActuel.getHero().isAlive()){
				handleControl();
				update();
				//playSound();
				render();
			}else{
				running = false;
			}
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		sa.arreter();
		render();
		sf.jouer();
	}
}	

	
