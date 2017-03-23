package tools;

import Model.Evaluation;
import algorithm.AutoAlgorithm;

public class Train {
	public int[][] trainArray;//{{1,2},{3,4}}
	public int[][] matrix;
	public int[] range;//{-3,2}
	public Train( int[][] trainArray, int[][] matrix, int[] range){
		this.trainArray = trainArray;
		this.matrix = matrix;
		this.range = range;
	}
	public void train(AutoAlgorithm alg){
		for(int i=0;i<trainArray.length;i++){
			int x = trainArray[i][0];
			int y = trainArray[i][1];
			for(int j=range[0];j<=range[1];j++){
				matrix[x][y] = j;
				alg.init();
				Evaluation eval = alg.run();
				String result = "Element:{"+x+","+y+"} Value:"+j+" "+eval.getAccuracy();
				System.out.println(result);
				Log.log(result);
			}
		}
	}

}
