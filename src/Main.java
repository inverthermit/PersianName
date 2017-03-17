import algorithm.*;

import java.text.*;
import java.util.*;
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
		Date endDate = new Date();
		String end = (dateFormat.format(endDate));
		System.out.println("Start:"+start);
		System.out.println("End: "+end);
	}
}
