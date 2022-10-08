package italo.iplot.funcs.g3d;

public class SenoFunc3D extends AbstractFunc3D {

    public SenoFunc3D() {
        super.xtipo = XTIPO.X;
    }
    
    public SenoFunc3D(XTIPO xtipo) {
        super.xtipo = xtipo;
    }
    
    @Override
    public double getY(double x, double z) {
        double a = super.calculaD( x, z );        
        return Math.sin( a );
    }
    
}
