package miscellanous;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
 
public class SonFight extends Thread 
{
    private URL url;
    private AudioClip sound;
  
    public SonFight() 
    {
        url = this.getClass().getClassLoader().getResource("res/fight_music.wav");
        sound = Applet.newAudioClip(url);
    }
     
    public void jouer() 
    {
        sound.play();
    }
     
    public void jouerEnBoucle() 
    {
        sound.loop();
    }
     
    public void arreter() 
    {
        sound.stop();
    }
}