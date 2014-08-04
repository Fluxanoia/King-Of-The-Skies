package kots.gamestate;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;

import kots.main.GameFrame;
import kots.tiles.Background;

public class PauseState extends GameState {

	private Background bg;

	private int currentChoice = 0;
	private String[] options = { "Resume", "Help", "Options", "Store",
			"Leaderboard", "Achievements", "Quit to Main Menu", "Quit to Desktop" };
	private int slide;

	public PauseState(GameStateManager gsm) {

		super(gsm);

		slide = 0;

		bg = new Background(gsm, "/backgrounds/clear.png");
		bg.setVector(0, 0);

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
		s = "Paused";
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

		for (int i = 0; i < ((options.length >= gsm.getButtonsPerSide()) ? gsm
				.getButtonsPerSide() : options.length); i++) {

			y = gsm.getButtonHeight() + (i * 25);

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
				y = gsm.getButtonHeight() + ((i - gsm.getButtonsPerSide()) * 25);

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

		// RESUME
		case 0:

			gsm.setState(GameStateManager.LEVELSTATE);

			break;

		// HELP
		case 1:

			gsm.setState(GameStateManager.HELPSTATE);

			break;

		// OPTIONS
		case 2:

			gsm.setState(GameStateManager.OPTIONSTATE);

			break;

		case 3:

			gsm.setState(GameStateManager.SHOPSTATE);

			break;

		// LEADERBOARDS
		case 4:

			gsm.setState(GameStateManager.LEADERBOARDSTATE);

			break;

		case 5:
			
			gsm.setState(GameStateManager.ACHEIVEMENTSTATE);
			
			break;
			
		// QUIT TO MENU
		case 6:

			gsm.setNextState(GameStateManager.MENUSTATE);
			gsm.setState(GameStateManager.AREYOUSURESTATE);

			break;

		// QUIT
		case 7:

			gsm.setNextState(-1);
			gsm.setState(GameStateManager.AREYOUSURESTATE);

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
