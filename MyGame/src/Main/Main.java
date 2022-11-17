package Main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame window=new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//pencerede çarpı tuşuna bastığımızda programın bitmesini sağladık
		window.setResizable(false);
        window.setTitle("Game of mustafa");
        
        GamePanel gamePanel=new GamePanel();
        window.add(gamePanel);
        window.pack();// jpaneli jframe otomatik ayarlamayı sağlıyor
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread(); 
	}
		
	

}
