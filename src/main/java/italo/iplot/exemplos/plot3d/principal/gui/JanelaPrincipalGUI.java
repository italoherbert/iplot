package italo.iplot.exemplos.plot3d.principal.gui;

import italo.iplot.exemplos.plot3d.principal.gui.gaussiana.GaussianaGUI;
import italo.iplot.exemplos.plot3d.principal.gui.gaussiana.GaussianaGUIListener;
import italo.iplot.exemplos.plot3d.principal.gui.gaussiana.GaussianaUI;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import italo.iplot.gui.DesenhoUI;

public class JanelaPrincipalGUI extends JFrame {
    
    private final JTabbedPane tp = new JTabbedPane();
    private final JPanel tp1 = new JPanel();
    private final JPanel tp3 = new JPanel();
    private final JPanel tp4 = new JPanel();
    private final GaussianaGUI gaussianaGUI;
            
    private final int larguraPainel;
    private final int alturaPainel;
    
    public JanelaPrincipalGUI( int larguraPainel, int alturaPainel ) {
        this.larguraPainel = larguraPainel;
        this.alturaPainel = alturaPainel;
        
        gaussianaGUI =  new GaussianaGUI( larguraPainel, alturaPainel );
        
        tp1.setLayout( new GridLayout() );         
        tp.addTab( "Funções", tp1 );
        tp.addTab( "Gaussiana", gaussianaGUI ); 
        tp.addTab( "Gaussiana Obj. 3D", tp3 );
        tp.addTab( "Cubo", tp4 ); 
        
        super.setTitle( "Plotagem de funções 3D" ); 
        super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        super.setContentPane( tp );
    }
    
    public void empacotaECentraliza() {
        super.pack();
        super.setLocationRelativeTo( this ); 
    }
    
    public void addFuncoesUIs( DesenhoUI[] desenhoUIs ) {
        tp1.setLayout( new GridLayout( 2, 2, 5, 5 ) );         
                        
        int l = ( larguraPainel ) / 2;
        int a = ( alturaPainel ) / 2;
                                
        for( int i = 0; i < desenhoUIs.length; i++ ) {                                                                                    
            JComponent c = desenhoUIs[i].getJComponent();
            c.setPreferredSize( new Dimension( l, a ) );
            tp1.add( c );                        
        }               
    }
    
    public Dimension addGaussianaDesenhoUI( DesenhoUI ui ) {
        return gaussianaGUI.addGaussianaUI( ui );
    }
    
    public Dimension addGaussiana2DesenhoUI( DesenhoUI ui ) {        
        JComponent c = ui.getJComponent();
        c.setPreferredSize( new Dimension( larguraPainel, alturaPainel ) ); 
        tp3.add( c );
        
        return c.getPreferredSize();
    }
    
    public Dimension addCuboDesenhoUI( DesenhoUI ui ) {        
        JComponent c = ui.getJComponent();
        c.setPreferredSize( new Dimension( larguraPainel, alturaPainel ) ); 
        tp4.add( c );
        
        return c.getPreferredSize();
    }
        
    public void setGaussianaListener( GaussianaGUIListener listener ) {
        gaussianaGUI.setGaussianaListener( listener ); 
    }

    public GaussianaUI getGaussianaGUI() {
        return gaussianaGUI;
    }
    
}
