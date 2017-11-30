/**
 * CS 2210
 * @author Yimin Xu 250876566
 * 
 */
import java.io.*;
import java.util.*;

public class RoadMap 
{
	private int scale;
	private int start;
	private int end;
	private int width;
	private int length;
	private int budget;
	private int toll;
	private int gain;
	private Node[] nodeList;
	private Graph mapGraph;
	
	//Constructor
	public RoadMap(String inputFile) throws MapException
	{
		BufferedReader br;
		try 
		{
			//Read the file 
			br = new BufferedReader(new FileReader(inputFile));
			scale = Integer.parseInt(br.readLine());
			start = Integer.parseInt(br.readLine());
			end = Integer.parseInt(br.readLine());
			budget = Integer.parseInt(br.readLine());
			width = Integer.parseInt(br.readLine());
			length = Integer.parseInt(br.readLine());
			toll = Integer.parseInt(br.readLine());
			gain = Integer.parseInt(br.readLine());
			
			String line = br.readLine();
			
			//The total length and the width are 2 times of that of nodes minus 1
			int matrixWidth = width * 2 - 1;
			int matrixLength = length * 2 - 1;

			String[][] map = new String[matrixLength][matrixWidth];


			int lengthCount = 0;
			int widthCount = 0;

			while (lengthCount < matrixLength)
			{	
				widthCount = 0;
				while (widthCount < matrixWidth)
				{
					//List the characters in the file into a matrix
					map[lengthCount][widthCount] = Character.toString(line.charAt(widthCount));
					widthCount ++;
				}
				line = br.readLine();
				lengthCount ++;
			}

			//The number of nodes equals the multiplication of length and width
			mapGraph = new Graph (width * length);
			nodeList = new Node[width * length];

			int nodeNum = 0;
			for (int i = 0; i < matrixLength; i = i + 2)
			{
				for (int j = 0; j < matrixWidth; j = j + 2)
				{
					map[i][j] = Integer.toString(nodeNum);
					nodeList[nodeNum] = new Node(nodeNum);
					nodeNum ++;
				}
			}

			//Build the edges between the nodes
			for (int i = 0; i < matrixLength; i ++)
			{
				if (i % 2 == 0)
				{
					for (int j = 1; j < matrixWidth; j = j + 2)
					{
						int node1 = Integer.parseInt(map[i][j - 1]);
						int node2 = Integer.parseInt(map[i][j + 1]);
						int type;
						if(!map[i][j].equals("X"))
						{
							if (map[i][j].equals("T"))
								type = toll;
							else if (map[i][j].equals("C"))
								type = gain;
							else
								type = 0;
							
							mapGraph.insertEdge(nodeList[node1], nodeList[node2], type);
						}
					}
				}else
				{
					for (int j = 0; j < matrixWidth; j = j + 2)
					{
						int node1 = Integer.parseInt(map[i - 1][j]);
						int node2 = Integer.parseInt(map[i + 1][j]);
						int type;
						if (!map[i][j].equals("X"))
						{
							if (map[i][j].equals("T"))
								type = toll;
							else if (map[i][j].equals("C"))
								type = gain;
							else
								type = 0;

							mapGraph.insertEdge(nodeList[node1],nodeList[node2], type);
						}
					}
				}
			}
		}
		catch (GraphException e)
		{
			throw new MapException("Graph Exception");
		}
		catch(NumberFormatException e)
		{
			throw new MapException("Map Exception");
		} catch (IOException e) 
		{
			throw new MapException("Map Exception");
		}		
	}
	
	//Return the graph
	public Graph getGraph()
	{
		return mapGraph;
	}
	
	//Return the name of the start node
	public int getStartingNode()
	{
		return nodeList[start].getName();
	}
	
	//Return the name of the end node
	public int getDestinationNode()
	{
		return nodeList[end].getName();
	}
	
	//Return the budget available to use
	public int getInitialMoney()
	{
		return budget;
	}
	
	//Return the iterator of the stack in which are the nodes from s to d, used for recursion
	public Iterator<Node> find(Node s, Node d, int m, Stack<Node> nodes, int cost) throws GraphException
	{
		int c = cost;
		s.setMark(true);
		nodes.push(s);
		if (s.getName() == d.getName())
			return nodes.iterator();
			
		Iterator<Edge> iter = mapGraph.incidentEdges(s);
		while (iter.hasNext())
		{
			Edge road = iter.next();
			Node nextNode = road.secondEndpoint();
			if ((c + road.getType() <= m) && !nextNode.getMark())
			{ 
				c = c + road.getType();
				System.out.println(s.getName() + " to " + nextNode.getName() + " now cost " + c + " budget " + m);
				Iterator<Node> result = find(nextNode,d,m,nodes,c);
				if (result != null)
					return result;
				else
						c = c - road.getType();			
			}
		}

		Node popped = nodes.pop();
		popped.setMark(false);
		return null;
	}
	
	//Use the recursive method above to find the path
	public Iterator<Node> findPath(int start, int destination, int initialMoney) throws GraphException
	{
		Node s = nodeList[start];
		Node d = nodeList[destination];
		Stack<Node> nodes = new Stack<Node>();
		return find(s,d,initialMoney,nodes,0);
	}
}

//Problem: if a node is marked but popped out of the stack, how could we visit it again?