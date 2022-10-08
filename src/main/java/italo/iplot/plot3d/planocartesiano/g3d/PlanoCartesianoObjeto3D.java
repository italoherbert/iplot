package italo.iplot.plot3d.planocartesiano.g3d;

import italo.iplot.planocartesiano.Legenda;
import italo.iplot.planocartesiano.regua.ReguaUtil;
import italo.iplot.plot3d.g3d.ComponenteObjeto3D;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T4T3F3F4ZDivisoes;
import java.awt.Color;
import java.util.List;
import italo.iplot.plot3d.g3d.Face3D;
import italo.iplot.planocartesiano.telaajuste.PCContainerTelaAjuste;
import italo.iplot.planocartesiano.telaajuste.PCPlotObjManagerTelaAjuste;
import italo.iplot.planocartesiano.telaajuste.PCTelaAjustador;
import italo.iplot.plot3d.g3d.Face3DVisivel;
import italo.iplot.plot3d.g3d.LinhaObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.ParalelepipedoObjeto3D;
import italo.iplot.plot3d.g3d.VN_Z_Face3DVisivel;
import italo.iplot.plot3d.planocartesiano.g3d.divs.Divisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.F1F2F3F4XDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.F1F2F3F4YDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T1F1F4T4YDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T1F1F4T4ZDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T1T2F2F1XDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T1T2F2F1ZDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T1T2T3T4XDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T1T2T3T4YDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T2T3F3F2YDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T2T3F3F2ZDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.divs.T4T3F3F4XDivisoes;
import italo.iplot.plot3d.planocartesiano.g3d.regua.Base12Regua;
import italo.iplot.plot3d.planocartesiano.g3d.regua.Base23Regua;
import italo.iplot.plot3d.planocartesiano.g3d.regua.Base34Regua;
import italo.iplot.plot3d.planocartesiano.g3d.regua.Base41Regua;
import italo.iplot.plot3d.planocartesiano.g3d.regua.Y1Regua;
import italo.iplot.plot3d.planocartesiano.g3d.regua.Y2Regua;
import italo.iplot.plot3d.planocartesiano.g3d.regua.Y3Regua;
import italo.iplot.plot3d.planocartesiano.g3d.regua.Y4Regua;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.planocartesiano.g3d.telaajuste.PCTelaAjuste3DTO;
import italo.iplot.plot3d.planocartesiano.objgrafico.PlanoCartesianoObj3DGrafico;
import java.awt.Font;
import java.util.ArrayList;

public class PlanoCartesianoObjeto3D extends ParalelepipedoObjeto3D implements PlotObj3DContainer, PCContainerObjeto3D, PCContainerTelaAjuste {
        
    private final int REGUA_TRACO_COMPRIMENTO_PX = 10;
    private final int REGUA_VALOR_DISTANCIA_PX = 20;
    
    private final int TITULO_GRAFICO_DIST_PX = 10;    
    private final int GRADE_PONTOS_ESPS_PX = 3;
    private final int EIXO_ROTULOS_DIST_PX = 20;
    private final int BORDA_PX = 10;    
    
    private final int LEGENDA_TRACO_COMPRIMENTO_PX = 20;
    private final int LEGENDA_PONTO_RAIO_PX = 3;
    private final int LEGENDA_BORDA_VERTICAL_PX = 0;
    private final int LEGENDA_BORDA_LATERAL_PX = 3;
    private final int LEGENDA_ESP_PX = 5;
    private final int GRUPO_LEGENDA_BORDA_PX = 10;
    private final int GRUPO_LEGENDA_YESP_PX = 4;
            
    private final boolean EH3D = true;
    
