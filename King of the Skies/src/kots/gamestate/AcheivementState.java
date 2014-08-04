package kots.gamestate;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import kots.main.GameFrame;
import kots.main.PreferenceManager;
import kots.tiles.Background;

public class AcheivementState extends GameState {

	private Background bg;
	private BufferedImage acheivedImage;

	private int currentChoice = 0;
	private String[] options = { "Back" };
	private int slide;

	private boolean[] acheived;
	private int page;
	private String[] acheiveLines = {
			"1000 points!//Get 1000 points in one game.",
			"Champion!//Reach the top of the leaderboard.",
			"Smashing!//Use your ability 10 times in one game.",
			"Collateral Damage!//Destroy 3 warnings, obstacles//or projectiles with one ability use.",
			"Maxed Out!//Buy EVERYTHING." };

	public AcheivementState(GameStateManager gsm) {

		super(gsm);

		bg = new Background(gsm, "/backgrounds/clear.png");
		bg.setVector(0, 0);
		
		acheivedImage = gsm.getImageLoader().loadImage("/acheivement/acheived.png");

		slide = 0;
		page = 0;

		acheived = new boolean[] {
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT1)),
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT2)),
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT3)),
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT4)),
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT5)) };

	}

	public void init() {
		
		page = 0;
		
		acheived = new boolean[] {
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT1)),
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT2)),
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT3)),
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT4)),
				Boolean.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ACHEIVEMENT5)) };
		
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

		String s = "";
		int x = 0;
		int y = 0;
		Rectangle r;
		Stroke stroke;

		String[] acheivements = acheiveLines[page].split("//");
		int widest = 0;

		g.setFont(gsm.getTitleFont());
		s = acheivements[0];
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

		g.setColor(gsm.getColorScheme().getFont());
		g.setFont(gsm.getFont());

		for (int i = 1; i < acheivements.length; i++) {

			s = acheivements[i];

			if (g.getFontMetrics().stringWidth(s) > g.getFontMetrics()
					.stringWidth(acheivements[widest])) {

				widest = i;

			}

		}
		
		if (acheived[page]) {
			
			g.drawImage(acheivedImage, (GameFrame.WIDTH / 2) - (acheivedImage.getWidth() / 2), GameFrame.HEIGHT - 70, null);
			
		}

		r = new Rectangle((GameFrame.WIDTH / 2)
				- (g.getFontMetrics().stringWidth(acheivements[widest]) / 2)
				- 5, (gsm.getButtonHeight())
				- ((g.getFontMetrics().getHeight()
						- g.getFontMetrics().getDescent() + 5)), g
				.getFontMetrics().stringWidth(acheivements[widest] + 10),
				((30 + ((acheivements.length - 1) * 15)) + ((g.getFontMetrics()
						.getDescent() + 5)))
						- ((30 + (0 * 15)) - ((g.getFontMetrics().getHeight()
								- g.getFontMetrics().getDescent() + 5))));
		g.setPaint(gsm.getColorScheme().getInner());
		g.fill(r);
		g.setPaint(gsm.getColorScheme().getBorder());
		g.draw(r);

		g.setPaint(gsm.getColorScheme().getFont());
		for (int i = 1; i < acheivements.length; i++) {

			s = acheivements[i];
			x = (GameFrame.WIDTH / 2)
					- (g.getFontMetrics().stringWidth(acheivements[i]) / 2);
			y = gsm.getButtonHeight() + (i * 15);

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

		switch (k) {
		}

	}

	public void keyReleased(int k) {

		switch (k) {

		case KeyEvent.VK_ENTER:

			this.select();

			break;

		case KeyEvent.VK_A:

			if (page == 0) {

				break;

			}

			page--;

			break;

		case KeyEvent.VK_D:

			if (page == acheiveLines.length - 1) {

				break;

			}

			page++;

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
