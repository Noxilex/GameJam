package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import main.Panel;

public class Mob extends Rectangle{

	private BufferedImage img;
	
	private String type;
	
	protected String direction;
	
	protected int vie;
	
	//Coordonnées
	private int initX;
	private int initY;
	
	private int scrollInitX;
	private int scrollInitY;

	//Deplacement
	private int dep;
	private int depRonde;
	private int depMove;
	private int xDep = 0;
	
	private int taille;
	private int distAggro;
	
	//Distance de ses rondes (zone de déplacement)
	protected int ronde = 50;
	
	private int degats;

	private boolean movable;

	private BufferedImage plante;

	private int yDep;
	
	public Mob(String type, String direction, int x, int y, int ronde){
		super(x, y-0, 0, 0);
		
		File f = null;
		try{
			
			f = new File("img/plante.png");
			plante = ImageIO.read(f);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		movable = true;
		
		if(type == "faible")
			taille = 20;
		else if(type == "moyen")
			taille = 30;
		else if(type == "difficile")
			taille = 40;
		else
			taille = 10;
		
		super.height = taille;
		super.width = taille;
		super.setLocation(x, y-height);
		
		initX = x;
		initY = y;
		this.direction = direction;
		this.type = type;
		this.ronde = ronde;
		
		if(type == "faible"){
			degats = 3;
			dep = Panel.vitesse/2;
			depMove = Panel.vitesse/2;
			depRonde = Panel.vitesse/2;
			distAggro = 50;
		}
		else if(type == "moyen"){
			degats = 4;
			dep = Panel.vitesse/3*2;
			depMove = Panel.vitesse/3*2;
			depRonde = Panel.vitesse/3*2;
			distAggro = 100;
		}
		else if(type == "difficile"){
			degats = 5;
			dep = Panel.vitesse/4*3;
			depMove = Panel.vitesse/4*3;
			depRonde = Panel.vitesse/4*3;
			distAggro = 200;
		}
		else
			degats = 0;
	}
	
	public Mob(String type, int x, int y, int largeur, int hauteur){
		super(x, y-hauteur, largeur, hauteur);
		
		File f = null;
		try{
			
			f = new File("img/plante.png");
			plante = ImageIO.read(f);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		initX = x;
		initY = y;
		this.type = type;
		direction = "absolue";
		
		ronde = 50;
		vie = 40;
		
		if(type == "faible"){
			degats = 3;
			dep = Panel.vitesse/2;
			depMove = Panel.vitesse/2;
			depRonde = Panel.vitesse/2;
			distAggro = 50;
		}
		else if(type == "moyen"){
			degats = 6;
			dep = Panel.vitesse/3*2;
			depMove = Panel.vitesse/3*2;
			depRonde = Panel.vitesse/3*2;
			distAggro = 100;
		}
		else if(type == "difficile"){
			degats = 15;
			dep = Panel.vitesse/4*3;
			depMove = Panel.vitesse/4*3;
			depRonde = Panel.vitesse/4*3;
			distAggro = 200;
		}
		else if(type == "boss"){
			degats = 20;
			dep = Panel.vitesse/3*2;
			depMove = Panel.vitesse/3*2;
			depRonde = Panel.vitesse/3*2;
			distAggro = 360;
		}
		else
			degats = 0;
	}
	
	public Mob(String type, int x, int y, BufferedImage img){
		super(x, y-img.getHeight(), img.getWidth(), img.getHeight());
		
		File f = null;
		try{
			
			f = new File("img/plante.png");
			plante = ImageIO.read(f);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.type = type;
		initX = x;
		initY = y;
		
		scrollInitX = x;
		scrollInitY = y;
		
		if(type == "faible"){
			degats = 3;
			dep = Panel.vitesse/2;
			depMove = Panel.vitesse/2;
			depRonde = Panel.vitesse/2;
			distAggro = 50;
		}
		else if(type == "moyen"){
			degats = 6;
			dep = Panel.vitesse/3*2;
			depMove = Panel.vitesse/3*2;
			depRonde = Panel.vitesse/3*2;
			distAggro = 100;
		}
		else if(type == "difficile"){
			degats = 10;
			dep = Panel.vitesse/4*3;
			depMove = Panel.vitesse/4*3;
			depRonde = Panel.vitesse/4*3;
			distAggro = 200;
		}
		else
			degats = 0;
	}

	public Mob(int degats, int x, int y, int largeur) {
		// TODO Auto-generated constructor stub
		File f = null;
		try{
			
			f = new File("img/plante.png");
			plante = ImageIO.read(f);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		movable = false;
	}

	public boolean isPlayerNear(Personnage p){
		if(p.getX() >= getX()-distAggro && p.getX() <= getX()+distAggro){
			return true;
		}else{
			return false;
		}
	}

	public int getDegats() {
		return degats;
	}

	public void setDegats(int degats) {
		this.degats = degats;
	}

	public BufferedImage getImg() {
		return plante;
	}

	public String getType() {
		return type;
	}

	public int getInitX() {
		return initX;
	}

	public int getInitY() {
		return initY;
	}
	
	public int getMove(){
		return xDep;
	}
	
	public int getDistAggro(){
		return distAggro;
	}
	
	public int getScrollInitX() {
		return scrollInitX;
	}

	public void setScrollInitX(int scrollInitX) {
		this.scrollInitX = scrollInitX;
	}
	
	public void ronde(){
		if(direction == "horizontale"){
			if(getX() <= scrollInitX-ronde){
				depRonde = Math.abs(depRonde);
			}else if (getX() >= scrollInitX+ronde){
				depRonde = Math.abs(depRonde)*(-1);
			}
			xDep += depRonde;
			setLocation((int)getScrollInitX()+getMove(), (int)getY());
		}else if(direction == "verticale"){
			if(getY() <= initY-ronde){
				depRonde = Math.abs(depRonde);
			}else if (getY() >= initY+ronde){
				depRonde = Math.abs(depRonde)*(-1);
			}
			xDep += depRonde;
			setLocation((int)getScrollInitX(), initY+getMove());
		}else if(direction == "absolue"){
			if(getX() <= scrollInitX-ronde){
				depRonde = Math.abs(depRonde);
			}else if (getX() >= scrollInitX+ronde){
				depRonde = Math.abs(depRonde)*(-1);
			}
			xDep += depRonde;
			if(getY() <= initY-ronde){
				depRonde = Math.abs(depRonde);
			}else if (getY() >= initY+ronde){
				depRonde = Math.abs(depRonde)*(-1);
			}
			yDep += depRonde;
			setLocation((int)getScrollInitX()+xDep, initY+yDep);
		}
		
		
	}

	public boolean isInRonde(){
		if(getX() > scrollInitX-ronde && getX() < scrollInitX+ronde){
			return true;
		}else{
			return false;
		}
	}
	
	public void move(Personnage p){
		if(getX()< p.getX()){
			xDep += depMove;
		}else{
			xDep -= depMove;
		}
		
		if(getY()< p.getY()){
			yDep ++;
		}else if(getY()> p.getY()){
			yDep --;
		}else{
			
		}
		setLocation((int)getScrollInitX()+xDep, initY+yDep);
	}

	public boolean isMovable() {
		// TODO Auto-generated method stub
		return movable;
	}
	
	public void subit(int degats){
		vie -= degats;
	}
	
	public boolean isAlive(){
		return vie > 0;
	}
}
