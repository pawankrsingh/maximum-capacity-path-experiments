package graphProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {
	private int[][] adjMatrix;
	private int[] nodeDegreeCount;
	private int maxDegree=10000000;
	private List<Edge> edgeList;
	
	public Graph(int nodeCount) 
	{	
		//Initializing Adjacency Matrix
		adjMatrix = new int[nodeCount][nodeCount];
		nodeDegreeCount = new int[nodeCount]; 
		edgeList=new ArrayList<Edge>();
	}

	public void setMaxDegree(int degree)
	{
		this.maxDegree=degree;
	}
	
	public int getTotalVertices()
	{
		return this.nodeDegreeCount.length;
	}
	
	public int getNodeDegreeCount(int vIndex)
	{
		if(vIndex>=0 && vIndex<nodeDegreeCount.length)
			return nodeDegreeCount[vIndex];
		return -1;
	}
	
	public List<Edge> getEdgeList()
	{
		return edgeList;
	}
	
	public int getEdgeWeight(int firstVertex, int secondVertex)
	{	
		if(firstVertex<nodeDegreeCount.length && secondVertex<nodeDegreeCount.length)
			return adjMatrix[firstVertex][secondVertex];
			
		return 0;
	}
	
	public boolean addEgde(int i, int j, int w)
	{
		if(i!=j && i<adjMatrix.length && j<adjMatrix[0].length && nodeDegreeCount[i]<maxDegree && nodeDegreeCount[j]<maxDegree)
		{
			
		//if edge already exists
			if(adjMatrix[i][j]>0) return false;
			
		//Else if it is not existing edge, create the edge	
			adjMatrix[i][j]=w;
			adjMatrix[j][i]=w;
			nodeDegreeCount[i]+=1;
			nodeDegreeCount[j]+=1;
			
		//Add entry to EdgeSet	
		edgeList.add(new Edge(i,j,w));
		return true;
		}

	return false;
	}
	
	
	public void clear()
	{	
		//For resetting the Graph
		int nodeCount=nodeDegreeCount.length;

		adjMatrix = null;
		nodeDegreeCount = null; 
		edgeList=null;
		
		adjMatrix = new int[nodeCount][nodeCount];
		nodeDegreeCount = new int[nodeCount]; 
		edgeList=new ArrayList<Edge>();
	}
	
	
	public void printNodeDegreeCount()
	{
		for(int i=0; i<nodeDegreeCount.length;i++)
		{
			System.out.println("degree["+i+"]="+nodeDegreeCount[i]+"  ");
			
		}
		
	}
	
	public int getConnectivityPercent()
	{
		int percent=0;
		for(int i=0; i<nodeDegreeCount.length;i++)
			percent+=nodeDegreeCount[i];	
		
		percent=percent/nodeDegreeCount.length;
		percent=percent*100/nodeDegreeCount.length;
		return percent;
	}
	
	public int gettConnectivityAverage()
	{
		int average=0;
		for(int i=0; i<nodeDegreeCount.length;i++)
			average+=nodeDegreeCount[i];	
		
		average=average/nodeDegreeCount.length;
		return average;
	}
	
	
	
	public void printNodeDegreeCountNonMAX() // To be removed later
	{
		for(int i=0; i<nodeDegreeCount.length;i++)
		{
			if(nodeDegreeCount[i]<this.maxDegree)
			System.out.println("degree["+i+"]="+nodeDegreeCount[i]+"  ");
			
		}
		
	}
	
	public void printAdjList() throws IOException
	{
		for(int i=0; i<adjMatrix.length;i++)
		{
			System.out.print(String.format("%4d", i)+"-->");   
			for(int j=0; j<adjMatrix[i].length;j++)
				{
					if(adjMatrix[i][j]!=0)
					{
						
						System.out.print(String.format("%4d", j)+"("+String.format("%2d",adjMatrix[i][j])+ ")-->"); 
					}
					
				}
			System.out.println();  
		}
	
	}
	
	
	
	public void printAdjMatrix()
	{
		for(int i=0; i<adjMatrix.length;i++)
		{
			for(int j=0; j<adjMatrix[i].length;j++)
				System.out.print(adjMatrix[i][j]+ " ");
			System.out.println();
		}
		
		System.out.println("\n");
	}
	
		
}




