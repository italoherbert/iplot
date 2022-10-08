package italo.iplot.plot2d.g2d;

public class VerticeObjeto2DFactory {        
    
    private int poligonoRegularQuantLados = 8;

    public Objeto2D novo( Objeto2D obj ) {
        int tipo = obj.getVerticeObjTipo();
        VerticeRaio2D vraio2D = obj.getVerticeRaio2D();
        
        Objeto2D vobj = null;
        switch( tipo ) {
            case Objeto2D.VPOLIGONO_REGULAR_OBJ: 
                vobj = new PoligonoRegularObjeto2D();  
                ((PoligonoRegularObjeto2D)vobj).setCor( obj.getVerticesCor() ); 
                ((PoligonoRegularObjeto2D)vobj).setQuantLados(poligonoRegularQuantLados ); 
                if ( vraio2D != null )
                    ((PoligonoRegularObjeto2D)vobj).setRaio2D( new Raio2DAdapter( vraio2D ) );                  
                break;
        }
        return vobj;
    }

    public int getPoligonoRegularQuantLados() {
        return poligonoRegularQuantLados;
    }

    public void setPoligonoRegularQuantLados(int poligonoRegularQuantLados) {
        this.poligonoRegularQuantLados = poligonoRegularQuantLados;
    }
    
}