    private ReguaPlanoCartesianoObjeto3D lY1 = null;
    private ReguaPlanoCartesianoObjeto3D lY2 = null;
    private ReguaPlanoCartesianoObjeto3D lY3 = null;
    private ReguaPlanoCartesianoObjeto3D lY4 = null;
    private ReguaPlanoCartesianoObjeto3D baseL12 = null;
    private ReguaPlanoCartesianoObjeto3D baseL23 = null;
    private ReguaPlanoCartesianoObjeto3D baseL34 = null;
    private ReguaPlanoCartesianoObjeto3D baseL41 = null;
    
    private final PlotObj3DManager plotObj3DManager = new PlotObj3DManager( this );
        
    private final ReguaUtil reguaUtil = new ReguaUtil();
        
    private int tituloGraficoDistanciaPX = TITULO_GRAFICO_DIST_PX;
    private int eixoRotulosDistanciaPX = EIXO_ROTULOS_DIST_PX;
    private int gradePontosEspsPX = GRADE_PONTOS_ESPS_PX;
    private int reguaTracoComprimentoPX = REGUA_TRACO_COMPRIMENTO_PX;
    private int reguaValorDistanciaPX = REGUA_VALOR_DISTANCIA_PX;
    private int bordaPX = BORDA_PX;

    private Color gradeCor = null;
    private Color baseCor = null;
    private Color reguaCor = null;
    private Color tituloCor = null;
    private Color eixoRotulosCor = null;
    private Color legendaCor = null;
    
    private boolean pintarCorBase = false;
    private boolean configurarPlotObjsManager = true;
    
    private double xrot = 0;
    private double yrot = 0;
        
    private double centroX = 0;
    private double centroY = 0;
    private double pcx = 0;
    private double pcy = 0;
    private double pcz = 0;
    private double pcLargura = 0;
    private double pcAltura = 0;
        
    private double reguaYValorLarguraMax = 0;
    private double reguaYValorAlturaMax = 0;
        
    private boolean ajustarATela = true;
    private double altura2D = 1.95d;    
    
    private boolean pintarRegua = true;
    private boolean pintarGrade = true;
    private boolean pontilharGrade = true;
    private boolean pintarEixoRotulos = true;
            
    private String titulo = "Plano";    
    private String xEixoRotulo = "Eixo X";
    private String yEixoRotulo = "Eixo Y";
    private String zEixoRotulo = "Eixo Z";
            
    private Font tituloFont = new Font( Font.MONOSPACED, Font.BOLD, 24 );
    private Font eixoRotuloFont = new Font( Font.MONOSPACED, Font.BOLD, 12 );    
    private Font reguaValoresFont = new Font( Font.MONOSPACED, Font.BOLD, 8 );
    private Font legendaFont = new Font( Font.MONOSPACED, Font.BOLD, 10 );

    private double tituloX = 0;
    private double tituloY = 0;
    
    private int legendaTracoComprimentoPX = LEGENDA_TRACO_COMPRIMENTO_PX;
    private int legendaPontoRaioPX = LEGENDA_PONTO_RAIO_PX;
    private int legendaBordaLateralPX = LEGENDA_BORDA_LATERAL_PX;
    private int legendaBordaVerticalPX = LEGENDA_BORDA_VERTICAL_PX;
    private int legendaEspPX = LEGENDA_ESP_PX;
    private int grupoLegendaBordaPX = GRUPO_LEGENDA_BORDA_PX;
    private int grupoLegendaYEspPX = GRUPO_LEGENDA_YESP_PX;
        
    private final List<Legenda> legendas = new ArrayList();
    
    private final PCTelaAjustador ajustador = new PCTelaAjustador();
    
    private final Divisoes[] DIVS = {
        new F1F2F3F4XDivisoes( this ),
        new F1F2F3F4YDivisoes( this ),
        new T1T2T3T4XDivisoes( this ),
        new T1T2T3T4YDivisoes( this ),
        new T1F1F4T4ZDivisoes( this ),                
        new T1F1F4T4YDivisoes( this ),
        new T2T3F3F2ZDivisoes( this ),
        new T2T3F3F2YDivisoes( this ),
        new T4T3F3F4XDivisoes( this ),
        new T4T3F3F4ZDivisoes( this ),
        new T1T2F2F1XDivisoes( this ),
        new T1T2F2F1ZDivisoes( this ) 
    };
                                
