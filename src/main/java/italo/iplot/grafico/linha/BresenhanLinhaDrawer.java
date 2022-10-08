package italo.iplot.grafico.linha;

import java.util.List;
import italo.iplot.gui.grafico.GraficoPixel;

public class BresenhanLinhaDrawer implements IntLinhaDrawer {
 
    @Override
    public void desenhaLinha( GraficoPixel gpx, int[] p0, int[] p1, int cor ) {
        if( p1[0] < p0[0] ) {
            int[] aux = p0;
            p0 = p1;
            p1 = aux;
        }
        
        int dx = Math.abs( p1[0]-p0[0] );
        int dy = Math.abs( p1[1]-p0[1] );        
        
        if ( dx == 0 ) {            
            if ( p1[1] < p0[1] )
                p0 = p1; 
            for( int i = 0; i <= dy; i++ ) {
                int x = p0[0];
                int y = p0[1]+i;                
                gpx.pintaPixel( x, y, cor ); 
            }
        } else if ( dy == 0 ) {
            for( int i = 0; i <= dx; i++ ) {
                int x = p0[0]+i;
                int y = p0[1];
                gpx.pintaPixel( x, y, cor ); 
            }
        } else {
            int x, y, d, incX, incY, movE, movNE;               
            x = p0[0];
            y = p0[1];
            incX = ( p1[0] >= p0[0] ? 1 : -1 );
            incY = ( p1[1] >= p0[1] ? 1 : -1 );        
            
            gpx.pintaPixel( x, y, cor ); 

            if ( dx >= dy ) {
                d = ( 2 * dy ) - dx;
                movE = 2 * dy;
                movNE = 2 * ( dy-dx );
                for( int i = 0; i < dx; i++ ) {
                    x += incX;
                    if ( d <= 0 ) {
                        d += movE;
                    } else {
                        d += movNE;
                        y += incY;
                    }      
                    gpx.pintaPixel( x, y, cor ); 
                }                
            } else {         
                d = ( 2 * dx ) - dy;
                movE = 2 * dx;
                movNE = 2 * ( dx - dy );                
                for( int i = 0; i < dy; i++ ) {
                    y += incY;
                    if ( d <= 0 ) {
                        d += movE;
                    } else {
                        d += movNE;
                        x += incX;
                    }
                    gpx.pintaPixel( x, y, cor ); 
                }                
            }                
        }
    }
    
    public void carregaLinhaPontosBresenham( List<int[]> pontos, int[] p0, int[] p1, boolean addPrim ) {
        if( p1[0] < p0[0] ) {
            int[] aux = p0;
            p0 = p1;
            p1 = aux;
        }
        
        int dx = Math.abs( p1[0]-p0[0] );
        int dy = Math.abs( p1[1]-p0[1] );        
        
        if ( dx == 0 ) {
            if ( p1[1] < p0[1] )
                p0 = p1;            
            int i = ( addPrim ? 0 : 1 );
            for( ; i <= dy; i++ ) {
                int x = p0[0];
                int y = p0[1]+i;
                pontos.add( new int[] { x, y } );
            }
        } else if ( dy == 0 ) {
            int i = ( addPrim ? 0 : 1 );
            for( ; i <= dx; i++ ) {
                int x = p0[0]+i;
                int y = p0[1];
                pontos.add( new int[] { x, y } );
            }
        } else {
            int x, y, d, incX, incY, movE, movNE;               
            x = p0[0];
            y = p0[1];
            incX = ( p1[0] >= p0[0] ? 1 : -1 );
            incY = ( p1[1] >= p0[1] ? 1 : -1 );        
            
            if ( addPrim )
                pontos.add( new int[] { x, y } );            
            
            if ( dx >= dy ) {
                d = ( 2 * dy ) - dx;
                movE = 2 * dy;
                movNE = 2 * ( dy-dx );
                for( int i = 0; i < dx; i++ ) {
                    x += incX;
                    if ( d <= 0 ) {
                        d += movE;
                    } else {
                        d += movNE;
                        y += incY;
                    }      
                    pontos.add( new int[] { x, y } );                    
                }                
            } else {         
                d = ( 2 * dx ) - dy;
                movE = 2 * dx;
                movNE = 2 * ( dx - dy );                
                for( int i = 0; i < dy; i++ ) {
                    y += incY;
                    if ( d <= 0 ) {
                        d += movE;
                    } else {
                        d += movNE;
                        x += incX;
                    }
                    pontos.add( new int[] { x, y } );
                }                
            }                
        }
    }    
    
    public void carregaLinhaPontos( List<double[]> pontos, double[] p0, double[] p1, boolean addPrim ) {
        double c = Math.sqrt( Math.pow( p1[0]-p0[0], 2 ) + Math.pow( p1[1]-p0[1], 2 ) );
        if ( c == 0 )
            return;
        
        double a = Math.atan2( p1[1]-p0[1], p1[0]-p0[0] );
        
        int r = ( addPrim ? 0 : 1 );
        for( ; r < c; r++ ) {
            double x = p0[0] + Math.cos( a ) * r;
            double y = p0[1] + Math.sin( a ) * r;
            pontos.add( new double[] { x, y } );
       }
        
       double x = p0[0] + Math.cos( a ) * r;
       double y = p0[1] + Math.sin( a ) * r;
       pontos.add( new double[] { x, y } );
    }
    
}
