package italo.iplot.plot3d.planocartesiano.controller;

import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.plot.Plot3DGUIListener;
import italo.iplot.gui.plot.Plot3DGUI;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DAplic;
import italo.iplot.plot3d.planocartesiano.g3d.PlanoCartesianoObjeto3D;
import java.awt.event.MouseEvent;

public class Plot3DControlador implements Plot3DGUIListener {

    private final PlanoCartesianoPlot3DAplic aplic;

    public Plot3DControlador( PlanoCartesianoPlot3DAplic aplic ) {
        this.aplic = aplic;
    }
    
    @Override
    public void arrastou(Plot3DGUI gui, DesenhoUI ui) {
        if ( gui.getBTSelecionado() == Plot3DGUI.GIRAR ) {
            aplic.getRotManager().execute( aplic );            
            aplic.getPlanoCartesiano().transforma( aplic );            
        } else if ( gui.getBTSelecionado() == Plot3DGUI.MOVER ) { 
            aplic.getMoveManager().execute( aplic );
        }
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void clicou(Plot3DGUI gui, DesenhoUI ui, MouseEvent e) {
        PlanoCartesianoObjeto3D pc = aplic.getPlanoCartesiano();
        if ( gui.getBTSelecionado() == Plot3DGUI.ZOOM_MAIS ) {
            pc.zoom( aplic.getZoom(), aplic );
        } else if ( gui.getBTSelecionado() == Plot3DGUI.ZOOM_MENOS ) {
            pc.zoom( 1.0d / aplic.getZoom(), aplic );
        }
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void moveu(Plot3DGUI gui, DesenhoUI ui) {
        
    }

    @Override
    public void apontadorBTAcionado(Plot3DGUI gui) {
        
    }

    @Override
    public void girarBTAcionado(Plot3DGUI gui) {
        
    }    
    
    @Override
    public void moverBTAcionado(Plot3DGUI gui) {
        
    }

    @Override
    public void zoomMaisBTAcionado(Plot3DGUI gui) {
        
    }

    @Override
    public void zoomMenosBTAcionado(Plot3DGUI gui) {
        
    }

    @Override
    public void gradeBTAcionado(Plot3DGUI gui) {
        PlanoCartesianoObjeto3D pc = aplic.getPlanoCartesiano();
        pc.gradeVisivel( !pc.isPintarGrade(), aplic );
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void reguaBTAcionado(Plot3DGUI gui) {
        PlanoCartesianoObjeto3D pc = aplic.getPlanoCartesiano();        
        pc.reguaVisivel( !pc.isPintarRegua(), aplic );
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void ajusteBTAcionado(Plot3DGUI gui) {
        PlanoCartesianoObjeto3D pc = aplic.getPlanoCartesiano();
        pc.constroi( aplic );
        aplic.getDesenhoUI().repaint();
    }
    
}
