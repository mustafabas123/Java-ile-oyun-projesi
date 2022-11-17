package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class sound {
	Clip clip;
	URL soundURL[]=new URL[10];	
	public sound() {
		soundURL[0]=getClass().getResource("/sounds/metin2Sound.wav");
		soundURL[1]=getClass().getResource("/sounds/fanfere.wav");
		soundURL[2]=getClass().getResource("/sounds/silah-sesi.wav");
		
	}
	public void setFile(int i) {
		
		try {
			AudioInputStream aui=AudioSystem.getAudioInputStream(soundURL[i]);
			clip=AudioSystem.getClip();
			clip.open(aui);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}

}
