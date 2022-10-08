package italo.iplot.plot3d.planocartesiano.g3d;

import italo.iplot.funcs.g3d.Func3D;
import italo.iplot.plot3d.g3d.ComponenteObjeto3D;
import italo.iplot.plot3d.g3d.ComponenteObjeto3DLimite;
import italo.iplot.plot3d.g3d.ContainerObjeto3D;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;

public class PCFuncParametricaObjeto3D extends Objeto3D implements ComponenteObjeto3D {
    
    private final int NUM_PTS_U = 20;
    private final int NUM_PTS_V = 20;
    
    private final int DEFAULT_U1 = -1;
    private final int DEFAULT_U2 =  1;
    private final int DEFAULT_V1 = -1;
    private final int DEFAULT_V2 =  1;
    
    private double[] u;
    private double[] v;
    
    private double[] vx;
    private double[] vy;
    private double[] vz;
    
    private double minX = -1;
    private double maxX = 1;    
    private double minY = -1;
    private double maxY = 1;
    private double minZ = -1;
    private double maxZ = 1;    
    
    private boolean xIntervaloAtivado = false;
    private boolean yIntervaloAtivado = false;
    private boolean zIntervaloAtivado = false;
    
    private int nvertices = 0;
    
    private Func3D funcX = null;
    private Func3D funcY = null;
    private Func3D funcZ = null;
    private PCFuncParametrica3DOpers opers = null;
    
    private ContainerObjeto3D container;            
    
    public PCFuncParametricaObjeto3D() {        
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
        if ( funcX == null || funcY == null || funcZ == null )
            return;
                        
        for( int i = 0; i < v.length; i++ ) {
            for( int j = 0; j < u.length; j++ ) {                                
                double x = container.calculaX( vx[ ( i * u.length ) + j ] );
                double y = container.calculaY( vy[ ( i * u.length ) + j ] );
                double z = container.calculaZ( vz[ ( i * u.length ) + j ] );
                
                Vertice3D v = new Vertice3D( x, y, z );
                
                super.getEstrutura().addVertice( v ); 
                
                if ( i > 0 && j > 0 ) { 
                    Vertice3D v1 = super.getEstrutura().getVertices().get( ( i-1 ) * ( u.length ) + ( j-1 ) ); 
                    Vertice3D v2 = super.getEstrutura().getVertices().get( ( i-1 ) * ( u.length ) + ( j   ) ); 
                    Vertice3D v3 = super.getEstrutura().getVertices().get( ( i   ) * ( u.length ) + ( j   ) ); 
                    Vertice3D v4 = super.getEstrutura().getVertices().get( ( i   ) * ( u.length ) + ( j-1 ) ); 
                                                                      
                    Face3D f = new Face3D();
                    f.addVertice( v1 );
                    f.addVertice( v2 );
                    f.addVertice( v3 );
                    f.addVertice( v4 ); 
                    
                    Face3D f2 = new Face3D();
                    f2.setInverterVN( true );
                    f2.addVertice( v1 );
                    f2.addVertice( v2 );
                    f2.addVertice( v3 );
                    f2.addVertice( v4 ); 
                                        
                    super.getEstrutura().addFace( f, to );                    
                    super.getEstrutura().addFace( f2, to ); 
                }                
            }
        }                   
    }

    @Override
    public void escalar(double escala, Objeto3DTO to) {
        double[] vc = container.verticeCentral();
        to.getTransformador3D().sub( estrutura.getVertices(), vc, to.getXYZFiltroV3D() );
        to.getTransformador3D().escala( this, escala, to.getXYZFiltroV3D() );        
        to.getTransformador3D().soma( estrutura.getVertices(), vc, to.getXYZFiltroV3D() );
    }

