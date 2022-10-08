package italo.iplot.plot2d.g2d.util.corte.util;

public class RetaUtil {
    
    public double calcX( double x1, double y1, double x2, double y2, double y ) {
        if ( x2 == x1 || y2 == y1 )
            return x1;
        double cang = ( y2 - y1 ) / ( x2 - x1 );
        return ( y - y1  + ( cang * x1 ) ) / cang;
    }
    
    public double calcY( double x1, double y1, double x2, double y2, double x ) {
        if ( x2 == x1 )
            return y1;        
        double cang = ( y2 - y1 ) / ( x2 - x1 );
        return cang * ( x - x1 ) + y1;
    }
    
}
