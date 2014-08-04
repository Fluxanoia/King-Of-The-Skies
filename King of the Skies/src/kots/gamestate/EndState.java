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

public class EndState extends GameState {

	private Background bg;
	private Background overlay;

	private int currentChoice = 0;
	private String[] options = { "Back" };
	private int slide;

	private boolean newHighscore;

	private String highscoreTag;
	private String[] highscoreTags;
	private String[] alphabet;
	private int selected;

	public EndState(GameStateManager gsm) {

		super(gsm);

		bg = new Background(gsm, "/backgrounds/dark.png");
		bg.setVector(0, 0);

		slide = 0;

		newHighscore = false;

		selected = 0;
		highscoreTag = "AAA";
		highscoreTags = new String[] { "A", "A", "A" };
		alphabet = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "0" };

		overlay = new Background(gsm, "/overlays/death.png");
		overlay.setVector(0, 0);

	}

	public void init() {
	}

	public void update() {

		bg.update();

		if (slide < 25)
			slide += 2;

		String delims = "\\s+";
		int score = gsm.getScore();

		String highscore1String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE1);
		String highscore2String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE2);
		String highscore3String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE3);

		int highscore1;
		int highscore2;
		int highscore3;

		try {

			String[] ints;

			ints = highscore1String.split(delims);
			highscore1 = Integer.valueOf(ints[1]);

			ints = highscore2String.split(delims);
			highscore2 = Integer.valueOf(ints[1]);

			ints = highscore3String.split(delims);
			highscore3 = Integer.valueOf(ints[1]);

		} catch (Exception e) {

			highscore1 = -1;
			highscore2 = -1;
			highscore3 = -1;

		}

		if (score > highscore1) {

			newHighscore = true;
			gsm.getAcheivementManager().highscoreChanged(1);

		}

		if (score > highscore2) {

			newHighscore = true;
			gsm.getAcheivementManager().highscoreChanged(2);

		}

		if (score > highscore3) {

			newHighscore = true;
			gsm.getAcheivementManager().highscoreChanged(3);

		}

	}

	public void reset() {

		this.setLeaderboards();

		slide = 0;
		currentChoice = 0;

		gsm.getPreferenceManager().set(
				PreferenceManager.MONEY,
				(Double.valueOf(this.getMoneyString(false)) + Double
						.valueOf(this.getMoneyEarnedString(false))) + "");
		newHighscore = false;

		selected = 0;
		highscoreTag = "AAA";
		highscoreTags = new String[] { "A", "A", "A" };
		alphabet = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "0" };

	}

	public void draw(Graphics2D g) {

		bg.draw(g);
		overlay.draw(g);

		int x;
		int y;
		String s;
		Rectangle r;
		Stroke stroke;

		g.setFont(gsm.getTitleFont());
		s = "You died!";
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

		int score = gsm.getScore();
		String delims = "\\s+";

		if (!newHighscore) {

			String highscore1String = gsm.getPreferenceManager().get(
					PreferenceManager.HIGHSCORE1);
			int highscore1;

			try {

				String[] ints = highscore1String.split(delims);
				highscore1 = Integer.valueOf(ints[1]);

			} catch (Exception e) {

				highscore1 = -1;

			}

			s = "The highscore is " + highscore1 + "!";
			x = (GameFrame.WIDTH / 2) - (g.getFontMetrics().stringWidth(s) / 2);
			y = GameFrame.HEIGHT - 80;

			r = new Rectangle(x - 5, (y - 2)
					- (g.getFontMetrics().getHeight() - g.getFontMetrics()
							.getDescent()),
					g.getFontMetrics().stringWidth(s) + 10, g.getFontMetrics()
							.getHeight() + 4);
			g.setPaint(gsm.getColorScheme().getInner());
			g.fill(r);
			g.setPaint(gsm.getColorScheme().getBorder());
			g.draw(r);
			g.setPaint(gsm.getColorScheme().getFont());
			g.drawString(s, x, y);

		} else {

			s = "You broke the highscore!";
			x = (GameFrame.WIDTH / 2) - (g.getFontMetrics().stringWidth(s) / 2);
			y = GameFrame.HEIGHT - 110;

			r = new Rectangle(x - 5, (y - 2)
					- (g.getFontMetrics().getHeight() - g.getFontMetrics()
							.getDescent()),
					g.getFontMetrics().stringWidth(s) + 10, g.getFontMetrics()
							.getHeight() + 4);
			g.setPaint(gsm.getColorScheme().getInner());
			g.fill(r);
			g.setPaint(gsm.getColorScheme().getBorder());
			g.draw(r);
			g.setPaint(gsm.getColorScheme().getFont());
			g.drawString(s, x, y);

		}

		if (newHighscore) {

			y = GameFrame.HEIGHT - 80;

			for (int i = 0; i < highscoreTags.length; i++) {

				s = highscoreTags[i];
				x = (80 + (i * 80)) - (g.getFontMetrics().stringWidth(s) / 2);
				r = new Rectangle(x - 5, (y - 2)
						- (g.getFontMetrics().getHeight() - g.getFontMetrics()
								.getDescent()), g.getFontMetrics().stringWidth(
						s) + 10, g.getFontMetrics().getHeight() + 4);
				g.setPaint(gsm.getColorScheme().getInner());

				if (selected == i) {

					g.setPaint(new GradientPaint((int) r.getMinX(), (int) r
							.getMinY(), gsm.getColorScheme().getLight(),
							(int) r.getMinX(), (int) r.getMaxY(), gsm
									.getColorScheme().getDark()));

				}

				g.fill(r);
				g.setPaint(gsm.getColorScheme().getBorder());
				g.draw(r);
				g.setPaint(gsm.getColorScheme().getFont());
				g.drawString(s, x, y);

			}

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
				y = gsm.getLowButtonHeight()
						+ ((i - gsm.getButtonsPerSide()) * 25);

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

		String money = this.getMoneyEarnedString(true);
		s = "You earned " + money + "!";
		x = (GameFrame.WIDTH / 2) - (g.getFontMetrics().stringWidth(s) / 2);
		y = GameFrame.HEIGHT - 130;

		r = new Rectangle(x - 5, (y - 2)
				- (g.getFontMetrics().getHeight() - g.getFontMetrics()
						.getDescent()), g.getFontMetrics().stringWidth(s) + 10,
				g.getFontMetrics().getHeight() + 4);
		g.setPaint(gsm.getColorScheme().getInner());
		g.fill(r);
		g.setPaint(gsm.getColorScheme().getBorder());
		g.draw(r);

		g.setPaint(gsm.getColorScheme().getFont());
		g.drawString(s, x, y);

		s = "Your score is " + score + "!";
		x = (GameFrame.WIDTH / 2) - (g.getFontMetrics().stringWidth(s) / 2);
		y = GameFrame.HEIGHT - 50;

		r = new Rectangle(x - 5, (y - 2)
				- (g.getFontMetrics().getHeight() - g.getFontMetrics()
						.getDescent()), g.getFontMetrics().stringWidth(s) + 10,
				g.getFontMetrics().getHeight() + 4);
		g.setPaint(gsm.getColorScheme().getInner());
		g.fill(r);
		g.setPaint(gsm.getColorScheme().getBorder());
		g.draw(r);

		g.setPaint(gsm.getColorScheme().getFont());
		g.drawString(s, x, y);

	}

	private void select() {

		switch (currentChoice) {

		// BACK
		case 0:

			gsm.setState(GameStateManager.MENUSTATE);

			break;

		}

	}

	public void keyPressed(int k) {
	}

	public void keyReleased(int k) {

		int sel;
		int newChar;
		String changedChar = highscoreTags[selected];
		int placeOfChar = -1;

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

		case KeyEvent.VK_SPACE:

			if (!newHighscore) {

				break;

			}

			for (int iii = 0; iii < alphabet.length; iii++) {

				if (alphabet[iii].equals(changedChar)) {

					placeOfChar = iii;

					break;

				}

			}

			newChar = placeOfChar + 1;

			if (newChar == alphabet.length) {

				newChar = 0;

			}

			highscoreTags[selected] = alphabet[newChar];
			highscoreTag = highscoreTags[0] + highscoreTags[1]
					+ highscoreTags[2];

			break;

		case KeyEvent.VK_A:

			if (!newHighscore) {

				break;

			}

			sel = selected - 1;

			if (sel == -1) {

				sel = 2;

			}

			selected = sel;

			break;

		case KeyEvent.VK_D:

			if (!newHighscore) {

				break;

			}

			sel = selected + 1;

			if (sel == 3) {

				sel = 0;

			}

			selected = sel;

			break;

		}

	}

	public void setLeaderboards() {

		int score = gsm.getScore();

		String highscore1String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE1);
		String highscore2String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE2);
		String highscore3String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE3);

		String highscore1text;
		String highscore2text;
		String highscore3text;

		String delims = "\\s+";

		int highscore1;
		int highscore2;
		int highscore3;

		try {

			String[] ints;

			ints = highscore1String.split(delims);
			highscore1 = Integer.valueOf(ints[1]);

			ints = highscore2String.split(delims);
			highscore2 = Integer.valueOf(ints[1]);

			ints = highscore3String.split(delims);
			highscore3 = Integer.valueOf(ints[1]);

		} catch (Exception e) {

			highscore1 = -1;
			highscore2 = -1;
			highscore3 = -1;

		}

		highscore1String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE1);
		highscore2String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE2);
		highscore3String = gsm.getPreferenceManager().get(
				PreferenceManager.HIGHSCORE3);

		if (score > highscore1) {

			newHighscore = true;
			highscore1 = score;
			highscore1text = highscoreTag + " " + highscore1;
			highscore2text = highscore1String;
			highscore3text = highscore2String;

			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE1,
					highscore1text);
			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE2,
					highscore2text);
			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE3,
					highscore3text);

			return;

		}

		if (score > highscore2) {

			newHighscore = true;
			highscore2 = score;
			highscore1text = highscore1String;
			highscore2text = highscoreTag + " " + highscore2;
			highscore3text = highscore2String;

			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE1,
					highscore1text);
			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE2,
					highscore2text);
			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE3,
					highscore3text);

			return;

		}

		if (score > highscore3) {

			newHighscore = true;
			highscore3 = score;
			highscore1text = highscore1String;
			highscore2text = highscore2String;
			highscore3text = highscoreTag + " " + highscore3;

			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE1,
					highscore1text);
			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE2,
					highscore2text);
			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE3,
					highscore3text);

			return;

		}
	}

	public String getMoneyString(boolean b) {

		double moneyDouble = Double.valueOf(gsm.getPreferenceManager().get(
				PreferenceManager.MONEY));
		int moneyInt = (int) Math.floor(moneyDouble);

		double penniesDouble = (double) (moneyDouble - moneyInt);
		penniesDouble = (double) penniesDouble * 100;
		int pennies = (int) Math.floor(penniesDouble);
		String pence = pennies + "";
		if (pennies < 10) {

			pence = "0" + pennies;

		}

		String s;

		if (b) {

			s = "£";

		} else {

			s = "";

		}

		return s + moneyInt + "." + pence;

	}

	public String getMoneyEarnedString(boolean b) {

		double moneyDouble = (double) gsm.getScore() / 10;
		int moneyInt = (int) Math.floor(moneyDouble);

		double penniesDouble = (double) (moneyDouble - moneyInt);
		penniesDouble = (double) penniesDouble * 100;
		int pennies = (int) Math.floor(penniesDouble);
		String pence = pennies + "";
		if (pennies < 10) {

			pence = "0" + pennies;

		}

		String s;

		if (b) {

			s = "£";

		} else {

			s = "";

		}

		return s + moneyInt + "." + pence;

	}

}
