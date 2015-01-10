import utils.java.graph.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class GraphCloner {
	public static GraphNode cloneGraph(GraphNode node,Set<GraphNode> visitedSet) {
		if(visitedSet.contains(node)) return null;	
		visitedSet.add(node);	
		GraphNode cloneNode = new GraphNode();
		for(GraphNode childNode : node) {
			GraphNode clonedChild = cloneGraph(childNode, visitedSet);
			if(clonedChild != null) {
				cloneNode.addChild(clonedChild);
			}	
		}
		return cloneNode;
	}
}
