package italo.iplot.gui;

public class TelaImpl implements Tela {
    
    private int x;
    private int y;    
    private int largura;
    private int altura;
    
    public TelaImpl(int largura, int altura) {
        this( 0, 0, largura, altura );
    }
    
    public TelaImpl(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public int getTelaLargura() {
        return largura;
    }

    @Override
    public int getTelaAltura() {
        return altura;
    }

    public int getTelaX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getTelaY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
