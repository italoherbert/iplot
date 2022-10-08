package italo.iplot.plot2d.planocartesiano.g2d;

import italo.iplot.planocartesiano.Legenda;
import italo.iplot.planocartesiano.telaajuste.PCTelaAjustador;
import italo.iplot.plot2d.g2d.Aresta2D;
import italo.iplot.plot2d.g2d.ComponenteObjeto2D;
import italo.iplot.plot2d.g2d.Face2D;
import italo.iplot.plot2d.g2d.Objeto2D;
import italo.iplot.plot2d.g2d.Objeto2DTO;
import italo.iplot.plot2d.g2d.Vertice2D;
import italo.iplot.plot2d.planocartesiano.g2d.telaajuste.PCTelaAjuste2DTO;
import italo.iplot.plot2d.planocartesiano.objgrafico.PlanoCartesianoObj2DGrafico;
import italo.iplot.planocartesiano.regua.ReguaUtil;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import italo.iplot.planocartesiano.telaajuste.PCPlotObjManagerTelaAjuste;
import italo.iplot.planocartesiano.telaajuste.PCContainerTelaAjuste;

public class PlanoCartesianoObjeto2D extends Objeto2D implements PlotObj2DContainer, PCContainerObjeto2D, PCContainerTelaAjuste {
    
    private final double PRECISAO = 0.000000000000001d;
    
    private final int GRADE_PONTOS_ESPS_PX = 3;
    private final int REGUA_TRACO_COMPRIMENTO_PX = 5;
    private final int REGUA_VALOR_DISTANCIA_PX = 5;
    private final int TITULO_GRAFICO_DIST_PX = 10;
    private final int EIXO_ROTULO_DIST_PX = 2;
    private final int BORDA = 10;    

    private final int LEGENDA_TRACO_COMPRIMENTO_PX = 20;
    private final int LEGENDA_PONTO_RAIO_PX = 3;
    private final int LEGENDA_BORDA_VERTICAL_PX = 0;
    private final int LEGENDA_BORDA_LATERAL_PX = 3;
    private final int LEGENDA_ESP_PX = 5;
    private final int GRUPO_LEGENDA_BORDA_PX = 10;
    private final int GRUPO_LEGENDA_YESP_PX = 4;
    
    private final int ARESTA_PONTOS_ESP_PX = 3;    
    
    private final boolean EH3D = false;
    
    private final PlotObj2DManager plotObj2DManager = new PlotObj2DManager( this );        
    private final ReguaUtil reguaUtil = new ReguaUtil();
           
    private Color gradeCor = Color.BLACK;
    private Color reguaCor = Color.BLACK;
    private Color tituloCor = Color.BLACK;
    private Color rotulosCor = Color.BLACK;
    private Color eixoRotulosCor = Color.BLACK; 
    private Color legendaCor = Color.BLACK;
    private Color mouseLinhasCor = Color.BLACK;
                
    private int gradePontosEspsPX = GRADE_PONTOS_ESPS_PX;

    private int reguaTracoComprimentoPX = REGUA_TRACO_COMPRIMENTO_PX;
    private int reguaValorDistanciaPX = REGUA_VALOR_DISTANCIA_PX;
    private int tituloGraficoDistanciaPX = TITULO_GRAFICO_DIST_PX;
    private int eixoRotulosDistanciaPX = EIXO_ROTULO_DIST_PX; 
    private int bordaPX = BORDA;

    private boolean pintarGrade = false;
    private boolean pontilharGrade = true;
    private boolean pintarRegua = true;
    private boolean pintarEixoRotulos = true;
    private boolean pintarRetBorda = false;
    private boolean pintarMouseLinhas = false;
                
