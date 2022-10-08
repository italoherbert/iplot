package italo.iplot.gui.plot.icone;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class ZoomIcone extends LupaIcone {

    private boolean pintarTracoVertical = true;
    
    public ZoomIcone( int dim, boolean pintarTracoVertical ) {
        super( dim );
        this.pintarTracoVertical = pintarTracoVertical;
    }
    
    @Override
    protected void pintaInterLupaIcone( Graphics2D g2D, int cx, int cy, int r ) {
        int lupaInterEsp;
        if ( desativarAntiAliasing ) {
            lupaInterEsp = (int)( stroke.getLineWidth() * 3 );            
        } else {
            lupaInterEsp = (int)( stroke.getLineWidth() + ( stroke.getLineWidth()*.6f ) );
        }
        int hx1 = cx - r + lupaInterEsp;
        int hy1 = cy;
        int hx2 = cx + r - lupaInterEsp;
        int hy2 = cy;
                        
        if ( stroke.getLineWidth() >= 2.0f ) {
            g2D.setStroke( new BasicStroke( stroke.getLineWidth() / 2 ) );
        } else {
            g2D.setStroke( new BasicStroke() );
        }
        g2D.drawLine( hx1, hy1, hx2, hy2 );
        
        if ( pintarTracoVertical ) {
            int vx1 = cx;
            int vy1 = cy - r + lupaInterEsp;
            int vx2 = cx;
            int vy2 = cy + r - lupaInterEsp;
            
            g2D.drawLine( vx1, vy1, vx2, vy2 ); 
        }
    }

    public boolean isPintarTracoVertical() {
        return pintarTracoVertical;
    }

    public void setPintarTracoVertical(boolean pintarTracoVertical) {
        this.pintarTracoVertical = pintarTracoVertical;
    }
    
}
