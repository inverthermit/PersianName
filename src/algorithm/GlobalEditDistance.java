package algorithm;
import Model.Distance;

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
		return false;
	}
	
	public int needlemanWunsch(String str1, String str2, Distance distance) {
		int lf = str1.length();
		int lt = str2.length();
		int[][] matrix = new int[lt+1][lf+1];
		matrix[0][0] = 0;
		for(int i=0;i<=lt;i++) matrix[i][0] = i*distance.insert();
		for(int i=0;i<=lf;i++) matrix[0][i] = i*distance.delete();
		
		return matrix[lt][lf];
		
		
	}

}