    private Font tituloFont = new Font( Font.MONOSPACED, Font.BOLD, 24 );
    private Font eixoRotuloFont = new Font( Font.MONOSPACED, Font.BOLD, 12 );
    private Font reguaValoresFont = new Font( Font.MONOSPACED, Font.BOLD, 8 );
    private Font legendaFont = new Font( Font.MONOSPACED, Font.BOLD, 10 );

        
    private int legendaTracoComprimentoPX = LEGENDA_TRACO_COMPRIMENTO_PX;
    private int legendaPontoRaioPX = LEGENDA_PONTO_RAIO_PX;
    private int legendaBordaLateralPX = LEGENDA_BORDA_LATERAL_PX;
    private int legendaBordaVerticalPX = LEGENDA_BORDA_VERTICAL_PX;
    private int legendaEspPX = LEGENDA_ESP_PX;
    private int grupoLegendaBordaPX = GRUPO_LEGENDA_BORDA_PX;
    private int grupoLegendaYEspPX = GRUPO_LEGENDA_YESP_PX;
    
    private String titulo = "Plano";        
    private String xEixoRotulo = "Eixo X";
    private String yEixoRotulo = "Eixo Y";
        
    private double centroX = 0;
    private double centroY = 0;
    private double maxLargura = 2.0d;
    private double maxAltura = 2.0d;
    
    private double pcx = 0;
    private double pcy = 0.1d;    
    private double dx = 1.79d;
    private double dy = 1.5d; 
    
    private double reguaYValorLarguraMax = 0;
    private double reguaYValorAlturaMax = 0;
    private int telaDX;   
    private boolean cortar = true;
    
    private double tituloX = 0;
    private double tituloY = 0;
    private double xEixoRotuloX = 0;
    private double xEixoRotuloY = 0;
    private double yEixoRotuloX = 0;
    private double yEixoRotuloY = 0;
                
    private final List<Traco2D> tracos = new ArrayList();
    private final List<Legenda> legendas = new ArrayList();
    private final List<PCFuncObjeto2D> funcObjs = new ArrayList();
    
    private final PCTelaAjustador ajustador = new PCTelaAjustador();
    
    private boolean configurarPlotObjsManager = true;
    
    public PlanoCartesianoObjeto2D() {
        super.pintarFaces = true;
        super.pintarArestas = true;
        super.pintarVertices = false;
        super.cor = new Color( 255, 255, 255 );
        super.arestasCor = Color.BLACK;                
                                        
        super.grafico = new PlanoCartesianoObj2DGrafico();
    }
    
