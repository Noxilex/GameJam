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
		
		File f = new File("img/bg1.png");
		try {
			bg = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		posBg = 0;
		
		//Création des monstres du haut
		for(int i = 0; i < 8; i++){
			listeMob.add(new Mob( "faible", "verticale", 800+i*100, sol-200, 40));
		}
		
		//Création des monstres du bas
		for(int i = 0; i < 8; i++){
			listeMob.add(new Mob( "faible", "verticale", 850+i*100, sol-40, 40));
		}
		
		Obstacle obs1 = new Obstacle(700, sol-90, 1000, 20);
		
		listeObstacle.add(obs1);
		
		inPorte = new Porte("in", 325, sol, 120, 140);
		outPorte = new Porte("out", 1750, sol, 120, 140);
		
		p.setLocation(250, (int)(sol-p.getHeight()));
		
	}
	
}
