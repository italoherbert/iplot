package italo.iplot.plot3d.planocartesiano.controller;

import italo.iplot.gui.DesenhoUI;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3DAplic;
import italo.iplot.gui.DesenhoGUIListener;

public class PCDesenhoControlador implements DesenhoGUIListener {

    private final PlanoCartesianoPlot3DAplic aplic;
        
    public PCDesenhoControlador( PlanoCartesianoPlot3DAplic aplic ) {
        this.aplic = aplic;
    }
    
    @Override
    public void arrastou(DesenhoUI ui) {
        aplic.getRotManager().execute( aplic );
        aplic.getPlanoCartesiano().transforma( aplic );                 
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void moveu( DesenhoUI ui) {
        
    }
        
}
