package italo.iplot.exemplos.plot3d.principal.gui.gaussiana;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import italo.iplot.gui.DesenhoUI;

public class GaussianaGUI extends JPanel implements GaussianaUI, ActionListener, ItemListener {
    
    private DecimalFormat decimalFormat = new DecimalFormat( "#.####" );

    private JLabel divX_LB = new JLabel( "Divisões X: " );
    private JLabel divY_LB = new JLabel( "Divisões Y: " );
    
    private JTextField nDivX_TF = new JTextField( 5 );
    private JTextField nDivZ_TF = new JTextField( 5 );
        
    private JCheckBox pintarVertices_CB = new JCheckBox( "Pintar vertices" );
    private JCheckBox desenharFaces_CB = new JCheckBox( "Desenhar faces" );
    private JCheckBox pintarFaces_CB = new JCheckBox( "Pintar faces");
    
    private JLabel raio_LB = new JLabel( "Raio: ");
    private JLabel altura_LB = new JLabel( "Altura: " );
    
    private JTextField raio_TF = new JTextField( 5 );
    private JTextField altura_TF = new JTextField( 5 ); 
        
    private JLabel intervaloX_LB = new JLabel( "Intervalo X: " );
    private JLabel intervaloZ_LB = new JLabel( "Intervalo Z: " );
    
    private JTextField x1_TF = new JTextField( 5 );
    private JTextField x2_TF = new JTextField( 5 );
    
    private JTextField z1_TF = new JTextField( 5 );
    private JTextField z2_TF = new JTextField( 5 );
    
    private JPanel xIntervalo_PNL = new JPanel();
    private JPanel zIntervalo_PNL = new JPanel();
    
    private JPanel desenhoUI_PNL = new JPanel();
    
    private JButton aplicar_BT = new JButton( "Aplicar" );

    private GaussianaGUIListener listener;
    
    private int larguraPainel;
    private int alturaPainel;
    
    public GaussianaGUI( int larguraPainel, int alturaPainel ) {                
        this.larguraPainel = larguraPainel;
        this.alturaPainel = alturaPainel;
        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        
        JPanel divX_PNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        divX_PNL.add( divX_LB );
        divX_PNL.add(nDivX_TF );
        
        JPanel divY_PNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        divY_PNL.add( divY_LB );
        divY_PNL.add(nDivZ_TF );                
                
        JPanel grafico_PNL = new JPanel( new GridBagLayout() );        
        
        JPanel divs_PNL = new JPanel( new GridBagLayout() );
        divs_PNL.setBorder( new TitledBorder( "Malha" ) ); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        divs_PNL.add( divX_PNL, gbc );
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        divs_PNL.add( divY_PNL, gbc );
                
        gbc.gridx = 0;
        gbc.gridy = 0;
        grafico_PNL.add( divs_PNL, gbc );
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        grafico_PNL.add( pintarVertices_CB, gbc );
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        grafico_PNL.add( desenharFaces_CB, gbc );
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        grafico_PNL.add( pintarFaces_CB, gbc );
        
        JPanel graficoBorda_PNL = new JPanel( new GridBagLayout() );
        graficoBorda_PNL.setBorder( new TitledBorder( "Geometria" ) ); 
        
        gbc.insets = new Insets( 0, 10, 10, 10 );
        gbc.gridx = 0;
        gbc.gridy = 0;
        graficoBorda_PNL.add( grafico_PNL, gbc );
        
        JPanel graficoBorda_LEFT_PNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        graficoBorda_LEFT_PNL.add( graficoBorda_PNL );
                                      
        xIntervalo_PNL.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        xIntervalo_PNL.add( intervaloX_LB );
        xIntervalo_PNL.add( x1_TF );
        xIntervalo_PNL.add( new JLabel( " a " ) );
        xIntervalo_PNL.add( x2_TF );
        
        zIntervalo_PNL.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        zIntervalo_PNL.add( intervaloZ_LB );
        zIntervalo_PNL.add( z1_TF );
        zIntervalo_PNL.add( new JLabel( " a " ) );
        zIntervalo_PNL.add( z2_TF );
                
        JPanel intervalos_PNL = new JPanel();
        intervalos_PNL.setBorder( new TitledBorder( "Eixos X e Z" ) );
        intervalos_PNL.setLayout( new GridBagLayout() );
        
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;        
        gbc.insets = new Insets( 0, 0, 0, 0 );
        gbc.weightx = 0;
        gbc.gridx = 0;
        
        gbc.gridy = 0;
        intervalos_PNL.add( xIntervalo_PNL, gbc );
        
        gbc.gridy = 1;
        intervalos_PNL.add( zIntervalo_PNL, gbc );
                        
        JPanel intarvalos_LEFT_PNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        intarvalos_LEFT_PNL.add( intervalos_PNL );

        JPanel raio_PNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        raio_PNL.add( raio_LB );
        raio_PNL.add( raio_TF );
        
        JPanel altura_PNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        altura_PNL.add( altura_LB );
        altura_PNL.add( altura_TF );
                
        JPanel func_PNL = new JPanel();
        func_PNL.setBorder( new TitledBorder( "Gaussiana" ) );
        func_PNL.setLayout( new GridBagLayout() );
        gbc.weightx = 0;
        gbc.insets = new Insets( 5, 0, 0, 0 );

        gbc.gridx = 0;
        gbc.gridy = 0;
        func_PNL.add( raio_LB, gbc );

        gbc.gridx = 1;
        gbc.gridy = 0;
        func_PNL.add( raio_TF, gbc );
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        func_PNL.add( altura_LB, gbc );
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        func_PNL.add( altura_TF, gbc );
                
        JPanel func_LEFT_PNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        func_LEFT_PNL.add( func_PNL );
        
        JPanel config_PNL = new JPanel();
        config_PNL.setLayout( new GridBagLayout() ); 
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets( 0, 0, 0, 0 );
        
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        config_PNL.add( graficoBorda_LEFT_PNL, gbc );                    
        
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridy = 0;
        gbc.gridx = 1;  
        config_PNL.add( intarvalos_LEFT_PNL , gbc );
        
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridy = 0;
        gbc.gridx = 2;
        config_PNL.add( func_LEFT_PNL, gbc );
                
        gbc.gridy = 0;
        gbc.gridx = 3;
        gbc.insets = new Insets( 10, 0, 0, 0 );
        config_PNL.add( aplicar_BT, gbc );
        
        JPanel config_LEFT_PNL = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        config_LEFT_PNL.add( config_PNL );
         
        JSplitPane split_JSP = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
        split_JSP.setTopComponent( desenhoUI_PNL );
        split_JSP.setBottomComponent( config_LEFT_PNL );
        
        super.setLayout( new GridLayout() ); 
        super.add( split_JSP );
        
        aplicar_BT.addActionListener( this );
        
        pintarVertices_CB.addItemListener( this );
        desenharFaces_CB.addItemListener( this );
        pintarFaces_CB.addItemListener( this ); 
    }
    
