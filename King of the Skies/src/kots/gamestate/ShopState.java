package kots.gamestate;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;

import kots.game.Player;
import kots.main.GameFrame;
import kots.main.PreferenceManager;
import kots.tiles.Background;

public class ShopState extends GameState {

	public Background bg;

	private int currentChoice = 0;
	private String[] options = { "Lives", "Cooldown", "Points", "Back" };
	private int slide;

	public ShopState(GameStateManager gsm) {

		super(gsm);

		slide = 0;
		
		bg = new Background(gsm, "/backgrounds/clear.png");
		bg.setVector(0, 0);
		
		if (!gsm.getPreferenceManager().get(PreferenceManager.ABILITY1).equalsIgnoreCase(Player.ABILITYMAX + "")) {
			
			options[0] = "Lives - Level " + gsm.getPreferenceManager().get(PreferenceManager.ABILITY1);
			
		} else {
			
			options[0] = "Lives - Level MAX";
			
		}
		
		if (!gsm.getPreferenceManager().get(PreferenceManager.ABILITY2).equalsIgnoreCase(Player.ABILITYMAX + "")) {
			
			options[1] = "Cooldown - Level " + gsm.getPreferenceManager().get(PreferenceManager.ABILITY2);
			
		} else {
			
			options[1] = "Cooldown - Level MAX";
			
		}
		
		if (!gsm.getPreferenceManager().get(PreferenceManager.ABILITY3).equalsIgnoreCase(Player.ABILITYMAX + "")) {
			
			options[2] = "Points - Level " + gsm.getPreferenceManager().get(PreferenceManager.ABILITY3);
			
		} else {
			
			options[2] = "Points - Level MAX";
			
		}

	}
	
	public void init() {
		
		if (!gsm.getPreferenceManager().get(PreferenceManager.ABILITY1).equalsIgnoreCase(Player.ABILITYMAX + "")) {
			
			options[0] = "Lives - Level " + gsm.getPreferenceManager().get(PreferenceManager.ABILITY1);
			
		} else {
			
			options[0] = "Lives - Level MAX";
			
		}
		
		if (!gsm.getPreferenceManager().get(PreferenceManager.ABILITY2).equalsIgnoreCase(Player.ABILITYMAX + "")) {
			
			options[1] = "Cooldown - Level " + gsm.getPreferenceManager().get(PreferenceManager.ABILITY2);
			
		} else {
			
			options[1] = "Cooldown - Level MAX";
			
		}
		
		if (!gsm.getPreferenceManager().get(PreferenceManager.ABILITY3).equalsIgnoreCase(Player.ABILITYMAX + "")) {
			
			options[2] = "Points - Level " + gsm.getPreferenceManager().get(PreferenceManager.ABILITY3);
			
		} else {
			
			options[2] = "Points - Level MAX";
			
		}
		
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
		s = "Store";
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

		x = x + (10 + g.getFontMetrics().stringWidth(s));
		s = this.getMoneyString(true);
		g.setPaint(gsm.getColorScheme().getFont());
		g.setFont(gsm.getFont());
		if (s == null) {
			s = "";
		}
		g.drawString(s, x, y);

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

		String moneyText;
		double money;
		double decrement;
		int ability;
		
		switch (currentChoice) {

		case 0:

			ability = Integer.valueOf(gsm.getPreferenceManager().get(PreferenceManager.ABILITY1));
			if (ability == Player.ABILITYMAX) {
				
				return;
				
			}
			ability++;
			moneyText = gsm.getPreferenceManager().get(PreferenceManager.MONEY);
			money = Double.valueOf(moneyText);
			decrement = (double) 50 * ability;
			money -= decrement;
			if (money < 0) {
				
				return;
				
			}
			moneyText = money + "";
			gsm.getPreferenceManager().set(PreferenceManager.MONEY, moneyText);
			gsm.getPreferenceManager().set(PreferenceManager.ABILITY1, ability + "");
			options[currentChoice] = "Lives - Level " + ability;
			if (ability == Player.ABILITYMAX) {
				
				options[currentChoice] = "Lives - Level MAX";
				
			}
			
			gsm.getAcheivementManager().somethingBought();

			break;

		case 1:

			ability = Integer.valueOf(gsm.getPreferenceManager().get(PreferenceManager.ABILITY2));
			if (ability == Player.ABILITYMAX) {
				
				return;
				
			}
			ability++;
			moneyText = gsm.getPreferenceManager().get(PreferenceManager.MONEY);
			money = Double.valueOf(moneyText);
			decrement = (double) 50 * ability;
			money -= decrement;
			if (money < 0) {
				
				return;
				
			}
			moneyText = money + "";
			gsm.getPreferenceManager().set(PreferenceManager.MONEY, moneyText);
			gsm.getPreferenceManager().set(PreferenceManager.ABILITY2, ability + "");
			options[currentChoice] = "Cooldown - Level " + ability;
			if (ability == Player.ABILITYMAX) {
				
				options[currentChoice] = "Cooldown - Level MAX";
				
			}
			
			gsm.getAcheivementManager().somethingBought();

			break;

		case 2:

			ability = Integer.valueOf(gsm.getPreferenceManager().get(PreferenceManager.ABILITY3));
			if (ability == Player.ABILITYMAX) {
				
				return;
				
			}
			ability++;
			moneyText = gsm.getPreferenceManager().get(PreferenceManager.MONEY);
			money = Double.valueOf(moneyText);
			decrement = (double) 50 * ability;
			money -= decrement;
			if (money < 0) {
				
				return;
				
			}
			moneyText = money + "";
			gsm.getPreferenceManager().set(PreferenceManager.MONEY, moneyText);
			gsm.getPreferenceManager().set(PreferenceManager.ABILITY3, ability + "");
			options[currentChoice] = "Points - Level " + ability;
			if (ability == Player.ABILITYMAX) {
				
				options[currentChoice] = "Points - Level MAX";
				
			}
			
			gsm.getAcheivementManager().somethingBought();
			
			break;

		case 3:

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
	
	public String getMoneyString(boolean b) {

		double moneyDouble = Double.valueOf(gsm.getPreferenceManager().get(PreferenceManager.MONEY));
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
