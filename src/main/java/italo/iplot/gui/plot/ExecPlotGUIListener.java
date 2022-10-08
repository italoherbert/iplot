package italo.iplot.gui.plot;

import italo.iplot.gui.DesenhoUI;

public interface ExecPlotGUIListener {
    
    public void plotEventoExecutando( PlotGUI pgui, DesenhoUI ui );
    
    public void plotEventoExecutado( PlotGUI pgui, DesenhoUI ui );
            
}
