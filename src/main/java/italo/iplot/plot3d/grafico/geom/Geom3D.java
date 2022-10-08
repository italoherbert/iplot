package italo.iplot.plot3d.grafico.geom;

public abstract class Geom3D {
    
    protected Geom3DTO geomTO;
    
    public Geom3D( Geom3DTO geomTO ) {
        this.geomTO = geomTO;
    }

    public Geom3DTO getGeomTO() {
        return geomTO;
    }
    
}
