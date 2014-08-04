package kots.main;

import java.util.prefs.Preferences;

public class PreferenceManager {

	public static final String NOFIELD = "FIELD_EMPTY_NULL";
	
	public static final String VOLUME = "volume";
	public static final String MUSIC = "music";
	public static final String SFX = "sfx";
	public static final String FPS = "fps";
	public static final String UPS = "ups";
	public static final String FRAMELIMIT = "framelimit";
	public static final String COLORSCHEME = "colorscheme";
	public static final String SCALE = "scale";
	public static final String FULLSCREEN = "fullscreen";
	
	public static final String ACHEIVEMENT1 = "acheivement1";
	public static final String ACHEIVEMENT2 = "acheivement2";
	public static final String ACHEIVEMENT3 = "acheivement3";
	public static final String ACHEIVEMENT4 = "acheivement4";
	public static final String ACHEIVEMENT5 = "acheivement5";
	
	public static final String HIGHSCORE1 = "highscore1";
	public static final String HIGHSCORE2 = "highscore2";
	public static final String HIGHSCORE3 = "highscore3";
	public static final String ABILITY1 = "ability1";
	public static final String ABILITY2 = "ability2";
	public static final String ABILITY3 = "ability3";
	public static final String MONEY = "money";
	
	public PreferenceManager() {
		
		Preferences prefs = Preferences.userNodeForPackage(kots.main.PreferenceManager.class);
		
		if (!exists(ACHEIVEMENT1)) {
			
			prefs.put(ACHEIVEMENT1, "false");

		}
		
		if (!exists(ACHEIVEMENT2)) {
			
			prefs.put(ACHEIVEMENT2, "false");

		}
		
		if (!exists(ACHEIVEMENT3)) {
			
			prefs.put(ACHEIVEMENT3, "false");

		}
		
		if (!exists(ACHEIVEMENT4)) {
			
			prefs.put(ACHEIVEMENT4, "false");

		}
		
		if (!exists(ACHEIVEMENT5)) {
			
			prefs.put(ACHEIVEMENT5, "false");

		}
		
		if (!exists(FULLSCREEN)) {
			
			prefs.put(FULLSCREEN, "false");

		}
		
		if (!exists(SCALE)) {
			
			prefs.put(SCALE, "2");
			
		}
		
		if (!exists(COLORSCHEME)) {
			
			prefs.put(COLORSCHEME, "0");
			
		}
		
		if (!exists(FRAMELIMIT)) {
			
			prefs.put(FRAMELIMIT, "true");
			
		}
		
		if (!exists(UPS)) {
			
			prefs.put(UPS, "false");
			
		}
		
		if (!exists(FPS)) {
			
			prefs.put(FPS, "false");
			
		}
		
		if (!exists(SFX)) {
			
			prefs.put(SFX, "true");
			
		}
		
		if (!exists(MUSIC)) {
			
			prefs.put(MUSIC, "true");
			
		}
		
		if (!exists(HIGHSCORE1)) {
			
			prefs.put(HIGHSCORE1, "AAA 75");
			
		}
		
		if (!exists(HIGHSCORE2)) {
			
			prefs.put(HIGHSCORE2, "AAA 50");
			
		}
		
		if (!exists(HIGHSCORE3)) {
			
			prefs.put(HIGHSCORE3, "AAA 25");
			
		}
		
		if (!exists(MONEY)) {
			
			prefs.put(MONEY, "0.0");
			
		}
		
		if (!exists(VOLUME)) {
			
			prefs.put(VOLUME, "-15");
			
		}
		
		if (!exists(ABILITY1)) {
			
			prefs.put(ABILITY1, "0");
			
		}
		
		if (!exists(ABILITY2)) {
			
			prefs.put(ABILITY2, "0");
			
		}
		
		if (!exists(ABILITY3)) {
			
			prefs.put(ABILITY3, "0");
			
		}
		
	}
	
	public boolean exists(String key) {
		
		Preferences prefs = Preferences.userNodeForPackage(kots.main.PreferenceManager.class);
		boolean b = false;
		
		String s = prefs.get(key, NOFIELD);
		
		if (!s.equals(NOFIELD)) {
			
			b = true;
			
		}
		
		return b;
		
	}
	
	public void set(String key, String value) {
		
		Preferences prefs = Preferences.userNodeForPackage(kots.main.PreferenceManager.class);
		
		prefs.put(key, value);
			
	}
	
	public String get(String key) {
		
		Preferences prefs = Preferences.userNodeForPackage(kots.main.PreferenceManager.class);
		
		return prefs.get(key, NOFIELD);
		
	}
	
}