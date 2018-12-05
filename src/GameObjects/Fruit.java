package GameObjects;

import Geom.Point3D;

public class Fruit extends GameObject {

    final public static int width = 25;
    final public static int height = 25;

    public Fruit(double lat, double lon, double alt,int id, TYPE type) {
        setPoint(new Point3D(lat,lon,alt));
        setId(id);
        setType(type);
    }

    @Override
    public String toString() {
        return "Fruit: [ "+super.toString()+" ]";
    }
}
