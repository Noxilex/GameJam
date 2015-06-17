package main;

import java.awt.Color;
import java.awt.Dimension;
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
import miscellanous.SonSaut;
import entities.Mob;
import entities.Obstacle;
import entities.Personnage;



@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable{

	private BufferedImage bg1;
	
	private BufferedImage left_fixe;
	private BufferedImage left_move1;
	private BufferedImage left_move2;
	
	private BufferedImage right_fixe;
	private BufferedImage right_move1;
	private BufferedImage right_move2;
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
	
	private SonSaut s = new SonSaut();
	
	private int taillePersonnage = 90;
	private int hauteurSaut = 50+taillePersonnage;
	
	private int cpt = 0;
	private int imgActuelle = 0;
	private int hauteurPersonnage = 0;
	private int sol = 335;
	private int xInit = 700/2-taillePersonnage/2;
	
	private int x = xInit;
	private int y = sol;
	
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
	
	private final int FPS = 60;
	private int vitesse = 300/FPS;
	
	private KeyMap m = new KeyMap();
	
	public Panel(String nomJoueur){
		File f = null;
		try{
			f = new File("img/bg1.png");
			bg1 = ImageIO.read(f);
			
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
	}
	
	private void start(){
		etat = 1;
		running = true;
		resetLevels();
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
				break;
			default:
				lvlActuel = level1;
		}
		
		//On verifie que le carre n'a pas atteint le sol
		if(lvlActuel.getHero().getY()+lvlActuel.getHero().getHeight() >= lvlActuel.getSol()) {
			enAir = false;
			tombe = false;
			hauteurPersonnage = 0;
		}
		
		//Si le carre est en l'air et qu'il tombe, on le fait tomber
		if(enAir && tombe) 
			lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY()+6);
		
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
		
		if(haut && hauteurPersonnage < hauteurSaut && !tombe){
			enAir = true;
			hauteurPersonnage += 6;
			lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY()-6);
		}else{
			tombe = true;
		}
		//On peut appuyer sur bas pour redescendre au sol
		if(bas){
			enAir = false;
			tombe = false;
			
			lvlActuel.getHero().setLocation((int)lvlActuel.getHero().getX(), (int)(lvlActuel.getSol()-lvlActuel.getHero().getHeight()));
			
			if(lvlActuel.doMobsIntersects()){
				System.out.println(lvlActuel.listeMobIntersects());
			}
			if(lvlActuel.doObstaclesIntersects()){
				System.out.println(lvlActuel.listeObstaclesIntersects());
			}
			//Si l'on appuie sur bas lorsqu'on est sur l'escalier de gauche, on descend d'un étage
			if(lvlActuel.intersectPorteDown() && etat > 1){
				//TODO Position d'arrivée dans le niveau
				etat--;
				System.out.println("Porte Down");
			}
			//Si l'on appuie sur bas lorsqu'on est sur l'escalier de droite, on monte d'un étage
			if(lvlActuel.intersectPorteUp() && etat < 4){
				etat++;
				System.out.println("Porte Up");
			}
		}
		
		if(moving()){
			lvlActuel.update();
		}
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
					
		//Dessin du background
		g.drawImage(lvlActuel.getBackground(), lvlActuel.getPosBg(), 0, 2000, 500, null, null);
						
		//Dessin des obstacles : Carres Bleus
		for(Obstacle o : lvlActuel.getListeObstacle()){
			g.setColor(Color.BLUE);
			g.fillRect((int)o.getX(), (int)o.getY(), (int)o.getWidth(), (int)o.getHeight());
		}
						
		//Dessin des mobs : Carres Rouges
		for(Mob m : lvlActuel.getListeMob()){
			g.setColor(Color.RED);
			g.fillRect((int)m.getX(), (int)m.getY(), (int)m.getWidth(), (int)m.getHeight());
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
			
			int lastKey = m.getLastEvent();
			
			if(lastKey == KeyEvent.VK_RIGHT){
				
			if(!enAir){
				if(imgActuelle == 0 || imgActuelle == 2){
					g.drawImage(right_fixe, (int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
				}else if(imgActuelle == 1){
					g.drawImage(right_move1, (int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
				}else if(imgActuelle == 3){	
					g.drawImage(right_move2, (int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
				}
			}else{
				//TODO Image de Saut droite
				g.drawImage(right_move1,(int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
			}
					
		} else if(lastKey == KeyEvent.VK_LEFT){
			if(!enAir){
				if(imgActuelle == 0 || imgActuelle == 2){
					g.drawImage(left_fixe, (int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
				}else if(imgActuelle == 1){
					g.drawImage(left_move1,(int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
				}else if(imgActuelle == 3){	
					g.drawImage(left_move2,(int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
				}
			}else{
				//TODO Image de Saut gauche
					
					g.drawImage(left_move1,(int)lvlActuel.getHero().getX(), (int)lvlActuel.getHero().getY(), (int)lvlActuel.getHero().getHeight()/2, (int)lvlActuel.getHero().getHeight(), null, null);
			}
		}
	}
		/*g.setColor(Color.WHITE);
		g.fillRect(x, y, 50, 50);*/
	
	@Override
	public void run() {

		while(running){
			handleControl();
			update();
			//playSound();
			render();
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}	

	
