package kots.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import kots.gamestate.GameStateManager;
import kots.gamestate.LevelState;
import kots.tiles.Tile;

public class Obstacle {

	private Tile[] tiles;
	private Rectangle bounds;
	private boolean active;
	private BufferedImage tileset;

	private int x, y, size;

	public Obstacle(GameStateManager gsm, int x, int y, int size) {

		this.x = x;
		this.y = y;
		this.size = size;
		tileset = gsm.getImageLoader().loadImage("/tiles/obstacle.png");

	}

	public void init() {

		active = true;

		tiles = new Tile[size];

		if (size == 1) {

			tiles[0] = new Tile(tileset.getSubimage(0, 0, LevelState.TILESIZE,
					LevelState.TILESIZE), null, x, y
					- (LevelState.TILESIZE / 2));

			return;

		}

		boolean odd = ((size % 2) == 1) ? true : false;
		int topHalf = (int) Math.floor(size / 2);
		int ymod = (odd) ? LevelState.TILESIZE / 2 : 0;
		int yy = (y - ymod) - (topHalf * LevelState.TILESIZE);

		for (int i = 0; i < size; i++) {

			BufferedImage ii = tileset.getSubimage(20, 0, LevelState.TILESIZE,
					LevelState.TILESIZE);

			if (i == 0) {

				ii = tileset.getSubimage(40, 0, LevelState.TILESIZE,
						LevelState.TILESIZE);

			}

			if (i == (size - 1)) {

				tileset.getSubimage(60, 0, LevelState.TILESIZE,
						LevelState.TILESIZE);

			}

			tiles[i] = new Tile(ii, null, x, yy
					+ (i * LevelState.TILESIZE));

		}

		bounds = new Rectangle(x, yy, LevelState.TILESIZE,
				LevelState.TILESIZE * size);

	}

	public void draw(Graphics2D g) {
		
		for (int i = 0; i < tiles.length; i++) {

			tiles[i].draw(g);

		}
		
	}
	
	public void update() {

		for (int i = 0; i < tiles.length; i++) {

			tiles[i].update();

		}

		bounds = new Rectangle(tiles[0].getX(), tiles[0].getY(),
				LevelState.TILESIZE, tiles.length * LevelState.TILESIZE);

		if (bounds.getMaxX() < 0) {

			active = false;

		}

	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean isActive() {
		return active;
	}

}