    @Override
    public void constroiObjeto2D( Objeto2DTO to ) {                                        
        super.removeTodosOsObjetos();
        
        tracos.clear();                
        legendas.clear();        

        telaDX = (int)to.getMath2D().telaUnidade( dx, to.getTela() );
                                      
        if ( configurarPlotObjsManager )
            plotObj2DManager.configura( to );                 
        
        PCTelaAjuste2DTO telaAjusteTO = new PCTelaAjuste2DTO( to );
        ajustador.ajusta( this, telaAjusteTO, EH3D );
                
        if ( titulo != null ) {            
            double[] tituloXY = ajustador.calculaPosTitulo( titulo, this, telaAjusteTO, EH3D );
            tituloX = tituloXY[0];
            tituloY = tituloXY[1]; 
        }
        
        if ( pintarEixoRotulos ) {
            if ( yEixoRotulo != null ) {
                double[] yEixoRotuloXY = ajustador.calculaPosEixoY2D( yEixoRotulo, this, telaAjusteTO );
                xEixoRotuloY = yEixoRotuloXY[0];
                yEixoRotuloY = yEixoRotuloXY[1];
            } 
            if ( xEixoRotulo != null ) {
                double[] xEixoRotuloXY = ajustador.calculaPosEixoX2D( xEixoRotulo, this, telaAjusteTO );
                xEixoRotuloX = xEixoRotuloXY[0];
                yEixoRotuloX = xEixoRotuloXY[1];                
            }
        }
                
        if ( pintarRetBorda ) {
            Vertice2D v11 = new Vertice2D( -maxLargura*.5d + centroX, -maxAltura*.5d + centroY );
            Vertice2D v21 = new Vertice2D(  maxLargura*.5d + centroX, -maxAltura*.5d + centroY );
            Vertice2D v31 = new Vertice2D(  maxLargura*.5d + centroX,  maxAltura*.5d + centroY );
            Vertice2D v41 = new Vertice2D( -maxLargura*.5d + centroX,  maxAltura*.5d + centroY );                
            Aresta2D a11 = new Aresta2D( v11, v21 );
            Aresta2D a21 = new Aresta2D( v21, v31 );
            Aresta2D a31= new Aresta2D( v31, v41 );
            Aresta2D a41 = new Aresta2D( v41, v11 );

            super.getEstrutura().addVertice( v11 );
            super.getEstrutura().addVertice( v21 );
            super.getEstrutura().addVertice( v31 );
            super.getEstrutura().addVertice( v41 ); 

            super.getEstrutura().addAresta( a11 );
            super.getEstrutura().addAresta( a21 );
            super.getEstrutura().addAresta( a31 );
            super.getEstrutura().addAresta( a41 );
        }
        
        Vertice2D v1 = new Vertice2D( -dx*.5d + pcx, -dy*.5d + pcy );
        Vertice2D v2 = new Vertice2D(  dx*.5d + pcx, -dy*.5d + pcy );
        Vertice2D v3 = new Vertice2D(  dx*.5d + pcx,  dy*.5d + pcy );
        Vertice2D v4 = new Vertice2D( -dx*.5d + pcx,  dy*.5d + pcy );
        
        Aresta2D a1 = new Aresta2D( v1, v2 );
        Aresta2D a2 = new Aresta2D( v2, v3 );
        Aresta2D a3 = new Aresta2D( v3, v4 );
        Aresta2D a4 = new Aresta2D( v4, v1 );
        a1.setCor( reguaCor );
        a2.setCor( reguaCor );
        a3.setCor( reguaCor );
        a4.setCor( reguaCor );         
        
        Face2D face = new Face2D();
        face.addVertice( v1 );
        face.addVertice( v2 );
        face.addVertice( v3 );
        face.addVertice( v4 );        
        
        super.getEstrutura().addVertice( v1 );
        super.getEstrutura().addVertice( v2 );
        super.getEstrutura().addVertice( v3 );
        super.getEstrutura().addVertice( v4 ); 
        
        super.getEstrutura().addAresta( a1 );
        super.getEstrutura().addAresta( a2 );
        super.getEstrutura().addAresta( a3 );
        super.getEstrutura().addAresta( a4 );
        
        super.getEstrutura().addFace( face ); 
                
        double[] p1 = v1.getP();
        double[] p2 = v2.getP();
        double[] p4 = v4.getP();
                                                      
        double reguaRotuloDist = to.getMath2D().verticeUnidade(reguaValorDistanciaPX, to.getTela() );
        double reguaTracoComprimento = to.getMath2D().verticeUnidade( reguaTracoComprimentoPX, to.getTela() );
        
        double iMinX = plotObj2DManager.getIMinX();
        double iMaxX = plotObj2DManager.getIMaxX();
        double xinc = plotObj2DManager.getIIncX();
        double xdesloc = plotObj2DManager.getIDeslocX();
        int xNumRotulos = plotObj2DManager.getXNumRotulos();
        double x1 = -dx*.5d - PRECISAO;        
        double y1 = -dy*.5d - PRECISAO;
        double x2 =  dx*.5d + PRECISAO;
        double y2 =  dy*.5d + PRECISAO;
            
        double xBorda = reguaUtil.calculaBorda( xNumRotulos, xinc, iMinX, iMaxX );                
        xBorda += 2*xdesloc;
        
        if ( pintarRegua ) {
            boolean parar = false;
            for( int i = 0; !parar; i++ ) {
                double pch = reguaUtil.calculaPlanoCartesianoH( xinc, xBorda, i );
                double h = reguaUtil.calculaH( dx, iMinX, iMaxX, pch );                        

                if ( h >= x1 && h <= x2 ) {
                    double px = pcx + h;
                    double cimaY = p4[1];
                    double baixoY = p1[1];
                    Vertice2D tracoCimaV1 = new Vertice2D( px, cimaY );
                    Vertice2D tracoCimaV2 = new Vertice2D( px, cimaY - reguaTracoComprimento );

                    Vertice2D tracoBaixoV1 = new Vertice2D( px, baixoY );
                    Vertice2D tracoBaixoV2 = new Vertice2D( px, baixoY + reguaTracoComprimento );

                    double valor = iMinX + ( i * xinc ) + xdesloc;

                    tracos.add( new Traco2D( this, px, cimaY +  reguaRotuloDist, valor, Traco2D.CIMA ) );
                    tracos.add( new Traco2D( this, px, baixoY - reguaRotuloDist, valor, Traco2D.BAIXO ) );

                    if ( pintarGrade ) {                
                        Aresta2D pa = new Aresta2D( tracoBaixoV1, tracoCimaV1 );
                        pa.setArestaPontilhada( true );
                        pa.setEspacoArestaPontosPX( ARESTA_PONTOS_ESP_PX ); 
                        pa.setCor( gradeCor );                
                        super.getEstrutura().addAresta( pa ); 
                    }

                    super.getEstrutura().addVertice( tracoCimaV1 );
                    super.getEstrutura().addVertice( tracoCimaV2 );
                    super.getEstrutura().addVertice( tracoBaixoV1 );
                    super.getEstrutura().addVertice( tracoBaixoV2 );

                    Aresta2D arestaBaixo = new Aresta2D( tracoCimaV1, tracoCimaV2 );
                    arestaBaixo.setCor( reguaCor );

                    Aresta2D arestaCima = new Aresta2D( tracoBaixoV1, tracoBaixoV2 );
                    arestaCima.setCor( reguaCor );

                    super.getEstrutura().addAresta( arestaBaixo );
                    super.getEstrutura().addAresta( arestaCima );                              
                } else {
                    if ( h >= x2 )
                        parar = true;
                }
            }

            double iMinY = plotObj2DManager.getIMinY();
            double iMaxY = plotObj2DManager.getIMaxY();
            double yinc = plotObj2DManager.getIIncY();
            double ydesloc = plotObj2DManager.getIDeslocY();
            int yNumRotulos = plotObj2DManager.getYNumRotulos();

            double yBorda = reguaUtil.calculaBorda( yNumRotulos, yinc, iMinY, iMaxY );
            yBorda += 2*ydesloc;

            parar = false;
            for( int i = 0; !parar; i++ ) {
                double pch = reguaUtil.calculaPlanoCartesianoH( yinc, yBorda, i );
                double h = reguaUtil.calculaH( dy, iMinY, iMaxY, pch );

                if ( h <= y2 && h >= y1 ) {            
                    double py = pcy + h;
                    double trazX = p1[0];
                    double frenteX = p2[0];
                    Vertice2D tracoTrazV1 = new Vertice2D( trazX, py );
                    Vertice2D tracoTrazV2 = new Vertice2D( trazX + reguaTracoComprimento, py );

                    Vertice2D tracoFrenteV1 = new Vertice2D( frenteX, py );
                    Vertice2D tracoFrenteV2 = new Vertice2D( frenteX - reguaTracoComprimento, py );

                    double valor = iMinY + ( i * yinc ) + ydesloc;

                    tracos.add( new Traco2D( this, trazX - reguaRotuloDist, py, valor, Traco2D.TRAZ ) );
                    tracos.add( new Traco2D( this, frenteX + reguaRotuloDist, py, valor, Traco2D.FRENTE ) );

                    if ( pintarGrade ) {
                        Aresta2D pa = new Aresta2D( tracoTrazV1, tracoFrenteV1 );
                        pa.setArestaPontilhada( true );
                        pa.setEspacoArestaPontosPX( ARESTA_PONTOS_ESP_PX ); 
                        pa.setCor( gradeCor );                
                        super.getEstrutura().addAresta( pa ); 
                    }

                    super.getEstrutura().addVertice( tracoTrazV1 );
                    super.getEstrutura().addVertice( tracoTrazV2 );
                    super.getEstrutura().addVertice( tracoFrenteV1 );
                    super.getEstrutura().addVertice( tracoFrenteV2 );

                    Aresta2D arestaTraz = new Aresta2D( tracoTrazV1, tracoTrazV2 );
                    arestaTraz.setCor( reguaCor );

                    Aresta2D arestaFrente = new Aresta2D( tracoFrenteV1, tracoFrenteV2 );
                    arestaFrente.setCor( reguaCor );

                    super.getEstrutura().addAresta( arestaTraz );
                    super.getEstrutura().addAresta( arestaFrente );                
                } else {
                    if ( h >= y2 )
                        parar = true;
                }
            }
        
        }
                          
        for( ComponenteObjeto2D plotObj2D : plotObj2DManager.getPlotObjs() )
            super.addObjeto((Objeto2D)plotObj2D );                                
    }
    
