package tools;

public class Common {
	public static int max(int[] arr){
		if(arr.length > 0){
			int max = arr[0];
			for(int i=1;i< arr.length;i++){
				if(arr[i]>max){
					max = arr[i];
				}
			}
			return max;
		}
		else{
			return 0;
		}
		
	}
	
	public static int min(int[] arr){
		if(arr.length > 0){
			int min = arr[0];
			for(int i=1;i< arr.length;i++){
				if(arr[i]<min){
					min = arr[i];
				}
			}
			return min;
		}
		else{
			return 0;
		}
		
	}

}