    @Override
    public ComponenteObjeto3DLimite calculaLimites() {
        double x1 = Double.MAX_VALUE;
        double x2 = Double.MIN_VALUE;
        double y1 = Double.MAX_VALUE;
        double y2 = Double.MIN_VALUE;
        double z1 = Double.MAX_VALUE;
        double z2 = Double.MIN_VALUE;                
        
        if ( u == null )
            this.setVetorU( DEFAULT_U1, DEFAULT_U2 );        
        if ( v == null )
            this.setVetorV( DEFAULT_V1, DEFAULT_V2 );

        nvertices = u.length * v.length;
        
        vx = new double[ nvertices ];
        vy = new double[ nvertices ];
        vz = new double[ nvertices ];

        int k = 0;
        for( int i = 0; i < v.length; i++ ) {
            for( int j = 0; j < u.length; j++ ) {                                        
                double pcx = Double.NaN;
                double pcy = Double.NaN;
                double pcz = Double.NaN;
                
                if ( funcX != null )  {
                    try {
                        pcx = funcX.getY( u[j], v[i] );                          
                        if ( pcx != Double.NaN && pcx != Double.NEGATIVE_INFINITY && pcx != Double.POSITIVE_INFINITY ) {                                                                                
                            if ( xIntervaloAtivado ? pcx >= minX && pcx <= maxX : true ) {
                                if ( pcx < x1 )
                                    x1 = pcx;
                                if ( pcx > x2 )
                                    x2 = pcx;
                            }
                        }
                    } catch ( ArithmeticException e ) {
                        pcx = Double.NaN;
                    }                                                
                }
                
                if ( funcY != null )  {
                    try {
                        pcy = funcY.getY( u[j], v[i] );                          
                        if ( pcy != Double.NaN && pcy != Double.NEGATIVE_INFINITY && pcy != Double.POSITIVE_INFINITY ) {                        
                            if ( yIntervaloAtivado ? pcy >= minY && pcy <= maxY : true ) {
                                if ( pcy < y1 )
                                    y1 = pcy;
                                if ( pcy > y2 )
                                    y2 = pcy;
                            }
                        }                        
                    } catch ( ArithmeticException e ) {
                        pcy = Double.NaN;
                    }                    
                }
                
                if ( funcZ != null )  {
                    try {
                        pcz = funcZ.getY( u[j], v[i] );  
                        if ( pcz != Double.NaN && pcz != Double.NEGATIVE_INFINITY && pcz != Double.POSITIVE_INFINITY ) {                        
                            if ( zIntervaloAtivado ? pcz >= minZ && pcz <= maxZ : true ) {
                                if ( pcz < z1 )
                                    z1 = pcz;
                                if ( pcz > z2 )
                                    z2 = pcz;
                            }
                        }                        
                    } catch ( ArithmeticException e ) {
                        pcz = Double.NaN;
                    }                    
                }

                vx[ k ] = pcx;
                vy[ k ] = pcy;
                vz[ k ] = pcz;
                k++;
            }
        }        
        
        if ( opers != null ) {
            double[][] mat = { vx, vy, vz };
            double[][] result = opers.operXYZ( mat, k );
            
            vx = result[0];
            vy = result[1];
            vz = result[2];
            
            for( int i = 0; i < k; i++ ) {
                double pcx = vx[ i ];
                double pcy = vy[ i ];
                double pcz = vz[ i ];
                
                if ( pcx != Double.NaN && pcx != Double.NEGATIVE_INFINITY && pcx != Double.POSITIVE_INFINITY ) {                                                                                
                    if ( xIntervaloAtivado ? pcx >= minX && pcx <= maxX : true ) {
                        if ( pcx < x1 )
                            x1 = pcx;
                        if ( pcx > x2 )
                            x2 = pcx;
                    }
                }
                
                if ( pcy != Double.NaN && pcy != Double.NEGATIVE_INFINITY && pcy != Double.POSITIVE_INFINITY ) {                        
                    if ( yIntervaloAtivado ? pcy >= minY && pcy <= maxY : true ) {
                        if ( pcy < y1 )
                            y1 = pcy;
                        if ( pcy > y2 )
                            y2 = pcy;
                    }
                }
                
                if ( pcz != Double.NaN && pcz != Double.NEGATIVE_INFINITY && pcz != Double.POSITIVE_INFINITY ) {                        
                    if ( zIntervaloAtivado ? pcz >= minZ && pcz <= maxZ : true ) {
                        if ( pcz < z1 )
                            z1 = pcz;
                        if ( pcz > z2 )
                            z2 = pcz;
                    }
                } 
            }
        }

        if ( x1 == Double.MAX_VALUE )
            x1 = ComponenteObjeto3DLimite.MINX;
        if ( x2 == Double.MIN_VALUE )
            x2 = ComponenteObjeto3DLimite.MAXX;
        
        if ( y1 == Double.MAX_VALUE )
            y1 = ComponenteObjeto3DLimite.MINY;
        if ( y2 == Double.MIN_VALUE )
            y2 = ComponenteObjeto3DLimite.MAXY;
        
        if ( z1 == Double.MAX_VALUE )
            z1 = ComponenteObjeto3DLimite.MINZ;
        if ( z2 == Double.MIN_VALUE )
            z2 = ComponenteObjeto3DLimite.MAXZ;
        
        return new ComponenteObjeto3DLimite( x1, x2, y1, y2, z1, z2 );
    }
        
