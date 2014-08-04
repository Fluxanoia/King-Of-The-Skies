package kots.gamestate;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;

import kots.main.GameFrame;
import kots.tiles.Background;

public class HelpState extends GameState {

	Background bg;

	private int currentChoice = 0;
	private String[] options = { "Back" };
	private int slide;

	private int page;
	private String[] helpLines = {
			"Page 1//Welcome to King of the Skies!//Use W, S and ENTER to navigate the menu.//Use SPACE to jump when in the game, P to pause//and ENTER to use your ability!//Use A and D to switch help pages.",
			"Page 2//If you break a highscore you will be able to//use A, D and SPACE to change your 'tag',//your score will be accompanied by//your tag on the leaderboard.//You earn 10p for each point you earn and//you will be able to spend your money//at the shop.",
			"Page 3//You can level up your perks at the shop with money.//The 1st level cost £50, the 2nd is £100//and the 3rd is £150, so on and so forth.//Lives will give you lives per game//that will allow you to die but respawn//as long as you have lives.//Cooldown will reduce the cooldown of your ability//and points will give you extra points//for the points you gather.",
			"Page 4//Use A and D to flick inbetween achievements//whilst in the acheivement pane."};

	public HelpState(GameStateManager gsm) {

		super(gsm);

		bg = new Background(gsm, "/backgrounds/clear.png");
		bg.setVector(0, 0);

		slide = 0;
		page = 0;

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

		g.setColor(gsm.getColorScheme().getFont());
		g.setFont(gsm.getFont());
		String s = "";
		int x = 0;
		int y = 0;
		Rectangle r;
		Stroke stroke;

		String[] helpPage = helpLines[page].split("//");
		int widest = 0;

		for (int i = 0; i < helpPage.length; i++) {

			s = helpPage[i];

			if (g.getFontMetrics().stringWidth(s) > g.getFontMetrics()
					.stringWidth(helpPage[widest])) {

				widest = i;

			}

		}

		r = new Rectangle((GameFrame.WIDTH / 2)
				- (g.getFontMetrics().stringWidth(helpPage[widest]) / 2) - 5,
				(30 + (0 * 15))
						- ((g.getFontMetrics().getHeight()
								- g.getFontMetrics().getDescent() + 5)), g
						.getFontMetrics().stringWidth(helpPage[widest] + 10),
				((30 + ((helpPage.length - 1) * 15)) + ((g.getFontMetrics()
						.getDescent() + 5)))
						- ((30 + (0 * 15)) - ((g.getFontMetrics().getHeight()
								- g.getFontMetrics().getDescent() + 5))));
		g.setPaint(gsm.getColorScheme().getInner());
		g.fill(r);
		g.setPaint(gsm.getColorScheme().getBorder());
		g.draw(r);

		g.setPaint(gsm.getColorScheme().getFont());
		for (int i = 0; i < helpPage.length; i++) {

			s = helpPage[i];
			x = (GameFrame.WIDTH / 2)
					- (g.getFontMetrics().stringWidth(helpPage[i]) / 2);
			y = 30 + (i * 15);

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

			if (page == helpLines.length - 1) {

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
