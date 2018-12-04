package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.sun.java.swing.plaf.motif.MotifBorders.BevelBorder;

import Game.Game;
import GameObjects.Fruit;
import GameObjects.GameObject;
import GameObjects.Packman;
import Maps.Map;


public class MyFrame extends JFrame implements ComponentListener{

	// JBackground component is an object that contains all GameSpirits elements and represent the "Game" object in UI.
	private JBackground jb;
	
	// pnl_toolbar contains all the management buttons such load,save and run.

	
	// pnl_toolbar's Buttons
	private JButton btn_load; // load game from file.
	private JButton btn_save; // save game to file.
	private JButton btn_run; // run current loaded game.
	
	// starting size of MyFrame.
	public int SIZEW = 1000;
	public int SIZEH = 600;
	
	private Game game;
	
	private Map map = new Map(); // temp;


	/**
	 * Serialization version UIDl
	 */
	private static final long serialVersionUID = 121312L;

	/**
	 * Constructor of MyFrame, initialization and showing the frame.
	 */
	public MyFrame() {
		super();
		init();
		this.setTitle("Packman!!!");
		this.setVisible(true);
		System.out.println("Making MyFrame visible...");
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


		// Set Component's settings
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

		////////////////
		// Background //
		////////////////
		Image img;
		img = loadImage("Ex3\\Ariel1.png"); // Should come from MAP
		jb = new JBackground(img);
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
		
		JMenu menu = new JMenu("File");
		menu.setFont(menuFont);
		menu.setCursor(handCursor);
		menu.setBorder(BorderFactory.createSoftBevelBorder(0));

		
		/// create buttons for toolbar
		JMenuItem i1 = new JMenuItem("Load game");
		i1.setFont(itemFont);
		i1.setCursor(handCursor);
		i1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGame();
			}
		});
		
		JMenuItem i2 = new JMenuItem("Save game");
		i2.setFont(itemFont);
		i2.setCursor(handCursor);
		i2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveGame();
			}
		});
		
		btn_run = new JButton();
		btn_run.setText("<- [RUN] ->");
		btn_run.setForeground(Color.BLUE);
		btn_run.setCursor(handCursor);
		btn_run.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MyFrame.this.Test();
				
			}
		});
		
		// pack menu
		menu.add(i1);
		menu.add(i2);
		menubar.add(menu);
		menubar.add(btn_run);
		
		/* pack it up. from last to first generated components to create the 'Z' height layer property and 
		 stack components */
		setJMenuBar(menubar);
		add(jb);

	}

	/**
	 * Loading an Image from file.
	 * @see: https://stackoverflow.com/questions/18777893/jframe-background-image
	 * @param path to the file, Ex: "Ex3\\Pacman.png" for Windows system.
	 * @return Image Object (can be casted into BufferedImage)
	 */
	public static Image loadImage(String path) {
		BufferedImage i = null;
		try {
			i =  ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return i;//i.getScaledInstance(i.getWidth(), i.getWidth(), Image.SCALE_SMOOTH);
	}
	
	/**
	 * @see https://stackoverflow.com/questions/8639567/java-rotating-images
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
		// update Background
		if(jb != null) {
			jb.setSize(this.size());

			this.map.updateScreenRange(this.getWidth(), this.getHeight()); // TEMP
			// TODO: set the scalefactor inside the Map Object.
			double scaleFactorX, scaleFactorY;
			scaleFactorX = ((double)getWidth())/SIZEW;
			scaleFactorY = ((double)getHeight())/SIZEH;

			for(Component c : jb.getComponents()) {
				if(c instanceof GameSpirit) {
					GameSpirit gameComponent = (GameSpirit) c; 
					gameComponent.setBounds(
							(int)(gameComponent.startX*scaleFactorX),
							(int)(gameComponent.startY*scaleFactorY),
							(int)(gameComponent.startWidth*scaleFactorX),
							(int)(gameComponent.startHeight*scaleFactorY)
							);
				} // if component instanceof GameSpirit
			} // for over components
		} // if jb != null 

	}
	
	/**
	 * Set a new Game object associated with this MyFrame.
	 * @param game - the game to set.
	 */
	public void setGame(Game game) {
		this.game = game;
		
		if(game == null) return;
			refreshGameUI();
	}
	
	/**
	 * Load game from CSV file.
	 * @param path - to the file
	 */
	public void loadGame() {
		FileDialog fd = new FileDialog(new Frame(), "Load Game File (CSV)",FileDialog.LOAD);
		fd.setDirectory("/");
		fd.setFile("*.csv");
		fd.setVisible(true);

		if(fd.getFiles().length != 0)
			setGame(new Game(fd.getFiles()[0].getAbsolutePath()));
	}
	
	/**
	 * reloading all game components and objects.
	 * TODO: might (?) be integrated inside JBackground
	 */
	public void refreshGameUI() {
		if(game == null || jb == null) return;
		
		jb.removeAll();
		
		for(GameObject obj : game.getObjects()) {
			// TODO: set the width and height inside the
			if(obj instanceof Packman)
				jb.add(new GameSpirit(obj, Packman.width, Packman.height, map)); 
			else if(obj instanceof Fruit)
				jb.add(new GameSpirit(obj, Fruit.width, Fruit.height, map));
		}
	}
	
	/**
	 * Saving the current game to a KML file
	 * TODO: implement
	 */
	public void saveGame() {
		FileDialog fd = new FileDialog(new Frame(), "Save Game File (KML)",FileDialog.SAVE);
		fd.setDirectory("/");
		fd.setFile("*.kml");
		fd.setVisible(true);
	}
	
	/**
	 * running the current game.
	 * TODO: implement
	 */
	public void runGame() {
		
	}
	
	/**
	 * [Developer Note] Only for debug and testing purposes
	 */
	public void Test() {
		if(jb == null) return;
		
		for(Component c : jb.getComponents()) {
			if(c instanceof GameSpirit) {
				GameSpirit gameComponent = (GameSpirit) c;
				gameComponent.moveByPixel(10, 0);
			}
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {}

	@Override
	public void componentMoved(ComponentEvent arg0) {}

	@Override
	public void componentShown(ComponentEvent arg0) {}


}
