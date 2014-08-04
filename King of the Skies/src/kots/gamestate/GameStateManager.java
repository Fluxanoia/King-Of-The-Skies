package kots.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import kots.aesthetics.ColorScheme;
import kots.game.AcheivementManager;
import kots.main.AudioPlayer;
import kots.main.GameFrame;
import kots.main.ImageLoader;
import kots.main.PreferenceManager;

public class GameStateManager {

	public static final int MENUSTATE = 0;
	public static final int HELPSTATE = 1;
	public static final int OPTIONSTATE = 2;
	public static final int LEVELSTATE = 3;
	public static final int PAUSESTATE = 4;
	public static final int ENDSTATE = 5;
	public static final int LEADERBOARDSTATE = 6;
	public static final int AREYOUSURESTATE = 7;
	public static final int SHOPSTATE = 8;
	public static final int RESETSTATSTATE = 9;
	public static final int ACHEIVEMENTSTATE = 10;

	private String[] sublines;
	private String subline;
	
	private int lowButtonHeight;
	private int buttonHeight;
	private int titleHeight;
	private int buttonsPerSide;

	private boolean stillProgress;
	private boolean lastAnswer;
	
	private int score;
	
	private int FPS;
	private int UPS;
	private int scale;
	private boolean showFPS;
	private boolean showUPS;
	private boolean fullscreen;
	private boolean frameLimit;
	private boolean takeScreenshot;

	private Font titleFont;
	private Font font;
	private Font sysFont;

	private ArrayList<ColorScheme> colorSchemes;
	private int currentScheme;

	private ArrayList<GameState> gameStates;
	private int[] lastGameStates;
	private int currentState;
	private int lastState;
	private int incomingState;
	private int nextState;

	private ImageLoader imageLoader;
	private AudioPlayer audioPlayer;
	private AcheivementManager acheivementManager;
	private PreferenceManager preferenceManager;

