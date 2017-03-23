package Model;

import tools.Common;

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
	
	public int equal(char c1,char c2){
		return c1==c2? m:r;
	}
	
	public int equalBLOSUM40(char c1, char c2){
		int x=Common.getCharIndex(c1, Common.BLOBSUM62CHAR);
		int y=Common.getCharIndex(c2, Common.BLOBSUM62CHAR);
		return Common.BLOBSUM62[x][y];
	}
	public int equalTIMMATRX(char c1, char c2){
		if(c1 == c2){
			return 3;
		}
		int x=Common.getCharIndex(c1, Common.TIMMATRIXCHAR);
		int y=Common.getCharIndex(c2, Common.TIMMATRIXCHAR);
		return Common.TIMMATRIX[x][y];
	}

}
