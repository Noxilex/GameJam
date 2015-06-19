package levels;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Mob;
import entities.Obstacle;
import entities.Personnage;
import entities.Porte;

public class Level4 extends Level {

	public Level4(Personnage p){
		super("BOSS FIGHT", p);
		
		File f = new File("img/bg4.png");
		try {
			bg = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		posBg = 0;
		
		int largMob = 80;
		int hautMob = 80;
		
		Mob mob1 = new Mob("boss", 300, sol-270, largMob, hautMob);
		
		listeMob.add(mob1);
		
		Obstacle ech1 = new Obstacle(0, sol-150, 60, 20);
		Obstacle ech2 = new Obstacle(0, sol-250, 60, 20);
		Obstacle ech3 = new Obstacle(640, sol-150, 60, 20);
		Obstacle ech4 = new Obstacle(640, sol-250, 60, 20);
		Obstacle ech5 = new Obstacle(210, sol-270, 60, 20);
		Obstacle ech6 = new Obstacle(430, sol-270, 60, 20);
		
		listeObstacle.add(ech1);
		listeObstacle.add(ech2);
		listeObstacle.add(ech3);
		listeObstacle.add(ech4);
		listeObstacle.add(ech5);
		listeObstacle.add(ech6);
		
		inPorte = new Porte("in", 290, sol, 120, 140);
		
		p.setLocation(250, (int)(sol-p.getHeight()));
		
	}
}
