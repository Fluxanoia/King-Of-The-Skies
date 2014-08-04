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

public class OptionState extends GameState {

	private Background bg;

	private int currentChoice = 0;
	private String[] options = { "Music - ON", "SFX - ON", "FPS - OFF",
			"UPS - OFF", "Frame Limit - ON", "Color Scheme", "Reset Stats",
			"Scale - 2", "Fullscreen", "Volume Up", "Volume Down", "Back" };
	private int slide;

	public OptionState(GameStateManager gsm) {

		super(gsm);

		slide = 0;

		bg = new Background(gsm, "/backgrounds/clear.png");
		bg.setVector(0, 0);
		
		String s;
		s = (Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.MUSIC))) ? "ON" : "OFF";
		options[0] = "Music - " + s;
		s = (Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.SFX))) ? "ON" : "OFF";
		options[1] = "SFX - " + s; 
		s = (Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.FPS))) ? "ON" : "OFF";
		options[2] = "FPS - " + s; 
		s = (Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.UPS))) ? "ON" : "OFF";
		options[3] = "UPS - " + s; 
		s = (Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.FRAMELIMIT))) ? "ON" : "OFF";
		options[4] = "Frame Limit - " + s; 
		
		s = gsm.getPreferenceManager().get(PreferenceManager.SCALE);
		options[7] = "Scale - " + s;
		
		
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
		s = "Options";
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
				y = gsm.getButtonHeight()
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

		String delims = "\\s+";

		switch (currentChoice) {

		case 0:
		case 1:
		case 2:
		case 3:
		case 4:

			boolean turningOn = false;

			String[] s = options[currentChoice].split(delims);

			if (s[s.length - 1].equalsIgnoreCase("OFF")) {

				s[s.length - 1] = "ON";
				String ss = "";

				for (int i = 0; i < s.length; i++) {

					String a = (i == s.length - 1) ? "" : " ";

					ss = ss + s[i] + a;

				}

				options[currentChoice] = ss;

				turningOn = true;

			}

			if (!turningOn) {

				if (s[s.length - 1].equalsIgnoreCase("ON")) {

					s[s.length - 1] = "OFF";
					String ss = "";

					for (int i = 0; i < s.length; i++) {

						String a = (i == s.length - 1) ? "" : " ";

						ss = ss + s[i] + a;

					}

					options[currentChoice] = ss;

					turningOn = false;

				}

			}

			switch (currentChoice) {

			case 0:

				if (!turningOn) {

					gsm.getPreferenceManager().set(PreferenceManager.MUSIC, "false");

				} else {

					gsm.getPreferenceManager().set(PreferenceManager.MUSIC, "true");

				}

				break;

			case 1:

				if (!turningOn) {

					gsm.getPreferenceManager().set(PreferenceManager.SFX, "false");

				} else {

					gsm.getPreferenceManager().set(PreferenceManager.SFX, "true");

				}

				break;

			case 2:

				if (!turningOn) {

					gsm.getPreferenceManager().set(PreferenceManager.FPS, "false");

				} else {

					gsm.getPreferenceManager().set(PreferenceManager.FPS, "true");

				}

				break;

			case 3:

				if (!turningOn) {

					gsm.getPreferenceManager().set(PreferenceManager.UPS, "false");

				} else {

					gsm.getPreferenceManager().set(PreferenceManager.UPS, "true");

				}

				break;

			case 4:

				if (!turningOn) {

					gsm.getPreferenceManager().set(PreferenceManager.FRAMELIMIT, "false");

				} else {

					gsm.getPreferenceManager().set(PreferenceManager.FRAMELIMIT, "true");

				}

				break;

			}

			break;

		case 5:

			gsm.changeSchemes();

			break;

		case 6:

			gsm.setNextState(GameStateManager.RESETSTATSTATE);
			gsm.setStillProgress(true);
			gsm.setState(GameStateManager.AREYOUSURESTATE);

			break;

		// SCALE
		case 7:

			String[] strs = options[currentChoice].split(delims);
			int scale = gsm.getScale();

			scale++;

			if (scale > 3) {

				scale = 1;

			}

			gsm.getPreferenceManager().set(PreferenceManager.SCALE, scale + "");

			options[currentChoice] = strs[0] + " " + strs[1] + " " + scale;

			break;

		// FULLSCREEN
		case 8:

			boolean fs = gsm.isFullscreen();

			fs = (fs) ? false : true;

			gsm.getPreferenceManager().set(PreferenceManager.FULLSCREEN, Boolean.toString(fs));

			break;

		// VOLUME UP
		case 9:

			gsm.getPreferenceManager().set(
					PreferenceManager.VOLUME,
					(Integer.valueOf(gsm.getPreferenceManager().get(
							PreferenceManager.VOLUME)) + 1)
							+ "");

			break;

		// VOLUME DOWN
		case 10:

			gsm.getPreferenceManager().set(
					PreferenceManager.VOLUME,
					(Integer.valueOf(gsm.getPreferenceManager().get(
							PreferenceManager.VOLUME)) - 1)
							+ "");

			break;

		case 11:

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
