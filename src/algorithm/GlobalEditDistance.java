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

import jxl.write.Label;
public class GlobalEditDistance extends AutoAlgorithm {
	//Distance of: Match, Insert, Delete, Replace
	
	private int[] LevenshteinDistance = {0,1,1,1};
	private int[] NormalDistance = {1,-1,-1,-1};
	@Override
	public String getDescription() {
		return "GlobalEditDistance";
	}

	@Override
	public boolean run() {
		// TODO Auto-generated method stub
		Distance distance = new Distance(NormalDistance);
		ArrayList<String> names = DataFileReader.read(new File(Config.PATH+Config.NAME_FILE)
        .getAbsolutePath());
		ArrayList<String> train = DataFileReader.read(Config.PATH+Config.TRAIN_FILE);
		
		ExecutorService pool = Executors.newCachedThreadPool();
		for(int tIndex=0;tIndex<Config.MAX_THREAD;tIndex++)
		{
			pool.execute(
			new Runnable(){
				public void run()
				{
					while(true)
					{
						String[] data= getUnhandledURL();
						if(data!=null)
						{
							HashMap<String,String> DataMap=KentGetDetails(data);
							putIntoWorkbook(DataMap,Integer.parseInt(data[0]));
							System.out.println(data[0]+" done.");
						}
						else{
							//System.out.println("No Unhandled");
							break;
						}
					}
					
					
				}
			});
		}
		pool.shutdown();
		pool.awaitTermination(600, TimeUnit.SECONDS);
		
		for(int i=0;i<train.size();i++){//train.size()
			//System.out.println(train.get(i).split("\t")[0]);
			
			
		}
		return true;
	}
	
	public String[] getMaxScore(String str, String[] names, Distance distance){
		int max = needlemanWunsch(train.get(i).split("\t")[0].toLowerCase(), names.get(0).toLowerCase(), distance);
		int maxIndex = 0;
		for(int j=1;j<names.size();j++){
			int dis =  needlemanWunsch(train.get(i).split("\t")[0].toLowerCase(), names.get(j).toLowerCase(), distance);
			if(dis > max){
				max = dis;
				maxIndex = j;
			}
		}
		System.out.println("Train:"+train.get(i)+"--Output:"+names.get(maxIndex)+"--Score:"+max);
		return {str,names.get(maxIndex),1};
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
	
	public static synchronized String[] getUnhandledURL()
	{
		for(int i=0;i<Data.length;i++)
		{
			if(Data[i][Data[0].length-1].equals("0"))
			{
				Data[i][Data[0].length-1]="1";
				return Data[i];
			}
		}
		return null;
	}
	
	public static synchronized void putIntoArray(HashMap<String,String> data,int index)
	{
		String[] Keys={"School","Level","Title","Type","Application Fee","Tuition Fee",
				"Academic Entry Requirement","IELTS Average Requirement",
				"IELTS Lowest Requirement","Structure","Length (months)","Month of Entry",
				"Scholarship"};
		for(int j=0;j<13;j++)
		{
			//label = new Label(j, i, data.get(Keys[j]));
		    Label label = new Label(j, index, data.get(Keys[j]));
		    try{
		    	sheet.addCell(label);
		    }
		    catch(Exception ee)
		    {
		    	ee.printStackTrace();
		    }
			
		}
	}

}
