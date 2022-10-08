package italo.iplot.plot2d.g2d;

import italo.iplot.plot2d.g2d.util.corte.CorteContainerObjeto2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Objeto2D {
    
    public final static int VCIRCULO = 1;
    public final static int VPOLIGONO_REGULAR_OBJ = 2;
    
    protected Objeto2D grupo = null;
    protected Objeto2DGrafico grafico = null;
    protected boolean executarGraficoAntesDePintar = false;
    
    protected boolean visivelValor = true;
    protected boolean pintarFaces = true;
    protected boolean pintarArestas = true;
    protected boolean pintarVertices = false; 
    
    protected boolean construido = false;
    protected boolean construidoNoObjetoGrupo = false;
    
    protected boolean verticesObjetosConstruidos = false;
                    
    protected Objeto2DVisivel obj2DVisivel = null;
    protected int verticeObjTipo = VCIRCULO;
    protected VerticeRaio2D verticeRaio2D = null;
        
    protected Color cor = new Color( 50, 250, 150 );    
    protected Color verticesCor = Color.BLUE;    
    protected Color arestasCor = Color.WHITE;
                  
    protected final Estrutura2D estrutura = new Estrutura2D( this );
    protected final List<Objeto2D> objetosFilhos = new ArrayList();
    protected List<Objeto2D> verticesObjetos = null;
    
    private ConstroiObj2DListener constroiObj2DListener = null;
    private final List<ConstroiObj2DListener> constroiObj2DListeners = new ArrayList(); 
    private final List<ConstroiObj2DListener> somenteUmaConstroiObj2DListeners = new ArrayList();

    public abstract void constroiObjeto2D( Objeto2DTO to );
    
    public void antesDeDesenhar() {}
    
    public void constroi( Objeto2DTO to ) {
        construido = false;
        
        if ( constroiObj2DListener != null )
            constroiObj2DListener.construindo( this, to ); 
        
        constroiObj2DListeners.forEach( l -> l.construindo( this, to ) ); 
        somenteUmaConstroiObj2DListeners.forEach( l -> l.construindo( this, to ) );
        
        estrutura.reinicia();
        
        this.constroiObjeto2D( to ); 
        
        verticesObjetosConstruidos = false;
        if ( this.isObjVertices() ) {
            if ( verticesObjetos == null ) {
                verticesObjetos = new ArrayList();
            } else {
                for( Objeto2D vobj : verticesObjetos )
                    this.removeObjeto( vobj );
                verticesObjetos.clear();                
            }
            
        for( Vertice2D v : estrutura.getVertices() ) {
                double x = v.getX();
                double y = v.getY();

                Objeto2D vobj = to.getVObj2DFactory().novo( this );
                if ( vobj != null ) {                        
                    vobj.constroi( to ); 
                    vobj.setObj2DVisivel( new VerticeObjeto2DVisivel( this ) );
                    vobj.setConstruidoNoObjetoGrupo( true );

                    to.getTransformador2D().translada( vobj, x, y, to.getInicialFiltroV2D() );             
                    to.getTransformador2D().translada( vobj, x, y, to.getXYFiltroV2D() );             
                    vobj.aplicaTransformacoes();

                    verticesObjetos.add( vobj );
                    this.addObjeto( vobj ); 
                }
            }
            verticesObjetosConstruidos = true;            
        }
            
        objetosFilhos.forEach(obj -> {
            if ( !obj.isConstruidoNoObjetoGrupo() )
                obj.constroi( to ); 
        } );                 
                      
        construido = true;
                        
        if ( constroiObj2DListener != null )
            constroiObj2DListener.construiu( this, to ); 
            
        constroiObj2DListeners.forEach( l -> l.construiu( this, to ) );                
        
        somenteUmaConstroiObj2DListeners.forEach( l -> l.construiu( this, to ) );               
        somenteUmaConstroiObj2DListeners.clear();        
    }
    
    public boolean construido() {
        if ( !construido )
            return false;
        
        for( Objeto2D obj : objetosFilhos )
            if ( !obj.construido() )
                return false;
        
        return true;
    }
            
    public void reiniciaVertices() {
        objetosFilhos.forEach( o -> o.reiniciaVertices() );
        estrutura.getVertices().forEach( v -> v.copiaP0ParaP() ); 
    }
    
    public void aplicaTransformacoes() {       
        objetosFilhos.forEach( o -> o.aplicaTransformacoes() );         
        estrutura.getVertices().forEach( v -> v.copiaParaVisao() );         
    }
    
    public void corta( CorteContainerObjeto2D container, Objeto2DTO to ) {
        to.getCortador2D().corta( container, estrutura, to.getXYFiltroV2D() );
        objetosFilhos.forEach( o -> o.corta( container, to ) );         
    }
    
    public boolean isObjVertices() {
        return verticeObjTipo != VCIRCULO;
    }
    
    public boolean isVisivel() {
        return visivelValor && ( obj2DVisivel == null ? true : obj2DVisivel.isVisivel() );            
    }         
        
    public void addObjeto(Objeto2D obj) {
        obj.setGrupo( this );
        objetosFilhos.add( obj );      
    }       
    
    public void removeObjeto( Objeto2D obj ) {
        obj.setGrupo( null );
        objetosFilhos.remove( obj );
    }
    
    public void removeTodosOsObjetos() {
        objetosFilhos.forEach( obj -> obj.setGrupo( null ) );
        objetosFilhos.clear();        
    }
    
    public ConstroiObj2DListener getConstroiObj3DListener() {
        return constroiObj2DListener;
    }

    public void setConstroiObj2DListener(ConstroiObj2DListener constroiObj2DListener) {
        this.constroiObj2DListener = constroiObj2DListener;
    }
        
    public boolean addConstroiObj2DListener(ConstroiObj2DListener listener) {
        return this.constroiObj2DListeners.add( listener );
    }
    
    public boolean removeConstroiObj2DListener( ConstroiObj2DListener listener ) {
        return constroiObj2DListeners.remove( listener );
    }
    
    public boolean addSomenteUmaConstroiObj2DListener( ConstroiObj2DListener listener ) {
        return somenteUmaConstroiObj2DListeners.add( listener );        
    }

    public boolean isConstruido() {
        return construido;
    }

    public List<Objeto2D> getObjetos() {
        return objetosFilhos;
    }

    public List<Objeto2D> getVerticesObjetos() {
        return verticesObjetos;
    }
        
    public Objeto2D getGrupo() {
        return grupo;
    }

    public void setGrupo(Objeto2D grupo) {
        this.grupo = grupo;
    }

    public boolean isPintarVertices() {
        return pintarVertices;
    }

    public void setPintarVertices(boolean pintarVertices) {
        this.pintarVertices = pintarVertices;
    }

    public Color getVerticesCor() {
        return verticesCor;
    }

    public void setVerticesCor(Color verticesCor) {
        this.verticesCor = verticesCor;
    }

    public boolean isPintarFaces() {
        return pintarFaces;
    }

    public void setPintarFaces(boolean pintarFaces) {
        this.pintarFaces = pintarFaces;
    }

    public boolean isPintarArestas() {
        return pintarArestas;
    }

    public void setPintarArestas(boolean pintarArestas) {
        this.pintarArestas = pintarArestas;
    }
    
    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public Color getArestasCor() {
        return arestasCor;
    }

    public void setArestasCor(Color arestasCor) {
        this.arestasCor = arestasCor;
    }

    public Objeto2DVisivel getObj2DVisivel() {
        return obj2DVisivel;
    }

    public void setObj2DVisivel(Objeto2DVisivel obj2DVisivel) {
        this.obj2DVisivel = obj2DVisivel;
    }

    public int getVerticeObjTipo() {
        return verticeObjTipo;
    }

    public void setVerticeObjTipo(int verticeObjTipo) {
        this.verticeObjTipo = verticeObjTipo;
    }

    public VerticeRaio2D getVerticeRaio2D() {
        return verticeRaio2D;
    }

    public void setVerticeRaio2D(VerticeRaio2D verticeRaio2D) {
        this.verticeRaio2D = verticeRaio2D;
    }

    public boolean isVerticesObjetosConstruidos() {
        return verticesObjetosConstruidos;
    }

    public void setVerticesObjetosConstruidos(boolean verticesObjetosConstruidos) {
        this.verticesObjetosConstruidos = verticesObjetosConstruidos;
    }
            
    public void setVisivelValor(boolean visivel) {
        this.visivelValor = visivel;
    }  
    
    public Objeto2DGrafico getGrafico() {
        return grafico;
    }

    public void setGrafico(Objeto2DGrafico grafico) {
        this.grafico = grafico;
    }

    public Estrutura2D getEstrutura() {
        return estrutura;
    }

    public boolean isConstruidoNoObjetoGrupo() {
        return construidoNoObjetoGrupo;
    }

    public void setConstruidoNoObjetoGrupo(boolean construidoNoObjetoGrupo) {
        this.construidoNoObjetoGrupo = construidoNoObjetoGrupo;
    }

    public boolean isExecutarGraficoAntesDePintar() {
        return executarGraficoAntesDePintar;
    }

    public void setExecutarGraficoAntesDePintar(boolean executarGraficoAntesDePintar) {
        this.executarGraficoAntesDePintar = executarGraficoAntesDePintar;
    }
    
}
