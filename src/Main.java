import algorithm.*;

import java.text.*;
import java.util.*;

import tools.Common;
import tools.Train;
public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ready");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date startDate = new Date();
		String start = (dateFormat.format(startDate));
		AutoAlgorithm alg = new GlobalEditDistance();
		
		alg.init();
		alg.run();
		
		/*int[][] trainArray={{6,17},{18,19},{10,20}};//j-h
		int[][] matrix = Common.TIMMATRIX;
		int[] range = {-3,2};//{-3,2}
		Train trainTask = new Train(trainArray, matrix, range);
		trainTask.train(alg);*/
		
		Date endDate = new Date();
		String end = (dateFormat.format(endDate));
		System.out.println("Start:"+start);
		System.out.println("End: "+end);
	}
}
