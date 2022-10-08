package italo.iplot.gui.plot.icone;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class ApontadorIcone extends Icone {
    
    public ApontadorIcone( int dim ) {
        super( dim );
    }

    @Override
    protected void pintaIcone( Graphics2D g2D, int x, int y ) {                        
        int xyc = dim / 2;
        double r = ( dim / 2 )*0.7d;
        double r2 = dim / 2;
        
        double ang = Math.toRadians( 45 );
        double ang2 = Math.toRadians( 225 ); 
        double ang3 = Math.toRadians( 135 );
                               
        int x1 = x + xyc + (int)( r * Math.cos( ang ) );
        int y1 = y + xyc - (int)( r * Math.sin( ang ) );
        int x2 = x + xyc + (int)( r * Math.cos( ang2 ) );
        int y2 = y + xyc - (int)( r * Math.sin( ang2 ) );
        int x3 = x - esp + xyc + (int)( r * Math.cos( ang3 ) );
        int y3 = y - esp + xyc - (int)( r * Math.sin( ang3 ) );
        
        double ang5 = Math.toRadians( 315 );
        
        int x4 = x + xyc;
        int y4 = y + xyc;
        int x5 = x + xyc + (int)( r2 * Math.cos( ang5 ) );
        int y5 = y + xyc - (int)( r2 * Math.sin( ang5 ) );                
        
        Polygon p = new Polygon();
        p.addPoint( x1, y1 );
        p.addPoint( x2, y2 );
        p.addPoint( x3, y3 );
                        
        g2D.setColor( cor ); 

        g2D.setStroke( new BasicStroke() );        
        g2D.fillPolygon( p ); 
        
        g2D.setStroke( super.stroke );        
        g2D.drawLine( x4, y4, x5, y5 ); 
        
    }
    
}