    public Dimension addGaussianaUI( DesenhoUI ui ) {            
        int l = larguraPainel;
        int a = alturaPainel * 3 / 4;
        
        JComponent c = ui.getJComponent();
        c.setPreferredSize( new Dimension( l, a ) ); 
        desenhoUI_PNL.add( c );      
        
        return c.getPreferredSize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == aplicar_BT ) {
            listener.aplicarBTAcionado( this );
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == pintarVertices_CB ) {
            listener.mostrarVerticesCBAlterado( this );
        } else if ( e.getSource() == desenharFaces_CB ) {
            listener.mostrarArestasCBAlterado( this );
        } else if ( e.getSource() == pintarFaces_CB ) {
            listener.mostrarFacesCBAlterado( this ); 
        }
    }
    
    @Override
    public void setGaussianaListener( GaussianaGUIListener listener ) {
        this.listener = listener;
    }

    @Override
    public int getNDivX() {
        return Integer.parseInt( nDivX_TF.getText() );
    }

    @Override
    public int getNDivZ() {
        return Integer.parseInt( nDivZ_TF.getText() );
    }

    @Override
    public boolean isPintarVertices() {
        return pintarVertices_CB.isSelected();
    }

    @Override
    public boolean isDesenharFaces() {
        return desenharFaces_CB.isSelected();
    }

    @Override
    public boolean isPintarFaces() {
        return pintarFaces_CB.isSelected();
    }    

    @Override
    public double getX1() {
        return stringParaDouble( x1_TF.getText() );
    }

    @Override
    public double getX2() {
        return stringParaDouble( x2_TF.getText() );
    }

    @Override
    public double getZ1() {
        return stringParaDouble( z1_TF.getText() );
    }

    @Override
    public double getZ2() {
        return stringParaDouble( z2_TF.getText() );
    }

    @Override
    public double getFuncRaio() {
        return stringParaDouble( raio_TF.getText() );
    }

    @Override
    public double getFuncAltura() {
        return stringParaDouble( altura_TF.getText() );
    }

    @Override
    public void setNDivX(int nDivX) {
        nDivX_TF.setText( String.valueOf( nDivX ) ); 
    }

    @Override
    public void setNDivZ(int nDivZ) {
        nDivZ_TF.setText( String.valueOf( nDivZ ) ); 
    }

    @Override
    public void setPintarVertices(boolean mostrar) {
        pintarVertices_CB.setSelected( mostrar ); 
    }

    @Override
    public void setDesenharFaces(boolean mostrar) {
        desenharFaces_CB.setSelected( mostrar );
    }

    @Override
    public void setPintarFaces(boolean mostrar) {
        pintarFaces_CB.setSelected( mostrar );
    }

    @Override
    public void setX1(double x1) {
        x1_TF.setText( this.doubleParaString( x1 ) ); 
    }

    @Override
    public void setX2(double x2) {
        x2_TF.setText( this.doubleParaString( x2 ) ); 
    }

    @Override
    public void setZ1(double z1) {
        z1_TF.setText( this.doubleParaString( z1 ) ); 
    }

    @Override
    public void setZ2(double z2) {
        z2_TF.setText( this.doubleParaString( z2 ) ); 
    }

    @Override
    public void setFuncRaio(double raio) {
        raio_TF.setText( this.doubleParaString( raio ) ); 
    }

    @Override
    public void setFuncAltura(double altura) {
        altura_TF.setText( this.doubleParaString( altura ) ); 
    }
    
    public double stringParaDouble( String s ) {
        String s2 = s.replace( ",", "." );
        return Double.parseDouble( s2 );
    }
    
    private String doubleParaString( double d ) {
        return decimalFormat.format( d ).replace( ".", "," );
    }
    
}
