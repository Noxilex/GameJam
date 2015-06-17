package levels;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Personnage;

public class Level2 extends Level{
	
	public Level2(Personnage p){
		super(p);
		
		File f = new File("img/bg2");
		try {
			bg = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
