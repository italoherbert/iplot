package italo.iplot.plot3d;

import java.awt.Graphics;
import italo.iplot.gui.Tela;
import italo.iplot.gui.TelaImpl;
import italo.iplot.plot3d.g3d.UniversoVirtual3D;

public class Plot3DSimples extends Plot3D {            
       
    private OperManager3D rotManager = super.novoRotManager();
        
    public Plot3DSimples() {
        super.grafico = super.novoAlocaImagemGrafico();
        super.aplicarPerspectiva = true;
    }
    
    public void constroi( Graphics g, Plot3DDriver drv, int largura, int altura ) {
        this.constroi( g, drv, new TelaImpl( largura, altura ) );
    }
                
    public void constroi( Graphics g, Plot3DDriver drv, Tela tela ) {
        super.inicializaPintura( tela ); 
        
        universoVirtual = new UniversoVirtual3D();
        drv.configura( this, universoVirtual );
        universoVirtual.constroi( this );
        
        if ( aplicarPerspectiva )
            super.getTransformador3D().perspectiva( universoVirtual, super.getVisaoFiltroV3D() );                 

        super.getPintura().pinta( g ); 
    }            
    
    public void constroi( Plot3DDriver drv, int largura, int altura ) {
        this.constroi( drv, new TelaImpl( largura, altura ) );
    }
    
    public void constroi( Plot3DDriver drv, Tela tela ) {
        super.inicializaPintura( tela );                                                
                        
        universoVirtual = new UniversoVirtual3D();
        drv.configura( this, universoVirtual );
                
        universoVirtual.constroi( this );        
        
        super.getDesenhoUI().repaint();
    }

    @Override
    public OperManager3D getRotManager() {
        return rotManager;
    }
            
    public void setRotManager( OperManager3D rotManager ) {
        this.rotManager = rotManager; 
    }
                            
}
