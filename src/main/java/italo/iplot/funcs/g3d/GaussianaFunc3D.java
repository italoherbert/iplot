package italo.iplot.funcs.g3d;

public class GaussianaFunc3D extends AbstractFunc3D {

    private double raio = 1.0d;
    private double altura = 1.0d;
    
    public GaussianaFunc3D() {}

    public GaussianaFunc3D(double raio, double altura) {
        this.raio = raio;
        this.altura = altura;
    }
    
    @Override
    public double getY(double x, double z) {
        double d = Math.sqrt( Math.pow( x, 2 ) + Math.pow( z, 2 ) );        
        return altura * Math.exp( - Math.pow( d / raio, 2 ) * 4 );                        
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
    
}