    public void mover( double deslocXH, double deslocYH, Objeto2DTO to ) {                
        double telaH = to.getMath2D().verticeUnidade( to.getTela().getTelaLargura(), to.getTela() );
        double telaW = to.getMath2D().verticeUnidade( to.getTela().getTelaAltura(), to.getTela() );
        double desX = deslocXH * ( dx / telaW );
        double desY = deslocYH * ( dy / telaH );
        plotObj2DManager.mover( desX, desY, to );
        configurarPlotObjsManager = false;
        super.constroi( to );
        configurarPlotObjsManager = true;        
    }
    
    public void zoom( double escala, Objeto2DTO to ) {
        plotObj2DManager.escalar( 1.0d / escala, to );
        configurarPlotObjsManager = false;
        super.constroi( to );
        configurarPlotObjsManager = true; 
    }
    
    public void gradeVisivel( boolean visivel, Objeto2DTO to ) {
        pintarGrade = visivel;
        
        configurarPlotObjsManager = false;
        super.constroi( to );
        configurarPlotObjsManager = true;        
    }
    
    public void reguaVisivel( boolean visivel, Objeto2DTO to ) {
        pintarRegua = visivel;
                
        configurarPlotObjsManager = false;
        super.constroi( to );
        configurarPlotObjsManager = true; 
    }
            
