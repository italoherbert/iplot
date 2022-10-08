package italo.iplot.plot2d.g2d;

public class VerticeObjeto2DVisivel implements Objeto2DVisivel {
    
    private final Objeto2D obj;

    public VerticeObjeto2DVisivel( Objeto2D obj ) {
        this.obj = obj;
    }

    @Override
    public boolean isVisivel() {
        return obj.isPintarVertices();
    }
    
}
