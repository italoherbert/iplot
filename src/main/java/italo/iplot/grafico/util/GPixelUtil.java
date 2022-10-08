package italo.iplot.grafico.util;

import italo.iplot.gui.grafico.GraficoPixel;
import italo.iplot.gui.grafico.pixel.DoublePixelLista;
import italo.iplot.gui.grafico.pixel.PixelLista;

public class GPixelUtil {
   
    public void desenhaDoublePontos( GraficoPixel gpx, DoublePixelLista lista, int cor ) {
        int tam = lista.tam();
        for( int i = 0; i < tam; i++ ) {
            int x = (int)Math.round( lista.getX( i ) );
            int y = (int)Math.round( lista.getY( i ) );
            gpx.pintaPixel( x, y, cor );         
        }
    }
    
    public void desenhaPontos( GraficoPixel gpx, PixelLista lista, int cor ) {
        int tam = lista.tam();
        for( int i = 0; i < tam; i++ ) {
            int x = (int)Math.round( lista.getX( i ) );
            int y = (int)Math.round( lista.getY( i ) );
            gpx.pintaPixel( x, y, cor );         
        }
    }
    
}
