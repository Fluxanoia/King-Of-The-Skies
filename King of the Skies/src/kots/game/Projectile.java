package kots.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import kots.gamestate.GameStateManager;
import kots.main.GameFrame;

public class Projectile {

	private BufferedImage image;
	private Rectangle bounds;
	private int x, y;
	private boolean active;

	public Projectile(GameStateManager gsm, int x, int y) {

		this.x = x;
		this.y = y;

		image = gsm.getImageLoader().loadImage("/sprites/projectile.png");

	}

	public void init() {

		active = true;

	}

	public void draw(Graphics2D g) {

		g.drawImage(image, x, y, null);

	}

	public void update() {

		x += GameFrame.FRAMESPEED * 2;

		bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());

	}

	public Rectangle getBounds() {
		return bounds;
	}

	public boolean isActive() {
		return active;
	}

}