	public GameStateManager() {

		imageLoader = new ImageLoader();
		preferenceManager = new PreferenceManager();
		audioPlayer = new AudioPlayer(this);
		acheivementManager = new AcheivementManager(this);

		currentState = -1;
		lastState = -1;
		incomingState = -1;
		nextState = -1;
		
		showFPS = Boolean.valueOf(preferenceManager.get(PreferenceManager.FPS));
		showUPS = Boolean.valueOf(preferenceManager.get(PreferenceManager.UPS));
		fullscreen = Boolean.valueOf(preferenceManager.get(PreferenceManager.FULLSCREEN));
		frameLimit = Boolean.valueOf(preferenceManager.get(PreferenceManager.FRAMELIMIT));
		takeScreenshot = false;
		
		score = 0;
		
		scale = Integer.valueOf(preferenceManager.get(PreferenceManager.SCALE));
		
		lowButtonHeight = GameFrame.HEIGHT - 30;
		buttonHeight = 80;
		titleHeight = 40;
		buttonsPerSide = 6;
		
		lastGameStates = new int[5];
		gameStates = new ArrayList<GameState>();
		colorSchemes = new ArrayList<ColorScheme>();

		colorSchemes.add(new ColorScheme(new Color(255, 115, 115), new Color(
				220, 0, 0), new Color(50, 0, 0), new Color(180, 0, 0),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		colorSchemes.add(new ColorScheme(new Color(255, 170, 90), new Color(
				175, 90, 0), new Color(115, 60, 0), new Color(255, 125, 0),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		colorSchemes.add(new ColorScheme(new Color(255, 255, 130), new Color(
				180, 180, 0), new Color(110, 110, 0), new Color(255, 255, 0),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		colorSchemes.add(new ColorScheme(new Color(140, 255, 140), new Color(
				10, 130, 0), new Color(5, 60, 0), new Color(20, 255, 20),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		colorSchemes.add(new ColorScheme(new Color(140, 245, 255), new Color(0,
				150, 165), new Color(0, 80, 80), new Color(0, 230, 255),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		colorSchemes.add(new ColorScheme(new Color(115, 175, 255), new Color(0,
				70, 160), new Color(0, 40, 90), new Color(0, 110, 255),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		colorSchemes.add(new ColorScheme(new Color(190, 135, 255), new Color(
				75, 0, 160), new Color(40, 0, 85), new Color(120, 0, 255),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		colorSchemes.add(new ColorScheme(new Color(255, 170, 240), new Color(
				150, 0, 115), new Color(100, 0, 75), new Color(255, 0, 200),
				new Color(0, 0, 0), new Color(0, 0, 0)));

		currentScheme = Integer.valueOf(preferenceManager.get(PreferenceManager.COLORSCHEME));
		
		sublines = new String[]{"It's legal!", "Gluten-free!", "Play me!", "Free!", "By Tyler!"};

		gameStates.add(new MenuState(this));
		gameStates.add(new HelpState(this));
		gameStates.add(new OptionState(this));
		gameStates.add(new LevelState(this));
		gameStates.add(new PauseState(this));
		gameStates.add(new EndState(this));
		gameStates.add(new LeaderboardState(this));
		gameStates.add(new AreYouSureState(this));
		gameStates.add(new ShopState(this));
		gameStates.add(new ResetStatState(this));
		gameStates.add(new AcheivementState(this));
		this.setState(MENUSTATE);

		titleFont = new Font("Century Gothic", Font.PLAIN, 28);
		font = new Font("Arial", Font.PLAIN, 12);
		sysFont = new Font("System", Font.PLAIN, 10);

	}

	public void setState(int state) {

		int ran = new Random().nextInt(sublines.length);
		subline = sublines[ran];
		
		for (int i = lastGameStates.length - 1; i > 0; i--) {
			
			lastGameStates[i] = lastGameStates[i - 1];
			
		}
		lastGameStates[0] = currentState;
		
		if (currentState != -1) {

			lastState = currentState;
			incomingState = state;
			gameStates.get(currentState).reset();

		}

		currentState = state;
		
		gameStates.get(currentState).init();

	}

	public void update() {
		
		gameStates.get(currentState).update();
		audioPlayer.update();
		acheivementManager.update();
		
		showFPS = Boolean.valueOf(preferenceManager.get(PreferenceManager.FPS));
		showUPS = Boolean.valueOf(preferenceManager.get(PreferenceManager.UPS));
		fullscreen = Boolean.valueOf(preferenceManager.get(PreferenceManager.FULLSCREEN));
		frameLimit = Boolean.valueOf(preferenceManager.get(PreferenceManager.FRAMELIMIT));
		currentScheme = Integer.valueOf(preferenceManager.get(PreferenceManager.COLORSCHEME));
		scale = Integer.valueOf(preferenceManager.get(PreferenceManager.SCALE));
		
	}

	public void draw(Graphics2D g) {

		gameStates.get(currentState).draw(g);
		acheivementManager.draw(g);

		g.setFont(sysFont);

		int x;
		int y;
		String s;

		if (showFPS) {

			s = "FPS: " + FPS;
			x = GameFrame.WIDTH - (g.getFontMetrics().stringWidth(s) + 2);
			y = (g.getFontMetrics().getHeight() + 7)
					- g.getFontMetrics().getDescent();
			g.drawString(s, x, y);

		}

		if (showUPS) {

			s = "UPS: " + UPS;
			x = GameFrame.WIDTH - (g.getFontMetrics().stringWidth(s) + 2);
			y = (g.getFontMetrics().getHeight()
					+ g.getFontMetrics().getHeight() + 7)
					- g.getFontMetrics().getDescent();
			g.drawString(s, x, y);

		}
		
	}

	public void keyPressed(int k) {

		gameStates.get(currentState).keyPressed(k);

	}

	public void keyReleased(int k) {

		gameStates.get(currentState).keyReleased(k);
		
		switch (k) {
		
		case KeyEvent.VK_F1:
			
			takeScreenshot = true;
			
			break;
		
		}

	}

	public void changeSchemes() {

		int i = currentScheme + 1;

		if (i == colorSchemes.size()) {

			i = 0;

		}

		preferenceManager.set(PreferenceManager.COLORSCHEME, i + "");

	}
	
	public Font getTitleFont() {
		return titleFont;
	}

	public Font getFont() {
		return font;
	}

	public Font getSysFont() {
		return sysFont;
	}

	public ColorScheme getColorScheme() {
		return colorSchemes.get(currentScheme);
	}

	public ArrayList<GameState> getStates() {
		return gameStates;
	}

	public void setLastState(int i) {
		lastState = i;
	}
	
	public int getLastState() {
		return lastState;
	}

	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	public int getFPS() {
		return FPS;
	}

	public void setFPS(int fPS) {
		FPS = fPS;
	}

	public int getUPS() {
		return UPS;
	}

	public void setUPS(int uPS) {
		UPS = uPS;
	}

	public int getIncomingState() {
		return incomingState;
	}
	
	public void setIncomingState(int i) {
		incomingState = i;
	}

	public int getButtonHeight() {
		return buttonHeight;
	}

	public int getTitleHeight() {
		return titleHeight;
	}

	public int getButtonsPerSide() {
		return buttonsPerSide;
	}
	
	public String getSubline() {
		return subline;
	}
	
	public PreferenceManager getPreferenceManager() {
		return preferenceManager;
	}

	public boolean getLastAnswer() {
		return lastAnswer;
	}

	public void setLastAnswer(boolean lastAnswer) {
		this.lastAnswer = lastAnswer;
	}
	
	public int[] getLastGameStates() {
		return lastGameStates;
	}

	public boolean getStillProgress() {
		return stillProgress;
	}

	public void setStillProgress(boolean stillProgress) {
		this.stillProgress = stillProgress;
	}

	public boolean takeScreenshot() {
		return takeScreenshot;
	}

	public void setTakeScreenshot(boolean takeScreenshot) {
		this.takeScreenshot = takeScreenshot;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public int getScale() {
		return scale;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLowButtonHeight() {
		return lowButtonHeight;
	}

	public int getNextState() {
		return nextState;
	}

	public void setNextState(int nextState) {
		this.nextState = nextState;
	}
	
	public boolean getFrameLimit() {
		return frameLimit;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public AcheivementManager getAcheivementManager() {
		return acheivementManager;
	}
	
}
