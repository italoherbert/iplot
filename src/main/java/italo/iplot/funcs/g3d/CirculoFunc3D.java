package italo.iplot.funcs.g3d;

public class CirculoFunc3D extends AbstractFunc3D {
    
    private double raio;

    public CirculoFunc3D(double raio) {
        this( XTIPO.D, raio );
    }
    
    public CirculoFunc3D(XTIPO xtipo, double raio) {
        super.xtipo = xtipo;
        this.raio = raio;
    }
    
    @Override
    public double getY(double x, double z) {
        double d = super.calculaD( x, z );
        
        if ( d <= raio )
            return Math.sqrt( Math.pow( raio, 2 ) - Math.pow( d, 2 ) );
        else return 0;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }
    
}
