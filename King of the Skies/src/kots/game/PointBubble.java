package kots.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import kots.gamestate.GameStateManager;
import kots.main.GameFrame;

public class PointBubble {

	private BufferedImage image;
	private AffineTransform affineTransform;
	private Rectangle bounds;
	private int points;
	private int x, y;
	private boolean active;

	private int rotationMod;
	private double rotation;

	private int hoverMod;
	private int hoverLeft;

	public PointBubble(GameStateManager gsm, int x, int y, int points) {

		this.points = points;

		active = true;

		image = gsm.getImageLoader().loadImage(
				"/sprites/points" + this.points + ".png");

		this.x = x;
		this.y = y;
		rotation = 0.0;
		rotationMod = -1;
		hoverMod = -1;
		hoverLeft = 10;
		AffineTransform af = new AffineTransform();
		af.translate(x, y);
		af.rotate(Math.toRadians(rotation));
		affineTransform = af;

	}

	public void draw(Graphics2D g) {

		g.setTransform(affineTransform);
		g.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, null);
		g.setTransform(new AffineTransform());

	}

	public void update() {

		this.x += (int) Math.floor(GameFrame.FRAMESPEED / 2);

		this.y += hoverMod;
		hoverLeft--;

		bounds = new Rectangle(x - (image.getWidth() / 2), y
				- (image.getHeight() / 2), image.getWidth(), image.getHeight());

		if (hoverLeft == 0) {

			hoverMod *= -1;
			hoverLeft = 10;

		}

		rotation += rotationMod;

		if (rotation > 15 || rotation < -15) {

			rotationMod *= -1;

		}

		AffineTransform af = new AffineTransform();
		af.translate(x, y);
		af.rotate(Math.toRadians(rotation));
		affineTransform = af;

		if (bounds.getMaxX() < 0) {

			active = false;

		}

	}

	public boolean isActive() {
		return active;
	}

	public int getPoints() {
		return points;
	}

	public Rectangle getBounds() {
		return bounds;
	}

}
