package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import levels.Level1;
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
	
	private Personnage p= new Personnage("Noxilex", xInit, sol, taillePersonnage/2, taillePersonnage);
	
	private Level1 a = new Level1(p);
	
	//Coordonnées du background
	private int px = 0;
	
	private int limiteDroite = -1300;
	private int limiteGauche = 0;
	
	private boolean running = false;
	
	private Thread thread;
	
	private final int FPS = 60;
	private int vitesse = 300/FPS;
	
	private KeyMap m = new KeyMap();
	
	public Panel(){
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
		
		start();

	}
	
	public void addNotify(){
		
		super.addNotify();
		
		requestFocus();
	}
	
	private void start(){
		etat = 1;
		running = true;
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
		//TODO
		switch(etat){
			case(1):
				//On verifie que le carre n'a pas atteint le sol
				if(a.getHero().getY()+a.getHero().getHeight() >= a.getSol()) {
					enAir = false;
					tombe = false;
					hauteurPersonnage = 0;
				}
				
				//Si le carre est en l'air et qu'il tombe, on le fait tomber
				if(enAir && tombe) 
					a.getHero().setLocation((int)a.getHero().getX(), (int)a.getHero().getY()+6);
				
				//Droite et gauche sont en dehors du else pour permettre le air-movement
				
				if(gauche){
					if(a.getPosBg() <= limiteGauche && (int)a.getHero().getX() <= xInit-1)
						a.setPosImg(a.getPosBg()+vitesse);
					else if(x > 0)
						a.getHero().setLocation((int)a.getHero().getX()-vitesse, (int)a.getHero().getY());
					//x+=vitesse;
				}
					
				else if(droite){
					if(a.getPosBg() >= limiteDroite && (int)a.getHero().getX()>= xInit+1) 
						a.setPosImg(a.getPosBg()-vitesse);
					else if(x < 700-45)
						a.getHero().setLocation((int)a.getHero().getX()+vitesse, (int)a.getHero().getY());
				}
				
				if(haut && hauteurPersonnage < hauteurSaut && !tombe){
					System.out.println("saute");
					enAir = true;
					hauteurPersonnage += 6;
					a.getHero().setLocation((int)a.getHero().getX(), (int)a.getHero().getY()-6);
				}else{
					tombe = true;
				}
				//On peut appuyer sur bas pour redescendre au sol
				if(bas){
					enAir = false;
					tombe = false;
					a.getHero().setLocation((int)a.getHero().getX(), a.getSol());
				}
				break;
			case(2):
				break;
			case(3):
				break;
			case(4):
				break;
		}
	}
	
	private void render(){
		this.repaint();
	}
	
	
	public void playSound(){
		if(keyDown(KeyEvent.VK_UP) && a.getHero().getY()+a.getHero().getHeight() >= a.getSol()-10 && !tombe){
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
		
		if(etat != 0){
			switch(etat){
				case(1):
					//TODO Faire l'algo de lecture des levels pour l'affichage
					
					//Dessin du background
					g.drawImage(a.getBackground(), a.getPosBg(), 0, 2000, 500, null, null);
					
					//Dessin des obstacles
					for(Obstacle o : a.getListeObstacle()){
						g.drawRect((int)o.getX()+a.getPosBg(), (int)o.getY(), (int)o.getWidth(), (int)o.getHeight());
					}
					
					//Dessin des mobs
					for(Mob m : a.getListeMob()){
						g.drawRect((int)m.getX()+a.getPosBg(), (int)m.getY(), (int)m.getWidth(), (int)m.getHeight());
					}
					
					//Dessin des portes
					g.drawRect((int)a.getInPorte().getX()+a.getPosBg(), (int)a.getInPorte().getY(), (int)a.getInPorte().getWidth(), (int)a.getInPorte().getHeight());
					g.drawRect((int)a.getOutPorte().getX()+a.getPosBg(), (int)a.getOutPorte().getY(), (int)a.getOutPorte().getWidth(), (int)a.getOutPorte().getHeight());
					
					//g.drawRect(325+a.getPosBg(), a.getSol(), 120, 140);
					
					//g.fillRect(100, 420, 50, 5);
					break;
				case(2):
					break;
				case(3):
					break;
				case(4):
					break;
			}
			
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
						g.drawImage(right_fixe, (int)a.getHero().getX(), (int)a.getHero().getY(), (int)a.getHero().getHeight()/2, (int)a.getHero().getHeight(), null, null);
					}else if(imgActuelle == 1){
						g.drawImage(right_move1, (int)a.getHero().getX(), (int)a.getHero().getY(), (int)a.getHero().getHeight()/2, (int)a.getHero().getHeight(), null, null);
					}else if(imgActuelle == 3){	
						g.drawImage(right_move2, (int)a.getHero().getX(), (int)a.getHero().getY(), (int)a.getHero().getHeight()/2, (int)a.getHero().getHeight(), null, null);
					}
				}else{
					//TODO Image de Saut
					g.drawImage(right_move1,(int)a.getHero().getX(), (int)a.getHero().getY(), (int)a.getHero().getHeight()/2, (int)a.getHero().getHeight(), null, null);
				}
					
			} else if(lastKey == KeyEvent.VK_LEFT){
				if(!enAir){
					if(imgActuelle == 0 || imgActuelle == 2){
						g.drawImage(left_fixe, (int)a.getHero().getX(), (int)a.getHero().getY(), (int)a.getHero().getHeight()/2, (int)a.getHero().getHeight(), null, null);
					}else if(imgActuelle == 1){
						g.drawImage(left_move1,(int)a.getHero().getX(), (int)a.getHero().getY(), (int)a.getHero().getHeight()/2, (int)a.getHero().getHeight(), null, null);
					}else if(imgActuelle == 3){	
						g.drawImage(left_move2,(int)a.getHero().getX(), (int)a.getHero().getY(), (int)a.getHero().getHeight()/2, (int)a.getHero().getHeight(), null, null);
					}
				}else{
					//TODO Image de Saut
					
					g.drawImage(left_move1,(int)a.getHero().getX(), (int)a.getHero().getY(), (int)a.getHero().getHeight()/2, (int)a.getHero().getHeight(), null, null);
				}
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
			playSound();
			render();
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}	

	