    public void addComponenteObj2D( ComponenteObjeto2D plotObj2d ) {
        plotObj2DManager.addPlotObj2D( plotObj2d );
        plotObj2d.setContainerObjeto2D( this ); 
        
        if ( plotObj2d instanceof PCFuncObjeto2D )
            funcObjs.add( (PCFuncObjeto2D)plotObj2d );
    }
    
    public double[] calculaMouseIXY( Objeto2DTO to ) {                  
        double[] p = to.getMath2D().doublePontoPX( pcx, pcy, to.getTela() );
        
        double h = to.getTela().getTelaAltura();
        
        double mouseIX = to.getMath2D().verticeUnidade( to.getMouseX() - p[0], to.getTela() );
        double mouseIY = to.getMath2D().verticeUnidade( h - p[1] - to.getMouseY(), to.getTela() );         
                                    
        mouseIX *= Math.abs( plotObj2DManager.getIX2() - plotObj2DManager.getIX1() ) / dx;
        mouseIY *= Math.abs( plotObj2DManager.getIY2() - plotObj2DManager.getIY1() ) / dy;        
        
        mouseIX += ( plotObj2DManager.getIX2() - plotObj2DManager.getIX1() )*.5d;
        mouseIY += ( plotObj2DManager.getIY2() - plotObj2DManager.getIY1() )*.5d;        
        
        mouseIX += plotObj2DManager.getIX1();        
        mouseIY += plotObj2DManager.getIY1();
        
        return new double[] { mouseIX , mouseIY };
    }

