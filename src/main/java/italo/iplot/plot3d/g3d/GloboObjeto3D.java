package italo.iplot.plot3d.g3d;

public class GloboObjeto3D extends Objeto3D implements Raio3D {

    private int numDivs = 8;
    private double raio = 0.5d;
    private Raio3D raio3D = null;
        
    private boolean primeiraConstrucao = true;
    
    @Override
    public void constroiObjeto3D( Objeto3DTO to ) {                 
        double phi_inc = Math.PI / numDivs;        
        double theta_inc = (2*Math.PI) / numDivs;
        
        double r = this.getRaio();
        
        int k = 0;
        int thetaK = 0, antThetaK = 0;
        double phi = 0;
        for( int i = 0; i <= numDivs; i++ ) {                                    
            thetaK = k;
            
            if ( i == 0 || i == numDivs ) {
                double[] xyz = to.getMath3D().coordenadasEsfericas( phi, 0, r );                
                super.estrutura.addVertice( new Vertice3D( xyz ) ); 
                k++;
            } else {            
                double theta = -Math.PI;
                for( int j = 0; j <= numDivs; j++ )  {                         
                    if ( j < numDivs ) {
                        double[] xyz = to.getMath3D().coordenadasEsfericas( phi, theta, r );                
                        super.estrutura.addVertice( new Vertice3D( xyz ) );                     
                    }

                    if ( i > 1 && j > 0 ) {                                                
                        Face3D f = new Face3D();                    
                        f.addVertice(super.estrutura.getVertices().get(antThetaK + ( j == numDivs ? 0 : j ) ) );
                        f.addVertice( super.estrutura.getVertices().get( antThetaK + j - 1 ) );
                        f.addVertice( super.estrutura.getVertices().get( k - 1 ) );
                        f.addVertice(super.estrutura.getVertices().get(j == numDivs ? thetaK : k ) );
                        super.estrutura.addFace( f, to );
                    }

                    if ( j < numDivs ) {
                        theta += theta_inc;
                        k++;
                    }
                }
            }
            phi += phi_inc;
            antThetaK = thetaK;
        }
        
        for( int i = 1; i <= numDivs; i++ ) {
            Face3D f = new Face3D();                    
            f.addVertice( super.estrutura.getVertices().get( 0 ) );
            f.addVertice( super.estrutura.getVertices().get( 1 + i - 1 ) );
            f.addVertice(super.estrutura.getVertices().get(1 + ( i == numDivs ? 0 : i ) ) );                        
            super.estrutura.addFace( f, to );
            
            Face3D f2 = new Face3D();                    
            f2.addVertice(super.estrutura.getVertices().get((k-numDivs-1) + ( i == numDivs ? 0 : i ) ) );                        
            f2.addVertice(super.estrutura.getVertices().get((k-numDivs-1) + i - 1 ) );
            f2.addVertice( super.estrutura.getVertices().get( k-1 ) );
            super.estrutura.addFace( f2, to );
        }        
    }

    @Override
    public double getRaio() {
        if ( raio3D != null )
            return raio3D.getRaio();
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public Raio3D getRaio3D() {
        return raio3D;
    }

    public void setRaio3D(Raio3D raio3D) {
        this.raio3D = raio3D;
    }

    public int getNumDivs() {
        return numDivs;
    }

    public void setNumDivs(int ndivs) {
        this.numDivs = ndivs;
    }
    
}
