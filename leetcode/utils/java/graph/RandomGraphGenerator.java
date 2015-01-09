package utils.java.graph;
import java.util.Random;
class RandomGraphGenerator implements GraphGenerator {
    
    public GraphNode genGraph(int adjacencyList[][]) {
        GraphNode nodeArray[] = new GraphNode[adjacencyList.length];
        for(int i = 0; i < nodeArray.length; i++) {
            nodeArray[i] = new GraphNode();
        } 
        for(int i = 0; i < adjacencyList.length; i++) {
            for( int indx : adjacencyList[i]) {
                if(indx >=0){
                    if(indx >= adjacencyList[i].length) {
                        throw new ArrayIndexOutOfBoundsException(indx);
                    }
                    nodeArray[i].addChild(nodeArray[indx]);
                }
            }
        } 
        return nodeArray[0];
    }

    public GraphNode genGraph(int num) {
        final int max = num -1 ;
        final int min = -1;
        int adjacencyList[][] = new int[num][num]; 
        Random rand = new Random();
        for(int[] node : adjacencyList) {
            for(int i = 0; i < node.length; i++) {
                int randomNum = rand.nextInt((max - min) + 1) + min;     
                node[i] = randomNum;
            }
        }
        /*for( int[] arr : adjacencyList) {
            for( int elem : arr) {
                System.out.println(elem);
            }
        } */
        return genGraph(adjacencyList);
        
    } 
    public static void main(String args[]) {
        GraphGenerator gen = new RandomGraphGenerator(); 
        gen.genGraph(5);
    }
}
