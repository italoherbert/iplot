package italo.iplot.plot3d.g3d;

public class VN_Z_Face3DVisivel implements Face3DVisivel {

    private Objeto3DTO to;

    public VN_Z_Face3DVisivel(Objeto3DTO to) {
        this.to = to;
    }
    
    @Override
    public boolean isVisivel( Face3D f ) {
        double[] vn = to.getMath3D().vetorNormal( f, to.getVisaoFiltroV3D() );                                                                                
        return vn[2] < 0;
    }
    
}
