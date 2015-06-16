import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyMap implements KeyListener{

	private boolean droite = false;
	private boolean gauche = false;
	private boolean haut = false;
	private boolean bas = false;
	private int lastDirection = KeyEvent.VK_RIGHT;

	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT){
			gauche = true;
			lastDirection = key;
		}
		else if(key == KeyEvent.VK_RIGHT){
			droite = true;
			lastDirection = key;
		}
		else if(key == KeyEvent.VK_UP){
			haut= true;
		}
		else if(key == KeyEvent.VK_DOWN){
			bas= true;
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT)
			gauche = false;
		else if(key == KeyEvent.VK_RIGHT)
			droite = false;
		else if(key == KeyEvent.VK_UP)
			haut= false;
		else if(key == KeyEvent.VK_DOWN)
			bas= false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isGauche(){
		return gauche;
	}
	
	public boolean isDroite(){
		return droite;
	}
	
	public boolean isHaut(){
		return haut;
	}
	
	public boolean isBas(){
		return bas;
	}
	
	public int getLastEvent(){
		return lastDirection;
	}

}
