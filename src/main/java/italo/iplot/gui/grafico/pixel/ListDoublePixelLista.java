package italo.iplot.gui.grafico.pixel;

import java.util.List;

public class ListDoublePixelLista implements DoublePixelLista {
    
    private final List<double[]> lista;

    public ListDoublePixelLista(List<double[]> lista) {
        this.lista = lista;
    }

    @Override
    public double getX( int i ) {
        return lista.get( i )[0];
    }

    @Override
    public double getY( int i ) {
        return lista.get( i )[1];
    }

    @Override
    public double[] getPonto( int i ) {
        return lista.get( i );
    }

    @Override
    public int tam() {
        return lista.size();
    }
    
}
