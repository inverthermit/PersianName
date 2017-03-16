package algorithm;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import tools.Common;
import tools.Config;
import tools.DataFileReader;
import Model.Distance;

import java.io.*;

public class GlobalEditDistance extends AutoAlgorithm {
	//Distance of: Match, Insert, Delete, Replace
	
	private final int[] LevenshteinDistance = {0,1,1,1};
	private final int[] NormalDistance = {1,-1,-1,-1};
	private Distance distance = new Distance(NormalDistance);
	private String[] names = DataFileReader.read(new File(Config.PATH+Config.NAME_FILE)
    .getAbsolutePath());
	private String[] train = DataFileReader.read(Config.PATH+Config.TRAIN_FILE);
	private int[] trainFlag = new int[train.length];
	private String[][] result = new String[train.length][3];
	@Override
	public String getDescription() {
		return "GlobalEditDistance";
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean run() {
		// TODO Auto-generated method stub
		/*Distance distance = new Distance(NormalDistance);
		ArrayList<String> names = DataFileReader.read(new File(Config.PATH+Config.NAME_FILE)
        .getAbsolutePath());
		ArrayList<String> train = DataFileReader.read(Config.PATH+Config.TRAIN_FILE);
		int[] trainFlag = new int[train.size()];*/
		
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
							String[] max = getMaxScore(train[index], names, distance);
							putIntoArray(max,index);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return true;
	}
	
	public String[] getMaxScore(String str, String[] names, Distance distance){
		int max = needlemanWunsch(str.split("\t")[0].toLowerCase(), names[0].toLowerCase(), distance);
		int maxIndex = 0;
		for(int j=1;j<names.length;j++){
			int dis =  needlemanWunsch(str.split("\t")[0].toLowerCase(), names[j].toLowerCase(), distance);
			if(dis > max){
				max = dis;
				maxIndex = j;
			}
		}
		//System.out.println("Train:"+str+"--Output:"+names.get(maxIndex)+"--Score:"+max);
		String[] result = {str,names[maxIndex], max+"","1"};
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
						matrix[i-1][j-1]+distance.equal(str1.charAt(j-1)+"",str2.charAt(i-1)+"")
				};
				matrix[i][j]=Common.max(input);
			}
		}
		return matrix[lt][lf];
		
		
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
	
	public synchronized void putIntoArray(String[] data,int index)
	{
		result[index] = data;
	}

	

}