    @Override
    public void addLegenda( Legenda legenda ) {
        legendas.add( legenda );        
    }

    @Override
    public double getIXFator() {        
        return plotObj2DManager.getIXF();        
    }

    @Override
    public double getIX1() {
        return plotObj2DManager.getIX1();
    }

    @Override
    public double getIX2() {
        return plotObj2DManager.getIX2();
    }

    @Override
    public int getTelaDX() {
        return telaDX;
    }
    
    @Override
    public double[] calculaIntervalo( double min, double max, int numRotulos ) {
        return reguaUtil.calculaIntervalo( min, max, numRotulos );
    }

    @Override
    public double calculaX( double x ) {
        return this.getX() + plotObj2DManager.calculaX( x );
    }

    @Override
    public double calculaY(double y) {
        return this.getY() + plotObj2DManager.calculaY( y );
    }
    
    public PlotObj2DManager getPlotObj2DManager() {
        return plotObj2DManager;
    }

    public List<PCFuncObjeto2D> getFuncObjs() {
        return funcObjs;
    }

    public List<Traco2D> getTracos() {
        return tracos;
    }

    public List<Legenda> getLegendas() {
        return legendas;
    }

    @Override
    public double getX() {
        return pcx;
    }

    @Override
    public double getY() {
        return pcy;
    }
    
    @Override
    public double getDX() {
        return dx;
    }

    @Override
    public double getDY() {
        return dy;
    }

    @Override
    public double getPCX() {
        return pcx;
    }

    @Override
    public double getPCY() {
        return pcy;
    }

    @Override
    public double getPCLargura() {
        return dx;
    }

    @Override
    public double getPCAltura() {
        return dy;
    }

    @Override
    public void setPCX(double x) {
        this.pcx = x;
    }

    @Override
    public void setPCY(double y) {
        this.pcy = y;
    }

    @Override
    public void setPCLargura(double largura) {
        this.dx = largura;
    }

