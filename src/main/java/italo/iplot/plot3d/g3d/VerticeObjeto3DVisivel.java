package italo.iplot.plot3d.g3d;

public class VerticeObjeto3DVisivel implements Objeto3DVisivel {
    
    private final Objeto3D obj;

    public VerticeObjeto3DVisivel( Objeto3D obj ) {
        this.obj = obj;
    }

    @Override
    public boolean isVisivel() {
        return obj.isPintarVertices();
    }
    
}