    public PlanoCartesianoObjeto3D() {
        super.setDX( 1.0d );
        super.setDY( 0.7d );
        super.setDZ( 1.0d );
        super.pintarFaces = false;
        super.pintarArestas = false;
        super.desenharFaces = false;
        
        super.pintarVertices = false;
        super.inverterVetoresNormais = true;
        super.aplicarIluminacaoAFace = false;  
        
        super.cortarFilhos = true;
        
        super.cor = new Color( 255, 255, 255 );
        super.faceArestasCor = Color.GRAY;
                
        //this.baseCor = new Color( 220, 220, 220 );
        this.gradeCor = Color.BLACK;
        this.reguaCor = Color.BLACK;                         
        this.tituloCor = Color.BLACK;
        this.eixoRotulosCor = Color.BLACK;
        this.legendaCor = Color.BLACK;
        
        super.grafico = new PlanoCartesianoObj3DGrafico();
    }
                       
    @Override
    public void constroiObjeto3D( Objeto3DTO to ) {
        super.constroiObjeto3D( to );
                
        super.removeTodosOsObjetos();
                
        legendas.clear();
                
        if ( configurarPlotObjsManager )
            plotObj3DManager.configura();
        
        if ( ajustarATela ) {   
            PCTelaAjuste3DTO telaAjusteTO = new PCTelaAjuste3DTO( to );
            ajustador.ajusta( this, telaAjusteTO, EH3D );                        
            
            if ( titulo != null ) {            
                double[] tituloXY = ajustador.calculaPosTitulo( titulo, this, telaAjusteTO, EH3D );
                tituloX = tituloXY[0];
                tituloY = tituloXY[1]; 
            }
        
        }
        
        if ( pintarCorBase )
            super.getFaceT4T3F3F4().setCor( baseCor );
                
        List<Face3D> faces = estrutura.getFaces();
        
        Face3DVisivel vn_z_face3DVisivel = new VN_Z_Face3DVisivel( to );
        faces.forEach( f -> f.setFace3DVisivel( vn_z_face3DVisivel ) );                     
        
        for( Divisoes div : DIVS ) {
            int numRotulos = div.getNumRotulos();
            double dn = div.getDN();
            double h_n1 = -dn*.5d;
            double h_n2 =  dn*.5d;
            for( int i = 0; i < numRotulos; i++ ) {
                double h = div.calculaH( reguaUtil, i );
                if ( h >= h_n1 && h <= h_n2 ) {
                    double[][] extrs = div.calculaExtremidades( h );
                                                            
                    LinhaObjeto3D linha = new LinhaObjeto3D( extrs[0], extrs[1] );
                    linha.setObj3DVisivel( () -> pintarGrade && div.getFace().isVisivel() );                    
                    if ( pontilharGrade )
                        linha.pontilharArestas( gradePontosEspsPX ); 
                    linha.setArestasCor( gradeCor ); 
                    linha.constroi( to );  
                                        
                    super.addObjeto( linha ); 
                }
            }        
        }
                
        double[] fv1 = super.getFrenteV1().getP().clone();
        double[] fv2 = super.getFrenteV2().getP().clone();
        double[] fv3 = super.getFrenteV3().getP().clone();
        double[] fv4 = super.getFrenteV4().getP().clone();

        double[] tv1 = super.getTrazV1().getP().clone();
        double[] tv2 = super.getTrazV2().getP().clone();
        double[] tv3 = super.getTrazV3().getP().clone();
        double[] tv4 = super.getTrazV4().getP().clone();                    

        Y1Regua rY1 = new Y1Regua( this );
        Y2Regua rY2 = new Y2Regua( this );
        Y3Regua rY3 = new Y3Regua( this );
        Y4Regua rY4 = new Y4Regua( this );

        Base12Regua rb12 = new Base12Regua( this );
        Base23Regua rb23 = new Base23Regua( this );
        Base34Regua rb34 = new Base34Regua( this );
        Base41Regua rb41 = new Base41Regua( this );

        lY1 = new ReguaPlanoCartesianoObjeto3D( this, rY1, tv4, tv1, Regua3DTipo.Y );
        lY2 = new ReguaPlanoCartesianoObjeto3D( this, rY2, tv3, tv2, Regua3DTipo.Y );
        lY3 = new ReguaPlanoCartesianoObjeto3D( this, rY3, fv3, fv2, Regua3DTipo.Y );
        lY4 = new ReguaPlanoCartesianoObjeto3D( this, rY4, fv4, fv1, Regua3DTipo.Y );

        baseL12 = new ReguaPlanoCartesianoObjeto3D( this, rb12, tv4, tv3, Regua3DTipo.X );
        baseL23 = new ReguaPlanoCartesianoObjeto3D( this, rb23, tv3, fv3, Regua3DTipo.Z );
        baseL34 = new ReguaPlanoCartesianoObjeto3D( this, rb34, fv3, fv4, Regua3DTipo.X );
        baseL41 = new ReguaPlanoCartesianoObjeto3D( this, rb41, fv4, tv4, Regua3DTipo.Z );

        ReguaPlanoCartesianoObjeto3D[] reguas = this.reguas();
        for( ReguaPlanoCartesianoObjeto3D regua : reguas ) {
            regua.setArestasCor( reguaCor );                                                 
            regua.setObj3DVisivel( () -> pintarRegua );
            
            super.addObjeto( regua );
        }
        
        for( ComponenteObjeto3D plotObj3D : plotObj3DManager.getPlotObjs() )
            super.addObjeto((Objeto3D)plotObj3D );                        
    }    
        
