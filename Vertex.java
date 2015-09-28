package graphProject;
public class Vertex implements Comparable<Vertex>{



	private int vertexNumber;
	private int distance;
	
	public Vertex(int vertexNumber, int distance)
	{
		this.vertexNumber=vertexNumber;
		this.distance=distance;
	}
	
	public int getVertexNumber() {
		return vertexNumber;
	}



	public void setVertexNumber(int vertexNumber) {
		this.vertexNumber = vertexNumber;
	}

	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}


    public int compareTo(Vertex vertex)
    {
        if (this.getDistance() > vertex.getDistance())
            return -1;
        if (this.getDistance() < vertex.getDistance())
            return 1;
        return 0;
    }
	
	
}
