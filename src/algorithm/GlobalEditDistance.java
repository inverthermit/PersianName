package algorithm;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import tools.*;
import Model.*;

import java.io.*;

public class GlobalEditDistance extends AutoAlgorithm {
	//Distance of: Match, Insert, Delete, Replace
	
	private final int[] LevenshteinDistance = {0,1,1,1};
	private final int[] NormalDistance = {1,-3,-3,-3};
	private Distance distance ;
	private String[] names ;
	private String[] train ;
	private int[] trainFlag ;
	private Result[] result ;
	private int correctness=0;
	@Override
	public String getDescription() {
		return "GlobalEditDistance";
	}

	@Override
	public void init() {
		distance = new Distance(NormalDistance);
		names = DataFileReader.read(new File(Config.PATH+Config.NAME_FILE)
	    .getAbsolutePath());
		train = DataFileReader.read(Config.PATH+Config.TRAIN_FILE);
		
		
		trainFlag = new int[train.length];
		result = new Result[train.length];
	}
	
	@Override
	public boolean run() {
		ExecutorService pool = Executors.newCachedThreadPool();
		for(int tIndex=0;tIndex<Config.MAX_THREAD;tIndex++)
		{
			pool.execute(
			new Runnable(){
				public void run()
				{
					while(true)
					{
						int index= getUnhandledDataIndex(trainFlag);
						if(index!=-1){
							Result max = getMaxScore(train[index], names, distance);
							putIntoArray(max,index);
							String trainTag =train[index].split("\t")[1];
							Iterator<SingleResult> iterator = max.queue.iterator();
							while (iterator.hasNext()) {
								if(iterator.next().target.equals(trainTag)){
									addCorrectness();
									break;
								}
							}
							//System.out.println(max.queue.size());
							System.out.println("Done:"+index);
						}
						else{
							break;
						}
					}
				}
			});
		}
		pool.shutdown();
		try {
			pool.awaitTermination(600, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0 ; i< result.length;i++){
			while(true){
				SingleResult sr = result[i].queue.poll();
				if(sr == null){
					break;
				}
				else{
					Log.log(i+" "+result[i].persianName+" "+sr.target+" "+sr.score);
				}
			}
			
		}
		Evaluation eval = new Evaluation(correctness,train.length);
		System.out.println("Accuracy:"+eval.getAccuracy());
		return true;
	}
	
	public Result getMaxScore(String str, String[] names, Distance distance){
		Comparator<SingleResult> comparator = new ScoreComparator();
        PriorityQueue<SingleResult> queue = 
            new PriorityQueue<SingleResult>(Config.MAX_RESULT_ARRAY, comparator);
		String persianName = str.split("\t")[0].toLowerCase();
		int queueMin = needlemanWunsch(persianName, names[0].toLowerCase(), distance);
		SingleResult sr= new SingleResult(0, persianName, names[0].toLowerCase(), queueMin);
		queue.add(sr);
		for(int j=1;j<names.length;j++){
			String lcName = names[j].toLowerCase();
			int dis =  needlemanWunsch(persianName,lcName , distance);
			if(queue.size() < Config.MAX_RESULT_ARRAY){
				SingleResult srTemp= new SingleResult(j, persianName, lcName, dis);
				queue.add(srTemp);
				if(dis < queueMin){
					queueMin = dis;
				}
			}
			else if(queue.size() >= Config.MAX_RESULT_ARRAY && dis>queueMin){
				SingleResult srTemp= new SingleResult(j, persianName, lcName, dis);
				queue.poll();
				queue.add(srTemp);
				queueMin = queue.peek().score;
			}
		}
		//System.out.println("Train:"+str+"--Output:"+names[maxIndex]+"--Score:"+max);
		Result result = new Result(persianName,queue);
		
		//String[] result = {str,names[maxIndex], max+"","1"};
		return result;
	}
	
	public int needlemanWunsch(String str1, String str2, Distance distance) {
		int lf = str1.length();
		int lt = str2.length();
		int[][] matrix = new int[lt+1][lf+1];
		matrix[0][0] = 0;
		for(int i=0;i<=lt;i++) matrix[i][0] = i*distance.insert();
		for(int i=0;i<=lf;i++) matrix[0][i] = i*distance.delete();
		//Start of dynamic programming
		for(int i=1;i<=lt;i++){
			for(int j=1;j<=lf;j++){
				int[] input = {
						matrix[i][j-1]+distance.delete(),
						matrix[i-1][j]+distance.insert(),
						matrix[i-1][j-1]+distance.equal(str1.charAt(j-1),str2.charAt(i-1))
				};
				matrix[i][j]=Common.max(input);
			}
		}
		return matrix[lt][lf];
		
		
	}
	public int smithWaterman(String str1, String str2, Distance distance) {//LocalEditDistance
		int lf = str1.length();
		int lt = str2.length();
		int[][] matrix = new int[lt+1][lf+1];
		matrix[0][0] = 0;
		for(int i=0;i<=lt;i++) matrix[i][0] = 0;
		for(int i=0;i<=lf;i++) matrix[0][i] = 0;
		int max=0;
		//Start of dynamic programming
		for(int i=1;i<=lt;i++){
			for(int j=1;j<=lf;j++){
				int[] input = {
						0,
						matrix[i][j-1]+distance.delete(),
						matrix[i-1][j]+distance.insert(),
						matrix[i-1][j-1]+distance.equal(str1.charAt(j-1),str2.charAt(i-1))
				};
				matrix[i][j]=Common.max(input);
				if(matrix[i][j]>max){
					max = matrix[i][j];
				}
			}
		}
		return max;
		
		
	}
	
	public synchronized int getUnhandledDataIndex( int[] flags)
	{
		for(int i=0;i<flags.length;i++)
		{
			if(flags[i]==0)
			{
				flags[i]=1;
				return i;
			}
		}
		return -1;
	}
	
	public void putIntoArray(Result data,int index)
	{
		this.result[index] = data;
	}
	
	public synchronized void addCorrectness()
	{
		this.correctness++;
	}
}
