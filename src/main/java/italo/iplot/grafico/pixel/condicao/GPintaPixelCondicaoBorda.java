package italo.iplot.grafico.pixel.condicao;

public class GPintaPixelCondicaoBorda implements GPintaPixelCondicao {

    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    public GPintaPixelCondicaoBorda( int x1, int y1, int x2, int y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    @Override
    public boolean condicao(int x, int y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }
    
}