    public void transforma( Objeto3DTO to ) {
        super.reiniciaVertices();
               
        to.getTransformador3D().rotY( this, yrot, to.getXYZFiltroV3D() );        
        to.getTransformador3D().rotX( this, xrot, to.getXYZFiltroV3D() );        
        to.getTransformador3D().transladaX( this, pcx, to.getXYZFiltroV3D() );
        to.getTransformador3D().transladaY( this, pcy, to.getXYZFiltroV3D() ); 

        double fp = to.getMath3D().ajusteATelaFatorProporcao( this, pcAltura, to.getTela() );
        to.getTransformador3D().escala( this, fp, fp, fp, to.getXYZFiltroV3D() );
                         
        super.aplicaTransformacoes(); 
    }
    
    public void mover( double deslocXH, double deslocYH, Objeto3DTO to ) {                
        double telaH = to.getMath3D().verticeUnidade( to.getTela().getTelaLargura(), to.getTela() );
        double telaW = to.getMath3D().verticeUnidade( to.getTela().getTelaAltura(), to.getTela() );
        double desX = deslocXH * ( dx / telaW );
        double desZ = deslocYH * ( dz / telaH );
                
        plotObj3DManager.mover( desX, desZ, to );
        configurarPlotObjsManager = false;
        super.constroi( to );
        configurarPlotObjsManager = true;        
    }
    
    public void zoom( double escala, Objeto3DTO to ) {
        plotObj3DManager.escalar( 1.0d / escala, to );
        configurarPlotObjsManager = false;
        super.constroi( to );
        configurarPlotObjsManager = true; 
    }
    
    public void gradeVisivel( boolean visivel, Objeto3DTO to ) {
        pintarGrade = visivel;
        desenharFaces = visivel;
        
        configurarPlotObjsManager = false;
        super.constroi( to );
        configurarPlotObjsManager = true;        
    }
    
