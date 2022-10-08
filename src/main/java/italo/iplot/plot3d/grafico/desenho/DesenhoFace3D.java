package italo.iplot.plot3d.grafico.desenho;

import italo.iplot.plot3d.grafico.geom.FaceGeom3D;
import java.awt.Color;

public class DesenhoFace3D {
                   
    public void preenche( FaceGeom3D geom, Pixel3D pixel, Color cor ) {        
        int[][] pontos = geom.pontosPX();
        int[] vx = pontos[0];
        int[] vy = pontos[1];
        double[][] vpts = geom.vertPontos();
        
        int size = vx.length;
                
        int minI = 0;
        int maxI = 0;
        
        for( int i = 0; i < size; i++ ) {
            if ( vy[i] < vy[ minI ] )
                minI = i;
            if ( vy[i] > vy[ maxI ] )
                maxI = i;
        }
                
        int esq_i0 = minI;
        int dir_i0 = minI;
        int esq_i = minI;
        int dir_i = ( esq_i == size-1 ? 0 : esq_i+1 );
        
        int minY = vy[ minI ];
        int maxY = vy[ maxI ];
        
        int y = minY;             
        
        while( y <= maxY ) {                                 
            while ( y <= vy[ maxI ] && y > vy[ esq_i ] ) {
                esq_i0 = esq_i;
                esq_i = ( esq_i == 0 ? size-1 : esq_i-1 );
            }
            
            while ( y <= vy[ maxI ] && y > vy[ dir_i ] ) {
                dir_i0 = dir_i;
                dir_i = ( dir_i == size-1 ? 0 : dir_i+1 );
            }
            
            if ( y <= vy[ maxI ] ) {                 
                int x1, x2;
                                                
                int esq_x0 = vx[ esq_i0 ];
                int esq_y0 = vy[ esq_i0 ];
                int esq_x = vx[ esq_i ];
                int esq_y = vy[ esq_i ];
                
                if ( esq_y0 == esq_y )   {
                    x1 = Math.min( esq_x0, esq_x );
                } else if ( esq_x0 == esq_x ) {
                    x1 = esq_x0;
                } else {
                    double cang = ((double)( esq_y - esq_y0 )) / ((double)( esq_x - esq_x0 ));
                    double x = ( ( y - esq_y0 ) + ( esq_x0*cang ) ) / cang;
                    x1 = (int)Math.floor( x );
                }
                
                int dir_x0 = vx[ dir_i0 ];
                int dir_y0 = vy[ dir_i0 ];
                int dir_x = vx[ dir_i ];
                int dir_y = vy[ dir_i ];
                
                if ( dir_y0 == dir_y ) {
                    x2 = Math.max( dir_x0, dir_x );
                } else if ( dir_x0 == dir_x ) {
                    x2 = dir_x0;
                } else {
                    double cang = ((double)( dir_y - dir_y0 )) / ((double)( dir_x - dir_x0 ));
                    double x = ( ( y - dir_y0 ) + ( dir_x0*cang ) ) / cang;
                    x2 = (int)Math.floor( x );
                }

                int minX = Math.min( x1, x2 );
                int maxX = Math.max( x1, x2 );
                for( int x = minX; x <= maxX; x++ ) {                                        
                    double nz = geom.calculaZ( x, y, vpts[0], vpts[1], vpts[2] ); 
                    pixel.pintaFacePixel( geom, x, y, nz, cor.getRGB() );                    
                }              
            }
            y++;
        }        
                
    }       
        
}
