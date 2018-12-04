package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

import GameObjects.GameObject;
import Maps.Map;

public class GameSpirit extends JComponent {
	
	private static final long serialVersionUID = 3817775198749911544L;
	public Image img;
	public int startWidth, startHeight, startX, startY;
	
	public GameSpirit(int x,int y, int width, int height, Image img) {
		this.img = img;
		setBounds(x, y, width, height); 
		
		/////////////////////////////////////
		// Used for Re-scaling of MyFrame. //
		/////////////////////////////////////
		this.startWidth = width;
		this.startHeight = height; 
		this.startX = x;
		this.startY = y; 
	}
	
	// TEMP ONLY
	public GameSpirit(GameObject obj, int width, int height, Map map) {
		this((int)map.getLocationOnScreen(obj).getX(), (int)map.getLocationOnScreen(obj).getY(), width, height, obj.getSpirit());
	}
	
	/**
	 * Updating this GameSpirit location using "setLocation(x,y)", but updating GameSpirit's related properties 
	 * to match MyFrame's scaling algorithm. <br>
	 * use it for moving the component around.
	 * @param x position in pixels
	 * @param y position in pixels
	 */
	public void updateLocation(int x, int y)
	{
		startX = x;
		startY = y;
		setLocation(x,y);
	}
	
	/**
	 * Moving the GameSpirit by x pixels horizontally and y pixels vertically (as right / down are positive and 
	 * left / up are negative values)
	 * @param x amount of pixels to shift right >>
	 * @param y amount of pixels to shift down 
	 */
	public void moveByPixel(int x, int y) {
		updateLocation(startX + x, startY + y);
	}
	
	

	
	
	/*
	 * [Developer Note] Status: Complete.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		/*
		 Drawing this Object's Image at Current X,Y position 
		 with the dimensions of (from (0,0) to (width,height)).
		 Note: if using MyFrame than MyFrame class is re-scaling all of it's components when resized.
		 */
		g.drawImage(img, 0,0,getWidth(),getHeight(),this);
	}
	
	
	
}
