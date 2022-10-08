package italo.iplot.gui.plot.icone;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class GirarIcone extends Icone {

    private double trianguloHDIMFator = 0.1666d;
    
    public GirarIcone( int dim ) {
        super( dim );
    }

    @Override
    protected void pintaIcone( Graphics2D g2D, int x, int y ) {                        
        int lw = (int)super.stroke.getLineWidth();
        
        int trianguloH = (int)Math.round( dim * trianguloHDIMFator );
                
        int xyc = dim / 2;
        int r = ( dim / 2 ) - esp - lw;

        int ax = x + xyc - r;
        int ay = y + xyc - r;
        int aw = 2*r;
        int ah = 2*r;
        
        double ang = Math.toRadians( 45 );
        double ang2 = Math.toRadians( 70 ); 
                
        int r1 = r - trianguloH;
        int r2 = r + trianguloH;
                
        int x1 = x + xyc + (int)( r1 * Math.cos( ang ) );
        int y1 = y + xyc - (int)( r1 * Math.sin( ang ) );
        int x2 = x + xyc + (int)( r2 * Math.cos( ang ) );
        int y2 = y + xyc - (int)( r2 * Math.sin( ang ) );
        int x3 = x + xyc + (int)( r * Math.cos( ang2 ) );
        int y3 = y + xyc - (int)( r * Math.sin( ang2 ) );
        
        Polygon p = new Polygon();
        p.addPoint( x1, y1 );
        p.addPoint( x2, y2 );
        p.addPoint( x3, y3 );
                        
        g2D.setColor( cor ); 

        g2D.setStroke( new BasicStroke() );        
        g2D.fillPolygon( p );   
        
        g2D.setStroke( stroke );
        g2D.drawArc( ax, ay, aw, ah, 135, 270 ); 
    }

    public double getTrianguloHDIMFator() {
        return trianguloHDIMFator;
    }

    public void setTrianguloHDIMFator(double trianguloHDIMFator) {
        this.trianguloHDIMFator = trianguloHDIMFator;
    }

}
