package italo.iplot.gui;

import italo.iplot.gui.grafico.Grafico;
import java.awt.Cursor;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;

public interface DesenhoUI {
                
    public void atualizaXYs();
    
    public void repaint();
        
    public JComponent getJComponent();
    
    public Grafico getGrafico();
    
    public Desenho getDesenho();
        
    public void setGrafico( Grafico grafico );

    public void setDesenho( Desenho desenho );
        
    public int getX1();

    public void setX1(int x1);

    public int getY1();

    public void setY1(int y1);

    public int getX2();

    public void setX2(int x2);

    public int getY2();
    
    public void setY2(int y2);
    
    public int getMouseX();
    
    public int getMouseY();

    public boolean isPrimeiroMovimento();

    public void setPrimeiroMovimento(boolean primeiroMovimento);
            
    public void setCursor( Cursor cursor );
   
    public void addDesenhoListener( DesenhoGUIListener listener );
    
    public boolean removeDesenhoListener( DesenhoGUIListener listener );
        
    public void addMouseListener( MouseListener listener );
    
    public void removeMouseListener( MouseListener listener );
            
    public void addMouseMotionListener( MouseMotionListener listener );
    
    public void removeMouseMotionListener( MouseMotionListener listener );
    
    
}
