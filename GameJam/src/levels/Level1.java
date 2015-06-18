package levels;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Mob;
import entities.Obstacle;
import entities.Personnage;
import entities.Porte;


public class Level1 extends Level{
	
	public Level1(Personnage p){
		super("Rez-De-Chaussée", p);
		
		File f = new File("img/bg1.png");
		try {
			bg = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		posBg = 0;
		
		Obstacle obs1 = new Obstacle(500, sol-100, 200, 25);
		
		Obstacle ech1 = new Obstacle(1000, sol-50, 50, 25);
		Obstacle ech2 = new Obstacle(1000, sol-100, 50, 25);
		Obstacle ech3 = new Obstacle(1000, sol-150, 50, 25);
		Obstacle ech4 = new Obstacle(1000, sol-200, 50, 25);
		
		listeObstacle.add(obs1);
		
		listeObstacle.add(ech1);
		listeObstacle.add(ech2);
		listeObstacle.add(ech3);
		listeObstacle.add(ech4);
		
		inPorte = new Porte("in", 325, sol, 120, 140);
		outPorte = new Porte("out", 1750, sol, 120, 140);
		
		p.setLocation(250, (int)(sol-p.getHeight()));
		
	}
	
}
