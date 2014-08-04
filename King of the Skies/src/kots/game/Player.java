package kots.game;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import kots.gamestate.GameStateManager;
import kots.main.GameFrame;

public class Player {

	public static final int ABILITYMAX = 3;

	private AffineTransform affineTransform;
	private BufferedImage image;
	private int x, y;
	private double speedX, speedY;
	private double rotation;
	private int toRotate;

	private boolean outofbounds = false;

	public Player(GameStateManager gsm, int x, int y) {

		image = gsm.getImageLoader().loadImage("/sprites/player.png");

		this.x = x;
		this.y = y;
		speedX = 0;
		speedY = 0;
		rotation = 0.0;
		AffineTransform af = new AffineTransform();
		af.translate(x, y);
		af.rotate(Math.toRadians(rotation));
		affineTransform = af;

	}

	public void update() {

		x += (int) Math.ceil(speedX);
		y += (int) Math.ceil(speedY);

		AffineTransform af = new AffineTransform();
		af.translate(x, y);
		af.rotate(Math.toRadians(rotation));
		affineTransform = af;

		rotation = rotation % 360;

		int quickRot = 0;

		if (toRotate > 360) {

			quickRot = toRotate - 360;

		}

		rotation += quickRot;

		for (int i = 0; i < 20; i++) {

			if (toRotate > 0) {

				rotation++;
				toRotate--;

			} else {

				while (rotation != 0) {

					rotation++;
					rotation = rotation % 360;

				}

				break;

			}

		}

		if (speedY < 3) {

			speedY += 0.5;

		}

		Rectangle r = new Rectangle(x - (image.getWidth() / 2), y
				- (image.getHeight() / 2), image.getWidth(), image.getHeight());

		if (r.getMinY() > GameFrame.HEIGHT) {

			outofbounds = true;

		}

		if (r.getMaxY() < 0) {

			speedY = 3;

		}

	}

	public void jump() {

		speedY = -8;
		toRotate += 360;

	}

	public boolean isOutOfBounds() {

		return outofbounds;

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

	public AffineTransform getTransform() {

		return affineTransform;

	}

}
