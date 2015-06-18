package miscellanous;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
 
public class SonAmbiance extends Thread 
{
    private URL url;
    private AudioClip sound;
  
    public SonAmbiance() 
    {
        url = this.getClass().getClassLoader().getResource("res/ambiance.wav");
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