package italo.iplot.plot2d.g2d.util;

import italo.iplot.gui.Tela;
import italo.iplot.plot2d.g2d.Vertice2D;
import italo.iplot.plot2d.g2d.vert.FiltroVert2D;
import java.util.List;

public class Math2D {
    
    public double getMeioH( Tela tela ) {
        return this.getMeioH( tela.getTelaLargura(), tela.getTelaAltura() );
    }
    
    public double getMeioH( double largura, double altura ) {
        return Math.min( altura, largura ) *.5d;
    }
    
    public double centroX( Tela tela ) {
        return tela.getTelaX() + tela.getTelaLargura()*.5d;                  
    }
    
    public double centroY( Tela tela ) {
        return tela.getTelaY() + tela.getTelaAltura()*.5d;
    }                
                    
    public double distancia( double x1, double y1, double x2, double y2 ) {
        return Math.sqrt( Math.pow( y2-y1, 2 ) + Math.pow( x2-x1, 2 ) );
    }
    
    public double[][] calculaMinMaxXY( List<Vertice2D> lista, FiltroVert2D filtro ) {
        double minX, minY, maxX, maxY;
        minX = minY = Double.MAX_VALUE;
        maxX = maxY = Double.MIN_VALUE;
        
        for( Vertice2D v : lista ) {
            double x = filtro.getX( v );
            double y = filtro.getY( v );
            
            if ( x < minX )
                minX = x;
            if ( y < minY )
                minY = y;
            if ( x > maxX )
                maxX = x;
            if ( y > maxY )
                maxY = y;
        }
        
        return new double[][] {
            { minX, minY },
            { maxX, maxY }                
        };        
        
    }
    
    public double ajusteATelaFatorProporcao( List<Vertice2D> vertices, double h, Tela tela, FiltroVert2D filtro ) {                
        int minY, maxY;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;
        
        for( Vertice2D v : vertices ) {      
            double x = filtro.getX( v );
            double y = filtro.getY( v );
            int[] pxy = this.pontoPX( x, y, tela );                      
            
            if ( pxy[1] < minY )
                minY = pxy[1];
            if ( pxy[1] > maxY )
                maxY = pxy[1];
        }
                
        return ( h * this.getMeioH( tela ) ) / Math.abs(maxY-minY);
    }
            
    public int[] pontoPX( double x, double y, Tela tela ) {
        int cx = tela.getTelaLargura() / 2;
        int cy = tela.getTelaAltura() / 2;        

        int h = Math.min( cx, cy );
        
        return new int[] {
            tela.getTelaX() + cx + (int)( x * h ),
            tela.getTelaY() + cy - (int)( y * h ) 
        }; 
    }
    
    public double[] doublePontoPX( double x, double y, Tela tela ) {
        int cx = tela.getTelaLargura() / 2;
        int cy = tela.getTelaAltura() / 2;        

        int h = Math.min( cx, cy );
        
        return new double[] {
            tela.getTelaX() + cx + ( x * h ),
            tela.getTelaY() + cy - ( y * h ) 
        }; 
    }
        
    public double verticeUnidade( double n, Tela tela ) {
        return n / this.getMeioH( tela );
    }
        
    public double telaUnidade( double n, Tela tela ) {
        return n * this.getMeioH( tela );
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
    
}
