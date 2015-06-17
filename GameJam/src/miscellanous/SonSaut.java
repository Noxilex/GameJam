package miscellanous;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
 
public class SonSaut extends Thread 
{
    private URL url;
    private AudioClip sound;
  
    public SonSaut() 
    {
        url = this.getClass().getClassLoader().getResource("res/boing.wav");
        System.out.println(url);
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