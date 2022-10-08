
package italo.iplot.exemplos.plot3d.principal.controller;

import italo.iplot.exemplos.plot3d.principal.gui.gaussiana.GaussianaGUIListener;
import italo.iplot.exemplos.plot3d.principal.gui.gaussiana.GaussianaUI;
import italo.iplot.exemplos.plot3d.principal.util.MSGUtil;
import italo.iplot.plot3d.planocartesiano.PlanoCartesianoPlot3D;
import italo.iplot.funcs.g3d.GaussianaFunc3D;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncObjeto3D;

public class GaussianaController implements GaussianaGUIListener {

    private final PlanoCartesianoPlot3D aplic;
    private final PCFuncObjeto3D funcObj3D;
    private final GaussianaFunc3D gaussianaF3D;
    private final MSGUtil msgUtil;
    
    public GaussianaController( PlanoCartesianoPlot3D aplic, PCFuncObjeto3D funcObj3D, GaussianaFunc3D gaussianaF3D, MSGUtil msgUtil ) {
        this.msgUtil = msgUtil;
        this.aplic = aplic;
        this.funcObj3D = funcObj3D;
        this.gaussianaF3D = gaussianaF3D;
    }
    
    @Override
    public void aplicarBTAcionado(GaussianaUI ui) {
        try {
            gaussianaF3D.setRaio( ui.getFuncRaio() );
        } catch ( NumberFormatException e ) {
            msgUtil.mostraAlerta( "Raio inválido." ); 
        }
        
        try {
            gaussianaF3D.setAltura( ui.getFuncAltura() ); 
        } catch ( NumberFormatException e ) {
            msgUtil.mostraAlerta( "Altura inválida." ); 
        }
            
        try {
            funcObj3D.setNDivs( ui.getNDivX(), ui.getNDivZ() ); 
        } catch ( NumberFormatException e ) {
            msgUtil.mostraAlerta( "Número de divisão inválido." ); 
        }
        
        try {
            funcObj3D.setXIntervalo( ui.getX1(), ui.getX2() );
        } catch ( NumberFormatException e ) {
            msgUtil.mostraAlerta( "Intervalo X inválido." ); 
        }
        
        try {
            funcObj3D.setZIntervalo( ui.getZ1(), ui.getZ2() );
        } catch ( NumberFormatException e ) {
            msgUtil.mostraAlerta( "Intervalo Z inválido." ); 
        }
       
        funcObj3D.setPintarVertices( ui.isPintarVertices() );
        funcObj3D.setDesenharFaces( ui.isDesenharFaces() );
        funcObj3D.setPintarFaces( ui.isPintarFaces() ); 
        
        aplic.getPlanoCartesiano().constroi( aplic );
        aplic.getDesenhoUI().repaint();
    }

    @Override
    public void mostrarVerticesCBAlterado(GaussianaUI ui) {
        funcObj3D.setPintarVertices( ui.isPintarVertices() );            
        aplic.getDesenhoUI().repaint();        
    }

    @Override
    public void mostrarArestasCBAlterado(GaussianaUI ui) {
        funcObj3D.setDesenharFaces( ui.isDesenharFaces() );            
        aplic.getDesenhoUI().repaint();        
    }

    @Override
    public void mostrarFacesCBAlterado(GaussianaUI ui) {
        funcObj3D.setPintarFaces( ui.isPintarFaces() );             
        aplic.getDesenhoUI().repaint();        
    }
    
}