    public void reguaVisivel( boolean visivel, Objeto3DTO to ) {
        pintarRegua = visivel;
        
        configurarPlotObjsManager = false;
        super.constroi( to );
        configurarPlotObjsManager = true; 
    }
    
    @Override
    public void antesDeDesenhar() { 
        if ( !super.construidoParcialmente )
            return; 
        
        this.atualizaVisibilidadeDasReguas();
    }

    @Override
    public void addLegenda(Legenda legenda) {
        legendas.add( legenda );
    }
    
    public void atualizaVisibilidadeDasReguas() {
        ReguaPlanoCartesianoObjeto3D[] lYs = { lY1, lY2, lY3, lY4 };
        
        ReguaPlanoCartesianoObjeto3D yregua = this.yFrenteRegua();
        for( ReguaPlanoCartesianoObjeto3D l : lYs )
            l.setVisivel( l == yregua );
        
        ReguaPlanoCartesianoObjeto3D xregua = this.xFrenteRegua();
        if ( xregua == baseL12 ) {
            baseL12.setVisivel( true );
            baseL34.setVisivel( false );
        } else {
            baseL12.setVisivel( false );
            baseL34.setVisivel( true );
        }
        
        ReguaPlanoCartesianoObjeto3D zregua = this.zFrenteRegua();
        if ( zregua == baseL23 ) {
            baseL23.setVisivel( true );
            baseL41.setVisivel( false );            
        } else {
            baseL23.setVisivel( false );
            baseL41.setVisivel( true );
        }          
    }        
    
    public ReguaPlanoCartesianoObjeto3D yFrenteRegua() {
        ReguaPlanoCartesianoObjeto3D[] lYs = { lY1, lY2, lY3, lY4 };
        ReguaPlanoCartesianoObjeto3D menorX_lY = null;
        double menorX = Double.MAX_VALUE;
        for( ReguaPlanoCartesianoObjeto3D l : lYs ) {
            if ( l.getAresta().getV1().getX() < menorX ) {
                menorX_lY = l;
                menorX = l.getAresta().getV1().getX();
            }
        }
        
        return menorX_lY;
    }
    
    public ReguaPlanoCartesianoObjeto3D zFrenteRegua() {
        double baseL23_min = Math.min( baseL23.getAresta().getV1().getY(), baseL23.getAresta().getV2().getY() );
        double baseL41_min = Math.min( baseL41.getAresta().getV1().getY(), baseL41.getAresta().getV2().getY() );
        
        if ( baseL23_min < baseL41_min ) {
            return baseL23;           
        } else {
            return baseL41;
        } 
    }
    
    public ReguaPlanoCartesianoObjeto3D xFrenteRegua() {
        double baseL12_min = Math.min( baseL12.getAresta().getV1().getY(), baseL12.getAresta().getV2().getY() );
        double baseL34_min = Math.min( baseL34.getAresta().getV1().getY(), baseL34.getAresta().getV2().getY() );
        
        if ( baseL12_min < baseL34_min ) {
            return baseL12;
        } else {
            return baseL34;
        }
    }

    @Override
    public double[][] xFrenteReguaLinha() {
        ReguaPlanoCartesianoObjeto3D xregua = this.xFrenteRegua();
        return new double[][] {
            xregua.getAresta().getV1().getP().clone(),
            xregua.getAresta().getV2().getP().clone()
        };
    }

    @Override
    public double[][] zFrenteReguaLinha() {
        ReguaPlanoCartesianoObjeto3D zregua = this.zFrenteRegua();
        return new double[][] {
            zregua.getAresta().getV1().getP().clone(),
            zregua.getAresta().getV2().getP().clone()
        };
    }
    
    @Override
    public double[][] planoBase() {
        return new double[][] {
            super.getTrazV3().getP().clone(), 
            super.getTrazV4().getP().clone(),
            super.getFrenteV3().getP().clone()
        };
    }
    
