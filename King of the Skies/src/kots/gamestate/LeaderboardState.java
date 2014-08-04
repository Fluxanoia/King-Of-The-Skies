package kots.gamestate;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;

import kots.main.GameFrame;
import kots.main.PreferenceManager;
import kots.tiles.Background;

public class LeaderboardState extends GameState {

	private Background bg;

	private int currentChoice = 0;
	private String[] options = { "Back" };
	private int slide;

	public LeaderboardState(GameStateManager gsm) {

		super(gsm);

		bg = new Background(gsm, "/backgrounds/clear.png");

		slide = 0;

	}

	public void init() {
	}

	public void update() {

		bg.update();

		if (slide < 25)
			slide += 2;

	}

	public void reset() {

		slide = 0;
		currentChoice = 0;

	}

	public void draw(Graphics2D g) {

		bg.draw(g);

		int x;
		int y;
		String s;
		Rectangle r;
		Stroke stroke;

		g.setFont(gsm.getTitleFont());
		s = "Leaderboard";
		x = 10;
		y = gsm.getTitleHeight();

		g.setPaint(new GradientPaint(0, 0, gsm.getColorScheme().getDark(),
				GameFrame.WIDTH, 0, gsm.getColorScheme().getLight()));
		r = new Rectangle(-10, ((g.getFontMetrics().getDescent() + y) - (g
				.getFontMetrics().getHeight() + 2)), GameFrame.WIDTH + 20, g
				.getFontMetrics().getHeight() + 4);
		g.fill(r);
		stroke = g.getStroke();
		g.setStroke(new BasicStroke(2f));
		g.setPaint(gsm.getColorScheme().getBorder());
		g.draw(r);
		g.setStroke(stroke);

		g.setPaint(gsm.getColorScheme().getAlternateFont());
		g.drawString(s, x, y);

		g.setFont(gsm.getFont());

		String highscore1String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE1);
		String highscore2String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE2);
		String highscore3String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE3);
		String[] highscoreArray = new String[] { highscore1String,
				highscore2String, highscore3String };

		int widest = 0;

		for (int i = 0; i < highscoreArray.length; i++) {

			s = highscoreArray[i];

			if (g.getFontMetrics().stringWidth(s) > g.getFontMetrics()
					.stringWidth(highscoreArray[widest])) {

				widest = i;

			}

		}

		r = new Rectangle((GameFrame.WIDTH / 2)
				- (g.getFontMetrics().stringWidth(highscoreArray[widest]) / 2)
				- 5, (110 + (0 * 15))
				- ((g.getFontMetrics().getHeight()
						- g.getFontMetrics().getDescent() + 5)), g
				.getFontMetrics().stringWidth(highscoreArray[widest] + 10),
				((30 + ((highscoreArray.length - 1) * 15)) + ((g
						.getFontMetrics().getDescent() + 5)))
						- ((30 + (0 * 15)) - ((g.getFontMetrics().getHeight()
								- g.getFontMetrics().getDescent() + 5))));
		g.setPaint(gsm.getColorScheme().getInner());
		g.fill(r);
		g.setPaint(gsm.getColorScheme().getBorder());
		g.draw(r);

		g.setPaint(gsm.getColorScheme().getFont());
		for (int i = 0; i < highscoreArray.length; i++) {

			s = highscoreArray[i];
			x = (GameFrame.WIDTH / 2)
					- (g.getFontMetrics().stringWidth(highscoreArray[i]) / 2);
			y = 110 + (i * 15);

			g.drawString(s, x, y);

		}

		for (int i = 0; i < ((options.length >= gsm.getButtonsPerSide()) ? gsm
				.getButtonsPerSide() : options.length); i++) {

			y = gsm.getLowButtonHeight() + (i * 25);

			if (i == currentChoice) {

				g.setPaint(new GradientPaint(0, 0, gsm.getColorScheme()
						.getDark(), 100, 0, gsm.getColorScheme().getLight()));
				x = slide;

			} else {

				g.setPaint(gsm.getColorScheme().getInner());
				x = 10;

			}

			s = options[i];

			r = new Rectangle(-50 + x,
					((g.getFontMetrics().getDescent() + y) - (g
							.getFontMetrics().getHeight() + 2)), 175, g
							.getFontMetrics().getHeight() + 4);
			g.fill(r);
			stroke = g.getStroke();
			g.setStroke(new BasicStroke(2f));
			g.setPaint(gsm.getColorScheme().getBorder());
			g.draw(r);
			g.setStroke(stroke);

			g.setPaint(gsm.getColorScheme().getFont());
			g.drawString(s, x, y);

		}

		if (options.length > gsm.getButtonsPerSide()) {

			for (int i = gsm.getButtonsPerSide(); i < options.length; i++) {

				s = options[i];
				y = gsm.getLowButtonHeight() + ((i - gsm.getButtonsPerSide()) * 25);

				int rawX;

				if (i == currentChoice) {

					g.setPaint(new GradientPaint(GameFrame.WIDTH - 100, 0, gsm
							.getColorScheme().getLight(), GameFrame.WIDTH, 0,
							gsm.getColorScheme().getDark()));
					x = GameFrame.WIDTH
							- (g.getFontMetrics().stringWidth(s) + slide);

					rawX = slide;

				} else {

					g.setPaint(gsm.getColorScheme().getInner());
					x = GameFrame.WIDTH
							- (g.getFontMetrics().stringWidth(s) + 10);

					rawX = 10;

				}

				r = new Rectangle((GameFrame.WIDTH + 50) - (175 + rawX), ((g
						.getFontMetrics().getDescent() + y) - (g
						.getFontMetrics().getHeight() + 2)), 175, g
						.getFontMetrics().getHeight() + 4);
				g.fill(r);
				stroke = g.getStroke();
				g.setStroke(new BasicStroke(2f));
				g.setPaint(gsm.getColorScheme().getBorder());
				g.draw(r);
				g.setStroke(stroke);

				g.setPaint(gsm.getColorScheme().getFont());
				g.drawString(s, x, y);

			}

		}

	}

	private void select() {

		switch (currentChoice) {

		// BACK
		case 0:

			gsm.setState(gsm.getLastState());

			break;

		}

	}

	public void keyPressed(int k) {
	}

	public void keyReleased(int k) {

		switch (k) {

		case KeyEvent.VK_ENTER:

			this.select();

			break;

		case KeyEvent.VK_W:

			slide = 10;
			gsm.getAudioPlayer().playAudio("click.wav", false, 1);

			int i = currentChoice - 1;

			if (i < 0) {

				i = options.length - 1;

			}

			currentChoice = i;

			break;

		case KeyEvent.VK_S:

			slide = 10;
			gsm.getAudioPlayer().playAudio("click.wav", false, 1);

			int ii = currentChoice + 1;

			if (ii >= options.length) {

				ii = 0;

			}

			currentChoice = ii;

			break;

		}

	}

}
