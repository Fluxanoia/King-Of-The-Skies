package kots.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import kots.gamestate.GameStateManager;

public class AudioPlayer {

	private GameStateManager gsm;
	private Clip[] clips;
	private boolean SFX;
	private boolean playNew;

	public AudioPlayer(GameStateManager gsm) {

		this.gsm = gsm;
		clips = new Clip[2];
		this.setPlay(Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.MUSIC)));
		playNew = Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.MUSIC));
		SFX = Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.SFX));

	}

	public void update() {

		for (int i = 0; i < clips.length; i++) {

			if (clips[i] != null) {

				FloatControl gainControl = (FloatControl) clips[i]
						.getControl(FloatControl.Type.MASTER_GAIN);
				float v = Integer.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.VOLUME));
				if (v > gainControl.getMaximum()) {

					v = gainControl.getMaximum();

				}
				if (v < gainControl.getMinimum()) {

					v = gainControl.getMinimum();

				}
				gainControl.setValue(v);

			}

		}
		
		SFX = Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.SFX));
		
		if (Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.MUSIC)) != playNew) {
			
			playNew = Boolean.valueOf(gsm.getPreferenceManager().get(PreferenceManager.MUSIC));
			this.setPlay(playNew);
			
		}

	}

	public void playAudio(String ss, boolean b, int channel) {

		if (channel == 1) {

			if (!SFX) {

				return;

			}

		}

		String s = "music/" + ss;

		URL url;
		AudioInputStream ais;
		Clip clip;

		int i = (b) ? -1 : 0;

		try {

			url = this.getClass().getClassLoader().getResource(s);
			ais = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(ais);

			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			float v = Integer.valueOf(gsm.getPreferenceManager().get(
					PreferenceManager.VOLUME));
			if (v > gainControl.getMaximum()) {

				v = gainControl.getMaximum();

			}
			if (v < gainControl.getMinimum()) {

				v = gainControl.getMinimum();

			}
			gainControl.setValue(v);

			clip.loop(i);
			clip.start();

			clips[channel] = clip;

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void stopAudio(int channel) {

		if (clips[channel] != null) {
			
			clips[channel].stop();
		
		}	
		
	}

	public void setPlay(boolean b) {

		if (b) {

			this.playAudio("soundtrack.wav", true, 0);

		} else {

			this.stopAudio(0);

		}

	}

	public void setSFX(boolean b) {
		SFX = b;
	}

}
