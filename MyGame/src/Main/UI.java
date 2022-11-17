package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	public int commandNum=0;
	
	public UI(GamePanel gp) {
		this.gp=gp;
		
	}
	public void draw(Graphics2D g2) {
		this.g2=g2;
		
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		//Title state
		if(gp.gameState==gp.titleState) {
			drawTitleScreen();
		}
	}
	public void drawTitleScreen() {
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0,0,gp.ScreenWidth,gp.ScreenHeight);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,76f));
		String text="Game of Mustafa";
		int x=getXforCenteredText(text);
		int y=gp.TileSize*3;
		
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		x=gp.ScreenWidth/2-gp.TileSize*2;
		y+=gp.TileSize*2;
		g2.drawImage(gp.image,x,y,gp.TileSize*2,gp.TileSize*2,null);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,38f));
		
		text="New Game";
		x=getXforCenteredText(text)-gp.TileSize*2/2;
		y+=gp.TileSize*3.5;
		g2.drawString(text,x, y);
		
		if(commandNum==0) {
			g2.drawString(">", x-gp.TileSize, y);
		}
		
		text="Quit";
		x=getXforCenteredText(text);
		y+=gp.TileSize;
		g2.drawString(text,x-gp.TileSize, y);
		if(commandNum==1) {
			g2.drawString(">", x-gp.TileSize*2, y);
		}
		
	}
	public int getXforCenteredText(String text) {
		   int lenght=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		   int x=gp.ScreenWidth/2-lenght/2;
		   return x;
	 }

}
