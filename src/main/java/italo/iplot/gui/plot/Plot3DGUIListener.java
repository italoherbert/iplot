package italo.iplot.gui.plot;

import italo.iplot.gui.DesenhoUI;
import java.awt.event.MouseEvent;

public interface Plot3DGUIListener {
  
    public void apontadorBTAcionado( Plot3DGUI gui );
        
    public void girarBTAcionado( Plot3DGUI gui );
    
    public void moverBTAcionado( Plot3DGUI gui );
    
    public void zoomMaisBTAcionado( Plot3DGUI gui );
    
    public void zoomMenosBTAcionado( Plot3DGUI gui );
    
    public void gradeBTAcionado( Plot3DGUI gui );
    
    public void reguaBTAcionado( Plot3DGUI gui );

    public void ajusteBTAcionado( Plot3DGUI gui );
            
    public void arrastou( Plot3DGUI gui, DesenhoUI ui );
        
    public void moveu( Plot3DGUI gui, DesenhoUI ui );
    
    public void clicou( Plot3DGUI gui, DesenhoUI ui, MouseEvent e );
    
}
