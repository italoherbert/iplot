package italo.iplot.plot3d.g3d;

public class Grelha {
    
    public void constroiFaces( GrelhaObjeto3D grelha, Objeto3DTO to ) {
        int xndivs = grelha.getXNDivs();
        int zndivs = grelha.getZNDivs();
        for( int i = 1; i <= xndivs; i++ ) {
            for( int j = 1; j <= zndivs; j++ ) {
                int k1 = ( (i-1) * (zndivs+1) ) + (j-1);
                int k2 = ( (i-1) * (zndivs+1) ) + (j  );
                int k3 = ( (i  ) * (zndivs+1) ) + (j  );           
                int k4 = ( (i  ) * (zndivs+1) ) + (j-1);
                    
                Face3D face1 = new Face3D();
                face1.addVertice( grelha.getVertices().get( k1 ) );
                face1.addVertice( grelha.getVertices().get( k2 ) );
                face1.addVertice( grelha.getVertices().get( k3 ) );
                face1.addVertice( grelha.getVertices().get( k4 ) );
                
                Face3D face2 = new Face3D();
                face2.setInverterVN( true ); 
                face2.addVertice( grelha.getVertices().get( k1 ) );
                face2.addVertice( grelha.getVertices().get( k2 ) );
                face2.addVertice( grelha.getVertices().get( k3 ) );
                face2.addVertice( grelha.getVertices().get( k4 ) );
                
                grelha.addFace( face1, to ); 
                grelha.addFace( face2, to ); 
            }
        }               
    }
    
}
