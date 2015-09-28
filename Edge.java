package graphProject;


public class Edge implements Comparable<Edge>{

	private int firstVertex;
	private int secondVertex;
	private int edgeWeight;
	
	public Edge(int firstVertex,int secondVertex, int weight)
	{
		this.firstVertex=firstVertex;
		this.secondVertex=secondVertex;
		this.edgeWeight=weight;
	}
	
	public int getFirstVertex() {
		return firstVertex;}

	public void setFirstVertex(int firstVertex) {
		this.firstVertex = firstVertex;	}

	public int getSecondVertex() {
		return secondVertex;}

	public void setSecondVertex(int secondVertex) {
		this.secondVertex = secondVertex;}

	public int getEdgeWeight() {
		return edgeWeight;	}

	public void setEdgeWeight(int edgeWeight) {
		this.edgeWeight = edgeWeight;}

    public int compareTo(Edge edge)
    {
        if (this.getEdgeWeight() > edge.getEdgeWeight())
            return -1;
        if (this.getEdgeWeight() < edge.getEdgeWeight())
            return 1;
        return 0;
    }
 
	
	
}