    public ReguaPlanoCartesianoObjeto3D[] reguas() {
        return new ReguaPlanoCartesianoObjeto3D[] { lY1, lY2, lY3, lY4, baseL12, baseL23, baseL34, baseL41 };

    }
    
    @Override
    public double[] calculaIntervalo( double min, double max, int numRotulos ) {
        return reguaUtil.calculaIntervalo( min, max, numRotulos );
    }
    
    @Override
    public void addComponenteObj3D( ComponenteObjeto3D plotObj3d ) {
        plotObj3DManager.addPlotObj3D( plotObj3d ); 
        plotObj3d.setContainerObjeto3D( this ); 
    }

    @Override
    public double calculaX(double x) {
        return plotObj3DManager.calculaX( x );
    }

    @Override
    public double calculaY(double y) {
        return plotObj3DManager.calculaY( y );
    }

    @Override
    public double calculaZ(double z) {
        return plotObj3DManager.calculaZ( z );
    }

    public List<Legenda> getLegendas() {
        return legendas;
    }
    
    public double getX() {
        return pcx;
    }

    public double getY() {
        return pcy;
    }
    
    public double getZ() {
        return pcz;
    }

    @Override
    public double getMaxLargura() {
        return altura2D;
    }

    @Override
    public double getMaxAltura() {
        return altura2D;
    }
    
    @Override
    public double getPCX() {
        return pcx;
    }

    @Override
    public void setPCX(double pcx) {
        this.pcx = pcx;
    }

    @Override
    public double getPCY() {
        return pcy;
    }

    @Override
    public void setPCY(double pcy) {
        this.pcy = pcy;
    }

    public double getPCZ() {
        return pcz;
    }

    public void setPCZ(double pcz) {
        this.pcz = pcz;
    }

    @Override
    public double getPCLargura() {
        return pcLargura;
    }

    @Override
    public void setPCLargura(double pcLargura) {
        this.pcLargura = pcLargura;
    }

    @Override
    public double getPCAltura() {
        return pcAltura;
    }

    @Override
    public void setPCAltura(double pcAltura) {
        this.pcAltura = pcAltura;
    }
    
    @Override
    public double getCentroX() {
        return centroX;
    }

    public void setCentroX(double centroX) {
        this.centroX = centroX;
    }

    @Override
    public double getCentroY() {
        return centroY;
    }

    public void setCentroY(double centroY) {
        this.centroY = centroY;
    }
        
    @Override
    public String getXZEixoRotulo() {
        return ( xEixoRotulo != null ? xEixoRotulo : zEixoRotulo );
    }

    public PlotObj3DManager getPlotObj3DManager() {
        return plotObj3DManager;
    }
      
    public ReguaUtil getReguaUtil() {
        return reguaUtil;
    }

    public ReguaPlanoCartesianoObjeto3D getlY1() {
        return lY1;
    }

    public ReguaPlanoCartesianoObjeto3D getlY2() {
        return lY2;
    }

    public ReguaPlanoCartesianoObjeto3D getlY3() {
        return lY3;
    }

    public ReguaPlanoCartesianoObjeto3D getlY4() {
        return lY4;
    }

    public Color getBaseCor() {
        return baseCor;
    }

    public void setBaseCor(Color baseCor) {
        this.baseCor = baseCor;
    }

    public Color getReguaCor() {
        return reguaCor;
    }

    public void setReguaCor(Color reguaCor) {
        this.reguaCor = reguaCor;
    }

    public Color getTituloCor() {
        return tituloCor;
    }

    public void setTituloCor(Color tituloCor) {
        this.tituloCor = tituloCor;
    }

    public boolean isPintarCorBase() {
        return pintarCorBase;
    }

    public void setPintarCorBase(boolean pintarCorBase) {
        this.pintarCorBase = pintarCorBase;
    }

    @Override
    public double getXRot() {
        return xrot;
    }

