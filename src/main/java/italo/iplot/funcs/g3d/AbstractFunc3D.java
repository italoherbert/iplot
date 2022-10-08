package italo.iplot.funcs.g3d;

public abstract class AbstractFunc3D implements Func3D {
            
    protected XTIPO xtipo = XTIPO.X;
    
    protected double calculaD( double x, double z ) {
        switch( xtipo ) {
            case X: return x;
            case Z: return z;
            case D: return Math.sqrt( x*x + z*z );
        }
        return x;
    }


    public XTIPO getXTipo() {
        return xtipo;
    }

    public void setXTipo(XTIPO xtipo) {
        this.xtipo = xtipo;
    }
    
}
