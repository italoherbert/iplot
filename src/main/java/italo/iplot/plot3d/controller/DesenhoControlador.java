package italo.iplot.plot3d.controller;

import italo.iplot.plot3d.Plot3DAplic;
import italo.iplot.gui.DesenhoUI;
import italo.iplot.gui.DesenhoGUIListener;

public class DesenhoControlador implements DesenhoGUIListener {

    private final Plot3DAplic aplic;
        
    public DesenhoControlador( Plot3DAplic aplic ) {
        this.aplic = aplic;
    }
    
    @Override
    public void arrastou( DesenhoUI ui ) { 
        aplic.getRotManager().execute( aplic ); 
        aplic.getUniversoVirtual().aplicaTransformacoes();
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void moveu(DesenhoUI ui) {
        
    }
    
}

