package italo.iplot.grafico.util;

import italo.iplot.gui.grafico.CoresUtil;
import java.awt.Color;

public class CoresUtilitario implements CoresUtil {         
    
    @Override
    public int misturaCores( int rgb1, int rgb2, double cf ) {       
        int r1 = ( 0x00FF0000 & rgb1 ) >> 16;
        int g1 = ( 0x0000FF00 & rgb1 ) >> 8;
        int b1 = ( 0x000000FF & rgb1 );

        int r2 = ( 0x00FF0000 & rgb2 ) >> 16;
        int g2 = ( 0x0000FF00 & rgb2 ) >> 8;
        int b2 = ( 0x000000FF & rgb2 );

        int r3 = (int)( ( r1 * cf ) + ( r2 * ( 1.0d - cf ) ) );
        int g3 = (int)( ( g1 * cf ) + ( g2 * ( 1.0d - cf ) ) );
        int b3 = (int)( ( b1 * cf ) + ( b2 * ( 1.0d - cf ) ) );
        
        return 0xFF000000 + ( r3 << 16 ) + ( g3 << 8 ) + b3;        
    }
    
    @Override
    public int escureceCor( int rgb, int redizirEm ) {
        int r = ( 0x00FF0000 & rgb ) >> 16;
        int g = ( 0x0000FF00 & rgb ) >> 8;
        int b = ( 0x000000FF & rgb );
                
        if ( r > redizirEm )
            r -= redizirEm;
        else r = 0;
        
        if ( g > redizirEm )
            g -= redizirEm;
        else g = 0;
        
        if ( b > redizirEm )
            b -= redizirEm;
        else b = 0;        
        
        return 0xFF000000 + ( r << 16 ) + ( g << 8 ) + b;                
    }
    
    @Override
    public Color color( int rgb ) {
        int r = ( 0x00FF0000 & rgb ) >> 16;
        int g = ( 0x0000FF00 & rgb ) >> 8;
        int b = ( 0x000000FF & rgb );
        
        return new Color( r, g, b );
    }
    
    @Override
    public int getR( int rgb ) {
        return ( 0x00FF0000 & rgb ) >> 16;
    }
    
    @Override
    public int getG( int rgb ) {
        return ( 0x0000FF00 & rgb ) >> 8;
    }
    
    @Override
    public int getB( int rgb ) {
        return 0x000000FF & rgb;
    }
    
    @Override
    public int getRGB( int r, int g, int b ) {
        return 0xFF000000 + ( r << 16 ) + ( g << 8 ) + b;
    }
            
    @Override
    public int getSinza( int rgb ) {
        return ( this.getR( rgb ) + this.getG( rgb ) + this.getB( rgb ) ) / 3;
    }
    
    @Override
    public int corMedia( int... cores ) {
        int r = 0;
        int g = 0;
        int b = 0;
        if ( cores.length > 0 ) {
            for( int cor : cores ) {
                r += this.getR( cor );
                g += this.getG( cor );
                b += this.getB( cor );
            }
            
            r /= cores.length;
            g /= cores.length;
            b /= cores.length;
        }
        
        return this.getRGB( r, g, b );
    }
    
}
