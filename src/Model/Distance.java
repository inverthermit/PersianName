package Model;

public class Distance {
	private int i, d, r, m;
	public Distance(int[] distance){
		if(distance.length == 4)
		{
			i = distance[0];
			d = distance[1];
			r = distance[2];
			m = distance[3];
		}
	}
	
	public int insert(){
		return i;
	}
	
	public int delete(){
		return d;
	}
	
	public int replace(){
		return r;
	}
	
	public int match(){
		return m;
	}

}
