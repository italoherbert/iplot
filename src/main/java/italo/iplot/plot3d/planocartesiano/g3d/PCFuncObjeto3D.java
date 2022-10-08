package italo.iplot.plot3d.planocartesiano.g3d;

import italo.iplot.plot3d.g3d.ComponenteObjeto3DLimite;
import italo.iplot.plot3d.g3d.ComponenteObjeto3D;
import java.util.List;
import italo.iplot.plot3d.g3d.ContainerObjeto3D;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Grelha;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;
import italo.iplot.funcs.g3d.Func3D;
import italo.iplot.plot3d.g3d.GrelhaObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import java.util.ArrayList;

public class PCFuncObjeto3D extends Objeto3D implements GrelhaObjeto3D, ComponenteObjeto3D {

    private int xndivs = 20;
    private int zndivs = 20;
    
    private boolean yIntervaloAtivado = false;
    
    private double x1 = -1;
    private double x2 = 1;
    private double z1 = -1;
    private double z2 = 1;
    
    private double minY = -1;
    private double maxY = 1;
    
    private double[] vx;
    private double[] vy;
    private double[] vz;
    private int nvertices;
            
    private Func3D func3D;
    
    private final Grelha grelha = new Grelha();
    private ContainerObjeto3D container;
    
    public PCFuncObjeto3D() {        
        super.pintarArestas = false;
        super.desenharFaces = true;
        super.pintarFaces = true;
        super.preenchimento = Preenchimento.GRADIENTE;
        super.arestaPreenchimento = Preenchimento.GRADIENTE;
        super.aplicarIluminacaoAFace = true;
        super.aplicarIluminacaoAAresta = true;
        
        super.addNovaFaceAposCorte = false;
        super.removerNovasArestasAposCorte = true;
    }
            
    @Override
    public void constroiObjeto3D( Objeto3DTO to ) {                                                                         
        List<Vertice3D> paraRemover = new ArrayList();
        
        for( int i = 0; i < nvertices; i++ ) {            
            boolean remover = false;
            if ( func3D != null ) {
                double pcy = vy[i];                                
                if ( pcy == Double.NaN || pcy == Double.NEGATIVE_INFINITY || pcy == Double.POSITIVE_INFINITY ) {
                    remover = true;
                } else if ( yIntervaloAtivado ) {
                    if ( pcy < minY || pcy > maxY )
                        remover = true;
                }                
            }
            
            double x = container.calculaX( vx[ i ] );
            double y = container.calculaY( vy[ i ] );
            double z = container.calculaZ( vz[ i ] );

            Vertice3D v = new Vertice3D( x, y, z );
            super.getEstrutura().addVertice( v ); 
            
            if ( remover )
                paraRemover.add( v );
        }

        if ( nvertices > 0 )
            grelha.constroiFaces( this, to );                 
        
        paraRemover.forEach( v -> super.getEstrutura().removeEstruturaVertice( v ) );
    }

    @Override
    public ComponenteObjeto3DLimite calculaLimites() {
        double y1 = Double.MAX_VALUE;
        double y2 = Double.MIN_VALUE;

        double retx = Math.abs(x2-x1) / xndivs;
        double retz = Math.abs(z2-z1) / zndivs;                

        nvertices = (xndivs+1) * (zndivs+1);

        vx = new double[ nvertices ];
        vy = new double[ nvertices ];
        vz = new double[ nvertices ];

        int k = 0;
        for( int i = 0; i <= xndivs; i++ ) {
            for( int j = 0; j <= zndivs; j++ ) {                        
                double pcx = x1 + ( i * retx );
                double pcz = z1 + ( j * retz );
                double pcy = Double.NaN;

                if ( func3D != null )  {
                    try {
                        pcy = func3D.getY( pcx, pcz );                          
                    } catch ( ArithmeticException e ) {
                        pcy = Double.NaN;
                    }
                    if ( pcy != Double.NaN && pcy != Double.NEGATIVE_INFINITY && pcy != Double.POSITIVE_INFINITY ) {
                        boolean setarMinMax = true;
                        if ( yIntervaloAtivado )
                            setarMinMax = pcy >= minY && pcy <= maxY;

                        if ( setarMinMax ) {
                            if ( pcy < y1 )
                                y1 = pcy;
                            if ( pcy > y2 )
                                y2 = pcy;
                        }
                    }
                }

                vx[ k ] = pcx;
                vy[ k ] = pcy;
                vz[ k ] = pcz;
                k++;
            }
        }

        if ( y1 == Double.MAX_VALUE )
            y1 = ComponenteObjeto3DLimite.MINY;


        if ( y2 == Double.MIN_VALUE )
            y2 = ComponenteObjeto3DLimite.MAXY;
        
        return new ComponenteObjeto3DLimite( x1, x2, y1, y2, z1, z2 );        
    }

    @Override
    public void escalar( double escala, Objeto3DTO to ) {
        double[] vc = container.verticeCentral();
        to.getTransformador3D().sub( estrutura.getVertices(), vc, to.getXYZFiltroV3D() );
        to.getTransformador3D().escala( this, escala, to.getXYZFiltroV3D() );        
        to.getTransformador3D().soma( estrutura.getVertices(), vc, to.getXYZFiltroV3D() ); 
    }

    @Override
    public List<Vertice3D> getVertices() {
        return super.getEstrutura().getVertices();
    }

    @Override
    public void addFace(Face3D face, Objeto3DTO to) {
        super.getEstrutura().addFace( face, to ); 
    }
    
    public void setIntervalos( double x1, double x2, double z1, double z2 ) {
        this.setXIntervalo( x1, x2 );
        this.setZIntervalo( z1, z2 );
    }
    
    public void setXIntervalo( double x1, double x2 ) {
        this.x1 = x1;
        this.x2 = x2;
    }
    
    public void setZIntervalo( double z1, double z2 ) {
        this.z1 = z1;
        this.z2 = z2;
    }
    
    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getZ1() {
        return z1;
    }

    public double getZ2() {
        return z2;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }
    
    public void setZ1(double z1) {
        this.z1 = z1;
    }

    public void setZ2(double z2) {
        this.z2 = z2;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }
    
    public void setNDivs( int ndivs ) {
        this.setNDivs( ndivs, ndivs ); 
    }

    public void setNDivs( int xndivs, int yndivs ) {
        this.xndivs = xndivs;
        this.zndivs = yndivs;
    }
    
    @Override
    public int getXNDivs() {
        return xndivs;
    }

    public void setXNDivs(int xndivs) {
        this.xndivs = xndivs;
    }

    @Override
    public int getZNDivs() {
        return zndivs;
    }

    public void setZNDivs(int zndivs) {
        this.zndivs = zndivs;
    }
    
    public boolean isYIntervaloAtivado() {
        return yIntervaloAtivado;
    }

    public void setYIntervaloAtivado(boolean yIntervaloAtivado) {
        this.yIntervaloAtivado = yIntervaloAtivado;
    }

    public Func3D getFunc3D() {
        return func3D;
    }

    public void setFunc3D(Func3D func3D) {
        this.func3D = func3D;
    }
    
    public ContainerObjeto3D getContainerObjeto3D() {
        return container;
    }

    @Override
    public void setContainerObjeto3D(ContainerObjeto3D container) {
        this.container = container;
    }
         
}
