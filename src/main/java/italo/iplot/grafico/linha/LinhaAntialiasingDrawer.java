package italo.iplot.grafico.linha;

import italo.iplot.gui.grafico.CoresUtil;
import italo.iplot.gui.grafico.DoubleGraficoPixel;

public class LinhaAntialiasingDrawer implements LinhaDrawer {
    
    private final CoresUtil coresUtil;
    
    public LinhaAntialiasingDrawer( CoresUtil coresUtil ) {
        this.coresUtil = coresUtil;
    }        
    
    @Override
    public void desenhaLinha( DoubleGraficoPixel dpx, double[] p0, double[] p1, int cor ) {               
        if ( Math.abs( Math.round( p0[1] ) - Math.round( p1[1] ) ) <= 1 ) {            
            this.desenhaLinhaEspessura2px( dpx, p0, p1, cor );            
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
                        this.desenhaLinhaEspessura2px( dpx, pant, p, cor );
                        pant[0] = p[0];
                        pant[1] = p[1];                        
                    }
                }            
            }                                    
            p[0] = max;
            p[1] = this.calcRetaY( p0[0], p0[1], p1[0], p1[1], max );
            this.desenhaLinhaEspessura2px( dpx, pant, p, cor );            
        }                         
    }
    
    public void desenhaLinhaEspessura2px( DoubleGraficoPixel dpx, double[] p0, double[] p1, int cor ) {        
        double rx1 = Math.round( p0[0] );
        double ry1 = Math.round( p0[1] );
        double rx2 = Math.round( p1[0] );
        double ry2 = Math.round( p1[1] );
        
        if ( rx1 == rx2 ) {
            double min = Math.min( ry1, ry2 );
            double max = Math.max( ry1, ry2 );
            for( double y = min; y <= max; y++ )                                  
                dpx.pintaPixel( rx1, y, cor );             
        } else if ( ry1 == ry2 ) {
            double min = Math.min( rx1, rx2 );
            double max = Math.max( rx1, rx2 );
            for( double x = min; x <= max; x++ )                                  
                dpx.pintaPixel( x, ry1, cor );  
        } else {        
            double cang = this.calcCoeficienteAngular( p0[0], p0[1], p1[0], p1[1] );        
            int bgcor = dpx.getGraficoPixel().getRGB( (int)Math.round( rx2 ), (int)Math.round( ry2 ) );                
            
            if ( Math.abs( cang ) >= 1 ) {                                
                if ( p1[1] >= p0[1] ) {          
                    for( double y = ry1; y <= ry2; y++ ) {                               
                        double x = this.calcRetaX( p0[0], p0[1], p1[0], p1[1], y );                          
                        double d1 = Math.abs( p0[0] - x );
                        double d2 = Math.abs( p1[0] - x );
                        if ( d1 > 1 )
                            d1 = 1;
                        if ( d2 > 1 )
                            d2 = 1;

                        int c1 = coresUtil.misturaCores( bgcor, cor, d1 );
                        int c2 = coresUtil.misturaCores( bgcor, cor, d2 );
                        
                        dpx.pintaPixel( rx1, y, c1 );                                                                
                        dpx.pintaPixel( rx2, y, c2 );                                              
                    }
                } else {
                    for( double y = ry2; y <= ry1; y++ ) {                             
                        double x = this.calcRetaX( p0[0], p0[1], p1[0], p1[1], y );                                                                
                        double d1 = Math.abs( p0[0] - x );
                        double d2 = Math.abs( p1[0] - x );

                        if ( d1 > 1 )
                            d1 = 1;
                        if ( d2 > 1 )
                            d2 = 1;

                        int c1 = coresUtil.misturaCores( bgcor, cor, d1 );
                        int c2 = coresUtil.misturaCores( bgcor, cor, d2 );

                        dpx.pintaPixel( rx1, y, c1 );                                                                
                        dpx.pintaPixel( rx2, y, c2 );                         
                    }
                }                
            } else {
                if ( p1[0] >= p0[0] ) {
                    for( double x = rx1; x <= rx2; x++ ) {                           
                        double y = this.calcRetaY( p0[0], p0[1], p1[0], p1[1], x );                                                                
                        double d1 = Math.abs( p0[1] - y );
                        double d2 = Math.abs( p1[1] - y );
                        if ( d1 > 1 )
                            d1 = 1;
                        if ( d2 > 1 )
                            d2 = 1;

                        int c1 = coresUtil.misturaCores( bgcor, cor, d1 );
                        int c2 = coresUtil.misturaCores( bgcor, cor, d2 );

                        dpx.pintaPixel( x, ry1, c1 );                                                                
                        dpx.pintaPixel( x, ry2, c2 );                         
                    }
                } else {
                    for( double x = rx2; x <= rx1; x++ ) {                                                
                        double y = this.calcRetaY( p0[0], p0[1], p1[0], p1[1], x );                                                                
                        double d1 = Math.abs( p0[1] - y );
                        double d2 = Math.abs( p1[1] - y );
                        if ( d1 > 1 )
                            d1 = 1;
                        if ( d2 > 1 )
                            d2 = 1;

                        int c1 = coresUtil.misturaCores( bgcor, cor, d1 );
                        int c2 = coresUtil.misturaCores( bgcor, cor, d2 );

                        dpx.pintaPixel( x, ry1, c1 );                                                                
                        dpx.pintaPixel( x, ry2, c2 );                                             
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