    @Override
    public void setPCAltura(double altura) {
        this.dy = altura;
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
    public double getMaxLargura() {
        return maxLargura;
    }

    public void setMaxLargura(double maxLargura) {
        this.maxLargura = maxLargura;
    }

    @Override
    public double getMaxAltura() {
        return maxAltura;
    }

    public void setMaxAltura(double maxAltura) {
        this.maxAltura = maxAltura;
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


    public Color getRotulosCor() {
        return rotulosCor;
    }

    public void setRotulosCor(Color rotulosCor) {
        this.rotulosCor = rotulosCor;
    }

    public Color getReguaCor() {
        return reguaCor;
    }

    public void setReguaCor(Color reguaCor) {
        this.reguaCor = reguaCor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
        
    @Override
    public String getXZEixoRotulo() {
        return xEixoRotulo;
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

    @Override
    public Font getTituloFont() {
        return tituloFont;
    }

    public void setTituloFont(Font tituloFont) {
        this.tituloFont = tituloFont;
    }

    @Override
    public Font getReguaValoresFont() {
        return reguaValoresFont;
    }

    public void setReguaValoresFont(Font reguaValoresFont) {
        this.reguaValoresFont = reguaValoresFont;
    }
   
    public Color getTituloCor() {
        return tituloCor;
    }

    public void setTituloCor(Color tituloCor) {
        this.tituloCor = tituloCor;
    }

    public Color getLegendaCor() {
        return legendaCor;
    }

    public void setLegendaCor(Color legendaCor) {
        this.legendaCor = legendaCor;
    }
    
    @Override
    public Font getEixoRotuloFont() {
        return eixoRotuloFont;
    }

    public void setEixoRotuloFont(Font eixoRotuloFont) {
        this.eixoRotuloFont = eixoRotuloFont;
    }

    public Font getLegendaFont() {
        return legendaFont;
    }

    public void setLegendaFont(Font legendaFont) {
        this.legendaFont = legendaFont;
    }

    public Color getEixoRotulosCor() {
        return eixoRotulosCor;
    }

    public void setEixoRotulosCor(Color eixoRotulosCor) {
        this.eixoRotulosCor = eixoRotulosCor;
    }

    @Override
    public boolean isPintarEixoRotulos() {
        return pintarEixoRotulos;
    }

    public void setPintarEixoRotulos(boolean pintarEixoRotulos) {
        this.pintarEixoRotulos = pintarEixoRotulos;
    }

    public double getTituloX() {
        return tituloX;
    }

    public double getTituloY() {
        return tituloY;
    }
    public double getXEixoRotuloX() {
        return xEixoRotuloX;
    }

    public double getXEixoRotuloY() {
        return xEixoRotuloY;
    }

    public double getYEixoRotuloX() {
        return yEixoRotuloX;
    }

    public double getYEixoRotuloY() {
        return yEixoRotuloY;
    }

    @Override
    public boolean isPintarRegua() {
        return pintarRegua;
    }

    public void setPintarRegua(boolean pintarRegua) {
        this.pintarRegua = pintarRegua;
    }

    public boolean isPintarRetBorda() {
        return pintarRetBorda;
    }

    public void setPintarRetBorda(boolean pintarRetBorda) {
        this.pintarRetBorda = pintarRetBorda;
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

    public void setReguaValorDistanciaPX(int reguaRotuloDistPX) {
        this.reguaValorDistanciaPX = reguaRotuloDistPX;
    }

    @Override
    public int getTituloGraficoDistanciaPX() {
        return tituloGraficoDistanciaPX;
    }

    public void setTituloGraficoDistanciaPX(int tituloGraficoDistPX) {
        this.tituloGraficoDistanciaPX = tituloGraficoDistPX;
    }

    @Override
    public int getBordaPX() {
        return bordaPX;
    }

    public void setBordaPX(int bordaPX) {
        this.bordaPX = bordaPX;
    }

    public Color getMouseLinhasCor() {
        return mouseLinhasCor;
    }

    public void setMouseLinhasCor(Color mouseLinhasCor) {
        this.mouseLinhasCor = mouseLinhasCor;
    }

    public boolean isPintarMouseLinhas() {
        return pintarMouseLinhas;
    }

    public void setPintarMouseLinhas(boolean mostrarMouseLinhas) {
        this.pintarMouseLinhas = mostrarMouseLinhas;
    }

    @Override
    public int getEixoRotulosDistanciaPX() {
        return eixoRotulosDistanciaPX;
    }

    public void setEixoRotulosDistanciaPX(int eixoRotulosDistPX) {
        this.eixoRotulosDistanciaPX = eixoRotulosDistPX;
    }

    public int getGradePontosEspsPX() {
        return gradePontosEspsPX;
    }

    public void setGradePontosEspsPX(int gradePontosEspsPX) {
        this.gradePontosEspsPX = gradePontosEspsPX;
    }

    public Color getGradeCor() {
        return gradeCor;
    }

    public void setGradeCor(Color gradeCor) {
        this.gradeCor = gradeCor;
    }

    public boolean isPintarGrade() {
        return pintarGrade;
    }

    public void setPintarGrade(boolean pintarGrade) {
        this.pintarGrade = pintarGrade;
    }

    public boolean isPontilharGrade() {
        return pontilharGrade;
    }

    public void setPontilharGrade(boolean pontilharGrade) {
        this.pontilharGrade = pontilharGrade;
    }
    
    @Override
    public boolean isCortar() {
        return cortar;
    }

    public void setCortar(boolean cortar) {
        this.cortar = cortar;
    }

    public boolean isConfigurarPlotObjsManager() {
        return configurarPlotObjsManager;
    }

    public void setConfigurarPlotObjsManager(boolean configurarPlotObjsManager) {
        this.configurarPlotObjsManager = configurarPlotObjsManager;
    }

    @Override
    public PCPlotObjManagerTelaAjuste getPCPlotObjManager() {
        return plotObj2DManager;
    }
    
}
