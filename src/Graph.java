/**
 * Graph
 * @author Yimin Xu 250876566
 *
 */

import java.util.*;

public class Graph implements GraphADT
{
	Edge[][] adjMatrix;
	Node[] nodeList;
	
	//Constructor
	public Graph(int numOfNodes)
	{
		adjMatrix = new Edge[numOfNodes][numOfNodes];
		nodeList = new Node[numOfNodes];
		
		for (int i = 0; i < numOfNodes; i ++)
			nodeList[i] = new Node(i);
	}
	
	//Return the node with the name 
	public Node getNode(int name) throws GraphException
	{
		if (name >= nodeList.length || nodeList[name] == null)
			throw new GraphException("Node not found.");
		
		return nodeList[name];
	}
	
	//Insert the edge connecting u,v 
	public void insertEdge(Node u, Node v, int edgeType) throws GraphException
	{
		if (u.getName() >= nodeList.length || v.getName() >= nodeList.length ||
				nodeList[u.getName()] == null || nodeList[v.getName()] == null
				|| adjMatrix[u.getName()][v.getName()] != null)
			throw new GraphException("Cannot insert the edge.");
		
		adjMatrix[u.getName()][v.getName()] = new Edge(u,v,edgeType); 
		adjMatrix[v.getName()][u.getName()] = new Edge(v,u,edgeType);
	}
	
	//Return the edges incident on node u
	public Iterator<Edge> incidentEdges(Node u) throws GraphException
	{
		if (u.getName() >= nodeList.length)
			throw new GraphException("Node not found.");
		
		Stack<Edge> edgeStack = new Stack<Edge>();
		for (int i = 0; i < nodeList.length; i ++)
		{
			if (adjMatrix[u.getName()][i] != null)
			{
				edgeStack.push(adjMatrix[u.getName()][i]);
			}
		}
		
		Iterator<Edge> iter = edgeStack.iterator();
		return iter;
	}
	
	//Return the edge connecting u and v if there is no such edge, throw an exception
	public Edge getEdge(Node u,Node v) throws GraphException
	{
		if (u.getName() >= nodeList.length || v.getName() >= nodeList.length 
				|| nodeList[u.getName()] == null || nodeList[v.getName()] == null|| 
				adjMatrix[u.getName()][v.getName()] == null)
			throw new GraphException("Edge not found.");
		
		return adjMatrix[u.getName()][v.getName()];
	}
	
	//Return whether u and v are connected
	public boolean areAdjacent(Node u, Node v) throws GraphException
	{
		if (u.getName() >= nodeList.length || v.getName() >= nodeList.length ||
				nodeList[u.getName()] == null || nodeList[v.getName()] == null)
			throw new GraphException("Node not found.");
		
		if (adjMatrix[u.getName()][v.getName()] == null)
			return false;
		else
			return true;
	}
}
