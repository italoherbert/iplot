package italo.iplot.plot2d.planocartesiano.controller;

import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.plot.Plot2DGUIListener;
import italo.iplot.gui.plot.Plot2DGUI;
import italo.iplot.plot2d.planocartesiano.PlanoCartesianoPlot2DAplic;
import italo.iplot.plot2d.planocartesiano.g2d.PlanoCartesianoObjeto2D;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class Plot2DControlador implements Plot2DGUIListener {
    
    private final PlanoCartesianoPlot2DAplic aplic;

    public Plot2DControlador( PlanoCartesianoPlot2DAplic aplic ) {
        this.aplic = aplic;
    }    

    @Override
    public void arrastou( Plot2DGUI gui, DesenhoUI ui ) {
        if ( gui.getBTSelecionado() == Plot2DGUI.MOVER ) {
            aplic.getMoveManager().execute( aplic ); 
        }
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void moveu(Plot2DGUI gui, DesenhoUI ui) {        
        PlanoCartesianoObjeto2D pc = aplic.getPlanoCartesiano();
        double[] mouseIXY = pc.calculaMouseIXY( aplic );        
        String mouseIXS = aplic.getRotuloDecimalFormat().format( mouseIXY[0] );           
        String mouseIYS = aplic.getRotuloDecimalFormat().format( mouseIXY[1] );
            
        aplic.setMouseIXYValor( mouseIXS+", "+mouseIYS, Color.BLACK );
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void clicou(Plot2DGUI gui, DesenhoUI ui, MouseEvent e) {
        PlanoCartesianoObjeto2D pc = aplic.getPlanoCartesiano();
        if ( gui.getBTSelecionado() == Plot2DGUI.ZOOM_MAIS ) {
            pc.zoom( aplic.getZoom(), aplic );
        } else if ( gui.getBTSelecionado() == Plot2DGUI.ZOOM_MENOS ) {
            pc.zoom( 1.0d / aplic.getZoom(), aplic );
        }
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void apontadorBTAcionado(Plot2DGUI gui) {
        
    }
    
    @Override
    public void moverBTAcionado(Plot2DGUI gui) {
        
    }

    @Override
    public void zoomMaisBTAcionado(Plot2DGUI gui) {
        
    }

    @Override
    public void zoomMenosBTAcionado(Plot2DGUI gui) {
        
    }

    @Override
    public void gradeBTAcionado(Plot2DGUI gui) {
        PlanoCartesianoObjeto2D pc = aplic.getPlanoCartesiano();
        pc.gradeVisivel( !pc.isPintarGrade(), aplic );
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void reguaBTAcionado(Plot2DGUI gui) {
        PlanoCartesianoObjeto2D pc = aplic.getPlanoCartesiano();        
        pc.reguaVisivel( !pc.isPintarRegua(), aplic );
        aplic.getDesenhoUI().repaint();
    }
    
    
    @Override
    public void ajusteBTAcionado(Plot2DGUI gui) {
        PlanoCartesianoObjeto2D pc = aplic.getPlanoCartesiano();
        pc.constroi( aplic );
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void mouseLinhasBTAcionado(Plot2DGUI gui) {
        PlanoCartesianoObjeto2D pc = aplic.getPlanoCartesiano();        
        pc.setPintarMouseLinhas( !pc.isPintarMouseLinhas() );
        aplic.getDesenhoUI().repaint();
    }
    
}
