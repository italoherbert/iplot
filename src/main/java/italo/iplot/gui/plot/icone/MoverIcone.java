package italo.iplot.gui.plot.icone;

import java.awt.Graphics2D;
import java.awt.Polygon;

public class MoverIcone extends Icone {
    
    private double trianguloHDIMFator = 0.125d;
    private boolean desenharTriangulos = true;
    
    public MoverIcone( int dim, boolean desenharTriangulos ) {
        super( dim );
        this.desenharTriangulos = desenharTriangulos;
    }
    

    @Override
    protected void pintaIcone( Graphics2D g2D, int x, int y ) {                
        int cx = dim / 2;
        int cy = dim / 2;
        
        int trianguloH = (int)Math.round( dim * trianguloHDIMFator );
                                
        int vlx1 = x + cx;
        int vly1 = y + esp + trianguloH;
        int vlx2 = x + cx;
        int vly2 = y + dim - esp - trianguloH;
        
        int hlx1 = x + esp + trianguloH;
        int hly1 = y + cy;
        int hlx2 = x + dim - esp - trianguloH;
        int hly2 = x + cy;                                
        
        if ( desenharTriangulos ) {
            int cimaPx1 = x + cx;
            int cimaPy1 = y + esp;
            int cimaPx2 = x + cx + trianguloH;
            int cimaPy2 = vly1+1;
            int cimaPx3 = x + cx - trianguloH;
            int cimaPy3 = vly1+1;
                        
            int baixoPx1 = x + cx - trianguloH;
            int baixoPy1 = vly2; 
            int baixoPx2 = x + cx + trianguloH;
            int baixoPy2 = vly2;           
            int baixoPx3 = x + cx;
            int baixoPy3 = y + dim - esp;
                        
            int trazPx1 = x + esp;
            int trazPy1 = y + cy;
            int trazPx2 = hlx1;
            int trazPy2 = y + cy - trianguloH;
            int trazPx3 = hlx1;
            int trazPy3 = y + cy + trianguloH;
            
            int frentePx1 = hlx2;
            int frentePy1 = y + cy - trianguloH;
            int frentePx2 = hlx2;
            int frentePy2 = y + cy + trianguloH;
            int frentePx3 = x + dim - esp;
            int frentePy3 = y + cy;

            Polygon cimaP = new Polygon();
            cimaP.addPoint( cimaPx1, cimaPy1 );
            cimaP.addPoint( cimaPx2, cimaPy2 );
            cimaP.addPoint( cimaPx3, cimaPy3 );

            Polygon baixoP = new Polygon();
            baixoP.addPoint( baixoPx1, baixoPy1 );
            baixoP.addPoint( baixoPx2, baixoPy2 );
            baixoP.addPoint( baixoPx3, baixoPy3 );

            Polygon trazP = new Polygon();
            trazP.addPoint( trazPx1, trazPy1 );
            trazP.addPoint( trazPx2, trazPy2 );
            trazP.addPoint( trazPx3, trazPy3 );

            Polygon frenteP = new Polygon();
            frenteP.addPoint( frentePx1, frentePy1 );
            frenteP.addPoint( frentePx2, frentePy2 );
            frenteP.addPoint( frentePx3, frentePy3 );
             
            g2D.setColor( linhaCor ); 
            g2D.drawLine( hlx1, hly1, hlx2, hly2 );
            g2D.drawLine( vlx1, vly1, vlx2, vly2 );
            g2D.fillPolygon( cimaP );
            g2D.fillPolygon( baixoP );
            g2D.fillPolygon( trazP );
            g2D.fillPolygon( frenteP );
        } else {
            hlx1 = x + esp;
            hlx2 += x + dim - esp;
            
            vly1 = y + esp;
            vly2 = y + dim - esp;
            
            g2D.setColor( linhaCor ); 
            g2D.drawLine( hlx1, hly1, hlx2, hly2 );
            g2D.drawLine( vlx1, vly1, vlx2, vly2 );
        }                   
    }

    public double getTrianguloHDIMFator() {
        return trianguloHDIMFator;
    }

    public void setTrianguloHDIMFator(double trianguloHDIMFator) {
        this.trianguloHDIMFator = trianguloHDIMFator;
    }
    
    public boolean isDesenharTriangulos() {
        return desenharTriangulos;
    }

    public void setDesenharTriangulos(boolean desenharTriangulos) {
        this.desenharTriangulos = desenharTriangulos;
    }

}
