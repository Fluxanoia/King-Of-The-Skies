package kots.tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import kots.main.GameFrame;

public class Tile {

	private BufferedImage image;
	private BufferedImage background;
	private int x, y;
	
	public Tile(BufferedImage image, BufferedImage background, int x, int y) {
		
		this.image = image;
		this.background = background;
		this.x = x;
		this.y = y;
		
	}
	
	public void update() {
		
		x += GameFrame.FRAMESPEED;
		
	}
	
	public void draw(Graphics2D g) {
		
		if (background != null) {
			
			g.drawImage(background, x, y, null);
			
		}
		
		g.drawImage(image, x, y, null);
		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
