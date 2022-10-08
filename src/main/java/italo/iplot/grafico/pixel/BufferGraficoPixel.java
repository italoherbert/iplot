package italo.iplot.grafico.pixel;

import italo.iplot.gui.grafico.GraficoPixel;

public class BufferGraficoPixel implements GraficoPixel {

    public final static int D = 1;
    
    private int[][] coresMap = null;                        
    
    public void criaBuffer( int larg, int alt ) {
        if ( coresMap == null ) {
            coresMap = new int[ alt ][ larg ];
        } else if ( coresMap.length > 0 ) {
            if ( alt > coresMap.length || larg > coresMap[0].length )
                coresMap = new int[ alt ][ larg ];
        }
    }
    
    public void iniciaBuffer( int rgb ) {
        for( int i = 0; i < coresMap.length; i++ )
            for( int j = 0; j < coresMap[i].length; j++ )
                coresMap[i][j] = rgb;
    }
    
    public void carregaBuffer( GraficoPixel gpx ) {
        this.criaBuffer( gpx.getLarg(), gpx.getAlt() ); 
        
        for( int i = 0; i < gpx.getAlt(); i++ )
            for( int j = 0; j < gpx.getLarg(); j++ )
                coresMap[i][j] = gpx.getRGB( j, i );
    }
    
    @Override
    public void pintaPixel(int x, int y, int rgb) {
        coresMap[ y ][ x ] = rgb;
    }

    @Override
    public int getRGB(int x, int y) {
        if ( y >= coresMap.length )
            System.out.println( coresMap.length+"  "+coresMap[0].length );
        return coresMap[ y ][ x ];
    }

    @Override
    public int getLarg() {
        return coresMap[0].length;
    }

    @Override
    public int getAlt() {
        return coresMap.length;
    }
    
}
