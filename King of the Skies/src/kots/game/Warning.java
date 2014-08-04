package kots.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import kots.gamestate.GameStateManager;

public class Warning {

	private BufferedImage image;
	private boolean active;
	private int x, y;
	private int alive = 300;
	private Object incoming;
	
	private int yChange = 1;
	private int yTol = 0;
	
	public Warning(GameStateManager gsm, int x, int y, Object o) {
		
		this.x = x;
		this.y = y;
		incoming = o;
		active = true;
		
		try {
			
			image = gsm.getImageLoader().loadImage("/sprites/warning.png");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, x - (image.getWidth() / 2), y - (image.getHeight() / 2), null);
		
	}
	
	public void update() {
		
		y += yChange;
		yTol += yChange;
		
		if (yTol > 9 || yTol < -9) {
			
			yChange *= -1;
			
		}
		
		if (alive == 0) {
			
			active = false;
			
		}
		
		alive--;
		
	}
	
	public Object getIncoming() {
		return incoming;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
}