    public void setXRot(double xrot) {
        this.xrot = xrot;
    }

    @Override
    public double getYRot() {
        return yrot;
    }

    public void setYRot(double yrot) {
        this.yrot = yrot;
    }

    @Override
    public int getTituloGraficoDistanciaPX() {
        return tituloGraficoDistanciaPX;
    }

    public void setTituloGraficoDistanciaPX(int tituloGraficoDistanciaPX) {
        this.tituloGraficoDistanciaPX = tituloGraficoDistanciaPX;
    }

    @Override
    public int getEixoRotulosDistanciaPX() {
        return eixoRotulosDistanciaPX;
    }

    public void setEixoRotulosDistanciaPX(int eixoRotulosDistanciaPX) {
        this.eixoRotulosDistanciaPX = eixoRotulosDistanciaPX;
    }

    public String getXEixoRotulo() {
        return xEixoRotulo;
    }

    public void setXEixoRotulo(String xEixoRotulo) {
        this.xEixoRotulo = xEixoRotulo;
    }

    @Override
    public String getYEixoRotulo() {
        return yEixoRotulo;
    }

    public void setYEixoRotulo(String yEixoRotulo) {
        this.yEixoRotulo = yEixoRotulo;
    }

    public String getZEixoRotulo() {
        return zEixoRotulo;
    }

    public void setZEixoRotulo(String zEixoRotulo) {
        this.zEixoRotulo = zEixoRotulo;
    }

    @Override
    public int getBordaPX() {
        return bordaPX;
    }

    public void setBordaPX(int bordaPX) {
        this.bordaPX = bordaPX;
    }

    @Override
    public double getReguaYValorLarguraMax() {
        return reguaYValorLarguraMax;
    }

    @Override
    public void setReguaYValorLarguraMax(double reguaYValorLarguraMax) {
        this.reguaYValorLarguraMax = reguaYValorLarguraMax;
    }

    @Override
    public double getReguaYValorAlturaMax() {
        return reguaYValorAlturaMax;
    }

    @Override
    public void setReguaYValorAlturaMax(double reguaYValorAlturaMax) {
        this.reguaYValorAlturaMax = reguaYValorAlturaMax;
    }

    @Override
    public Font getEixoRotuloFont() {
        return eixoRotuloFont;
    }

    public void setEixoRotuloFont(Font eixoRotuloFont) {
        this.eixoRotuloFont = eixoRotuloFont;
    }

    @Override
    public Font getReguaValoresFont() {
        return reguaValoresFont;
    }

    public void setReguaValoresFont(Font reguaValoresFont) {
        this.reguaValoresFont = reguaValoresFont;
    }

    public Font getLegendaFont() {
        return legendaFont;
    }

    public void setLegendaFont(Font legendaFont) {
        this.legendaFont = legendaFont;
    }

    @Override
    public int getReguaTracoComprimentoPX() {
        return reguaTracoComprimentoPX;
    }

    public void setReguaTracoComprimentoPX(int reguaTracoComprimentoPX) {
        this.reguaTracoComprimentoPX = reguaTracoComprimentoPX;
    }

    @Override
    public int getReguaValorDistanciaPX() {
        return reguaValorDistanciaPX;
    }

    public void setReguaValorDistanciaPX(int reguaValorDistanciaPX) {
        this.reguaValorDistanciaPX = reguaValorDistanciaPX;
    }

    public Color getGradeCor() {
        return gradeCor;
    }

    public void setGradeCor(Color gradeCor) {
        this.gradeCor = gradeCor;
    }

    public Color getEixoRotulosCor() {
        return eixoRotulosCor;
    }

    public void setEixoRotulosCor(Color eixoRotulosCor) {
        this.eixoRotulosCor = eixoRotulosCor;
    }

    public Color getLegendaCor() {
        return legendaCor;
    }

    public void setLegendaCor(Color legendaCor) {
        this.legendaCor = legendaCor;
    }

