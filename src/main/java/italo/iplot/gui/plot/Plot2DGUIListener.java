package italo.iplot.gui.plot;

import italo.iplot.gui.DesenhoUI;
import java.awt.event.MouseEvent;

public interface Plot2DGUIListener {
    
    public void apontadorBTAcionado( Plot2DGUI gui );
    
    public void moverBTAcionado( Plot2DGUI gui );
    
    public void zoomMaisBTAcionado( Plot2DGUI gui );
    
    public void zoomMenosBTAcionado( Plot2DGUI gui );
    
    public void gradeBTAcionado( Plot2DGUI gui );
    
    public void reguaBTAcionado( Plot2DGUI gui );

    public void ajusteBTAcionado( Plot2DGUI gui );

    public void mouseLinhasBTAcionado( Plot2DGUI gui );
    
    public void arrastou( Plot2DGUI gui, DesenhoUI ui );
    
    public void moveu( Plot2DGUI gui, DesenhoUI ui );       
        
    public void clicou( Plot2DGUI gui, DesenhoUI ui, MouseEvent e );
    
}
