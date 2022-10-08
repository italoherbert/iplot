package italo.iplot.plot3d.planocartesiano.g3d;

import italo.iplot.plot3d.planocartesiano.objgrafico.TracoReguaObj3DGrafico;
import italo.iplot.plot3d.g3d.LinhaObjeto3D;
import italo.iplot.plot3d.g3d.Objeto3DTO;
import italo.iplot.plot3d.g3d.Vertice3D;

public class TracoRequaPlanoCartesianoObjeto3D extends LinhaObjeto3D {
    
    private double valor = 0;
    private final ReguaPlanoCartesianoObjeto3D reguaObj;
    private final Vertice3D rotuloVertice; 
    
    public TracoRequaPlanoCartesianoObjeto3D( ReguaPlanoCartesianoObjeto3D reguaObj, double valor, double[] p1, double[] p2, double[] rotuloV ) {
        super( p1, p2 );        
        this.reguaObj = reguaObj;
        this.valor = valor;
        this.rotuloVertice = new Vertice3D( rotuloV ); 
        
        super.cortavel = false;
        super.grafico = new TracoReguaObj3DGrafico();
    }
    
    @Override
    public void constroiObjeto3D( Objeto3DTO to ) {
        super.constroiObjeto3D( to ); 
        super.getEstrutura().addVertice( rotuloVertice ); 
    }
    
    public Vertice3D getRotuloVertice() {
        return rotuloVertice;
    }

    public ReguaPlanoCartesianoObjeto3D getReguaObj() {
        return reguaObj;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
}
