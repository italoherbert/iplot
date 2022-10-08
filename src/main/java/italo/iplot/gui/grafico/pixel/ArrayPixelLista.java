package italo.iplot.gui.grafico.pixel;

public class ArrayPixelLista implements PixelLista {

    private final int[][] array;

    public ArrayPixelLista(int[][] array) {
        this.array = array;
    }
    
    @Override
    public int getX(int i) {
        return array[i][0];
    }

    @Override
    public int getY(int i) {
        return array[i][1];
    }

    @Override
    public int[] getPonto(int i) {
        return array[i];
    }

    @Override
    public int tam() {
        return array.length;
    }
    
}
