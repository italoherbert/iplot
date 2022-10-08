package italo.iplot.plot3d.g3d;

public class Raio3DAdapter implements Raio3D {
    
    private final VerticeRaio3D raio3D;
    
    public Raio3DAdapter( VerticeRaio3D raio3D ) {
        this.raio3D = raio3D;
    }

    @Override
    public double getRaio() {
        return raio3D.getVerticeRaio();
    }
    
}
