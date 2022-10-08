package italo.iplot.plot3d.planocartesiano.g3d;

import italo.iplot.planocartesiano.coordenada.PCCoordenadaCalc;
import italo.iplot.planocartesiano.movesc.PCMovEscConfigurador;
import italo.iplot.planocartesiano.telaajuste.PCPlotObjManagerTelaAjuste;
import italo.iplot.planocartesiano.regua.ReguaUtil;
import italo.iplot.plot3d.g3d.ComponenteObjeto3DLimite;
import italo.iplot.plot3d.g3d.ComponenteObjeto3D;
import java.util.ArrayList;
import java.util.List;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.planocartesiano.g3d.coordenada.XContainerCoordenada3D;
import italo.iplot.plot3d.planocartesiano.g3d.coordenada.YContainerCoordenada3D;
import italo.iplot.plot3d.planocartesiano.g3d.coordenada.ZContainerCoordenada3D;
import italo.iplot.plot3d.planocartesiano.g3d.movesc.XContainerMovEsc3D;
import italo.iplot.plot3d.planocartesiano.g3d.movesc.ZContainerMovEsc3D;

public class PlotObj3DManager implements PCPlotObjManagerTelaAjuste {
            
    protected PlotObj3DContainer container;
          
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private double minZ;
    private double maxZ;
    
    private double ix1;
    private double ix2;
    private double iz1;
    private double iz2;
    private double iy1;
    private double iy2;
    
    private double ixf;
    private double iyf;
    private double izf;
        
    private double iincX = 0.5d;
    private double iincY = 0.5d;
    private double iincZ = 0.5d;       
       
    private double ideslocX = 0;
    private double ideslocY = 0;
    private double ideslocZ = 0;
    
    private int xNumRotulos = ReguaUtil.NUM_ROTULOS_PADRAO;
    private int yNumRotulos = ReguaUtil.NUM_ROTULOS_PADRAO;
    private int zNumRotulos = ReguaUtil.NUM_ROTULOS_PADRAO;
    
    private final XContainerCoordenada3D xcalcula = new XContainerCoordenada3D( this );
    private final YContainerCoordenada3D ycalcula = new YContainerCoordenada3D( this );
    private final ZContainerCoordenada3D zcalcula = new ZContainerCoordenada3D( this );
    private final PCCoordenadaCalc calculadora = new PCCoordenadaCalc();
    
    private final PCMovEscConfigurador movescCFG = new PCMovEscConfigurador();
    private final XContainerMovEsc3D xmovesc = new XContainerMovEsc3D( this );
    private final ZContainerMovEsc3D zmovesc = new ZContainerMovEsc3D( this );
     
    private boolean calcularIntervalo = true;
    
    private final List<ComponenteObjeto3D> plotObjs = new ArrayList();
    
    public PlotObj3DManager( PlotObj3DContainer container ) {
        this.container = container;
    }
    
    public void removePlotObjs() {
        synchronized( plotObjs ) {
            plotObjs.clear();
        }
    }
    
    public void addPlotObj3D( ComponenteObjeto3D obj ) {
        synchronized( plotObjs ) {
            plotObjs.add( obj );
        }        
    }
    
    public void constroi( Objeto3DTO to ) {
        synchronized( plotObjs ) {
            for( ComponenteObjeto3D obj : plotObjs )
                obj.constroi( to ); 
        }
    }
    
