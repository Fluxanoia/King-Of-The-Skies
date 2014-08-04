package kots.gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import kots.game.Obstacle;
import kots.game.Player;
import kots.game.PointBubble;
import kots.game.PowerManager;
import kots.game.Projectile;
import kots.game.Warning;
import kots.main.GameFrame;
import kots.main.PreferenceManager;
import kots.tiles.Background;
import kots.tiles.Tile;

public class LevelState extends GameState {

	public static final int TILESIZE = 20;

	private PowerManager powerManager;
	private Background bg;
	private Player player;

	private int lives;
	private int score;
	private int abilitiesUsed;

	private ArrayList<Projectile> projectiles;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Tile> tiles;
	private BufferedImage tileset;

	private ArrayList<Warning> warnings;
	private int warningTicks;
	private ArrayList<PointBubble> pointbubbles;
	private int bubbleTicks;

	public LevelState(GameStateManager gsm) {

		super(gsm);
		
		bg = new Background(gsm, "/backgrounds/cloudy.png");
		bg.setVector(GameFrame.FRAMESPEED, 0);

		powerManager = new PowerManager(gsm);
		player = new Player(gsm, 100, GameFrame.HEIGHT / 2);

		score = 0;
		abilitiesUsed = 0;

		tileset = gsm.getImageLoader().loadImage("/tiles/ground.png");

		tiles = new ArrayList<Tile>();
		obstacles = new ArrayList<Obstacle>();
		projectiles = new ArrayList<Projectile>();
		warnings = new ArrayList<Warning>();
		warningTicks = new Random().nextInt(100) + 101;
		pointbubbles = new ArrayList<PointBubble>();
		bubbleTicks = new Random().nextInt(400) + 101;

	}
	
	public void init() {
		
		if (gsm.getLastState() != GameStateManager.PAUSESTATE) {

			score = 0;
			lives = Integer.valueOf(gsm.getPreferenceManager().get(
					PreferenceManager.ABILITY1));

			powerManager = new PowerManager(gsm);

			tiles = new ArrayList<Tile>();
			obstacles = new ArrayList<Obstacle>();
			projectiles = new ArrayList<Projectile>();
			warnings = new ArrayList<Warning>();
			warningTicks = new Random().nextInt(100) + 101;
			pointbubbles = new ArrayList<PointBubble>();
			bubbleTicks = new Random().nextInt(400) + 101;

			int amountToAdd = GameFrame.WIDTH / TILESIZE;
			amountToAdd += 5;

			for (int i = 0; i < amountToAdd; i++) {

				tiles.add(new Tile(tileset
						.getSubimage(0, 0, TILESIZE, TILESIZE), null, i
						* TILESIZE, GameFrame.HEIGHT - TILESIZE));

			}

		}
		
	}

	@SuppressWarnings("unchecked")
	public void draw(Graphics2D g) {

		bg.draw(g);

		g.setTransform(player.getTransform());
		g.drawImage(player.getImage(), -player.getImage().getWidth() / 2,
				-player.getImage().getHeight() / 2, null);
		g.setTransform(new AffineTransform());

		ArrayList<Projectile> tempProjs = (ArrayList<Projectile>) projectiles
				.clone();

		for (Projectile p : tempProjs) {

			p.draw(g);

		}

		ArrayList<Obstacle> tempObs = (ArrayList<Obstacle>) obstacles.clone();

		for (Obstacle ob : tempObs) {

			ob.draw(g);

		}

		ArrayList<Tile> tempTiles = (ArrayList<Tile>) tiles.clone();

		for (Tile t : tempTiles) {

			t.draw(g);

		}

		ArrayList<PointBubble> tempPointBubbles = (ArrayList<PointBubble>) pointbubbles
				.clone();

		for (PointBubble pb : tempPointBubbles) {

			pb.draw(g);

		}

		ArrayList<Warning> tempWarn = (ArrayList<Warning>) warnings.clone();

		for (Warning w : tempWarn) {

			w.draw(g);

		}

		powerManager.draw(g);

		String s;
		int x;
		int y;
		Rectangle r;

		g.setFont(gsm.getFont());

		s = "Score: " + score;
		x = 10;
		y = 20;
		r = new Rectangle(x - 5, (y - 2)
				- (g.getFontMetrics().getHeight() - g.getFontMetrics()
						.getDescent()), g.getFontMetrics().stringWidth(s) + 10,
				g.getFontMetrics().getHeight() + 4);
		g.setPaint(gsm.getColorScheme().getInner());
		g.fill(r);
		g.setPaint(gsm.getColorScheme().getBorder());
		g.draw(r);
		g.setPaint(gsm.getColorScheme().getFont());
		g.drawString(s, x, y);

		s = "Lives Left: " + lives;
		x = 10;
		y = 50;
		r = new Rectangle(x - 5, (y - 2)
				- (g.getFontMetrics().getHeight() - g.getFontMetrics()
						.getDescent()), g.getFontMetrics().stringWidth(s) + 10,
				g.getFontMetrics().getHeight() + 4);
		g.setPaint(gsm.getColorScheme().getInner());
		g.fill(r);
		g.setPaint(gsm.getColorScheme().getBorder());
		g.draw(r);
		g.setPaint(gsm.getColorScheme().getFont());
		g.drawString(s, x, y);

	}

