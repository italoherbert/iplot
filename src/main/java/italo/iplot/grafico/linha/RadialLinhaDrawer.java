package italo.iplot.grafico.linha;

import italo.iplot.gui.grafico.DoubleGraficoPixel;

public class RadialLinhaDrawer implements LinhaDrawer {           
                      
    @Override
    public void desenhaLinha( DoubleGraficoPixel px, double[] p0, double[] p1, int cor ) {                                       
        double dx = Math.abs( p1[0] - p0[0] );
        double dy = Math.abs( p1[1] - p0[1] );
        
        if ( dx == 0 ) {            
            if ( p1[1] < p0[1] )
                p0 = p1; 
            for( int i = 0; i <= dy; i++ ) {
                double x = p0[0];
                double y = p0[1]+i;                 
                px.pintaPixel( x, y, cor );
            }
        } else if ( dy == 0 ) {
            if ( p1[0] < p0[0] )
                p0 = p1;
            for( int i = 0; i <= dx; i++ ) {
                double x = p0[0]+i;
                double y = p0[1];
                px.pintaPixel( x, y, cor );
            }
        } else {            
            double c = Math.sqrt( Math.pow( p1[1] - p0[1], 2 ) + Math.pow( p1[0] - p0[0], 2 ) );
            if ( c <= 0 )
                return;
                        
            double a = Math.atan2( p1[1] - p0[1], p1[0] - p0[0] );
            boolean fim = false;   
            for( double r = 0; !fim; r+=0.5 ) {                                                            
                if ( r >= c ) {
                    r = c;
                    fim = true;
                }
                                
                double x = p0[0] + ( r * Math.cos( a ) );
                double y = p0[1] + ( r * Math.sin( a ) );                

                px.pintaPixel( x, y, cor ); 
            }            
        }
    }                     
           
}
