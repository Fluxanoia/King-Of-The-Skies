package kots.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import kots.gamestate.GameStateManager;
import kots.main.GameFrame;

public class Notification {

	private GameStateManager gsm;
	private BufferedImage image;
	private String content;
	private int x, y;
	
	private boolean ended;
	
	private int yTol = 0;
	private int yChange = 1;
	
	public Notification(GameStateManager gsm, String content) {
		
		this.gsm = gsm;
		this.content = content;
		ended = false;
		image = gsm.getImageLoader().loadImage("/acheivement/button.png");
		x = GameFrame.WIDTH - image.getWidth();
		y = -image.getHeight();
		
	}
	
	public void update() {
		
		yTol += yChange;
		y += yChange;
		
		if (y > 0) {
			y = 0;
		}
		
		if (yTol >= 200) {
			yChange *= -1;
		}
		
		if (yTol <= -image.getHeight() - 5) {
			ended = true;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		g.setFont(gsm.getFont());
		g.setPaint(gsm.getColorScheme().getFont());
		
		g.drawImage(image, x, y, null);
		g.drawString(content, x + 3, y + 12);
		
	}
	
	public boolean isEnded() {
		return ended;
	}
	
}
