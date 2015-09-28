package graphProject;

import java.io.IOException;
import java.util.*;

public class MainProgram {

	public static void main(String[] args) throws IOException {
			
			int source=0, destination=0;
			Graph denseGraph = null;
			Graph sparseGraph = null;
			int vertexMaxDegree=10;
			double startTime=0, endTime=0, duration=0;
			Random rn = new Random();
			
			System.out.println("Processing Started");
			
			
			
			double[][] denseGraphStatistics = new double[25][3];
			double[][] sparseGraphStatistics = new double[25][3];
			int denseRunCounter=0, sparseRunCounter=0;
			
			
			for(int iterationCount=0; iterationCount<5; iterationCount++)
			{
				//In each iteration, we will a pair of Graph : Dense and Sparse and In each iteration we will select 5 pair of (Source,Destination) to test our Algorithms
				
				
				//Dense Graph Creation. Initializing Graph variable
				denseGraph = new Graph(5000);
				vertexMaxDegree=1000;
				denseGraph.setMaxDegree(vertexMaxDegree);  
				
				//Random graph generated
				GraphUtilities.denseGraphGenerator(denseGraph, vertexMaxDegree);
				
				// Creation of 5 source and destination pairs 
				for(int innerIteration=0;innerIteration<5;innerIteration++)
				{
					source = rn.nextInt(5000);
					destination = rn.nextInt(5000);
					
					//Connect the source and vertices with a path connecting the remaining vertices of the graph to make sure the source and destination are connected.
					GraphUtilities.createPath(denseGraph, source, destination);
					
					
					//Testing Dijkstra's Max Capacity Path without Heap on the  randomly generated graph
					startTime = System.nanoTime();
					GraphUtilities.dijkstraMCPath(denseGraph, source, destination);
					endTime = System.nanoTime();
					duration=endTime-startTime;
					duration/=1000000;  //Getting Time in Millisecond
					denseGraphStatistics[denseRunCounter][0]=duration;
					
					System.out.println("Dense Graph Dijkstra's Algorithm (Wihout Heap) Iteration : "+(iterationCount*5+innerIteration+1)+" completed");
					
					//Testing Dijkstra's Max Capacity Path using Heap on the  randomly generated graph
					startTime = System.nanoTime();
					GraphUtilities.dijkstraMCPathUsingHeap(denseGraph, source, destination);
					endTime = System.nanoTime();
					duration=endTime-startTime;
					duration/=1000000;  //Getting Time in Millisecond
					denseGraphStatistics[denseRunCounter][1]=duration;
					
					System.out.println("Dense Graph Dijkstra's Algorithm (Heap) Iteration : "+(iterationCount*5+innerIteration+1)+" completed");
					
					//Testing Kruskal' Max Capacity Path on the  randomly generated graph
					startTime = System.nanoTime();
					GraphUtilities.kruskalMCPath(denseGraph, source, destination);
					endTime = System.nanoTime();
					duration=endTime-startTime;
					duration/=1000000;  //Getting Time in Millisecond
					denseGraphStatistics[denseRunCounter][2]=duration;
					
					System.out.println("Dense Graph Kruskal's Algorithm Iteration : "+(iterationCount*5+innerIteration+1)+" completed");
					
					denseRunCounter++;
					System.out.println();
				}
				
				
				
				//Sparse Graph Creation
				sparseGraph = new Graph(5000);
				vertexMaxDegree=6;
				sparseGraph.setMaxDegree(vertexMaxDegree);  
				
				//Random Sparse graph generated
				GraphUtilities.sparseGraphGenerator(sparseGraph, vertexMaxDegree);
				
				// Creation of 5 source and destination pairs 
				for(int innerIteration=0;innerIteration<5;innerIteration++)
				{
					source = rn.nextInt(5000);
					destination = rn.nextInt(5000);
					
					//Connect the source and vertices with a path connecting the remaining vertices of the graph.
					GraphUtilities.createPath(sparseGraph, source, destination);
					
					
					//Testing Dijkstra's Max Capacity Path without Heap on the  randomly generated graph
					startTime = System.nanoTime();
					GraphUtilities.dijkstraMCPath(sparseGraph, source, destination);
					endTime = System.nanoTime();
					duration=endTime-startTime;
					duration/=1000000;  //Getting Time in Millisecond
					sparseGraphStatistics[sparseRunCounter][0]=duration;
					
					System.out.println("Sparse Graph Dijkstra's Algorithm (Wihout Heap) Iteration : "+(iterationCount*5+innerIteration+1)+" completed");
					
					//Testing Dijkstra's Max Capacity Path using Heap on the  randomly generated graph
					startTime = System.nanoTime();
					GraphUtilities.dijkstraMCPathUsingHeap(sparseGraph, source, destination);
					endTime = System.nanoTime();
					duration=endTime-startTime;
					duration/=1000000;  //Getting Time in Millisecond
					sparseGraphStatistics[sparseRunCounter][1]=duration;
					
					System.out.println("Sparse Graph Dijkstra's Algorithm (Heap) Iteration : "+(iterationCount*5+innerIteration+1)+" completed");
					
					//Testing Kruskal's Max Capacity Path on the  randomly generated graph
					startTime = System.nanoTime();
					GraphUtilities.kruskalMCPath(sparseGraph, source, destination);
					endTime = System.nanoTime();
					duration=endTime-startTime;
					duration/=1000000;  //Getting Time in Millisecond
					sparseGraphStatistics[sparseRunCounter][2]=duration;
					
					System.out.println("Sparse Graph Kruskal's Algorithm Iteration : "+(iterationCount*5+innerIteration+1)+" completed");
					
					sparseRunCounter++;
					System.out.println();
				}
				
				
				
				
			}
			
			
			
			
			//Printing the Dense Graph Statistics
			System.out.println("\n\n                          Max Capacity Path running time statistics for Dense Graph");
			System.out.println("	      |  Dijkstra (Without Heap) Running Time |   Dijkstra (Using Heap) Running Time    |     Kruskal Running Time        |");
			double[] averageRunningTime= new double[3];
			double[] maxRunningTime = new double[3];
			for(int i=0;i<denseGraphStatistics.length;i++)
			{
				
				
				 System.out.println("	      |------------------------------------------------------------------------------------------------------------------|");
				 System.out.println(String.format("Iteration: %2d |	     %10.5f milliseconds         |	     %10.5f milliseconds           |     %10.5f milliseconds     |",(i+1),denseGraphStatistics[i][0],denseGraphStatistics[i][1],denseGraphStatistics[i][2]));
				
				//Max Running time of Dijkstra (Without Heap) Running Time
				if(maxRunningTime[0]<denseGraphStatistics[i][0])
				{maxRunningTime[0]=denseGraphStatistics[i][0];}
				
				//Max Running time of Dijkstra (Using Heap) Running Time
				if(maxRunningTime[1]<denseGraphStatistics[i][1])
				{maxRunningTime[1]=denseGraphStatistics[i][1];}
				
				//Max Running time of Kruskal Running Time
				if(maxRunningTime[2]<denseGraphStatistics[i][2])
				{maxRunningTime[2]=denseGraphStatistics[i][2];}
				
				averageRunningTime[0]+=denseGraphStatistics[i][0];
				averageRunningTime[1]+=denseGraphStatistics[i][1];
				averageRunningTime[2]+=denseGraphStatistics[i][2];
				
			}
			
			
			//Evaluating the average running Time
			averageRunningTime[0]/=25;
			averageRunningTime[1]/=25;
			averageRunningTime[2]/=25;
			System.out.println("	      |------------------------------------------------------------------------------------------------------------------|");
			System.out.println("Avg. Time"+String.format("     |	     %10.5f milliseconds         |	     %10.5f milliseconds           |     %10.5f milliseconds     |",averageRunningTime[0],averageRunningTime[1],averageRunningTime[2]));
			System.out.println("	      |------------------------------------------------------------------------------------------------------------------|");
			System.out.println("Max. Time"+String.format("     |	     %10.5f milliseconds         |	     %10.5f milliseconds           |     %10.5f milliseconds     |",maxRunningTime[0],maxRunningTime[1],maxRunningTime[2]));
			System.out.println("	      |------------------------------------------------------------------------------------------------------------------|");
		
			
			
			
			//Reinitializing Statistics Values
			averageRunningTime= new double[3];
			maxRunningTime = new double[3];
			
			//Printing the Sparse Graph Statistics
			System.out.println("\n\n                          Max Capacity Path running time statistics for Sparse Graph");
			System.out.println("	      |  Dijkstra (Without Heap) Running Time |   Dijkstra (Using Heap) Running Time    |     Kruskal Running Time        |");
			for(int i=0;i<sparseGraphStatistics.length;i++)
			{
				 System.out.println("	      |------------------------------------------------------------------------------------------------------------------|");
				 System.out.println(String.format("Iteration: %2d |	     %10.5f milliseconds         |	     %10.5f milliseconds           |     %10.5f milliseconds     |",(i+1),sparseGraphStatistics[i][0],sparseGraphStatistics[i][1],sparseGraphStatistics[i][2]));
				
				//Max Running time of Dijkstra (Without Heap) Running Time
				if(maxRunningTime[0]<sparseGraphStatistics[i][0])
				{maxRunningTime[0]=sparseGraphStatistics[i][0];}
				
				//Max Running time of Dijkstra (Using Heap) Running Time
				if(maxRunningTime[1]<sparseGraphStatistics[i][1])
				{maxRunningTime[1]=sparseGraphStatistics[i][1];}
				
				//Max Running time of Kruskal Running Time
				if(maxRunningTime[2]<sparseGraphStatistics[i][2])
				{maxRunningTime[2]=sparseGraphStatistics[i][2];}
				
				averageRunningTime[0]+=sparseGraphStatistics[i][0];
				averageRunningTime[1]+=sparseGraphStatistics[i][1];
				averageRunningTime[2]+=sparseGraphStatistics[i][2];
				
			}

			//Evaluating the average running Time
			averageRunningTime[0]/=25;
			averageRunningTime[1]/=25;
			averageRunningTime[2]/=25;
			System.out.println("	      |------------------------------------------------------------------------------------------------------------------|");
			System.out.println("Avg. Time"+String.format("     |	     %10.5f milliseconds         |	     %10.5f milliseconds           |     %10.5f milliseconds     |",averageRunningTime[0],averageRunningTime[1],averageRunningTime[2]));
			System.out.println("	      |------------------------------------------------------------------------------------------------------------------|");
			System.out.println("Max. Time"+String.format("     |	     %10.5f milliseconds         |	     %10.5f milliseconds           |     %10.5f milliseconds     |",maxRunningTime[0],maxRunningTime[1],maxRunningTime[2]));
			System.out.println("	      |------------------------------------------------------------------------------------------------------------------|");
			
			System.out.println("\nProcessing Over\n");
			
	}
	
	
	
	
}
