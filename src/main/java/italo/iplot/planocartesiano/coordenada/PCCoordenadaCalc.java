package italo.iplot.planocartesiano.coordenada;

public class PCCoordenadaCalc {
    
    public double calcula( PCContainerCoordenada calcula, double n ) {
        double d = calcula.getDN();
        double min = calcula.getMin();
        double max = calcula.getMax();
        double imin = calcula.getIMin();
        double imax = calcula.getIMax();
        double ifator = calcula.getIFator();
        
        double inc = ( Math.abs( min - imin ) - Math.abs( imax - max ) ) *.5d;
        inc *= d / Math.abs( imax - imin );
        
        double nn = d * ( ( n - min ) / ( max - min ) );
        return -d*.5d + inc + ( ( d - ( d * ifator ) )*.5d ) + ( nn * ifator ); 
    }
    
}
