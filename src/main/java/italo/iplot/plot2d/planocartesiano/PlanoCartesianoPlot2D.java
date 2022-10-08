package italo.iplot.plot2d.planocartesiano;

import italo.iplot.gui.Tela;
import italo.iplot.gui.TelaImpl;
import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.gui.plot.Plot2DGUIListener;
import italo.iplot.plot2d.Plot2D;
import italo.iplot.plot2d.g2d.UniversoVirtual2D;
import italo.iplot.plot2d.planocartesiano.controller.Plot2DControlador;
import italo.iplot.plot2d.planocartesiano.g2d.PlanoCartesianoObjeto2D;
import italo.iplot.plot2d.planocartesiano.move.PCPlot2DMoveManager;
import java.awt.Color;
import java.awt.Graphics;
import italo.iplot.plot2d.OperManager2D;

public class PlanoCartesianoPlot2D extends Plot2D implements PlanoCartesianoPlot2DAplic {
       
    private PlanoCartesianoObjeto2D planoCartesiano;
    private double zoom = 2.0d;
                    
    private OperManager2D moveManager = this.novoPCMoveManager();    
    private Plot2DGUI plot2DGUI = null;
                            
    public PlanoCartesianoPlot2D() {
        super.grafico = super.novoAlocaImagemGrafico();
    }
    
    public void constroi( Graphics g, PlanoCartesianoPlot2DDriver drv, int larguraTela, int alturaTela ) {
        this.constroi( g, drv, new TelaImpl( larguraTela, alturaTela ) );
    }
    
    public void constroi( Graphics g, PlanoCartesianoPlot2DDriver drv, Tela tela ) {        
        super.inicializaPintura( tela ); 
        
        this.configuraUniversoVirtual( drv );
        
        super.getUniversoVirtual().constroi( this );        
        super.getPintura().pinta( g ); 
    }

    public void constroi( PlanoCartesianoPlot2DDriver drv, int largura, int altura ) {
        this.constroi( drv, new TelaImpl( largura, altura ) ); 
    }
         
    public void constroi( PlanoCartesianoPlot2DDriver drv, Tela tela ) {                        
        super.inicializaPintura( tela );
                                                   
        this.configuraUniversoVirtual( drv );
                
        super.getUniversoVirtual().constroi( this );

        super.getDesenhoUI().repaint();
    }
    
    private void configuraUniversoVirtual( PlanoCartesianoPlot2DDriver drv ) {
        planoCartesiano = new PlanoCartesianoObjeto2D();                                    
                                  
        universoVirtual = new UniversoVirtual2D();
        universoVirtual.setCorFundo( Color.WHITE ); 
        universoVirtual.addObjeto( planoCartesiano );                                                         
        
        drv.configura( this, planoCartesiano );  
    }

    @Override
    public PlanoCartesianoObjeto2D getPlanoCartesiano() {
        return planoCartesiano;
    }
        
    public Plot2DGUI novaPlot2DGUI() {
        return this.novaPlot2DGUI( this.novoPlot2DGUIListener() );
    }
    
    public Plot2DGUI novaPlot2DGUI( Plot2DGUIListener listener ) {
        plot2DGUI = new Plot2DGUI();        
        plot2DGUI.setDesenhoUI( desenhoGUI ); 
        plot2DGUI.setPlot2DListener( listener ); 
        plot2DGUI.acionarApontadorBT();
        return plot2DGUI;
    }
    
    public Plot2DGUIListener novoPlot2DGUIListener() {
        return new Plot2DControlador( this );
    }

    @Override
    public OperManager2D getMoveManager() {
        return moveManager;
    }
    
    public void setMoveManager( OperManager2D moveManager ) {
        this.moveManager = moveManager;
    }
    
    public OperManager2D novoPCMoveManager() {
        return new PCPlot2DMoveManager();
    }
    
    @Override
    public void setMouseIXYValor( String valor, Color cor ) {
        if ( plot2DGUI != null )
            plot2DGUI.setTextoInfo( valor, cor );
    }

    @Override
    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
    
}
