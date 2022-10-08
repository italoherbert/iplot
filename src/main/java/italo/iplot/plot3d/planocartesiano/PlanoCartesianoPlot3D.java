package italo.iplot.plot3d.planocartesiano;

import italo.iplot.plot3d.planocartesiano.rot.PCIncPlot3DRotManager;
import italo.iplot.plot3d.Plot3D;
import java.awt.Color;
import java.awt.Graphics;
import italo.iplot.plot3d.g3d.ConstroiObj3DAdapter;
import italo.iplot.plot3d.g3d.Objeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.UniversoVirtual3D;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;
import italo.iplot.gui.DesenhoGUIListener;
import italo.iplot.gui.Tela;
import italo.iplot.gui.TelaImpl;
import italo.iplot.gui.plot.Plot3DGUI;
import italo.iplot.gui.plot.Plot3DGUIListener;
import italo.iplot.plot3d.OperManager3D;
import italo.iplot.plot3d.planocartesiano.controller.PCDesenhoControlador;
import italo.iplot.plot3d.planocartesiano.controller.Plot3DControlador;
import italo.iplot.plot3d.planocartesiano.move.PCPlot3DMoveManager;

public class PlanoCartesianoPlot3D extends Plot3D implements PlanoCartesianoPlot3DAplic {
            
    private PlanoCartesianoObjeto3D planoCartesiano;
    private double zoom = 2.0d;
    
    private double inicialXRot = -Math.PI / 8.0d;
    private double inicialYRot =  Math.PI / 4.5d;
    private boolean rotXY = true;
                    
    private OperManager3D rotManager = this.novoPCIncRotManager();
    private OperManager3D moveManager = this.novoPCMoveManager();
                                
    public PlanoCartesianoPlot3D() {
        super.grafico = super.novoAlocaImagemGrafico();
    }
    
    public void constroi( Graphics g, PlanoCartesianoPlot3DDriver drv, int larguraTela, int alturaTela ) {
        this.constroi( g, drv, new TelaImpl( larguraTela, alturaTela ) ); 
    }
        
    public void constroi( Graphics g, PlanoCartesianoPlot3DDriver drv, Tela tela ) {        
        super.inicializaPintura( tela ); 
        
        this.configuraUniversoVirtual( drv );
        
        super.getUniversoVirtual().constroi( this );        
        super.getPintura().pinta( g ); 
    }

    public void constroi( PlanoCartesianoPlot3DDriver drv, int largura, int altura ) {
        this.constroi( drv, new TelaImpl( largura, altura ) );             
    }
         
    public void constroi( PlanoCartesianoPlot3DDriver drv, Tela tela ) {                        
        super.inicializaPintura( tela );
                                                   
        this.configuraUniversoVirtual( drv );
                
        super.getUniversoVirtual().constroi( this );
                
        super.getDesenhoUI().repaint();
    }
    
    private void configuraUniversoVirtual( PlanoCartesianoPlot3DDriver drv ) {
        planoCartesiano = new PlanoCartesianoObjeto3D();                            
                
        if ( rotXY ) {
            planoCartesiano.setXRot( inicialXRot );
            planoCartesiano.setYRot( inicialYRot );                         
        }                
        
        planoCartesiano.addConstroiObj3DListener( new ConstroiObj3DAdapter() {            
            @Override
            public void construiuParcialmente(Objeto3D obj, Objeto3DTO to) {
                planoCartesiano.transforma( to );             
            }                 
        } );
                
        universoVirtual = new UniversoVirtual3D();
        universoVirtual.setCorFundo( Color.WHITE ); 
        universoVirtual.addObjeto( planoCartesiano );                                                         
        
        drv.configura( planoCartesiano );  
    }

    @Override
    public PlanoCartesianoObjeto3D getPlanoCartesiano() {
        return planoCartesiano;
    }

    @Override
    public OperManager3D getRotManager() {
        return rotManager;
    }

    @Override
    public OperManager3D getMoveManager() {
        return moveManager;
    }
               
    public void setRotManager( OperManager3D rotManager ) {
        this.rotManager = rotManager;
    }
    
    public void setMoveManager( OperManager3D moveManager ) {
        this.moveManager = moveManager;
    }

    public OperManager3D novoPCIncRotManager() {
        return new PCIncPlot3DRotManager();
    }
    
    public OperManager3D novoPCMoveManager() {
        return new PCPlot3DMoveManager();
    }
        
    public void addPCRotacaoDesenhoGUIListener( DesenhoGUIListener listener ) {
        this.getDesenhoUI().addDesenhoListener( listener ); 
    }
    
    public void addPCRotacaoDesenhoGUIListener() {
        this.addPCRotacaoDesenhoGUIListener( this.novoPCRotacaoDesenhoGUIListener() ); 
    }
    
    public DesenhoGUIListener novoPCRotacaoDesenhoGUIListener() {
        return new PCDesenhoControlador( this );
    }

    public Plot3DGUI novoPlotGUI() {
        return this.novoPlotGUI( this.novoPlotGUIListener() );
    }
    
    public Plot3DGUI novoPlotGUI( Plot3DGUIListener listener ) {
        Plot3DGUI pgui = new Plot3DGUI();        
        pgui.setDesenhoUI( desenhoGUI ); 
        pgui.setPlot3DListener( listener );         
        pgui.acionarGirarBT();
                            
        return pgui;
    }
    
    public Plot3DGUIListener novoPlotGUIListener() {
        return new Plot3DControlador( this );
    }

    @Override
    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public double getInicialXRot() {
        return inicialXRot;
    }

    public void setInicialXRot(double inicialXRot) {
        this.inicialXRot = inicialXRot;
    }

    public double getInicialYRot() {
        return inicialYRot;
    }

    public void setInicialYRot(double inicialYRot) {
        this.inicialYRot = inicialYRot;
    }

    public boolean isRotXY() {
        return rotXY;
    }

    public void setRotXY(boolean rotXY) {
        this.rotXY = rotXY;
    }
        
}
