package Model;

import java.util.PriorityQueue;

public class Result {
	public PriorityQueue<SingleResult> queue;
	public String persianName;
	public Result(String persianName,PriorityQueue<SingleResult> queue){
		this.queue = queue;
		this.persianName = persianName;
	}

}
