package GUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import Algorithms.PathFinding;
import GUI.Animation.SimulatePath;
import Game.Game;
import GameObjects.Fruit;
import GameObjects.Packman;
import Geom.Point3D;
import Maps.Map;
import Path.Path;


/**
 * Singleton
 * @author neyne
 *
 */
public final class MyFrame extends JFrame implements ComponentListener{

	private static MyFrame instance;
	
	final public static boolean DEBUG = true;
	
	// JBackground component is an object that contains all GameSpirits elements and represent the "Game" object in UI.
	private JBackground jb;
	
	// images and media, see "init" function for initialization.
	ImageIcon saveIcon;
	ImageIcon loadIcon;
	ImageIcon runIcon;
	ImageIcon exitIcon;
	ImageIcon pacmanIcon;
	ImageIcon fruitIcon;
	
	// menubar's Buttons
	private JButton btn_run; // run current loaded game.
	private JButton btn_exitDropMode; // stop the abillity to drop game objects
	private JButton btn_stopSimulation;
	
	// Simulation
	private SimulatePath simulation;
	private boolean simulating = false;
	
	// starting size of MyFrame.
	public final int SIZEW = 1433;
	public final int SIZEH = 642;



	/**
	 * Serialization version UIDl
	 */
	private static final long serialVersionUID = 121312L;

	
	public static MyFrame getInstance() {
		if(instance == null)
			instance = new MyFrame();
		
		return instance;
	}
	
	/**
	 * Constructor of MyFrame, initialization and showing the frame.
	 */
	private MyFrame() {
		super();
		init();
		this.setTitle("Packman!!!");
	}
	