    public void configura() {                
        this.minX = Double.MAX_VALUE;
        this.maxX = Double.MIN_VALUE;
        this.minY = Double.MAX_VALUE;
        this.maxY = Double.MIN_VALUE;
        this.minZ = Double.MAX_VALUE;
        this.maxZ = Double.MIN_VALUE;
        
        if ( plotObjs.isEmpty() ) {
            this.minX = ComponenteObjeto3DLimite.MINX;
            this.maxX = ComponenteObjeto3DLimite.MAXX;
            this.minY = ComponenteObjeto3DLimite.MINY;
            this.maxY = ComponenteObjeto3DLimite.MAXY;
            this.minZ = ComponenteObjeto3DLimite.MINZ;
            this.maxZ = ComponenteObjeto3DLimite.MAXZ;
        } else {
            for( ComponenteObjeto3D obj : plotObjs ) {
                ComponenteObjeto3DLimite lims = obj.calculaLimites();
                if ( lims.getMinX() < minX )
                    minX = lims.getMinX();
                if ( lims.getMaxX() > maxX )
                    maxX = lims.getMaxX();            
                if ( lims.getMinY() < minY )
                    minY = lims.getMinY();
                if ( lims.getMaxY() > maxY )
                    maxY = lims.getMaxY();
                if ( lims.getMinZ() < minZ )
                    minZ = lims.getMinZ();
                if ( lims.getMaxZ() > maxZ )
                    maxZ = lims.getMaxZ();        
            }                     
        }
        
        double[] xinter = container.calculaIntervalo( minX, maxX, xNumRotulos );
        double[] yinter = container.calculaIntervalo( minY, maxY, yNumRotulos );
        double[] zinter = container.calculaIntervalo( minZ, maxZ, zNumRotulos );

        if ( calcularIntervalo ) {
            ix1 = xinter[0];
            ix2 = xinter[1];
            iy1 = yinter[0];
            iy2 = yinter[1];        
            iz1 = zinter[0];
            iz2 = zinter[1];

            iincX = xinter[2];
            iincY = yinter[2];
            iincZ = zinter[2];                                           
        } else {
            ix1 = minX;
            ix2 = maxX;
            iincX = Math.abs(ix2-ix1) / xNumRotulos;
            
            iy1 = minY;
            iy2 = maxY;
            iincY = Math.abs(iy2-iy1) / yNumRotulos;
            
            iz1 = minZ;
            iz2 = maxZ;
            iincZ = Math.abs(iz2-iz1) / zNumRotulos;
        }                
        
        ixf = Math.abs( maxX - minX ) / Math.abs( ix2 - ix1 );
        iyf = Math.abs( maxY - minY ) / Math.abs( iy2 - iy1 );
        izf = Math.abs( maxZ - minZ ) / Math.abs( iz2 - iz1 );
        
        ideslocX = ideslocY = ideslocZ = 0;
    }
    
