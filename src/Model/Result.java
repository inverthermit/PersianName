package Model;

import java.util.PriorityQueue;

public class Result {
	public PriorityQueue<SingleResult> queue;
	public String persianName;
	public String latinName;
	public boolean correctness = false;
	public Result(String persianName,PriorityQueue<SingleResult> queue,String latinName){
		this.queue = queue;
		this.persianName = persianName;
		this.latinName = latinName;
	}

}
