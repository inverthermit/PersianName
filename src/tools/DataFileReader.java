package tools;
import java.io.*;
import java.util.ArrayList;

public class DataFileReader {
	/*private String path;
	public DataFileReader(String path){
		this.path = path;
	}*/
	public static String[] read(String path){
		
		try{
			int lines = getLineCount(path);
			BufferedReader br = new BufferedReader(new FileReader(path));
			String[] result = new String[lines];
			int count = 0;
			while(br.ready()){
				result[count]=br.readLine();
				count++;
			}
			br.close();
			return result;
		}
		catch(Exception ee){
			ee.printStackTrace();
			return null;
		}
		
	}
	
	public static int getLineCount(String path){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			int lines = 0;
			while (reader.readLine() != null) lines++;
			reader.close();
			return lines;
		}
		catch(Exception ee){
			ee.printStackTrace();
		}
		return 0;
	}

}
