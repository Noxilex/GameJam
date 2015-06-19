package levels;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Mob;
import entities.Obstacle;
import entities.Personnage;
import entities.Porte;

public class Level2 extends Level{
	
	public Level2(Personnage p){
		super("Etage 2", p);
		
		File f = new File("img/bg2.png");
		try {
			bg = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		posBg = 0;
		
		Mob mob1 = new Mob( "faible", "verticale", 600, sol-80, 80);
		Mob mob2 = new Mob( "faible", "verticale", 700, sol-80, 80);
		Mob mob3 = new Mob( "faible", "verticale", 800, sol-80, 80);
		Mob mob4 = new Mob( "faible", "verticale", 900, sol-80, 80);
		
		listeMob.add(mob1);
		listeMob.add(mob2);
		listeMob.add(mob3);
		listeMob.add(mob4);
		
		Obstacle obs1 = new Obstacle(1100, sol-110, (int)p.getWidth(), 20);
		Obstacle obs2 = new Obstacle(1300, sol-140, (int)p.getWidth(), 20);
		Obstacle obs4 = new Obstacle(1500, sol-170, (int)p.getWidth(), 20);
		Obstacle obs5 = new Obstacle(1750, sol-210, 170, 20);
		
		listeObstacle.add(obs1);
		listeObstacle.add(obs2);
		listeObstacle.add(obs4);
		listeObstacle.add(obs5);
		
		inPorte = new Porte("in", 325, sol, 120, 140);
		outPorte = new Porte("out", 1800, sol-210, 120, 140);
		
		p.setLocation(250, (int)(sol-p.getHeight()));
		
	}
	
}
