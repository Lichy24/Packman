package GameObjects;

import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;

import GUI.MyFrame;
import Geom.Point3D;
import Path.Path;

public class Packman extends GameObject {

    private double radius,speed;
	private static final String packmanImagePath = "GameData\\PACMAN.png";
	private static final Image packmanImage = MyFrame.loadImage(packmanImagePath);

	private Path path;
	
    public Packman(double lat, double lon, double alt,int id, double radius, double speed) {
        setPoint(new Point3D(lat,lon,alt));
        setId(id);
        setRadius(radius);
        setSpeed(speed);
        
        // initial values for this type
        setType(TYPE.P);
        setInitialWidth(31);
        setInitialHeight(31);
    	setSpirit(packmanImage);
    }
    
    /**
     *  creates an empty Object with default values
     */
    public Packman() {
    	this(0, 0, 0, (int)(Math.random()*432133), 1, 1);// TODO: do not make it random, count ID's instead
    }

    public double getRadius() {
        return radius;
    }

    public double getSpeed() {
        return speed;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Packman: [ "+super.toString()+" , Speed: "+getSpeed()+" , Radius: "+getRadius()+" ]";
    }

	/**
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	/** 
	 * NOT COMPLETE
	 */
	public void getTimeStamps() {
		long time = 0;
		HashMap<Point3D, String> timestamps = new HashMap<>();
		Iterator<Point3D> iter = path.iterator();
		Point3D p1 = iter.next();
		while(iter.hasNext()) {
			Point3D p2 = iter.next();
		//	timestamps.put(iter.next(), );
			
		}
	}


}
