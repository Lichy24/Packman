package Maps;

import java.awt.Rectangle;

import GUI.ImageFactory;

public class TelAvivMap extends Map{
	
	/**
	 * Creates a new Map object with TelAviv coordinates and background image.
	 */
	public TelAvivMap() {
		// default values
				double x1 = 34.766460; // upper left corner
				double y1 = 32.091937;
				double x2 = 34.794639; // lower right corner
				double y2 = 32.082491;
				 
				mapRange = new MapRange(x1,y1,x2,y2);
				screenRange = new Rectangle(0, 0, 1433, 642);
				originalScreenRange = new Rectangle(0, 0, 1433, 642);

				background = ImageFactory.getImageFromDisk("GameData\\TelAviv.png");
	}

}
