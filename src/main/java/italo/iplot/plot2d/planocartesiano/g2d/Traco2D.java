package italo.iplot.plot2d.planocartesiano.g2d;

public class Traco2D {
        
    public final static int TRAZ = 1;
    public final static int FRENTE = 2;    
    public final static int CIMA = 3;
    public final static int BAIXO = 4;
    
    private double x;
    private double y;    
    private double valor;
    private int tipo = TRAZ;
    
    private PlanoCartesianoObjeto2D plano;

    public Traco2D( PlanoCartesianoObjeto2D plano, double x, double y, double valor, int tipo ) {
        this.x = x;
        this.y = y;
        this.valor = valor;
        this.tipo = tipo;
        this.plano = plano;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public PlanoCartesianoObjeto2D getPlano() {
        return plano;
    }

    public void setPlano(PlanoCartesianoObjeto2D plano) {
        this.plano = plano;
    }
    
}
