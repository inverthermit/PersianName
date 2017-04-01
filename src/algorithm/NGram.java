package algorithm;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import tools.Common;
import tools.Config;
import tools.DataFileReader;
import tools.Log;
import tools.NGramComparator;
import Model.Distance;
import Model.Evaluation;
import Model.Result;
import Model.SingleResult;

public class NGram extends AutoAlgorithm {
	private Distance distance ;
	private String[] names ;
	private String[] train ;
	private int[] trainFlag ;
	private Result[] result ;
	private int correctness=0;
	@Override
	public String getDescription() {
		return "NGram";
	}

	

	@Override
	public void init() {
		names = DataFileReader.read(new File(Config.PATH+Config.NAME_FILE)
	    .getAbsolutePath());
		train = DataFileReader.read(Config.PATH+Config.TRAIN_FILE);
		trainFlag = new int[train.length];
		result = new Result[train.length];
	}
	
	@Override
	public Evaluation run() {
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
								SingleResult sr = iterator.next();
								if(sr.target.equals(trainTag)){
									addCorrectness();
									max.correctness = true;
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
					Log.log(i+" "+result[i].persianName+"["+result[i].latinName+"] "+sr.target+" "+sr.score);
				}
			}
			Log.log("-------------------"+result[i].correctness+"-------------------------");
			
		}
		Evaluation eval = new Evaluation(correctness,train.length);
		System.out.println("Accuracy:"+eval.getAccuracy());
		resetCorrectness();
		return eval;
	}
	
	public Result getMaxScore(String str, String[] names, Distance distance){
		Comparator<SingleResult> comparator = new NGramComparator();
        PriorityQueue<SingleResult> queue = 
            new PriorityQueue<SingleResult>(Config.MAX_RESULT_ARRAY, comparator);
		String persianName = str.split("\t")[0].toLowerCase();
		String latinName = str.split("\t")[1].toLowerCase();
		//int queueMin =  needlemanWunsch(persianName, names[0].toLowerCase(), distance);
		//int queueMin = smithWaterman(persianName, names[0].toLowerCase(), distance);
		NGram ng = new NGram();
		int queueMax = ng.getDistance(persianName, names[0].toLowerCase(), 3);
		SingleResult sr= new SingleResult(0, persianName, names[0].toLowerCase(), queueMax);
		queue.add(sr);
		for(int j=1;j<names.length;j++){
			String lcName = names[j].toLowerCase();
			//int dis =  needlemanWunsch(persianName,lcName , distance);
			//int dis = smithWaterman(persianName, lcName, distance);
			int dis = ng.getDistance(persianName, lcName, 3);
			SingleResult srTemp= new SingleResult(j, persianName, lcName, dis);
			queue.add(srTemp);
		}
		PriorityQueue<SingleResult> limitedQueue = 
	            new PriorityQueue<SingleResult>(Config.MAX_RESULT_ARRAY, comparator);
		for( int j=0;j<Config.MAX_RESULT_ARRAY;j++){
			limitedQueue.add(queue.poll());
		}
		//System.out.println("Train:"+str+"--Output:"+names[maxIndex]+"--Score:"+max);
		Result result = new Result(persianName,limitedQueue,latinName);
		
		//String[] result = {str,names[maxIndex], max+"","1"};
		return result;
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
	
	public void resetCorrectness(){
		this.correctness = 0;
	}
	
    private class result
    {
        private String theWord;
        private int theCount;
 
        public result(String w, int c)
        {
            theWord = w;
            theCount = c;
        }
 
        public void setTheCount(int c)
        {
            theCount = c;
        }
 
        public String getTheWord()
        {
            return theWord;
        }
 
        public int getTheCount()
        {
            return theCount;
        }
    }
 
    private List<result> results;
 
    public NGram()
    {
        results = new ArrayList<result>();
    }
    public NGram(String str, int n)
    {
 
    }
    
    public int getDistance(String wordOne, String wordTwo, int n)
    {
        List<result> res1 = processString(wordOne, n);
        //displayResult(res1);
        List<result> res2 = processString(wordTwo, n);
        //displayResult(res2);
        int c = common(res1,res2);
        //int u = union(res1,res2);
        //double sim = (double)c/(double)u;
 
        //return sim;
        int dis = res1.size()+res2.size()-2*c;
        return dis;
    }
 
    public double getSimilarity(String wordOne, String wordTwo, int n)
    {
        List<result> res1 = processString(wordOne, n);
        //displayResult(res1);
        List<result> res2 = processString(wordTwo, n);
        //displayResult(res2);
        int c = common(res1,res2);
        int u = union(res1,res2);
        double sim = (double)c/(double)u;
 
        return sim;
    }
 
    private int common(List<result> One, List<result> Two)
    {
        int res = 0;
 
        for (int i = 0; i < One.size(); i++)
        {
            for (int j = 0; j < Two.size(); j++)
            {
                if (One.get(i).theWord.equalsIgnoreCase(Two.get(j).theWord)) res++;
            }
        }
 
        return res;
    }
 
    private int union(List<result> One, List<result> Two)
    {
        List<result> t = One;
 
        for (int i = 0; i < Two.size(); i++)
        {
            int pos = -1;
            boolean found = false;
            for (int j = 0; j < t.size() && !found; j++)
            {
                if (Two.get(i).theWord.equalsIgnoreCase(t.get(j).theWord))
                {
                    found = true;
                }
                pos = j;
            }
 
            if (!found)
            {
                result r = Two.get(i);
                t.add(r);
            }
        }
 
        return t.size();
    }
 
    private List<result> processString(String c, int n)
    {
        List<result> t = new ArrayList<result>();
 
        String spacer = "";
        for (int i = 0; i < n-1; i++)
        {
            spacer = spacer + "%";
        }
        c = spacer + c + spacer;
         
        for (int i = 0; i < c.length(); i++)
        {
            if (i <= (c.length() - n))
            {
                if (contains(c.substring(i, n+i)) > 0)
                {
                    t.get(i).setTheCount(results.get(i).getTheCount()+1);
                }
                else
                {
                    t.add(new result(c.substring(i,n+i),1));
                }
            }
        }
        return t;
    }
 
    private int contains(String c)
    {
        for (int i = 0; i < results.size(); i++)
        {
            if (results.get(i).theWord.equalsIgnoreCase(c))
                return i;
        }
        return 0;
    }
 
    private void displayResult(List<result> d)
    {
        for (int i = 0; i < d.size(); i++)
        {
            System.out.println(d.get(i).theWord+" occurred "+d.get(i).theCount+" times");
        }
    }
	

}
