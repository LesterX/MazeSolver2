/**
 * Node
 * @author Yimin Xu 250876566 
 *
 */

public class Node 
{
	int name;
	boolean mark;
	
	public Node(int newName)
	{
		name = newName;
		mark = false;
	}
	
	public void setMark(boolean newMark)
	{
		mark = newMark;
	}
	
	public boolean getMark()
	{
		return mark;
	}
	
	public int getName()
	{
		return name;
	}
}
