package kots.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kots.gamestate.GameStateManager;

@SuppressWarnings("serial")
public class GameFrame extends JPanel implements Runnable, KeyListener {

	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int FRAMESPEED = -5;
	public static final String TITLE = "King of the Skies!";

	private int oldScale = 2;
	private int scale = 2;
	private boolean fullscreen = false;
	private boolean oldScreen = false;
	private boolean FRAME_LIMIT = true;

	private JFrame jf;
	private Thread thread;
	private boolean running;

	private BufferedImage image;
	private Graphics2D g;

	private GameStateManager gsm;

	public static void main(String[] args) {

		new GameFrame();

	}

	public GameFrame() {

		super();

		this.setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));
		this.setFocusable(true);
		this.requestFocus();

		Toolkit t = Toolkit.getDefaultToolkit();
		
		jf = new JFrame(TITLE);
		jf.setContentPane(this);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setUndecorated(true);
		jf.setLocation((int) (t.getScreenSize().getWidth() / 2)
				- ((WIDTH * scale) / 2), (int) (t.getScreenSize()
				.getHeight() / 2) - ((HEIGHT * scale) / 2));

		ImageIcon ico = null;
		ico = new ImageIcon(getClass().getResource(
				"/logo/King of the Skies.png"));
		jf.setIconImage(ico.getImage());

		jf.pack();
		jf.setVisible(true);

	}

	public void addNotify() {

		super.addNotify();

		if (thread == null) {

			thread = new Thread(this);
			this.addKeyListener(this);
			thread.start();

		}

	}

	public void run() {

		init();

		long lastTime = System.nanoTime();
		long lastTimer = System.currentTimeMillis();
		double nsPerTick = 1000000000 / 60;
		double delta = 0;
		int frames = 0, ticks = 0;

		int framesRan = 0;

		while (running) {

			long now = System.nanoTime();

			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			delta += 0.1;

			boolean shouldRender = (FRAME_LIMIT) ? false : true;

			while (delta >= 1) {

				ticks++;
				this.update();
				delta -= 1;
				shouldRender = true;

			}

			try {

				Thread.sleep(5);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

			if (shouldRender) {

				framesRan++;

				if (FRAME_LIMIT) {

					if ((framesRan % 2) == 0) {

						frames++;
						this.draw();
						this.drawToScreen();

					}

				} else {

					frames++;
					this.draw();
					this.drawToScreen();

				}

			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {

				lastTimer += 1000;
				gsm.setFPS(frames);
				gsm.setUPS(ticks);
				frames = 0;
				ticks = 0;

			}

		}

	}

	private void init() {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		gsm = new GameStateManager();

	}

	private void takeScreenshot() {

		File f = null;
		boolean foundPath = false;
		int i = 0;

		while (!foundPath) {

			f = new File(System.getProperty("user.home")
					+ "/Desktop/screenshot" + i + ".png");

			if (!f.exists()) {

				foundPath = true;

			}

			i++;

		}

		try {

			Rectangle screenRect;

			if (fullscreen) {

				Toolkit t = Toolkit.getDefaultToolkit();

				screenRect = new Rectangle(0, 0, (int) t.getScreenSize()
						.getWidth(), (int) t.getScreenSize().getHeight());

			} else {

				screenRect = new Rectangle(jf.getX() + jf.getInsets().left,
						jf.getY() + jf.getInsets().top, WIDTH * scale, HEIGHT
								* scale);

			}

			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, "png", f);

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Unspecified Exception", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;

		}

	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

		if (gsm != null) {
			gsm.keyPressed(e.getKeyCode());
		}

	}

	public void keyReleased(KeyEvent e) {

		if (gsm != null) {
			gsm.keyReleased(e.getKeyCode());
		}

	}

	private void update() {

		if (gsm != null) {

			gsm.update();

			FRAME_LIMIT = gsm.getFrameLimit();
			scale = gsm.getScale();
			fullscreen = gsm.isFullscreen();

			if (gsm.takeScreenshot()) {

				takeScreenshot();
				gsm.setTakeScreenshot(false);

			}

		}

		if (oldScale != scale) {

			if (!fullscreen) {

				Toolkit t = Toolkit.getDefaultToolkit();
				
				jf.setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));
				jf.setLocation((int) (t.getScreenSize().getWidth() / 2)
						- ((WIDTH * scale) / 2), (int) (t.getScreenSize()
						.getHeight() / 2) - ((HEIGHT * scale) / 2));
				jf.pack();

			}

			oldScale = scale;

		}

		if (oldScreen != fullscreen) {

			if (fullscreen) {

				Toolkit t = Toolkit.getDefaultToolkit();
				
				jf.setLocation(0, 0);
				jf.setPreferredSize(new Dimension((int) t.getScreenSize().getWidth(),(int) t.getScreenSize().getHeight()));
				jf.pack();

			} else {

				Toolkit t = Toolkit.getDefaultToolkit();
				
				jf.setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));
				jf.setLocation((int) (t.getScreenSize().getWidth() / 2)
						- ((WIDTH * scale) / 2), (int) (t.getScreenSize()
						.getHeight() / 2) - ((HEIGHT * scale) / 2));
				jf.pack();

			}

			oldScreen = fullscreen;

		}

	}

	private void draw() {

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (gsm != null) {

			gsm.draw(g);

		}

	}

	private void drawToScreen() {

		Toolkit t = Toolkit.getDefaultToolkit();

		Graphics g2 = this.getGraphics();

		if (fullscreen) {

			g2.drawImage(image, 0, 0, (int) t.getScreenSize().getWidth(),
					(int) t.getScreenSize().getHeight(), null);

		} else {

			g2.drawImage(image, 0, 0, WIDTH * scale, HEIGHT * scale, null);

		}

		g2.dispose();

	}

}