	@SuppressWarnings("unchecked")
	public void update() {

		bg.update();
		player.update();
		powerManager.update();
		gsm.getAcheivementManager().scoreChanged(score);
		
		if (player.isOutOfBounds()) {

			loseLife();

		}

		Rectangle p = new Rectangle(player.getX()
				- (player.getImage().getWidth() / 2), player.getY()
				- (player.getImage().getHeight() / 2), player.getImage()
				.getWidth(), player.getImage().getHeight());

		ArrayList<Projectile> tempProjs = (ArrayList<Projectile>) projectiles
				.clone();

		for (Projectile pp : tempProjs) {

			pp.update();

			if (!pp.isActive()) {

				projectiles.remove(pp);

			}

			if (pp.getBounds().intersects(p)) {

				loseLife();

			}

		}

		ArrayList<Obstacle> tempObs = (ArrayList<Obstacle>) obstacles.clone();

		for (Obstacle ob : tempObs) {

			ob.update();

			if (!ob.isActive()) {

				obstacles.remove(ob);

			}

			if (ob.getBounds().intersects(p)) {

				loseLife();

			}

		}

		ArrayList<PointBubble> tempPointBubbles = (ArrayList<PointBubble>) pointbubbles
				.clone();

		for (PointBubble pb : tempPointBubbles) {

			pb.update();

			if (!pb.isActive()) {

				pointbubbles.remove(pb);

			}

			if (pb.getBounds().intersects(p)) {

				pointbubbles.remove(pb);
				score += pb.getPoints();
				score += Integer.valueOf(gsm.getPreferenceManager().get(
						PreferenceManager.ABILITY3));
				gsm.getAudioPlayer().playAudio("coin.wav", false, 1);

			}

		}

		ArrayList<Warning> tempWarn = (ArrayList<Warning>) warnings.clone();

		for (Warning w : tempWarn) {

			w.update();

			if (!w.isActive()) {

				warnings.remove(w);
				this.activate(w.getIncoming());

			}

		}

		ArrayList<Tile> tempTiles = (ArrayList<Tile>) tiles.clone();
		int maxX = 0;

		for (Tile t : tempTiles) {

			t.update();

			Rectangle r = new Rectangle(t.getX(), t.getY(), TILESIZE, TILESIZE);

			if (r.getMaxX() > maxX) {

				maxX = (int) r.getMaxX();

			}

			if (t.getX() + TILESIZE < 0) {

				tiles.remove(t);

			}

		}

		if (maxX <= GameFrame.WIDTH) {

			int ran = new Random().nextInt(9);

			switch (ran) {

			case 0:
			case 1:
			case 2:
			case 3:

				tiles.add(new Tile(tileset
						.getSubimage(0, 0, TILESIZE, TILESIZE), null,
						GameFrame.WIDTH, GameFrame.HEIGHT - TILESIZE));

				break;

			case 4:

				tiles.add(new Tile(tileset.getSubimage(120, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH, GameFrame.HEIGHT
						- TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(140, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + TILESIZE,
						GameFrame.HEIGHT - TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(160, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + (TILESIZE * 2),
						GameFrame.HEIGHT - TILESIZE));

				break;

			case 5:

				tiles.add(new Tile(tileset.getSubimage(60, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH, GameFrame.HEIGHT
						- TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(100, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + TILESIZE,
						GameFrame.HEIGHT - TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(80, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + (TILESIZE * 2),
						GameFrame.HEIGHT - TILESIZE));

				tiles.add(new Tile(tileset
						.getSubimage(0, 0, TILESIZE, TILESIZE), null,
						GameFrame.WIDTH + (TILESIZE * 3), GameFrame.HEIGHT
								- TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(20, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH, GameFrame.HEIGHT
						- (TILESIZE * 2)));

				tiles.add(new Tile(tileset
						.getSubimage(0, 0, TILESIZE, TILESIZE), null,
						GameFrame.WIDTH + TILESIZE, GameFrame.HEIGHT
								- (TILESIZE * 2)));

				tiles.add(new Tile(tileset.getSubimage(40, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + (TILESIZE * 2),
						GameFrame.HEIGHT - (TILESIZE * 2)));

				break;

			case 6:

				tiles.add(new Tile(tileset.getSubimage(60, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH, GameFrame.HEIGHT
						- TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(80, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + TILESIZE,
						GameFrame.HEIGHT - TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(220, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH, GameFrame.HEIGHT
						- (TILESIZE * 2)));

				tiles.add(new Tile(tileset.getSubimage(240, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + TILESIZE,
						GameFrame.HEIGHT - (TILESIZE * 2)));

				tiles.add(new Tile(tileset.getSubimage(260, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH, GameFrame.HEIGHT
						- (TILESIZE * 3)));

				tiles.add(new Tile(tileset.getSubimage(280, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + TILESIZE,
						GameFrame.HEIGHT - (TILESIZE * 3)));

				break;

			case 7:

				tiles.add(new Tile(tileset.getSubimage(120, 0, TILESIZE,
						TILESIZE), tileset.getSubimage(320, 0, TILESIZE,
						TILESIZE), GameFrame.WIDTH, GameFrame.HEIGHT - TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(140, 0, TILESIZE,
						TILESIZE), tileset.getSubimage(320, 0, TILESIZE,
						TILESIZE), GameFrame.WIDTH + TILESIZE, GameFrame.HEIGHT
						- TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(160, 0, TILESIZE,
						TILESIZE), tileset.getSubimage(320, 0, TILESIZE,
						TILESIZE), GameFrame.WIDTH + (TILESIZE * 2),
						GameFrame.HEIGHT - TILESIZE));

				break;

			case 8:

				tiles.add(new Tile(tileset.getSubimage(60, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH, GameFrame.HEIGHT
						- TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(100, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + TILESIZE,
						GameFrame.HEIGHT - TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(100, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + (TILESIZE * 2),
						GameFrame.HEIGHT - TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(100, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + (TILESIZE * 3),
						GameFrame.HEIGHT - TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(80, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + (TILESIZE * 4),
						GameFrame.HEIGHT - TILESIZE));

				tiles.add(new Tile(tileset.getSubimage(340, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH, GameFrame.HEIGHT
						- (TILESIZE * 2)));

				tiles.add(new Tile(tileset.getSubimage(120, 0, TILESIZE,
						TILESIZE), tileset.getSubimage(320, 0, TILESIZE,
						TILESIZE), GameFrame.WIDTH + TILESIZE, GameFrame.HEIGHT
						- (TILESIZE * 2)));

				tiles.add(new Tile(tileset.getSubimage(140, 0, TILESIZE,
						TILESIZE), tileset.getSubimage(320, 0, TILESIZE,
						TILESIZE), GameFrame.WIDTH + (TILESIZE * 2),
						GameFrame.HEIGHT - (TILESIZE * 2)));

				tiles.add(new Tile(tileset.getSubimage(160, 0, TILESIZE,
						TILESIZE), tileset.getSubimage(320, 0, TILESIZE,
						TILESIZE), GameFrame.WIDTH + (TILESIZE * 3),
						GameFrame.HEIGHT - (TILESIZE * 2)));

				tiles.add(new Tile(tileset.getSubimage(360, 0, TILESIZE,
						TILESIZE), null, GameFrame.WIDTH + (TILESIZE * 4),
						GameFrame.HEIGHT - (TILESIZE * 2)));

				break;

			}

		}

		if (warningTicks == 0) {

			warningTicks = new Random().nextInt(100) + 101;

			int x = GameFrame.WIDTH - 10;
			int y = new Random().nextInt(181) + 21;
			int size = new Random().nextInt(3) + 1;
			Object o = null;

			int ran = new Random().nextInt(2);

			switch (ran) {

			case 0:

				o = new Obstacle(gsm, x, y, size);

				break;

			case 1:

				o = new Projectile(gsm, x, y);

				break;

			}

			warnings.add(new Warning(gsm, x, y, o));

		} else {

			warningTicks--;

		}

		if (bubbleTicks == 0) {

			int points = new Random().nextInt(3);
			switch (points) {

			case 0:

				points = 1;

				break;

			case 1:

				points = 2;

				break;

			case 2:

				points = 5;

				break;

			}

			pointbubbles.add(new PointBubble(gsm, GameFrame.WIDTH + 50,
					new Random().nextInt(181) + 21, points));
			bubbleTicks = new Random().nextInt(400) + 101;

		} else {

			bubbleTicks--;

		}

	}

	public void reset() {

		if (gsm.getIncomingState() != GameStateManager.PAUSESTATE) {

			gsm.setScore(score);
			player = new Player(gsm, 100, GameFrame.HEIGHT / 2);
			abilitiesUsed = 0;
			score = 0;
			lives = Integer.valueOf(gsm.getPreferenceManager().get(
					PreferenceManager.ABILITY1));

			powerManager = new PowerManager(gsm);

			tiles = new ArrayList<Tile>();
			obstacles = new ArrayList<Obstacle>();
			projectiles = new ArrayList<Projectile>();
			warnings = new ArrayList<Warning>();
			warningTicks = new Random().nextInt(100) + 101;
			pointbubbles = new ArrayList<PointBubble>();
			bubbleTicks = new Random().nextInt(400) + 101;

			int amountToAdd = GameFrame.WIDTH / TILESIZE;
			amountToAdd += 5;

			for (int i = 0; i < amountToAdd; i++) {

				tiles.add(new Tile(tileset
						.getSubimage(0, 0, TILESIZE, TILESIZE), null, i
						* TILESIZE, GameFrame.HEIGHT - TILESIZE));

			}

		}

	}

	public void activate(Object o) {

		if (o instanceof Obstacle) {

			Obstacle ob = (Obstacle) o;
			ob.init();
			obstacles.add(ob);

		}

		if (o instanceof Projectile) {

			Projectile p = (Projectile) o;
			p.init();
			projectiles.add(p);

		}

	}

	public void keyPressed(int k) {

		powerManager.keyPressed(k);

	}

	public void keyReleased(int k) {

		powerManager.keyReleased(k);

		switch (k) {

		case KeyEvent.VK_SPACE:

			player.jump();
			gsm.getAudioPlayer().playAudio("swish.wav", false, 1);

			break;

		case KeyEvent.VK_P:

			gsm.setState(GameStateManager.PAUSESTATE);

			break;

		}

	}

	public void smash() {

		abilitiesUsed++;
		
		int destroyed = 0;
		destroyed += obstacles.size();
		destroyed += projectiles.size();
		destroyed += warnings.size();
		
		gsm.getAcheivementManager().abilityUsed(abilitiesUsed, destroyed);
		
		tiles = new ArrayList<Tile>();
		obstacles = new ArrayList<Obstacle>();
		projectiles = new ArrayList<Projectile>();
		warnings = new ArrayList<Warning>();
		warningTicks = new Random().nextInt(721) + 240;

		int amountToAdd = GameFrame.WIDTH / TILESIZE;
		amountToAdd += 7;

		for (int i = 0; i < amountToAdd; i++) {

			Tile t = new Tile(tileset.getSubimage(180, 0, TILESIZE, TILESIZE),
					null, i * TILESIZE, GameFrame.HEIGHT - TILESIZE);

			if (i == amountToAdd - 2) {

				t = new Tile(tileset.getSubimage(200, 0, TILESIZE, TILESIZE),
						null, i * TILESIZE, GameFrame.HEIGHT - TILESIZE);

			}

			if (i == amountToAdd - 1) {

				t = new Tile(tileset.getSubimage(0, 0, TILESIZE, TILESIZE),
						null, i * TILESIZE, GameFrame.HEIGHT - TILESIZE);

			}

			tiles.add(t);

		}

	}

	private void loseLife() {

		smash();
		player = new Player(gsm, 100, GameFrame.HEIGHT / 2);
		lives--;

		if (lives < 0) {

			gsm.setState(GameStateManager.ENDSTATE);

		}

	}

	public GameStateManager getGameStateManager() {
		return gsm;
	}

	public int getScore() {
		return score;
	}

}