    public boolean isPontilharGrade() {
        return pontilharGrade;
    }

    public void setPontilharGrade(boolean pontilharGrade) {
        this.pontilharGrade = pontilharGrade;
    }

    public int getGradePontosEspsPX() {
        return gradePontosEspsPX;
    }

    public void setGradePontosEspsPX(int gradePontosEspsPX) {
        this.gradePontosEspsPX = gradePontosEspsPX;
    }
    
    @Override
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    @Override
    public Font getTituloFont() {
        return tituloFont;
    }

    public void setTituloFont(Font tituloFont) {
        this.tituloFont = tituloFont;
    }

    public boolean isPintarGrade() {
        return pintarGrade;
    }

    public void setPintarGrade(boolean pintarGrade) {
        this.pintarGrade = pintarGrade;
    }

    @Override
    public boolean isPintarEixoRotulos() {
        return pintarEixoRotulos;
    }

    public void setPintarEixoRotulos(boolean pintarEixoRotulos) {
        this.pintarEixoRotulos = pintarEixoRotulos;
    }

    @Override
    public boolean isPintarRegua() {
        return pintarRegua;
    }

    public void setPintarRegua(boolean pintarRegua) {
        this.pintarRegua = pintarRegua;
    }
    
    public double getTituloX() {
        return tituloX;
    }

    public double getTituloY() {
        return tituloY;
    }

    @Override
    public PCPlotObjManagerTelaAjuste getPCPlotObjManager() {
        return plotObj3DManager;
    }

    public boolean isConfigurarPlotObjsManager() {
        return configurarPlotObjsManager;
    }

    public void setConfigurarPlotObjsManager(boolean configurarPlotObjsManager) {
        this.configurarPlotObjsManager = configurarPlotObjsManager;
    }

    public boolean isAjustarATela() {
        return ajustarATela;
    }

    public void setAjustarATela(boolean ajustarATela) {
        this.ajustarATela = ajustarATela;
    }

    public double getAltura2D() {
        return altura2D;
    }

    public void setAltura2D(double altura2D) {
        this.altura2D = altura2D;
    }

    public int getLegendaTracoComprimentoPX() {
        return legendaTracoComprimentoPX;
    }

    public void setLegendaTracoComprimentoPX(int legendaTracoComprimentoPX) {
        this.legendaTracoComprimentoPX = legendaTracoComprimentoPX;
    }

    public int getLegendaPontoRaioPX() {
        return legendaPontoRaioPX;
    }

    public void setLegendaPontoRaioPX(int legendaPontoRaioPX) {
        this.legendaPontoRaioPX = legendaPontoRaioPX;
    }

    public int getLegendaBordaLateralPX() {
        return legendaBordaLateralPX;
    }

    public void setLegendaBordaLateralPX(int legendaBordaLateralPX) {
        this.legendaBordaLateralPX = legendaBordaLateralPX;
    }

    public int getLegendaBordaVerticalPX() {
        return legendaBordaVerticalPX;
    }

    public void setLegendaBordaVerticalPX(int legendaBordaVerticalPX) {
        this.legendaBordaVerticalPX = legendaBordaVerticalPX;
    }

    public int getLegendaEspPX() {
        return legendaEspPX;
    }

    public void setLegendaEspPX(int legendaEspPX) {
        this.legendaEspPX = legendaEspPX;
    }

    public int getGrupoLegendaBordaPX() {
        return grupoLegendaBordaPX;
    }

    public void setGrupoLegendaBordaPX(int grupoLegendaBordaPX) {
        this.grupoLegendaBordaPX = grupoLegendaBordaPX;
    }

    public int getGrupoLegendaYEspPX() {
        return grupoLegendaYEspPX;
    }

    public void setGrupoLegendaYEspPX(int grupoLegendaYEspPX) {
        this.grupoLegendaYEspPX = grupoLegendaYEspPX;
    }
            
}
