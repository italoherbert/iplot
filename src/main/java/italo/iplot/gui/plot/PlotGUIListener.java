package italo.iplot.gui.plot;

import italo.iplot.gui.DesenhoUI;
import java.awt.event.MouseEvent;

public interface PlotGUIListener {
    
    public void apontadorBTAcionado( PlotGUI gui );
    
    public void girarBTAcionado( PlotGUI gui );
    
    public void moverBTAcionado( PlotGUI gui );
    
    public void zoomMaisBTAcionado( PlotGUI gui );
    
    public void zoomMenosBTAcionado( PlotGUI gui );
    
    public void gradeBTAcionado( PlotGUI gui );
    
    public void reguaBTAcionado( PlotGUI gui );
    
    public void ajusteBTAcionado( PlotGUI gui );

    public void mouseLinhasBTAcionado( PlotGUI gui );
    
    public void arrastou( PlotGUI gui, DesenhoUI ui );
    
    public void moveu( PlotGUI gui, DesenhoUI ui );
            
    public void clicou( PlotGUI gui, DesenhoUI ui, MouseEvent e );
    
}
