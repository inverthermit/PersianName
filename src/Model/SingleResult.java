package Model;

public class SingleResult {
	public int index;
	public String source;
	public String target;
	public int score;
	public SingleResult(int index, String source, String target, int score){
		this.index = index;
		this.source = source;
		this.target = target;
		this.score = score;
	}

}
