package kots.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import kots.gamestate.GameStateManager;
import kots.gamestate.LevelState;
import kots.main.PreferenceManager;

public class PowerManager {

	public static final int COOLDOWNRESET = 3000;

	private GameStateManager gsm;

	private BufferedImage image;
	private int cooldown;

	private Dimension zoomSize;
	private int zoomTolerance;
	private int zoomChange;

	public PowerManager(GameStateManager gsm) {

		this.gsm = gsm;

		zoomSize = new Dimension(LevelState.TILESIZE, LevelState.TILESIZE);
		zoomTolerance = 0;
		zoomChange = -1;

		cooldown = 0;

		image = gsm.getImageLoader().loadImage("/hud/groundsmash.png");

	}

	public void reset() {
	}

	public void draw(Graphics2D g) {

		int x = 5;
		int y = 205;
		Rectangle r = new Rectangle(x, y, image.getWidth() + 10,
				image.getHeight() + 10);

		g.setPaint(gsm.getColorScheme().getInner());
		g.fill(r);
		g.setPaint(gsm.getColorScheme().getBorder());
		g.draw(r);

		double percent = (double) cooldown / COOLDOWNRESET;
		int coolChange = (int) Math.ceil((double) image.getWidth() * percent);

		if (coolChange > 0) {

			x = 10;
			y = 210;
			g.drawImage(image, x, y, null);

			int imageBottom = y + image.getHeight();
			y += image.getHeight() - coolChange;
			g.setPaint(new Color(0, 0, 0, 80));
			r = new Rectangle(x, y, image.getWidth(), imageBottom - y);
			g.fill(r);

		} else {

			x = 10 + (zoomTolerance / 2);
			y = 210 + (zoomTolerance / 2);

			g.drawImage(image, x, y, (int) zoomSize.getWidth(),
					(int) zoomSize.getHeight(), null);

		}

	}

	public void update() {

		zoomTolerance += zoomChange;
		zoomSize = new Dimension(LevelState.TILESIZE + (zoomTolerance * -1),
				LevelState.TILESIZE + (zoomTolerance * -1));

		if (zoomTolerance == -5 || zoomTolerance == 5) {

			zoomChange *= -1;

		}

		if (cooldown > 0) {

			cooldown--;

		}

	}

	public void keyPressed(int k) {

	}

	public void keyReleased(int k) {

		switch (k) {

		case KeyEvent.VK_ENTER:

			if (cooldown == 0) {

				((LevelState) gsm.getStates().get(GameStateManager.LEVELSTATE))
						.smash();
				gsm.getAudioPlayer().playAudio("explosion.wav", false, 1);
				cooldown = COOLDOWNRESET
						- (500 * Integer.valueOf(gsm.getPreferenceManager()
								.get(PreferenceManager.ABILITY2)));

			}

			break;

		}

	}
}
