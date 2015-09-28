package graphProject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class GraphUtilities {
	
	public static int heapSize=0;
	public static int [] heapIndex;
	public static void denseGraphGenerator(Graph graph, int vertexMaxDegree)
	{
		
		graph.setMaxDegree(vertexMaxDegree);
		
		int totalVertices=graph.getTotalVertices();
		
		Random rn = new Random();  
		int randomWeight=0;
		
		int firstVertex=0,randomSecondVertex=0;
		
		for( int i=0;i<totalVertices-1;i++)
		{	
			firstVertex=i; 
			innerLoop: for( int j=i+1; j<totalVertices;j++)
			{
				if(graph.getNodeDegreeCount(firstVertex)==vertexMaxDegree)
				break innerLoop;
				//Choosing a random second vertex
				randomSecondVertex=rn.nextInt(totalVertices-j)+j;   

				if(graph.getNodeDegreeCount(randomSecondVertex)<vertexMaxDegree)
				{
					randomWeight=rn.nextInt(20)+2;  //Choosing a random value for edge weight
					graph.addEgde(firstVertex,randomSecondVertex, randomWeight);
				}
				
				
				
			} //End of Inner For Loop
		
		}	
				
	}
	
	public static void sparseGraphGenerator(Graph graph, int vertexMaxDegree)
	{
		graph.setMaxDegree(vertexMaxDegree);

		int totalVertices=graph.getTotalVertices();
		
		//Adding all the nodes in the unvisited List
		Random rn = new Random();  //rn.nextInt(maxAge-minAge) + minAge
		int randomWeight=0;

		
		//Start filling the vertices
		int randomFirstVertex=0,randomSecondVertex=0;
		int breakcounter=0;
		boolean reset=false;
		outerLoop: for( int i=0;i<totalVertices;i++)
		{	
			breakcounter=0;
			randomFirstVertex=i; 
			while(graph.getNodeDegreeCount(randomFirstVertex)<6)
			{	breakcounter++; 
				randomSecondVertex=rn.nextInt(totalVertices);
				if(graph.getNodeDegreeCount(randomSecondVertex)<vertexMaxDegree && randomFirstVertex!=randomSecondVertex)
				{
					randomWeight=rn.nextInt(20)+2;
					graph.addEgde(randomFirstVertex,randomSecondVertex, randomWeight);
				}
			
				if(breakcounter>10000)  // A likely scope for racing condition 
				{reset=true; break outerLoop;}
				
				
			}
			
		
		
		}
		
		if(reset)
		{
			graph.clear();    //Resetting the graph
			sparseGraphGenerator(graph,6);   //calling the method again
		}
		
		
		
		
	}
	
	public static void createPath(Graph graph, int source, int destination)
	{
		//Remove the Degree Count restriction from the Graph
		graph.setMaxDegree(Integer.MAX_VALUE);
		List<Integer> unVisitedNodes=new ArrayList<Integer>();
		List<Integer> visitedNodes=new ArrayList<Integer>();
		
		//Adding all the nodes in the unvisited List
		
		int first=0, last=0;
		for(int i=0; i<graph.getTotalVertices();i++)
		{	
			if(i!=source && i!=destination)
			unVisitedNodes.add(i);
		}
				
		Random rn = new Random();  
		int randomUnvisitedVertex=0, randomVisitedVertex=0, randomWeight=0;
		while (unVisitedNodes.size()!=0)
		{
					
			//Choosing Random Vertices from Unvisited Nodes
			randomVisitedVertex = visitedNodes.size()==0 ? -1 : randomUnvisitedVertex;	
			randomUnvisitedVertex = unVisitedNodes.get(rn.nextInt(unVisitedNodes.size()));
					
			if(randomVisitedVertex!=-1)
			{	
				randomWeight=rn.nextInt(20)+2;
				graph.addEgde(randomVisitedVertex,randomUnvisitedVertex, randomWeight);
				visitedNodes.add((Integer)randomUnvisitedVertex);
				unVisitedNodes.remove((Integer)randomUnvisitedVertex);
				
			}
			else if (randomVisitedVertex == -1)
			{	
				first=randomUnvisitedVertex;
				visitedNodes.add(randomUnvisitedVertex); 
				unVisitedNodes.remove((Integer)randomUnvisitedVertex);
						
			}

		}
		
		last=randomUnvisitedVertex;
		randomWeight=rn.nextInt(20)+2;
		graph.addEgde(source,first, randomWeight);
		
		randomWeight=rn.nextInt(20)+2;
		graph.addEgde(destination,last, randomWeight);
		
		
	}

	public static void dijkstraMCPath(Graph graph, int source, int destination) throws IOException
	{
		
		int totalVertices=graph.getTotalVertices();
		//Vertex validation and Return -1
		if(source<0 || source>totalVertices-1 || destination<0 || destination>totalVertices-1)	
		  return;
		
		//Dijkstra Modification
		String[] status = new String[totalVertices];
		Integer [] distance= new Integer[totalVertices];
		int[] dad = new int[totalVertices];
		
		//Step 1:
		for(int i=0;i<totalVertices;i++)
		{
			status[i]="Unseen";
			distance[i]=Integer.MIN_VALUE;  //Negative Infinity Equivalent for Integer Class -(2^31) 
		}
		
		//Step 2:
		status[source]="inTree";
		dad[source]=-1; distance[source]=0;		
		
		//Step 3:
		int edgeWeight=-1;
		SortedSet<Vertex> fringes = new TreeSet<Vertex>();
		for(int i=0;i<totalVertices;i++)
		{	
			edgeWeight=graph.getEdgeWeight(source, i);
			if(edgeWeight>0) //Process for each adjacent edge
			{
				status[i]="Fringe";
				fringes.add(new Vertex(i,edgeWeight));
				dad[i]=source;
				distance[i]=edgeWeight;
			}
		}
		
		
		//Step 4:
		while(fringes.size()>0)
		{
			Vertex v=fringes.first();
			fringes.remove(v);
			int vNumber=v.getVertexNumber();
			status[vNumber]="inTree";
			//For each edge (v,i), Add i to fringe
			for(int i=0;i<totalVertices;i++)
			{	
				edgeWeight=graph.getEdgeWeight(vNumber, i);
				if(edgeWeight>0) //Process for each adjacent edge
				{	
					if (status[i].equals("Unseen")) {
						status[i]="Fringe";
						distance[i]=Math.min(distance[vNumber], edgeWeight);
						dad[i]=vNumber;
						fringes.add(new Vertex(i,distance[i]));
						
						}
					else if(status[i].equals("Fringe") && distance[i]<Math.min(distance[vNumber], edgeWeight)){
						removeVertexForUpdate(fringes,i);
						distance[i]=Math.min(distance[vNumber],edgeWeight);
						dad[i]=vNumber; 
						fringes.add(new Vertex(i,distance[i]));
					}
						
				}
			}
		}//End of While Loop
		
		
	}
	
	public static void removeVertexForUpdate(SortedSet<Vertex> fringes, int vertexNumber)
	{
		Vertex vertex=null;
		Iterator<Vertex> iterator = fringes.iterator();
	    while(iterator.hasNext()) {
	    	vertex = iterator.next();
	            if(vertex.getVertexNumber() == vertexNumber)             
	            {
	            	fringes.remove(vertex); break;
	            }
	        }
		
	}
	
	public static void kruskalMCPath(Graph graph, int source, int destination) throws IOException
	{
		int totalVertices=graph.getTotalVertices();
		
		Edge[] edgeList = new Edge[graph.getEdgeList().size()];
		graph.getEdgeList().toArray(edgeList);
		edgeHeapSort(edgeList);
		
		int[] dad = new int[totalVertices];
		int[] rank = new int[totalVertices];
		
		for(int i=0;i<totalVertices;i++)
		{
			dad[i]=i;  
			rank[i]=0;
		}
		
		List<Edge> maxSpanningTree=new ArrayList<Edge>();
		int u,v,r1,r2;
		Edge edge;
		
		for(int i=0;i<edgeList.length;i++)
		{
			edge=edgeList[i];
			u=edge.getFirstVertex();
			v=edge.getSecondVertex();
			r1=find(u,dad);
			r2=find(v,dad);
			if(r1!=r2)
			{
				maxSpanningTree.add(edge);
				Union(r1,r2,dad,rank);
				if(maxSpanningTree.size()==totalVertices-1)
					break;
			}
		}
		

	}
	
	public static int find(int vertex, int[] dad)
	{
	    while (vertex != dad[vertex]) {
            dad[vertex] = dad[dad[vertex]];    // path compression by halving
            vertex = dad[vertex];
        }
        return vertex;
		
 	}
	
	public static void Union(int r1, int r2,int[] dad, int[] rank)
	{
		if(rank[r1]>rank[r2])
			dad[r1]=r2;
		else if(rank[r1]<rank[r2])
			dad[r2]=r1;
		else
		{
			dad[r1]=r2;
			rank[r1]=rank[r1]+1;
		}
	}
	
	public static void edgeHeapSort(Edge[] edgeList)
	{	 
		  edgeHeapify(edgeList);        
	        for (int i = heapSize; i > 0; i--)
	        {
	            edgeSwap(edgeList,0, i);
	            heapSize = heapSize-1;
	            edgeMaxHeap(edgeList, 0);
	        }
	}
 
    /* Function to build a heap */   
    public static void edgeHeapify(Edge[] edgeList)
    {
        heapSize = edgeList.length-1;
        for (int i = heapSize/2; i >= 0; i--)
            edgeMaxHeap(edgeList, i);        
    }

    /* Function to swap largest element in heap */        
    public static void edgeMaxHeap(Edge[] edgeList, int i)
    { 
    	int left = 2*i ;
        int right = 2*i + 1;
        int max = i;
        if (left <= heapSize && edgeList[left].getEdgeWeight() < edgeList[i].getEdgeWeight())
            max = left;
        if (right <= heapSize && edgeList[right].getEdgeWeight() < edgeList[max].getEdgeWeight() )        
            max = right;
 
        if (max != i)
        {
            edgeSwap(edgeList, i, max);
            edgeMaxHeap(edgeList, max);
        }
    }    

    /* Function to swap two numbers in an array */
    public static void edgeSwap(Edge[] edgeList, int i, int j)
    {

        Edge tmp = edgeList[i];
        edgeList[i] = edgeList[j]; 
        edgeList[j] = tmp;
    }
	
    public static void dijkstraMCPathUsingHeap(Graph graph, int source, int destination)  {
    
    	int totalVertices=graph.getTotalVertices();
		//Vertex validation and Return -1
		if(source<0 || source>totalVertices-1 || destination<0 || destination>totalVertices-1)	
		  return;
		
		//Dijkstra Modification
		String[] status = new String[totalVertices];
		int [] distance= new int[totalVertices];
		int[] dad = new int[totalVertices];
		heapIndex = new int[totalVertices];
		int i, vNumber,edgeWeight;
		
    	Vertex t=null;
    	Vertex[] heapArray=new Vertex[totalVertices];
    	
    	
		for(i=0;i<totalVertices;i++)
		{
			status[i]="Unseen";
		}   	
    	
    	status[source] = "inTree";
    	distance[source] = 0;
    	dad[source] = 0;
    	
    	
    	for(i=0; i<totalVertices; i++) {
    		edgeWeight=graph.getEdgeWeight(source, i);
    		if(edgeWeight != 0) {
    			status[i] = "Fringe";
    			distance[i] = edgeWeight;
    			dad[i] = source;
				heapIndex[i] = insertMaxHeap(heapArray, new Vertex(i,distance[i]));
    		} 	
    	}
    	while(heapSize != 0) {
    		t = heapArray[0]; // Max capacity: t is the vertex with max t.value
    		vNumber = t.getVertexNumber();
    		deleteMaxHeap(heapArray, heapIndex[vNumber]);
    		status[vNumber] = "inTree";
    		for(i=0; i<totalVertices; i++) {
    			edgeWeight=graph.getEdgeWeight(vNumber, i);
    			if(edgeWeight > 0) {
    				if(status[i].equals("Unseen")){
    					status[i] = "Fringe";
    					distance[i] = Math.min(edgeWeight, distance[vNumber]);
    					dad[i] = vNumber;
    					heapIndex[i] = insertMaxHeap(heapArray, new Vertex(i,distance[i]));
    				}
    				else if(status[i].equals("Fringe") && distance[i] < Math.min(distance[vNumber], edgeWeight)) {
    					deleteMaxHeap(heapArray, heapIndex[i]);
    					distance[i] = Math.min(distance[vNumber], edgeWeight);
    					dad[i] = vNumber;
    					heapIndex[i] = insertMaxHeap(heapArray, new Vertex(i,distance[i]));
    				}
    			}
    		}	
    	}
    
    	
    
    }
	
	public static void printMCPath(int[] dad, int source, int destination)
	{
		//Traversing Path
    	String maxCapacityPath="";
    	maxCapacityPath+=destination;
    	while(destination != source)
    	{
    		destination=dad[destination];
    		maxCapacityPath=destination +"-->"  +  maxCapacityPath;
    	}
    	
    	System.out.println(maxCapacityPath);
	}
	
    public static int insertMaxHeap(Vertex[] array, Vertex x) {  
    	heapSize=heapSize+1;
    	int insertIndex = heapSize-1;
    	array[insertIndex] = x;
    	int t; 
    	Vertex temp;
    	if(insertIndex%2 == 0)
    		t = insertIndex/2 - 1;
    	else
    		t = insertIndex/2;
    	while(insertIndex > 0 && array[insertIndex].getDistance() > array[t].getDistance()) {  
    		temp = array[insertIndex];  
    		array[insertIndex] = array[t];
    		array[t] = temp;
    		heapIndex[array[t].getVertexNumber()]=insertIndex;
    		insertIndex = t;
    		if(insertIndex%2 == 0)
    			t = insertIndex/2 - 1;
    		else
    			t = insertIndex/2;
    	}	
    	return insertIndex; 
    }

    static void deleteMaxHeap(Vertex[] array, int x) { 
    	array[x] = array[heapSize-1];
    	heapIndex[array[heapSize-1].getVertexNumber()]=x;
		heapSize=heapSize-1;
    	maxHeapify(array, x);
    }

    static void maxHeapify(Vertex[] array, int i) {
    	int l,r,largest;
    	Vertex temp;

    	l = 2*i + 1;
    	r = 2*i + 2;
    	if(l < heapSize && array[l].getDistance() > array[i].getDistance())
    		largest = l;
    	else
    		largest = i;
    	if(r < heapSize && array[r].getDistance() > array[largest].getDistance())
    		largest = r;
    	if(largest != i) {
    		temp = array[largest];
    		array[largest] = array[i];
    		array[i] = temp;
    		heapIndex[array[i].getVertexNumber()]=i;
    		heapIndex[array[largest].getVertexNumber()]=largest;
    		maxHeapify(array, largest);
    	}
    }
    
    
    
    
	
}
