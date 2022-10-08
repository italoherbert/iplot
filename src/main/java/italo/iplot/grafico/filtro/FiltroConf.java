package italo.iplot.grafico.filtro;

public class FiltroConf implements FiltroConfig {
    
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    public FiltroConf(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public int getX1() {
        return x1;
    }

    @Override
    public int getY1() {
        return y1;
    }

    @Override
    public int getX2() {
        return x2;
    }

    @Override
    public int getY2() {
        return y2;
    }
    
}
