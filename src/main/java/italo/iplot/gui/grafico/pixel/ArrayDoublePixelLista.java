package italo.iplot.gui.grafico.pixel;

public class ArrayDoublePixelLista implements DoublePixelLista {

    private final double[][] array;

    public ArrayDoublePixelLista(double[][] pixels) {
        this.array = pixels;
    }
    
    @Override
    public double getX( int i ) {
        return array[ i ][ 0 ];
    }

    @Override
    public double getY( int i ) {
        return array[ i ][ 1 ];
    }
    
    @Override
    public double[] getPonto( int i ) {
        return array[ i ];
    }

    @Override
    public int tam() {
        return array.length;
    }
    
}