    public void mover( double xdesloc, double zdesloc, Objeto3DTO to ) {        
        double idx = Math.abs( ix2 - ix1 );
        double idz = Math.abs( iz2 - iz1 );                                           
        double xf = idx / container.getDX();
        double zf = idz / container.getDZ();

        double[][] xReguaLinha = container.xFrenteReguaLinha();
        double[][] zReguaLinha = container.zFrenteReguaLinha();
        
        double a;
        double yang = container.getYRot();
        double xang = container.getXRot();
        if ( yang < 0 ) {
            a = 2*Math.PI + ( yang % (2*Math.PI) );
        } else {
            a = yang % ( 2*Math.PI );
        }        
                        
        double[][] zlinha = zReguaLinha;
        double[][] xlinha = xReguaLinha;                              

        int ang_q = (int)( ( a + (Math.toRadians( 1 ) )) / (Math.PI*.5d) );
        if ( ( ang_q % 2 == 1 && xang < 0 ) || ( ang_q % 2 == 0 && xang >= 0) ) {
            double[][] laux = zlinha;
            zlinha = xlinha;
            xlinha = laux;            
        }
         
        if ( zlinha[0][1] < zlinha[1][1] ) {
            double[] aux = zlinha[0];
            zlinha[0] = zlinha[1];
            zlinha[1] = aux; 
        }

        if ( xlinha[0][1] > xlinha[1][1] ) {
            double[] aux = xlinha[0];
            xlinha[0] = xlinha[1];
            xlinha[1] = aux; 
        }
        
        double[] p = zlinha[0].clone();
        p[0] += zdesloc; 
                
        double[][] planoBase = container.planoBase();
        double[] pb1 = planoBase[0];
        double[] pb2 = planoBase[1];
        double[] pb3 = planoBase[2];
        double[] vnPlanoBase = to.getMath3D().vetorNormal( pb1, pb2, pb3 );
        
        double[] zlinhaP1 = zlinha[0];
        double[] zlinhaP2 = zlinha[1];        
                
        double[] xlinhaP1 = xlinha[0];
        double[] xlinhaP2 = xlinha[1];
                
        double[] projp = to.getMath3D().projecaoOrtogonalDePontoSobrePlano( p, pb1, pb2, pb3 );                
                                              
        double[] pm = to.getMath3D().soma( xlinhaP1, xlinhaP2 );
        pm = to.getMath3D().div( pm, 2.0d );
                
        double[] lp1Vet = to.getMath3D().sub( xlinhaP1, pm );
        
        double[] zdesP = to.getMath3D().soma( projp, lp1Vet );        

        double[] p1 = zlinhaP1;             
        double[] p2 = zlinhaP2;
        double[] p3 = to.getMath3D().soma( p1, vnPlanoBase );
        
        double desZ = 0;
        double desX = 0;
        if ( !to.getMath3D().verificaSeRetaEParalelaAPlano( projp, zdesP, p1, p2, p3 ) ) {
            double[] p_int = to.getMath3D().intersecaoRetaPlano( projp, zdesP, p1, p2, p3 );
            if ( p_int != null ) {                
                desX = to.getMath3D().distancia( p_int, projp );                                                                
                desZ = to.getMath3D().distancia( p_int, zlinhaP1 );
                
                if ( !to.getMath3D().verificaSeParalelepipedoContemPonto( zlinhaP1, zlinhaP2, p_int ) ) {
                    desX = -desX;
                    desZ = -desZ;                    
                }                
                                                
                double[] pp = zlinhaP1.clone();
                pp[2] += xdesloc;
                
                pp = to.getMath3D().projecaoOrtogonalDePontoSobrePlano( pp, pb1, pb2, pb3 );
                
                pm = to.getMath3D().soma( zlinhaP1, zlinhaP2 );
                pm = to.getMath3D().div( pm, 2.0d );
                
                double[] xvet = to.getMath3D().sub( zlinhaP1, pm );
                
                double[] xp = to.getMath3D().soma( pp, xvet );

                p1 = xlinhaP1;             
                p2 = xlinhaP2;
                p3 = to.getMath3D().soma( p1, vnPlanoBase );
                
                if ( !to.getMath3D().verificaSeRetaEParalelaAPlano( pp, xp, p1, p2, p3 ) ) {
                    p_int = to.getMath3D().intersecaoRetaPlano( pp, xp, p1, p2, p3 );
                    if ( p_int != null ) {
                        double desX2 = to.getMath3D().distancia( p_int, xlinhaP1 );
                        
                        xvet = to.getMath3D().sub( p_int, xlinhaP1 );
                        double[] xp2 = to.getMath3D().soma( zlinhaP1, xvet );
                        
                        double desZ2 = to.getMath3D().distancia( pp, xp2 );                                                                        
                                                 
                        if ( !to.getMath3D().verificaSeParalelepipedoContemPonto( xlinhaP1, xlinhaP2, p_int ) ) {
                            desZ2 = -desZ2;
                        } else {
                            desX2 = -desX2;
                        }                         
                                                                        
                        desX += desX2;
                        desZ += desZ2;
                    }
                }                 
            }
        }

        double daux;
        if ( xang < 0 ) {            
            if ( ang_q % 2 == 1 ) {
                daux = desX;
                desX = desZ;
                desZ = daux;
            }
            switch( ang_q ) {
                case 1:                        
                    desX = -desX;
                    break;
                case 2:
                    desX = -desX;
                    desZ = -desZ;
                    break;
                case 3:                
                    desZ = -desZ;
                    break;
            }       
        } else {
            if ( ang_q % 2 == 0 ) {
                daux = desX;
                desX = desZ;
                desZ = daux;           
            }                        
            switch( ang_q ) {
                case 1:     
                    desX = -desX;
                    break;
                case 2:
                    desZ = -desZ;
                    desX = -desX;
                    break;
                case 3:                
                    desZ = -desZ;
                    break;
            }                    
        }
                        
        movescCFG.move( zmovesc, desZ * zf );                
        movescCFG.move( xmovesc, desX * xf );         
    }
     
