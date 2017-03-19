package Model;

public class Evaluation {
	
	private int correctness;
	private int total;
	public Evaluation(int correctness, int total){
		this.correctness = correctness;
		this.total = total;
	}
	public double getAccuracy(){
		//TODO: Accuracy calculation(With single output)
		return (correctness+0.0)/total;
	}
	
	public double getPrecision(){
		//TODO: Precision calculation(With multiple output)
		return 0;
	}
	
	public double getCallback(){
		//TODO: Callback calculation(With multiple output)
		return 0;
	}
}
