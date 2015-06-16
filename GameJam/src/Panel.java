import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


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
	private boolean droite;
	private boolean gauche;
	private boolean haut;
	private boolean bas;
	private boolean enAir = false;
	private boolean tombe = false;
	
	private int cpt = 0;
	private int imgActuelle = 0;
	private int hauteurPersonnage = 0;
	private int sol = 335;
	private int x = 0;
	private int y = sol;
	private int taillePersonnage = 90;
	private int hauteurSaut = 50+taillePersonnage;
	
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
		
		setPreferredSize(new Dimension(500, 500));
		addKeyListener(m);
		
		start();
	}
	
	public void addNotify(){
		
		super.addNotify();
		
		requestFocus();
	}
	
	private void start(){
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
	
	private void update(){
		//On verifie que le carre n'a pas atteint le sol
		if(y >= sol) {
			enAir = false;
			tombe = false;
			hauteurPersonnage = 0;
		}
		
		//Si le carre est en l'air et qu'il tombe, on le fait tomber
		if(enAir && tombe) 
			y+=6;
		
		//Droite et gauche sont en dehors du else pour permettre le air-movement
		if(droite) 
			x+=vitesse;
		if(gauche) 
			x-=vitesse;

		if(haut && hauteurPersonnage < hauteurSaut && !tombe){
			enAir = true;
			hauteurPersonnage += 6;
			y = y - 6;
		}else{
			tombe = true;
		}
		//On peut appuyer sur bas pour redescendre au sol
		if(bas){
			enAir = false;
			y=sol;
		}
	}
	
	private void render(){
		this.repaint();
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
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bg1, 0, 0, 2000, 500, null, null);
		
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
					g.drawImage(right_fixe, x, y, taillePersonnage/2, taillePersonnage, null, null);
				}else if(imgActuelle == 1){
					g.drawImage(right_move1, x, y, taillePersonnage/2, taillePersonnage, null, null);
				}else if(imgActuelle == 3){	
					g.drawImage(right_move2, x, y, taillePersonnage/2, taillePersonnage, null, null);
				}
			}else{
				//TODO Image de Saut
				g.drawImage(right_move1, x, y, taillePersonnage/2, taillePersonnage, null, null);
			}
				
		} else if(lastKey == KeyEvent.VK_LEFT){
			if(!enAir){
				if(imgActuelle == 0 || imgActuelle == 2){
					g.drawImage(left_fixe, x, y, taillePersonnage/2, taillePersonnage, null, null);
				}else if(imgActuelle == 1){
					g.drawImage(left_move1, x, y, taillePersonnage/2, taillePersonnage, null, null);
				}else if(imgActuelle == 3){	
					g.drawImage(left_move2, x, y, taillePersonnage/2, taillePersonnage, null, null);
				}
			}else{
				//TODO Image de Saut
				g.drawImage(left_move1, x, y, taillePersonnage/2, taillePersonnage, null, null);
			}
		}
		/*g.setColor(Color.WHITE);
		g.fillRect(x, y, 50, 50);*/
	}
	
	@Override
	public void run() {
		while(running){
			handleControl();
			update();
			render();	
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}	

	
