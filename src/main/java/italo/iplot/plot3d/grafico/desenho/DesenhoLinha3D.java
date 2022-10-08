package italo.iplot.plot3d.grafico.desenho;

import italo.iplot.plot3d.grafico.geom.ArestaGeom3D;
import java.awt.Color;

public class DesenhoLinha3D {
    
    public void desenhaPontilhada( ArestaGeom3D geom, Pixel3D pixel, Color cor, int esp ) {
        int[] p0_2D = geom.calculaP0();
        int[] p_2D = geom.calculaP1();
        int x1 = p0_2D[0];
        int y1 = p0_2D[1];
        int x2 = p_2D[0];
        int y2 = p_2D[1];
                        
        int c = (int)Math.sqrt( Math.pow( x2-x1, 2 ) + Math.pow( y2-y1, 2) );
        if ( c == 0 )
            return;
                
        double a = Math.atan2( y2-y1, x2-x1 );
        int npontos = c / esp;
        for( int i = 0; i <= npontos; i++ ) {
            int x = x1 + (int)Math.floor( Math.cos( a ) * ( i * esp ) );
            int y = y1 + (int)Math.floor( Math.sin( a ) * ( i * esp ) );
            double nz = geom.calculaZ( x, y );
            pixel.pintaArestaPixel( geom, x, y, nz, cor.getRGB() ); 
        }
        
        int mod = c % esp;
        if ( mod != 0 ) {
            int x = x1 + (int)Math.floor( Math.cos( a ) * c );
            int y = y1 + (int)Math.floor( Math.sin( a ) * c );
            double nz = geom.calculaZ( x, y );
            pixel.pintaArestaPixel( geom, x, y, nz, cor.getRGB() ); 
        }
    }
    
    private void desenhaHV( ArestaGeom3D geom, int[] p0, int[] p1, Pixel3D pixel, Color cor, int esp ) {                       
        int x = Math.min( p0[0], p1[0] );
        int y = Math.min( p0[1], p1[1] );
        int dx = Math.abs( p1[0]-p0[0] );
        int dy = Math.abs( p1[1]-p0[1] );        
        
        if ( dx == 0 ) {
            int min = Math.min( p0[1], p1[1] );
            int max = Math.max( p0[1], p1[1] );            
            int i = -1;
            for( y = min; y <= max; y++ ) {
                i++;
                if ( i % esp != 0 )
                    continue;
                
                double nz = geom.calculaZ( x, y );
                pixel.pintaArestaPixel( geom, x, y, nz, cor.getRGB() );
            }
        } else if ( dy == 0 ) {
            int min = Math.min( p0[0], p1[0] );
            int max = Math.max( p0[0], p1[0] );   
            int i = -1;
            for( x = min; x <= max; x++ ) {
                i++;
                if ( i % esp != 0 )
                    continue;
                
                double nz = geom.calculaZ( x, y );
                pixel.pintaArestaPixel( geom, x, y, nz, cor.getRGB() );
            }
        }
    }

    public void desenha( ArestaGeom3D geom, Pixel3D pixel, Color cor, int esp ) {
        int[] p0 = geom.calculaP0();
        int[] p1 = geom.calculaP1();
           
        if( p1[0] < p0[0] ) {
            int[] aux = p0;
            p0 = p1;
            p1 = aux;
        }
        
        int x, y, dx, dy, d, incX, incY, movE, movNE;               
        x = p0[0];
        y = p0[1];
        dx = Math.abs( p1[0]-p0[0] );
        dy = Math.abs( p1[1]-p0[1] );
        incX = ( p1[0] >= p0[0] ? 1 : -1 );
        incY = ( p1[1] >= p0[1] ? 1 : -1 );        
                        
        if ( dx == 0 || dy == 0 ) {
            this.desenhaHV( geom, p0, p1, pixel, cor, esp );
        } else {            
            double nz = geom.calculaZ( x, y );            
            pixel.pintaArestaPixel( geom, x, y, nz, cor.getRGB() );
            if ( dx >= dy ) {                
                d = ( 2 * dy ) - dx;
                movE = 2 * dy;
                movNE = 2 * ( dy-dx );
                for( int i = 1; i <= dx; i++ ) {                                        
                    x += incX;
                    if ( d <= 0 ) {
                        d += movE;
                    } else {
                        d += movNE;
                        y += incY;
                    }                                               
                    
                    if ( i % esp != 0 )
                        continue;
                    nz = geom.calculaZ( x, y );            
                    pixel.pintaArestaPixel( geom, x, y, nz, cor.getRGB() );
                }                
            } else {                                                        
                d = ( 2 * dx ) - dy;
                movE = 2 * dx;
                movNE = 2 * ( dx - dy );                
                for( int i = 1; i <= dy; i++ ) {                                        
                    y += incY;
                    if ( d <= 0 ) {
                        d += movE;
                    } else {
                        d += movNE;
                        x += incX;
                    }
                    
                    if ( i % esp != 0 )
                        continue;
                    nz = geom.calculaZ( x, y );            
                    pixel.pintaArestaPixel( geom, x, y, nz, cor.getRGB() );
                }
            }                                                
        }      
    }        
    
}
