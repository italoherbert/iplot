package italo.iplot.gui.grafico.pixel;

import java.util.List;

public class ListPixelLista implements PixelLista {

    private final List<int[]> list;

    public ListPixelLista(List<int[]> list) {
        this.list = list;
    }
    
    @Override
    public int getX(int i) {
        return list.get( i )[0];
    }

    @Override
    public int getY(int i) {
        return list.get( i )[1];
    }

    @Override
    public int[] getPonto(int i) {
        return list.get( i );
    }

    @Override
    public int tam() {
        return list.size();
    }
    
}
