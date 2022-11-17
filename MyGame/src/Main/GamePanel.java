package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Ates{
	public Ates(int Ates_x, int Ates_y) {
		this.Ates_x=Ates_x;
		this.Ates_y=Ates_y;
	}
	private int Ates_x;
	private int Ates_y;
	
	public int getAtesX() {
		return Ates_x;
	}
	public int getAtesY() {
		return Ates_y;
	}
	public void setAtesY(int deger) {
		this.Ates_y=deger;
	}
	public void setAtesX(int deger) {
		this.Ates_x=deger;
	}
}

public class GamePanel<image> extends JPanel implements Runnable,KeyListener{
	//Screen's setting
	final int originalTileSize=16;//20pixels
	final int scale=3;
	public int TileSize=originalTileSize*scale;//20x3=60
	public int MaxScreenCol=16;
	public int MaxScreenRow=12;
	public int ScreenWidth=MaxScreenCol*TileSize;
	public int ScreenHeight=MaxScreenRow*TileSize;
	//Font
	Font arial_40;
	//Time
	double playTime=0;
	DecimalFormat dFormat=new DecimalFormat("#0.0");
	
	private int harcanan_ates=0;
	public BufferedImage image;
	private ArrayList<Ates> atesler=new ArrayList<>();
	private int atesDirY=3;
	private int topX=0;
	private int topDirX=4;
	private int UzayGemisiX=0;
	private int UzayGemisiDirX=20;
	
	//KeyHandler
	public boolean rightPress=false,LeftPress=false;

	
	//Fps
	int FPS=60;
	
	//game state
	public int gameState;
	public final int titleState=0;
	public final int playState=1;
		
	//System
	Thread gameTheard;
	sound Sound=new sound();
	sound SoundEffec=new sound();
	UI ui=new UI(this);
	
	public boolean kontrolEt() {
		for(Ates ates:atesler) {
			if(new Rectangle(ates.getAtesX(),ates.getAtesY(),10,20).intersects(new Rectangle(topX,0,20,20))) {
				return true;
			}
		}
		return false;
		
	}
	
	public GamePanel() {
		arial_40=new Font("Arial",Font.PLAIN,30);
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/res/spaceShip.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(this);
	}

	@Override
	public void run() {
		double drawInterval=1000000000/FPS;
		double delta=0;
		long lastTime=System.nanoTime();
		long currentTime;
		long timer=0;
		int drawCount=0;
		while(gameTheard!=null) {
			currentTime=System.nanoTime();
			delta+=(currentTime-lastTime)/drawInterval;
			timer+=(currentTime-lastTime);
			lastTime=currentTime;
			if(delta>=1) {
				update();			
			    repaint();
			    delta--;
			    drawCount++;
			}
		
	}
}
	public void startGameThread() {
		gameTheard=new Thread(this);
		gameTheard.start();
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2=(Graphics2D) g;
		super.paintComponent(g);
		if(gameState==titleState) {
			ui.draw(g2);
		}
		else {
			g2.setColor(Color.red);
			g2.fillOval(topX,0,20,20);
			g2.drawImage(image,UzayGemisiX,490,image.getWidth()/3,image.getHeight()/3,this);
			for(Ates ates:atesler) {
				if(ates.getAtesY()<0) {
					atesler.remove(ates);
				}	
			}
			g2.setColor(Color.blue);
			for(Ates ates:atesler) {
				g2.fillRect(ates.getAtesX(),ates.getAtesY(),10,20);
			}
			g2.setColor(Color.white);
			playTime+=(double)1/60;
			g2.setFont(arial_40);
			String time=dFormat.format(playTime);
			
			//g2.drawString("Time:"+dFormat.format(playTime),TileSize*13,60);
			if(kontrolEt()) {
				playSoundEffect(1);
				gameTheard=null;
				String message="Kazandınız\n"+
				"Harcanan ateş:"+harcanan_ates+
				"\nGeçen süre:"+time+" saniye";
				JOptionPane.showMessageDialog(this, message);
				System.exit(0);
				
			}
		}


		
		g2.dispose();
	}
	public void setupGame() {
		gameState=titleState;
		playMusic(0);		
	}
	public void update() {

		if(LeftPress==true || rightPress==true) {
			if(LeftPress==true) {
				if(UzayGemisiX>=710) {
					UzayGemisiX=710;
				}
				else {
					UzayGemisiX+=UzayGemisiDirX;
				}
				
			}
			else if(rightPress==true) {
				if(UzayGemisiX<=0) {
					UzayGemisiX=0;
				}
				else {
					  UzayGemisiX-=UzayGemisiDirX;
			         }					
				}

				
		}
		topX+=topDirX;
		if(topX>=750) {
			topDirX=-topDirX;
    	}
		if(topX<=0) {
			topDirX=-topDirX;
		}
		for(Ates ates:atesler) {
			ates.setAtesY(ates.getAtesY()-atesDirY);
			
		}


	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		if(gameState==titleState) {
			if(code==KeyEvent.VK_W) {
				ui.commandNum--;
				if(ui.commandNum<0) {
					ui.commandNum=1;
				}
			}
			if(code==KeyEvent.VK_S) {
				ui.commandNum++;
				if(ui.commandNum>1) {
					ui.commandNum=0;
				}
			}
			if(code==KeyEvent.VK_ENTER) {
				if(ui.commandNum==0) {
					gameState=playState;
					playMusic(0);
				}
				if(ui.commandNum==1) {
					System.exit(0);
				}
			}
			
		}
		if(gameState==playState) {
			if(code==KeyEvent.VK_A) {
				rightPress=true;
			}

			if(code==KeyEvent.VK_D) {
				 LeftPress=true;
			}
			if(code==KeyEvent.VK_SPACE) {
				playSoundEffect(2);
				atesler.add(new Ates(UzayGemisiX+27,480));
				harcanan_ates++;
			}
		}
		



	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code=e.getKeyCode();
		if(code==KeyEvent.VK_A) {
			rightPress=false;
		}

		if(code==KeyEvent.VK_D) {
			 LeftPress=false;
		}
		

		
	}
	public void playMusic(int i) {
		Sound.setFile(i);
		Sound.play();
		Sound.loop();
	}
	public void stopMusic() {
		Sound.stop();
	}
	public void playSoundEffect(int i) {
		SoundEffec.setFile(i);
		SoundEffec.play();
	}

}
