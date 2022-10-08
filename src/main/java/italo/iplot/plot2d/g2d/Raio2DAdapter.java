package italo.iplot.plot2d.g2d;

public class Raio2DAdapter implements Raio2D {
    
    private final VerticeRaio2D raio2D;
    
    public Raio2DAdapter( VerticeRaio2D raio2D ) {
        this.raio2D = raio2D;
    }

    @Override
    public double getRaio() {
        return raio2D.getVerticeRaio();
    }
    
}
