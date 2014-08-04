package kots.gamestate;

import java.awt.Graphics2D;

import kots.main.PreferenceManager;

public class ResetStatState extends GameState {

	public ResetStatState(GameStateManager gsm) {

		super(gsm);

	}
	
	public void init() {
	}

	public void update() {

		if (gsm.getLastAnswer()) {

			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE1,
					"AAA 75");
			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE2,
					"AAA 50");
			gsm.getPreferenceManager().set(PreferenceManager.HIGHSCORE3,
					"AAA 25");
			gsm.getPreferenceManager().set(PreferenceManager.MONEY, "0.0");
			gsm.getPreferenceManager().set(PreferenceManager.ABILITY1, "0");
			gsm.getPreferenceManager().set(PreferenceManager.ABILITY2, "0");
			gsm.getPreferenceManager().set(PreferenceManager.ABILITY3, "0");
			gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT1,
					"false");
			gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT2,
					"false");
			gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT3,
					"false");
			gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT4,
					"false");
			gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT5,
					"false");

		}

		int[] gss = gsm.getLastGameStates();
		gsm.setState(gss[1]);
		gsm.setLastState(gss[3]);

	}

	public void reset() {
	}

	public void draw(Graphics2D g) {
	}

	public void keyPressed(int k) {
	}

	public void keyReleased(int k) {
	}

}
