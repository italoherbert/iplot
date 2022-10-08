package italo.iplot.plot3d;

import java.awt.Graphics;
import javax.swing.JComponent;
import italo.iplot.plot3d.desenho.Desenho3D;
import italo.iplot.gui.Pintura;
import italo.iplot.gui.Tela;
import italo.iplot.plot3d.g3d.util.Math3D;
import italo.iplot.plot3d.g3d.util.Transformador3D;
import italo.iplot.plot3d.g3d.UniversoVirtual3D;
import italo.iplot.plot3d.g3d.est_opers.EstruturaOperador;
import italo.iplot.plot3d.g3d.vert.InicialFiltroVert3D;
import italo.iplot.plot3d.g3d.vert.VisaoFiltroVert3D;
import italo.iplot.plot3d.g3d.vert.XYZFiltroVert3D;
import italo.iplot.gui.DesenhoGUI;
import italo.iplot.gui.DesenhoGUIListener;
import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.grafico.Grafico;
import italo.iplot.plot3d.controller.DesenhoControlador;
import italo.iplot.plot3d.g3d.VerticeObjeto3DFactory;
import italo.iplot.plot3d.g3d.util.Cortador3D;
import italo.iplot.plot3d.g3d.util.Estrutura3DUtil;
import italo.iplot.plot3d.g3d.util.Malhador3D;
import italo.iplot.plot3d.grafico.AlocaImagemGrafico3D;
import italo.iplot.plot3d.grafico.Java2DGrafico3D;
import italo.iplot.plot3d.rot.IncPlot3DRotManager;
import italo.iplot.plot3d.rot.Plot3DRotManager;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

public abstract class Plot3D implements Plot3DAplic {
    
    private Object objeto;
    
    protected DesenhoGUI desenhoGUI;
    
    protected Math3D math3D = null;    
    protected Transformador3D transformador = null;
    protected Malhador3D malhador3D = null;
    protected Cortador3D cortador3D = null;
    protected Estrutura3DUtil est3DUtil = null;
    protected VerticeObjeto3DFactory vobj3DFactory = null;
    
    protected InicialFiltroVert3D inicialFiltroV3D = null;
    protected XYZFiltroVert3D xyzFiltroV3D = null;
    protected VisaoFiltroVert3D visaoFiltroV3D = null;
    protected Desenho3D desenho3D = null;
    protected EstruturaOperador boolEstOpers = null;
    protected Grafico grafico;
    
    protected UniversoVirtual3D universoVirtual = null;
    
    protected double[][] luzes = null;
        
    protected Tela tela;
    
    protected boolean aplicarPerspectiva = false;
    
    private final DecimalFormat rotuloDecimalFormat = new DecimalFormat( "0.######" );    
    
    public Plot3D() {
        desenhoGUI = new DesenhoGUI();
        desenho3D = new Desenho3D( this ); 
                
        visaoFiltroV3D = new VisaoFiltroVert3D();
        xyzFiltroV3D = new XYZFiltroVert3D();
        inicialFiltroV3D = new InicialFiltroVert3D();
            
        est3DUtil = new Estrutura3DUtil();
        transformador = new Transformador3D();
        math3D = new Math3D();    
        malhador3D = new Malhador3D();
        cortador3D = new Cortador3D( est3DUtil );
        boolEstOpers = new EstruturaOperador();           
        vobj3DFactory = new VerticeObjeto3DFactory();
    }
                
    @Override
    public double[][] getLuzes() {
        if ( luzes == null ) {
            luzes = new double[][] {
                {  3,  3,  3 },
                { -3, -3,  3 },
                { -3, 0, -3 },
            };
        }
        return luzes;
    }
            
    public void pintaGraphics( Graphics g ) {
        this.getPintura().pinta( g );         
    }
    
    public void addRotacaoDesenhoGUIListener() {
        this.addRotacaoDesenhoGUIListener( this.novoRotacaoDesenhoGUIListener() ); 
    }

    public void addRotacaoDesenhoGUIListener( DesenhoGUIListener listener ) {
        this.getDesenhoUI().addDesenhoListener( listener ); 
    }        
    
    public DesenhoGUIListener novoRotacaoDesenhoGUIListener() {
        return new DesenhoControlador( this );
    }   
                  
    public void inicializaPintura( Tela tela ) {
        this.getPintura().inicializa( tela, grafico ); 
        this.getPintura().setDesenho( desenho3D );        
    }

    @Override
    public Rectangle2D getStringLimites( String texto, Font fonte ) {
        return this.getPintura().getGrafico().stringLimites( texto, fonte );
    }
        
    @Override
    public UniversoVirtual3D getUniversoVirtual() {        
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
    public Desenho3D getDesenho3D() {
        return desenho3D;
    }
    
    @Override
    public Transformador3D getTransformador3D() {
        return transformador;
    }

    @Override
    public VisaoFiltroVert3D getVisaoFiltroV3D() {
        return visaoFiltroV3D;
    }
    
    @Override
    public XYZFiltroVert3D getXYZFiltroV3D() {
        return xyzFiltroV3D;
    }
    
    @Override
    public InicialFiltroVert3D getInicialFiltroV3D() {
        return inicialFiltroV3D;
    }
    
    @Override
    public Math3D getMath3D() {
        return math3D;
    }

    @Override
    public Malhador3D getMalhador3D() {
        return malhador3D;
    }

    @Override
    public Cortador3D getCortador3D() {
        return cortador3D;
    }
    
    @Override
    public EstruturaOperador getEstOper() {
        return boolEstOpers;
    }

    @Override
    public VerticeObjeto3DFactory getVObj3DFactory() {
        return vobj3DFactory;
    }
     
    @Override
    public Pintura getPintura() {        
        return desenhoGUI.getPintura();
    }
    
    @Override
    public void setLuzes( double[][] luzes ) {
        this.luzes = luzes;
    }
        
    public Grafico novoAlocaImagemGrafico() {
        return new AlocaImagemGrafico3D();
    }
    
    public Grafico novoJava2DGrafico() {
        return new Java2DGrafico3D();
    }
    
    public OperManager3D novoIncRotManager() {
        return new IncPlot3DRotManager();
    }
    
    public OperManager3D novoRotManager() {
        return new Plot3DRotManager();
    }
        
    public void setGrafico( Grafico grafico ) {
        this.grafico = grafico; 
    }

    @Override
    public boolean isAplicarPerspectiva() {
        return aplicarPerspectiva;
    }

    public void setAplicarPerspectiva(boolean aplicarPerspectiva) {
        this.aplicarPerspectiva = aplicarPerspectiva;
    }

    @Override
    public Object getObjeto() {
        return objeto;
    }

    @Override
    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    @Override
    public DecimalFormat getRotuloDecimalFormat() {
        return rotuloDecimalFormat;
    }
    
}
