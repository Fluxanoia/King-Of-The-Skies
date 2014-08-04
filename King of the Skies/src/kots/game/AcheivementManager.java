package kots.game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import kots.gamestate.GameStateManager;
import kots.main.PreferenceManager;

public class AcheivementManager {

	/*
	 * Reach 1000 points in a game 
	 * Beat the 1st highscore 
	 * Use your ability 10 times in one game 
	 * Destroy 3 obstacles in one go 
	 * Buy everythings
	 */

	private GameStateManager gsm;
	private ArrayList<Notification> notifications;

	public AcheivementManager(GameStateManager gsm) {

		this.gsm = gsm;
		notifications = new ArrayList<Notification>();

	}

	public void scoreChanged(int score) {

		if (!Boolean.valueOf(gsm.getPreferenceManager().get(
				PreferenceManager.ACHEIVEMENT1))) {

			if (score >= 1000) {
				
				gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT1,
						"true");
				notifications.add(new Notification(gsm, "1000 points!"));
			
			}

		}

	}
	
	public void highscoreChanged(int placed) {

		if (!Boolean.valueOf(gsm.getPreferenceManager().get(
				PreferenceManager.ACHEIVEMENT2))) {

			if (placed == 1) {
				
				gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT2,
						"true");
				notifications.add(new Notification(gsm, "Champion!"));
			
			}

		}

	}
	
	public void abilityUsed(int timesUsed, int thingsDestroyed) {

		if (!Boolean.valueOf(gsm.getPreferenceManager().get(
				PreferenceManager.ACHEIVEMENT3))) {

			if (timesUsed >= 10) {
				
				gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT3,
						"true");
				notifications.add(new Notification(gsm, "Smashing!"));
			
			}

		}
		
		if (!Boolean.valueOf(gsm.getPreferenceManager().get(
				PreferenceManager.ACHEIVEMENT4))) {

			if (thingsDestroyed >= 3) {
				
				gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT4,
						"true");
				notifications.add(new Notification(gsm, "Collateral Damage!"));
			
			}

		}

	}
	
	public void somethingBought() {
		
		if (!Boolean.valueOf(gsm.getPreferenceManager().get(
				PreferenceManager.ACHEIVEMENT5))) {
			
			if (Integer.valueOf(gsm.getPreferenceManager().get(
				PreferenceManager.ABILITY1)) == Player.ABILITYMAX) {
				
				if (Integer.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ABILITY2)) == Player.ABILITYMAX) {
						
					if (Integer.valueOf(gsm.getPreferenceManager().get(
							PreferenceManager.ABILITY3)) == Player.ABILITYMAX) {
							
						gsm.getPreferenceManager().set(PreferenceManager.ACHEIVEMENT5,
								"true");
						notifications.add(new Notification(gsm, "Maxed Out!"));
							
						}
						
					}
				
			}
			
		}
		
	}

	@SuppressWarnings("unchecked")
	public void update() {

		ArrayList<Notification> tempNot = (ArrayList<Notification>) notifications
				.clone();

		for (Notification n : tempNot) {
			n.update();
		}

	}

	@SuppressWarnings("unchecked")
	public void draw(Graphics2D g) {

		ArrayList<Notification> tempNot = (ArrayList<Notification>) notifications
				.clone();

		for (Notification n : tempNot) {
			n.draw(g);
		}

	}

}
