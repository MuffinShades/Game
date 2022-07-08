package game;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main implements Runnable {
	public static boolean running = false;
	private Random r;
	private Thread thread;
	
	public static float offX = 0;
	public static float offY = 0;
	
	public static int WIDTH = 1600;
	public static int HEIGHT = 900;
	
	public static ObjectHandler hnd = new ObjectHandler();
	public static GroundHandler g_hnd = new GroundHandler();
	
	public final static int pix = 20;
	
	public static Player p;
	
	public infReader di = new infReader();
	
	public String BLOCK_INF_FILE;
	
	public boolean esc = false;
	
	public static JFrame frame;
	
	public static JPanel pane;
	
	public static boolean pauseDraw = false;
	
	public static int FPS = 0;
	
	public static Graphics g;
	
	public static JPanel winCon = new JPanel(new CardLayout());
	
	private static long lst = 0;
		
	private static int fpsCount = 0;
	
	public static float TPS = 0;
	
	/*
	 * 
	 * Main Renderer
	 * 
	 */
	
	public static JPanel pan = new JPanel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6878174851151007838L;

		@Override
		public void paintComponent(Graphics g) {
			if (g != null) {
				g.clearRect(0, 0, WIDTH, HEIGHT);
				
				Main.draw(g);
			}
			
			fpsCount++;
			
			if (System.currentTimeMillis() - lst >= 1000) {
				//System.out.println("FPS: "+fpsCount);
				FPS = fpsCount;
				fpsCount=0;
				lst = System.currentTimeMillis();
			}

			repaint();
		}
	};
	
	/*
	 *
	 * Main game class decleration
	 * 
	 */
	
	Main() {
		Log.init();
		
		frame = new Window(WIDTH, HEIGHT, "2d Game", this).win;
		
		pan.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		pan.setSize(WIDTH, HEIGHT);
		
		//add rendering panel to main container
		winCon.add(pan);
		
		frame.add(winCon);
		frame.setVisible(true);
		
		//creating the player
		p = new Player(10, -90, 20, 34, WIDTH, HEIGHT);
		
		//-----------------------------------------
		
		//key input
		frame.addKeyListener(new Keyboard(p));
		
		int xx = 0;
		
		Random r = new Random();
		
		Perlin1d perlin = new Perlin1d(r.nextInt(128), 16, 8, 8, HEIGHT, 0);
		
		for (int i = 0; i < 0; i++) {
			double n = perlin.noise(i);
			
			new Block(pix * i, Math.round(Math.round(n * pix) / pix) * pix, pix, 0);
		}
		
		double max = 4294967296.0;
		
		world w = new world(Math.round((-max) + ((max) - (-max)) * r.nextDouble()));
		
		w.Generate();
		
		this.start();
	}
	
	/*
	 *Rendering and display
	 *
	 * handles game ticks, loops, display, etc...
	 * 
	 */
	
	public void tick() {
		//update all objects
		hnd.tick();
		
		g_hnd.tick();
		
		//update the player
		p.tick();
	}
	
	public void render() {
		//handle rendering
		//pan.repaint();
	}
	
	public static void draw(Graphics ctx) {if (running) {
		ObjectHandler.RemovePendingBlocks();
		//draw background
		ctx.setColor(new Color(187, 221, 255));
		ctx.fillRect(0, 0, WIDTH, HEIGHT);
		
		//draw the objects
		hnd.render(ctx);
		
		g_hnd.render(ctx);
		
		//draw the player
		p.render(ctx);
		
		ctx.setColor(new Color(0, 0, 0));
		ctx.drawString("FPS: "+FPS+"  |  TPS: "+TPS, 5, HEIGHT - 25 - 50);
	}}
	
	/*
	 *
	 * Escape Menu
	 * 
	 * Call using change() or this.change() or Main.change()
	 * 
	 */
	
	public void change() {
		if (!esc) {
			
			pane = new JPanel();
			
			JButton b = new JButton("Exit");
			
			pane.add(b);
			
			b.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e)
	            {
	                change();
	            }
	        });
			
			pane.add(new JLabel("Stuf goes here. :)"));
			
			pane.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.5f));
			
			pane.setVisible(true);
			
			pan.setVisible(false);
			
			winCon.add(pane);
			
			winCon.setVisible(true);
			
			esc = true;
			
			//pauseDraw = false;
		} else {
			winCon.remove(pane);
			
			pan.setVisible(true);
			
			winCon.setVisible(true);
			
			esc = false;
		}
	}
	
	/*
	 *
	 * Handle start and stop of game loop
	 * 
	 */
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 * Main Key Input Events
	 * 
	 * Keydown and Keyup
	 * 
	 */
	
	public void onkeydown(int e) {
		switch(e) {
			case KeyEvent.VK_ESCAPE:
				this.change();
			break;
			case KeyEvent.VK_E:
				Player.openInventory();
			break;
		}
	}
	
	public void onkeyup(int e) {
		//keyup stuff
	}
	
	/*
	 *-------------
	 * Game Loop
	 * 
	 * Just used for ticking no rendering
	 * -------------
	 * 
	 */
	public void run() {
		float tps_count  = 0;
		//create game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 120.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				tps_count+=1;
				delta--;
			}
			
			/*if (running && tickCount <= 0) {
				//render();
				frames++;
			}*/
			
			//old
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.TPS = tps_count;
				tps_count = 0;
			}
		}
		
		stop();
	}
	
	/*
	 *
	 * Main function
	 * 
	 * runs on start
	 * 
	 */
	
	public static void main(String[] args) {
		Main g = new Main();
		
		g.BLOCK_INF_FILE = infReader.ReadFile("block_inf.inf");
		
		g.frame.addKeyListener(new MainKeyboard(g));
		
		//g.change();
	}
}
