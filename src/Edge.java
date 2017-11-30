/**
 * Edge
 * @author Yimin Xu 250876566
 *
 */

public class Edge 
{
	int type;
	String label;
	Node end1;
	Node end2;
	
	public Edge(Node u, Node v, int newType)
	{
		end1 = u;
		end2 = v;
		type = newType;
		label = "";
	}
	
	public Node firstEndpoint()
	{
		return end1;
	}
	
	public Node secondEndpoint()
	{
		return end2;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setLabel(String newLabel)
	{
		label = newLabel;
	}
	
	public String getLabel()
	{
		return label;
	}
}