	/**
	 * Initialize JComponents, toolbar, background image and such.
	 */
	private void init() {
		/////////////////////////
		// Set global settings //
		/////////////////////////
		setSize(SIZEW,SIZEH);
		setMinimumSize(new Dimension(600,300));
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addComponentListener(this); // for resizing the component


		//// Set Component's settings
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		 // icons
		saveIcon = new ImageIcon(ImageFactory.getImageFromDisk("GameData\\save_icon.png"));
		loadIcon = new ImageIcon(ImageFactory.getImageFromDisk("GameData\\load_icon.png"));
		runIcon = new ImageIcon(ImageFactory.getImageFromDisk("GameData\\run_icon.png"));
		exitIcon = new ImageIcon(ImageFactory.getImageFromDisk("GameData\\exit_icon.png"));
		pacmanIcon = new ImageIcon(ImageFactory.getImageFromDisk("GameData\\pacman_icon.png"));
		fruitIcon = new ImageIcon(ImageFactory.getImageFromDisk("GameData\\fruit_icon.png"));

	

		////////////////
		// Background //
		////////////////
		jb = new JBackground();
		jb.setBounds(0,0,SIZEW,SIZEH);



		/////////////
		// Toolbar //
		/////////////

		int menuFontSize, itemFontSize;
		menuFontSize = 18;
		itemFontSize = 22;
		
		Font menuFont = new Font("Arial", Font.PLAIN, menuFontSize);
		Font itemFont = new Font("Arial", Font.PLAIN, itemFontSize);
		
		JMenuBar menubar = new JMenuBar();
		
		// creating menus
		JMenu menu = new JMenu("File");
		menu.setFont(menuFont);
		menu.setCursor(handCursor);
		menu.setBorder(BorderFactory.createSoftBevelBorder(0));
		
		JMenu menuObjects = new JMenu("Game Objects");
		menuObjects.setFont(menuFont);
		menuObjects.setCursor(handCursor);
		menuObjects.setBorder(BorderFactory.createSoftBevelBorder(0));

		
		/// create buttons for menubar's menus
		JMenuItem i1 = new JMenuItem("Load game");
		i1.setFont(itemFont);
		i1.setCursor(handCursor);
		i1.setBackground(Color.cyan);
		i1.setIcon(loadIcon);
		i1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {loadGame();}});
		
		JMenuItem i2 = new JMenuItem("Save game");
		i2.setFont(itemFont);
		i2.setCursor(handCursor);
		i2.setBackground(Color.cyan);
		i2.setIcon(saveIcon);
		i2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {saveGame();}});
		
		JMenuItem gmobjAddPackman = new JMenuItem("Pokemon +");
		gmobjAddPackman.setFont(itemFont);
		gmobjAddPackman.setCursor(handCursor);
		gmobjAddPackman.setBackground(Color.ORANGE); 
		gmobjAddPackman.setIcon(pacmanIcon);
		gmobjAddPackman.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {addPackman();}});
		
		JMenuItem gmobjAddFruit = new JMenuItem("Yummy +");
		gmobjAddFruit.setFont(itemFont);
		gmobjAddFruit.setCursor(handCursor);
		gmobjAddFruit.setBackground(Color.ORANGE); 
		gmobjAddFruit.setIcon(fruitIcon);
		gmobjAddFruit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {addFruit(); }});
		
		btn_run = new JButton();
		btn_run.setText("RUN");
		btn_run.setForeground(Color.getHSBColor(0.35f, 1.0f, 0.5f));
		btn_run.setCursor(handCursor);
		btn_run.setIcon(runIcon);
		btn_run.setHorizontalTextPosition(SwingConstants.LEFT); // TO SLIDE ICON TO THE RIGHT
		btn_run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {MyFrame.this.Test();}});
		
		btn_exitDropMode = new JButton();
		btn_exitDropMode.setText("stop dropMode");
		btn_exitDropMode.setVisible(false);
		btn_exitDropMode.setIcon(exitIcon);
		btn_exitDropMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {exitDropMode();}});
		
		btn_stopSimulation = new JButton();
		btn_stopSimulation.setText("Stop simulation");
		btn_stopSimulation.setVisible(false);
		btn_stopSimulation.setIcon(exitIcon);
		btn_stopSimulation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {stop();}});
		
		// pack menus
		menu.add(i1);
		menu.add(i2);
		menuObjects.add(gmobjAddPackman);
		menuObjects.add(gmobjAddFruit);
		menubar.add(menu);
		menubar.add(menuObjects);
		menubar.add(btn_exitDropMode);
		menubar.add(btn_run);
		menubar.add(btn_stopSimulation);
		
		/* pack it up. from last to first generated components to create the 'Z' height layer property and 
		 stack components */
		setJMenuBar(menubar);
		add(jb);

	}


	
	/**
	 * @see "https://stackoverflow.com/questions/8639567/java-rotating-images"
	 * @param img
	 * @param angle
	 * @return
	 */
	public static Image rotateImage(Image img, float angle) {
		BufferedImage image = (BufferedImage) img;

		// Rotation information

		double rotationRequired = Math.toRadians (angle);
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		return op.filter(image, null);
	}

	public void componentResized(ComponentEvent ce) {
		rescaleGameUI();
	}
	
	/**
	 * Rescaling the GameUI components to match screen size, width and height <br>
	 * adjusting position and image size.
	 */
	public void rescaleGameUI() {
		// update Background
		if(jb != null) {
			jb.setSize(this.size());
			
			if(jb.getGame() == null)
				return;
			
			Map map = jb.getGame().getMap();
			
			map.updateScreenRange(this.getWidth(), this.getHeight());

			for(Component c : jb.getComponents()) {
				if(c instanceof GameSpirit) {
					GameSpirit gameComponent = (GameSpirit) c; 
					map.updateLocationOnScreen(gameComponent);
				} // if component instanceof GameSpirit
			} // for over components
		} // if jb != null 
	}
	
	/**
	 * Set a new Game object associated with this MyFrame.
	 * @param game - the game to set.
	 */
	public void setGame(Game game) {
		jb.setGame(game);
		rescaleGameUI();
	}
	
	/**
	 * Load game from CSV file.
	 * @param *path - to the file
	 */
	public void loadGame() {
		FileDialog fd = new FileDialog(new Frame(), "Load Game File (CSV)",FileDialog.LOAD);
		fd.setDirectory("/");
		fd.setFile("*.csv");
		fd.setVisible(true);

		if(fd.getFiles().length != 0)
			setGame(new Game(fd.getFiles()[0].getAbsolutePath()));

		// TEMP
		//TODO: remove.
		PathFinding pf = new PathFinding(jb);
		pf.calcPath();

	}
	
	
	
	/**
	 * Saving the current game to a CSV file
	 * TODO: implement
	 */
	public void saveGame() {
		if(jb == null) return;
		
		FileDialog fd = new FileDialog(new Frame(), "Save Game File (CSV)",FileDialog.SAVE);
		fd.setDirectory("/");
		fd.setFile("*.csv");
		fd.setVisible(true);
		if (fd.getFiles().length != 0)
			jb.getGame().toCsv(fd.getDirectory()+fd.getFile());
	}
	
	/**
	 * running the current game.
	 * TODO: implement
	 */
	public void runGame() {
		
	}
	
	
	/**
	 * setting the dropMode of this.JButton to true for allowing dropping items on map from type Packman.
	 */
	public void addPackman() {
		if(jb == null) return;
		
		jb.dropItem = new Packman();
		enterDropMode();
	}
	
	/**
	 * setting the dropMode of this.JButton to true for allowing dropping items on map from type Fruit.
	 */
	public void addFruit() {
		if(jb == null) return;
		
		jb.dropItem = new Fruit();
		enterDropMode();
	}
	
	/**
	 * setting the dropMode of this.JButton to true for allowing dropping items on map.
	 */
	public void enterDropMode() {
		if(jb == null) return;
		
		jb.dropMode = true;
		btn_exitDropMode.setVisible(true);
	}
	
	/**
	 * Handles the click of exiting drop Mode
	 */
	public void exitDropMode() {
		if(jb == null) return;
		
		jb.dropMode = false;
		btn_exitDropMode.setVisible(false);
	}
	
	/**
	 * Stop running, stopping simulation
	 */
	public void stop() {
		
		simulation.interrupt();
		
		try {
			simulation.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		btn_run.setVisible(true);
		btn_stopSimulation.setVisible(false);
	}
	
	/**
	 * [Developer Note] Only for debug and testing purposes
	 */
	public void Test() {
		if(jb == null) return;
		
		btn_stopSimulation.setVisible(true);
		btn_run.setVisible(false);
		
		/*Path pat = new Path();
		pat.add(new Point3D(35.211222,32.104496,30));
		pat.add(new Point3D(35.207462,32.102482,30));
		pat.add(new Point3D(35.208462,32.103482,30)); //different
		pat.add(new Point3D(35.203462,32.103482,30)); //different
		pat.add(new Point3D(35.204462,32.104082,30)); //different
		

		Path pat2 = new Path();
		pat2.add(new Point3D(35.204462,32.104082,30)); //different
		pat2.add(new Point3D(35.203462,32.103482,30)); //different
		pat2.add(new Point3D(35.208462,32.103482,30)); //different
		pat2.add(new Point3D(35.207462,32.102482,30));
		pat2.add(new Point3D(35.211222,32.104496,30));

		

		GameSpirit s1 = (GameSpirit) jb.getComponent(1);
		Packman p1 = (Packman) s1.getGameObj();
		p1.setPath(pat);
		
		GameSpirit s2 = (GameSpirit) jb.getComponent(2);
		Packman p2 = (Packman) s2.getGameObj();
		p2.setPath(pat2);
		*/

		simulation = new SimulatePath(jb);
		simulation.start();
		
//		Map map = jb.getGame().getMap();
//		
//		for(Component c : jb.getComponents()) {
//			if(c instanceof GameSpirit) {
//				GameSpirit gameComponent = (GameSpirit) c;
//				map.moveLocationByPixels(gameComponent, -10, 10);
//			}
//		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {}

	@Override
	public void componentMoved(ComponentEvent arg0) {}

	@Override
	public void componentShown(ComponentEvent arg0) {}


}
