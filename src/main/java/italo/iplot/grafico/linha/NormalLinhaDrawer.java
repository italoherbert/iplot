package italo.iplot.grafico.linha;

import italo.iplot.gui.grafico.DoubleGraficoPixel;

public class NormalLinhaDrawer implements LinhaDrawer {
         
    @Override
    public void desenhaLinha( DoubleGraficoPixel px, double[] p0, double[] p1, int cor ) {               
        if ( Math.abs( Math.round( p0[1] ) - Math.round( p1[1] ) ) <= 1 || Math.abs( Math.round( p0[0] ) - Math.round( p1[0] ) ) <= 1 ) {            
            this.desenhaLinhaEspessura2px( px, p0, p1, cor );            
        } else {
            double[] pant = new double[2];
            double[] p = new double[2];
            boolean prim = true;

            double min = Math.min( p0[0], p1[0] );
            double max = Math.max( p0[0], p1[0] );            
            for( double x = min; x < max; x++ ) {
                p[0] = x;
                p[1] = this.calcRetaY( p0[0], p0[1], p1[0], p1[1], x );

                if ( prim ) {
                    pant[0] = p[0];
                    pant[1] = p[1];
                    prim = false;
                } else {                    
                    if ( Math.round( pant[1] ) != Math.round( p[1] ) ) {                                                             
                        this.desenhaLinhaEspessura2px( px, pant, p, cor );
                        pant[0] = p[0];
                        pant[1] = p[1];                        
                    }
                }            
            }                                    
            p[0] = max;
            p[1] = this.calcRetaY( p0[0], p0[1], p1[0], p1[1], max );
            this.desenhaLinhaEspessura2px( px, pant, p, cor );            
        }                         
    }
      
    public void desenhaLinhaEspessura2px( DoubleGraficoPixel px, double[] p0, double[] p1, int cor ) {        
        double rx1 = Math.round( p0[0] );
        double ry1 = Math.round( p0[1] );
        double rx2 = Math.round( p1[0] );
        double ry2 = Math.round( p1[1] );
               
        if ( rx1 == rx2 ) {                      
            double min = Math.min( p0[1], p1[1] );
            double max = Math.max( p0[1], p1[1] );
            for( double y = min; y <= max; y++ )   
                px.pintaPixel( p0[0], y, cor );    
            px.pintaPixel( p0[0], max, cor );                                        
        } else if ( ry1 == ry2 ) {            
            double min = Math.min( p0[0], p1[0] );
            double max = Math.max( p0[0], p1[0] );
            for( double x = min; x <= max; x++ )                                  
                px.pintaPixel( x, p0[1], cor );                                   
            px.pintaPixel( max, p0[1], cor );  
        } else {        
            double cang = this.calcCoeficienteAngular( p0[0], p0[1], p1[0], p1[1] );                    
            if ( Math.abs( cang ) >= 1 ) {                                
                if ( p1[1] >= p0[1] ) {          
                    for( double y = ry1; y <= ry2; y++ ) {                               
                        double x = this.calcRetaX( p0[0], p0[1], p1[0], p1[1], y );                                                  
                        px.pintaPixel( x, y, cor );                                        
                    }
                } else {
                    for( double y = ry2; y <= ry1; y++ ) {                             
                        double x = this.calcRetaX( p0[0], p0[1], p1[0], p1[1], y );                                                                                        
                        px.pintaPixel( x, y, cor );                                        
                    }
                }                
            } else {
                if ( p1[0] >= p0[0] ) {
                    for( double x = rx1; x <= rx2; x++ ) {                           
                        double y = this.calcRetaY( p0[0], p0[1], p1[0], p1[1], x );                                                                                        
                        px.pintaPixel( x, y, cor );                                        
                    }
                } else {
                    for( double x = rx2; x <= rx1; x++ ) {                                                
                        double y = this.calcRetaY( p0[0], p0[1], p1[0], p1[1], x );                                                                                        
                        px.pintaPixel( x, y, cor );                                        
                    }
                }            
            }
        }         
    }               
            
    public double calcRetaX( double x1, double y1, double x2, double y2, double y ) {
        if ( x2 == x1 || y2 == y1 )
            return x1;
        double cang = ( y2 - y1 ) / ( x2 - x1 );
        return ( y - y1  + ( cang * x1 ) ) / cang;
    }
    
    public double calcRetaY( double x1, double y1, double x2, double y2, double x ) {
        if ( x2 == x1 )
            return y1;        
        double cang = ( y2 - y1 ) / ( x2 - x1 );
        return cang * ( x - x1 ) + y1;
    }  
    
    public double calcCoeficienteAngular( double x1, double y1, double x2, double y2 ) {
        if ( x1 == x2 )
            return Double.POSITIVE_INFINITY;
        return ( y2 -y1 ) / ( x2 - x1 );
    }
    
}
