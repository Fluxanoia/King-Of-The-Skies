package kots.tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import kots.gamestate.GameStateManager;
import kots.main.GameFrame;

public class Background {

	private BufferedImage image;

	private double x;
	private double y;
	private double dx;
	private double dy;

	public Background(GameStateManager gsm, String s) {

		image = gsm.getImageLoader().loadImage(s);

	}

	public void setPosition(double x, double y) {

		this.x = x % GameFrame.WIDTH;
		this.y = y % GameFrame.HEIGHT;

	}

	public void setVector(double dx, double dy) {

		this.dx = dx;
		this.dy = dy;

	}

	public void update() {

		x += dx;
		y += dy;

		if (x < -GameFrame.WIDTH) {

			this.setPosition(0, y);

		}

	}

	public void draw(Graphics2D g) {

		g.drawImage(image, (int) x, (int) y, null);

		if (x < 0) {

			g.drawImage(image, (int) x + GameFrame.WIDTH, (int) y, null);

		}

		if (x > 0) {

			g.drawImage(image, (int) x - GameFrame.WIDTH, (int) y, null);

		}

	}

}