    public void escalar( double escala, Objeto3DTO to ) {
        movescCFG.escalar( xmovesc, escala );
        movescCFG.escalar( zmovesc, escala ); 
        
        for( ComponenteObjeto3D obj : plotObjs )
            obj.escalar( 1.0d / escala, to );
    }
        
    public double calculaX( double x ) {
        return calculadora.calcula( xcalcula, x );
    }
    
    public double calculaY( double y ) {
        return calculadora.calcula( ycalcula, y );        
    }
    
    public double calculaZ( double z ) {                
        return calculadora.calcula( zcalcula, z );      
    }

    public List<ComponenteObjeto3D> getPlotObjs() {
        return plotObjs;
    }
    
    public double getIMinX() {
        return Math.min( ix1, ix2 );
    }

    public double getIMaxX() {
        return Math.max( ix1, ix2 );
    }

    public double getIMinZ() {
        return Math.min( iz1, iz2 );
    }

    public double getIMaxZ() {
        return Math.max( iz1, iz2 ); 
    }

    public double getIMinY() {
        return Math.min( iy1, iy2 );
    }
    
    public double getIMaxY() {
        return Math.max( iy1, iy2 ); 
    }

    public double getIX1() {
        return ix1;
    }

    public void setIX1(double ix1) {
        this.ix1 = ix1;
    }

    public double getIX2() {
        return ix2;
    }

    public void setIX2(double ix2) {
        this.ix2 = ix2;
    }

    public double getIZ1() {
        return iz1;
    }

    public void setIZ1(double iz1) {
        this.iz1 = iz1;
    }

    public double getIZ2() {
        return iz2;
    }

    public void setIZ2(double iz2) {
        this.iz2 = iz2;
    }

    public double getIY1() {
        return iy1;
    }

    public void setIY1(double iy1) {
        this.iy1 = iy1;
    }

    public double getIY2() {
        return iy2;
    }

    public void setIY2(double iy2) {
        this.iy2 = iy2;
    }

    public double getIXF() {
        return ixf;
    }

    public void setIXF(double ixf) {
        this.ixf = ixf;
    }

    public double getIYF() {
        return iyf;
    }

    public void setIYF(double iyf) {
        this.iyf = iyf;
    }

    public double getIZF() {
        return izf;
    }

    public void setIZF(double izf) {
        this.izf = izf;
    }

    public double getIIncX() {
        return iincX;
    }

    public void setIIncX(double iincX) {
        this.iincX = iincX;
    }

    public double getIIncY() {
        return iincY;
    }

    public void setIIncY(double iincY) {
        this.iincY = iincY;
    }

    public double getIIncZ() {
        return iincZ;
    }

    public void setIIncZ(double iincZ) {
        this.iincZ = iincZ;
    }

    public double getIDeslocX() {
        return ideslocX;
    }

    public void setIDeslocX(double ideslocX) {
        this.ideslocX = ideslocX;
    }

    public double getIDeslocY() {
        return ideslocY;
    }

    public void setIDeslocY(double ideslocY) {
        this.ideslocY = ideslocY;
    }

    public double getIDeslocZ() {
        return ideslocZ;
    }

    public void setIDeslocZ(double ideslocZ) {
        this.ideslocZ = ideslocZ;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }
    
    public void setXYZNumRotulos( int numRotulos ) {
        this.xNumRotulos = this.yNumRotulos = this.zNumRotulos = numRotulos;
    }

    public int getXNumRotulos() {
        return xNumRotulos;
    }

    public int getYNumRotulos() {
        return yNumRotulos;
    }

    public int getZNumRotulos() {
        return zNumRotulos;
    }

    public void setXNumRotulos(int xNumRotulos) {
        this.xNumRotulos = xNumRotulos;
    }

    public void setYNumRotulos(int yNumRotulos) {
        this.yNumRotulos = yNumRotulos;
    }

    public void setZNumRotulos(int zNumRotulos) {
        this.zNumRotulos = zNumRotulos;
    }

    public boolean isCalcularIntervalo() {
        return calcularIntervalo;
    }

    public void setCalcularIntervalo(boolean calcularIntervalo) {
        this.calcularIntervalo = calcularIntervalo;
    }     

    public PlotObj3DContainer getContainer() {
        return container;
    }
    
}
