package italo.iplot.plot3d.g3d;

import java.util.List;

public class SuperficieObjeto3D extends Objeto3D implements GrelhaObjeto3D, ComponenteObjeto3D {
            
    private double[] vetorX = new double[ 0 ];
    private double[] vetorZ = new double[ 0 ];
    private double[][] matrizY = new double[ 0 ][ 0 ];
    private ContainerObjeto3D container;

    private final Grelha grelha = new Grelha();

    public SuperficieObjeto3D() {
        super.addNovaFaceAposCorte = false;
    }
    
    public SuperficieObjeto3D( double[] vetorX, double[] vetorZ, double[][] matrizY ) {
        this.vetorX = vetorX;
        this.vetorZ = vetorZ;
        this.matrizY = matrizY;
        
        super.addNovaFaceAposCorte = false;
    }
    
    @Override
    public void constroiObjeto3D(Objeto3DTO to) {
        if ( vetorX == null || vetorZ == null || matrizY == null )
            return;
                               
        for( int i = 0; i < vetorX.length; i++ ) {
            for( int j = 0; j < vetorZ.length; j++ ) {
                double x = container.calculaX( vetorX[i] );
                double z = container.calculaZ( vetorZ[j] );
                double y = container.calculaY( matrizY[i][j] );
                
                super.getEstrutura().addVertice( new Vertice3D( x, y, z ) );
            }
        }
        
        grelha.constroiFaces( this, to );
    }      

    @Override
    public ComponenteObjeto3DLimite calculaLimites() {
        boolean temVertice = true;
        if ( vetorX == null || vetorZ == null || matrizY == null )
            temVertice = false;
        if ( vetorX.length == 0 || vetorZ.length == 0 )
            temVertice = false;
            
        if ( temVertice ) {
            double minX = Double.MAX_VALUE;
            double maxX = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE;
            double maxY = Double.MIN_VALUE;
            double minZ = Double.MAX_VALUE;
            double maxZ = Double.MIN_VALUE;
            for( double x : vetorX ) {
                if ( x < minX )
                    minX = x;
                if ( x > maxX )
                    maxX = x;
            }
            for( double z : vetorZ ) {
                if ( z < minZ )
                    minZ = z;
                if ( z > maxZ )
                    maxZ = z;
            }
            for( int i = 0; i < vetorX.length; i++ ) {
                for( int j = 0; j < vetorZ.length; j++ ) {
                    if ( matrizY[i][j] < minY )
                        minY = matrizY[i][j];
                    if ( matrizY[i][j] > maxY )
                        maxY = matrizY[i][j];
                }
            }        
            return new ComponenteObjeto3DLimite( minX, maxX, minY, maxY, minZ, maxZ );
        }
        return new ComponenteObjeto3DLimite();
    }

    @Override
    public void escalar(double escala, Objeto3DTO to) {
        double[] vc = container.verticeCentral();
        to.getTransformador3D().sub( estrutura.getVertices(), vc, to.getXYZFiltroV3D() );
        to.getTransformador3D().escala( this, escala, to.getXYZFiltroV3D() );        
        to.getTransformador3D().soma( estrutura.getVertices(), vc, to.getXYZFiltroV3D() ); 
    }        

    @Override
    public int getXNDivs() {
        return vetorX.length-1;
    }

    @Override
    public int getZNDivs() {
        return vetorZ.length-1;
    }

    @Override
    public List<Vertice3D> getVertices() {
        return super.getEstrutura().getVertices();
    }

    @Override
    public void addFace(Face3D face, Objeto3DTO to) {
        super.getEstrutura().addFace( face, to ); 
    }

    public double[] getVetorX() {
        return vetorX;
    }

    public void setVetorX(double[] vetorX) {
        this.vetorX = vetorX;
    }

    public double[] getVetorZ() {
        return vetorZ;
    }

    public void setVetorZ(double[] vetorZ) {
        this.vetorZ = vetorZ;
    }

    public double[][] getMatrizY() {
        return matrizY;
    }

    public void setMatrizY(double[][] matrizY) {
        this.matrizY = matrizY;
    }
    
    public ContainerObjeto3D getContainerObjeto3D() {
        return container;
    }

    @Override
    public void setContainerObjeto3D(ContainerObjeto3D container) {
        this.container = container;
    }
    
}
