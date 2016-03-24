package mrfoops;

/** coordinate class*/
public class Coordinate{
	public int x;
	public int y;

	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

	
	public boolean equals(Coordinate other){
		return (this.x == other.x) && (this.y == other.y);
	}

}