    public void setVetoresUeV( double u1, double u2, double v1, double v2 ) {
        this.setVetorU( u1, u2, NUM_PTS_U );
        this.setVetorV( v1, v2, NUM_PTS_V );
    }
        
    public void setVetorU( double u1, double u2 ) {
        this.setVetorU( u1, u2, NUM_PTS_U );
    }
    
    public void setVetorV( double v1, double v2 ) {
        this.setVetorV( v1, v2, NUM_PTS_V );
    }
        
    public void setVetoresUeV( double u1, double u2, int npu, double v1, double v2, int npv ) {
        this.setVetorU( u1, u2, npu );
        this.setVetorV( v1, v2, npv );
    }
    
    public void setVetorU( double u1, double u2, int npu ) {
        u = new double[ npu ];
        
        double du = Math.abs( u2 - u1 ) / ( npu - 1 );
        
        for( int i = 0; i < npu; i++ )
            u[ i ] = u1 + i * du;               
    }
    
    public void setVetorV( double v1, double v2, int npv ) {
        v = new double[ npv ];
        
        double dv = Math.abs( v2 - v1 ) / ( npv - 1 );
        
        for( int i = 0; i < npv; i++ )
            v[ i ] = v1 + i * dv;               
    }
    
    public void setVetoresUeV( double[] u, double[] v ) {
        this.u = u;
        this.v = v;
    }

    public double[] getVetorU() {
        return u;
    }

    public void setVetorU(double[] u) {
        this.u = u;
    }

    public double[] getVetorV() {
        return v;
    }

    public void setVetorV(double[] v) {
        this.v = v;
    }

    public PCFuncParametrica3DOpers getFuncParametricaOpers() {
        return opers;
    }

    public void setFuncParametricaOpers(PCFuncParametrica3DOpers opers) {
        this.opers = opers;
    }

    public Func3D getFuncX() {
        return funcX;
    }

    public void setFuncX(Func3D funcX) {
        this.funcX = funcX;
    }

    public Func3D getFuncY() {
        return funcY;
    }

    public void setFuncY(Func3D funcY) {
        this.funcY = funcY;
    }

    public Func3D getFuncZ() {
        return funcZ;
    }

    public void setFuncZ(Func3D funcZ) {
        this.funcZ = funcZ;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
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

    public double getMinZ() {
        return minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    public boolean isXIntervaloAtivado() {
        return xIntervaloAtivado;
    }

    public void setXIntervaloAtivado(boolean xIntervaloAtivado) {
        this.xIntervaloAtivado = xIntervaloAtivado;
    }

    public boolean isYIntervaloAtivado() {
        return yIntervaloAtivado;
    }

    public void setYIntervaloAtivado(boolean yIntervaloAtivado) {
        this.yIntervaloAtivado = yIntervaloAtivado;
    }

    public boolean isZIntervaloAtivado() {
        return zIntervaloAtivado;
    }

    public void setZIntervaloAtivado(boolean zIntervaloAtivado) {
        this.zIntervaloAtivado = zIntervaloAtivado;
    }
        
    public ContainerObjeto3D getContainerObjeto3D() {
        return container;
    }
    
    @Override
    public void setContainerObjeto3D(ContainerObjeto3D container) {
        this.container = container;
    }
    
}
