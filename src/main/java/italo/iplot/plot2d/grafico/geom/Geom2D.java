package italo.iplot.plot2d.grafico.geom;

public abstract class Geom2D {
    
    protected Geom2DTO geomTO;
    
    public Geom2D( Geom2DTO geomTO ) {
        this.geomTO = geomTO;
    }

    public Geom2DTO getGeomTO() {
        return geomTO;
    }
    
}
