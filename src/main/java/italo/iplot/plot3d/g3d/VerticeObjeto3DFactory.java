package italo.iplot.plot3d.g3d;

public class VerticeObjeto3DFactory {
    
    private int globoNumDivs = 3;
    private int poligonoRegularQuantLados = 8;

    public Objeto3D novo( Objeto3D obj ) {
        int tipo = obj.getVerticeObjTipo();
        VerticeRaio3D vraio3D = obj.getVerticeRaio3D();
        
        Objeto3D vobj = null;
        switch( tipo ) {
            case Objeto3D.VGLOBO_OBJ: 
                vobj = new GloboObjeto3D();  
                ((GloboObjeto3D)vobj).setCor( obj.getVerticesCor() ); 
                ((GloboObjeto3D)vobj).setNumDivs( globoNumDivs ); 
                if ( vraio3D != null )
                    ((GloboObjeto3D)vobj).setRaio3D( new Raio3DAdapter( vraio3D ) );                  
                break;
            case Objeto3D.VPOLIGONO_REGULAR_OBJ: 
                vobj = new PoligonoRegularObjeto3D();  
                ((PoligonoRegularObjeto3D)vobj).setCor( obj.getVerticesCor() ); 
                ((PoligonoRegularObjeto3D)vobj).setQuantLados( poligonoRegularQuantLados ); 
                if ( vraio3D != null )
                    ((PoligonoRegularObjeto3D)vobj).setRaio3D( new Raio3DAdapter( vraio3D ) );                  
                break;
        }
        return vobj;
    }

    public int getGloboNumDivs() {
        return globoNumDivs;
    }

    public void setGloboNumDivs(int globoNumDivs) {
        this.globoNumDivs = globoNumDivs;
    }

    public int getPoligonoRegularQuantLados() {
        return poligonoRegularQuantLados;
    }

    public void setPoligonoRegularQuantLados(int poligonoRegularQuantLados) {
        this.poligonoRegularQuantLados = poligonoRegularQuantLados;
    }

}
