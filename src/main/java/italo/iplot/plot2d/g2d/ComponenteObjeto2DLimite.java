package italo.iplot.plot2d.g2d;

public class ComponenteObjeto2DLimite {
    
    public final static double MINX = -1;
    public final static double MINY = -1;
    public final static double MAXX =  1;
    public final static double MAXY =  1;
    
    private double minX = MINX;
    private double maxX = MAXX;
    private double minY = MINY;
    private double maxY = MAXY;

    public ComponenteObjeto2DLimite() {}
    
    public ComponenteObjeto2DLimite( double minX, double maxX, double minY, double maxY ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
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
           
}
