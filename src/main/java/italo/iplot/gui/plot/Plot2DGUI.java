package italo.iplot.gui.plot;

import italo.iplot.gui.DesenhoUI;
import java.awt.event.MouseEvent;

public class Plot2DGUI extends PlotGUI implements PlotGUIListener {
    
    private Plot2DGUIListener listener = null;
    
    public Plot2DGUI() {
        super.girarBT.setVisible( false );
        super.setMouseLinhasVisiveis( true ); 
        super.setPlotListener( this );
    }

    @Override
    public void apontadorBTAcionado(PlotGUI gui) {
       if ( listener != null )
            listener.apontadorBTAcionado(this );
    }

    @Override
    public void girarBTAcionado(PlotGUI gui) {
        
    }

    @Override
    public void moverBTAcionado(PlotGUI gui) {
        if ( listener != null )
            listener.moverBTAcionado( this ); 
    }

    @Override
    public void zoomMaisBTAcionado(PlotGUI gui) {
        if ( listener != null )
            listener.zoomMaisBTAcionado( this ); 
    }

    @Override
    public void zoomMenosBTAcionado(PlotGUI gui) {
        if ( listener != null )
            listener.zoomMenosBTAcionado( this );
    }

    @Override
    public void gradeBTAcionado(PlotGUI gui) {
        if ( listener != null )
            listener.gradeBTAcionado( this );
    }

    @Override
    public void reguaBTAcionado(PlotGUI gui) {
        if ( listener != null )
            listener.reguaBTAcionado( this );
    }

    @Override
    public void ajusteBTAcionado(PlotGUI gui) {
        if ( listener != null )
            listener.ajusteBTAcionado( this );
    }

    @Override
    public void mouseLinhasBTAcionado(PlotGUI gui) {
        if ( listener != null )
            listener.mouseLinhasBTAcionado( this ); 
    }

    @Override
    public void arrastou(PlotGUI gui, DesenhoUI ui) {
        if ( listener != null )
            listener.arrastou( this, ui );
    }

    @Override
    public void moveu(PlotGUI gui, DesenhoUI ui) {
        if ( listener != null )
            listener.moveu( this, ui ); 
    }
        
    @Override
    public void clicou(PlotGUI gui, DesenhoUI ui, MouseEvent e) {
        if ( listener != null )
            listener.clicou( this, ui, e );
    }
    
    public void setPlot2DListener( Plot2DGUIListener listener ) {
        this.listener = listener;
    }
    
}

