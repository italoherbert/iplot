package italo.iplot.plot3d.g3d;

public class ComponenteObjeto3DLimite {
    
    public final static double MINX = -1;
    public final static double MINY = -1;
    public final static double MINZ = -1;
    public final static double MAXX =  1;
    public final static double MAXY =  1;
    public final static double MAXZ =  1;
    
    private double minX = MINX;
    private double maxX = MAXX;
    private double minY = MINY;
    private double maxY = MAXY;
    private double minZ = MINZ;
    private double maxZ = MAXZ;

    public ComponenteObjeto3DLimite() {}
    
    public ComponenteObjeto3DLimite( double minX, double maxX, double minY, double maxY, double minZ, double maxZ ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public double getMinZ() {
        return minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }
            
}
