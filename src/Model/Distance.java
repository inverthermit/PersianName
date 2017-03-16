package Model;

public class Distance {
	private int i, d, r, m;
	public Distance(int[] distance){
		if(distance.length == 4)
		{
			m = distance[0];
			i = distance[1];
			d = distance[2];
			r = distance[3];
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
	
	public int equal(String str1,String str2){
		if(str1.equals(str2)){
			return m;
		}
		else{
			return r;
		}
	}

}
