package tools;
import java.io.*;
import java.util.ArrayList;

public class DataFileReader {
	/*private String path;
	public DataFileReader(String path){
		this.path = path;
	}*/
	public static ArrayList<String> read(String path){
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			ArrayList<String> result = new ArrayList<String>();
			while(br.ready()){
				result.add(br.readLine());
			}
			br.close();
			return result;
		}
		catch(Exception ee){
			ee.printStackTrace();
			return null;
		}
		
	}

}
