package levels;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Mob;
import entities.Obstacle;
import entities.Personnage;
import entities.Porte;

public class Level3 extends Level{

	public Level3(Personnage p){
		super("Etage 3", p);
		
		File f = new File("img/bg3.png");
		try {
			bg = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		posBg = 0;
		
		Mob mob1 = new Mob(10, 200, sol, initSol);
		Mob mob2 = new Mob(10, 300, sol, initSol);
		Mob mob3 = new Mob(10, 400, sol, initSol);
		
		listeMob.add(mob1);
		listeMob.add(mob2);
		listeMob.add(mob3);
		
		Obstacle obs1 = new Obstacle(80, sol, 10, 80);
		
		listeObstacle.add(obs1);
		
		inPorte = new Porte("in", 325, sol, 120, 140);
		outPorte = new Porte("out", 1750, sol, 120, 140);
		
		p.setLocation(250, (int)(sol-p.getHeight()));
		
	}
	
}
