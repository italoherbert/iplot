package italo.iplot.plot2d;

import italo.iplot.gui.DesenhoGUI;
import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.Pintura;
import italo.iplot.gui.Tela;
import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot2d.desenho.Desenho2D;
import italo.iplot.plot2d.g2d.UniversoVirtual2D;
import italo.iplot.plot2d.g2d.VerticeObjeto2DFactory;
import italo.iplot.plot2d.g2d.util.Math2D;
import italo.iplot.plot2d.g2d.util.Transformador2D;
import italo.iplot.plot2d.g2d.util.corte.Cortador2D;
import italo.iplot.plot2d.g2d.vert.InicialFiltroVert2D;
import italo.iplot.plot2d.g2d.vert.VisaoFiltroVert2D;
import italo.iplot.plot2d.g2d.vert.XYFiltroVert2D;
import italo.iplot.plot2d.grafico.Java2DGrafico2D;
import italo.iplot.plot2d.grafico.Java2DGrafico2D;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.JComponent;

public abstract class Plot2D implements Plot2DAplic {
    
    private Object objeto;
    
    protected DesenhoGUI desenhoGUI;
    
    protected Math2D math2D = null;    
    protected Transformador2D transformador2D = null;
    protected Cortador2D corte2D = null;
    protected VerticeObjeto2DFactory vobj2DFactory = null;
        
    protected InicialFiltroVert2D inicialFiltroV2D = null;
    protected XYFiltroVert2D xyFiltroV2D = null;
    protected VisaoFiltroVert2D visaoFiltroV2D = null;
    protected Desenho2D desenho2D = null;
    protected Grafico grafico;
    
    protected UniversoVirtual2D universoVirtual = null;
            
    protected Tela tela;
    
    protected DecimalFormat rotuloDecimalFormat = new DecimalFormat( "0.######" );    
        
    public Plot2D() {
        desenhoGUI = new DesenhoGUI();
        desenho2D = new Desenho2D( this );
                
        visaoFiltroV2D = new VisaoFiltroVert2D();
        xyFiltroV2D = new XYFiltroVert2D();
        inicialFiltroV2D = new InicialFiltroVert2D();
            
        transformador2D = new Transformador2D();
        corte2D = new Cortador2D();
        math2D = new Math2D();    
        vobj2DFactory = new VerticeObjeto2DFactory();        
    }
                            
    public void pintaGraphics( Graphics g ) {
        this.getPintura().pinta( g );         
    }
                  
    public void inicializaPintura( Tela tela ) {
        this.tela = tela;
        this.getPintura().inicializa( tela, grafico ); 
        this.getPintura().setDesenho( desenho2D );        
    }
    
    public Java2DGrafico2D novoJava2DGrafico() {
        return new Java2DGrafico2D();
    }
    
    public Java2DGrafico2D novoAlocaImagemGrafico() {
        return new Java2DGrafico2D();
    }

    public void setGrafico( Grafico grafico ) {
        this.grafico = grafico;
    }

    @Override
    public int getMouseX() {
        return this.getDesenhoUI().getMouseX();
    }

    @Override
    public int getMouseY() {
        return this.getDesenhoUI().getMouseY();
    }
    
    @Override
    public Rectangle2D getStringLimites(String texto, Font fonte) {
        return this.getPintura().getGrafico().stringLimites( texto, fonte );
    }           
    
    @Override
    public UniversoVirtual2D getUniversoVirtual() {        
        return universoVirtual;
    }
                      
    @Override
    public Tela getTela() {
        return this.getPintura().getTela();
    }
    
    public JComponent getDesenhoComponent() {
        return (JComponent)this.getDesenhoUI();
    }
        
    @Override
    public DesenhoUI getDesenhoUI() {
        return desenhoGUI;
    }
    
    @Override
    public Desenho2D getDesenho2D() {
        return desenho2D;
    }
    
    @Override
    public Transformador2D getTransformador2D() {
        return transformador2D;
    }

    @Override
    public Cortador2D getCortador2D() {
        return corte2D;
    }

    @Override
    public VerticeObjeto2DFactory getVObj2DFactory() {
        return vobj2DFactory;
    }

    @Override
    public VisaoFiltroVert2D getVisaoFiltroV2D() {
        return visaoFiltroV2D;
    }
    
    @Override
    public XYFiltroVert2D getXYFiltroV2D() {
        return xyFiltroV2D;
    }
    
    @Override
    public InicialFiltroVert2D getInicialFiltroV2D() {
        return inicialFiltroV2D;
    }

    @Override
    public DecimalFormat getRotuloDecimalFormat() {
        return rotuloDecimalFormat;
    }
    
    @Override
    public Math2D getMath2D() {
        return math2D;
    }    
     
    @Override
    public Pintura getPintura() {
        return desenhoGUI.getPintura();
    }
    
    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

}

