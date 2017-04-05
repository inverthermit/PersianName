package algorithm;

import tools.Config;
import tools.DataFileReader;

public class FeatureSelection {
	public static final String EXCEPTIONS ="aei";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		select(Config.PATH+Config.TRAIN_FILE);
	}
	
	public static void select(String path){
		String[] train = DataFileReader.read(Config.PATH+Config.TRAIN_FILE);
		int[][] result = new int[26][26];
		String[][] splitTrain = new String[train.length][2];
		for(int i=0;i<train.length;i++){
			splitTrain[i][0] = train[i].split("\t")[0].toLowerCase();
			splitTrain[i][1] = train[i].split("\t")[1].toLowerCase();
		}
		for(int i=0;i<26;i++){
			for(int j=0;j<26;j++){
				for(int k=0;k<splitTrain.length;k++){
					if(splitTrain[k][0].contains((char)('a'+i)+"")&&splitTrain[k][1].contains((char)('a'+j)+"")){
						result[i][j]++;
					}
				}
			}
		}
		for(int i=0;i<26;i++){
			int total = 0;
			for(int j=0;j<26;j++){
				total+=result[i][j];
			}
			for(int j=0;j<26;j++){
				if(EXCEPTIONS.contains((char)('a'+i)+"")||EXCEPTIONS.contains((char)('a'+j)+"")||i==j){
					continue;
				}
				System.out.println((char)('a'+i)+"-"+(char)('a'+j)+":"+result[i][j]*100.0/total+"%");
			}
		}
		
	}

}
