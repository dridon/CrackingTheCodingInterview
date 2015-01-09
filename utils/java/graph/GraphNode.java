package utils.java.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

class GraphNode implements Iterable<GraphNode>, Cloneable {
    private ArrayList<GraphNode> childNodes;

    public GraphNode() {
        childNodes = null;
    }

    public int getNumChildren() {
        if(childNodes == null) {
            return 0;
        }
        return childNodes.size();
    }

    public GraphNode getChild(int i) {
        return childNodes.get(i);
    }

    public void addChild(GraphNode node) {
        if(childNodes == null) {
            childNodes = new ArrayList<GraphNode>();
        }
        childNodes.add(node);
    }
    
    public void removeChild(GraphNode node) {
        childNodes.remove(node);
    }

    @Override
    public Iterator<GraphNode> iterator(){
        return childNodes.iterator();
    }
    
    public GraphNode(Collection<GraphNode> childNodes) {
        this.childNodes = new ArrayList<GraphNode>();
        this.childNodes.addAll(childNodes);
    } 

    @Override
    protected Object clone() {
        GraphNode node =  new GraphNode();
        for(GraphNode child : this) {
            node.addChild((GraphNode)child.clone());
        } 
        return node;
    }
    public static void main(String args[]) {
        GraphNode node = new GraphNode();
        System.out.println(node.getNumChildren());          
    }    
}

