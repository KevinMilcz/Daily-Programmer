package DailyProg;

public class OddEvenSort {

	public static final int SIZE = 100;
	
	public static void main(String[] args) {
		int[] nums = new int[SIZE];
		
		for(int i = 0; i < SIZE; i++){
			nums[i] = (int) Math.round(Math.random() * SIZE);
		}

		doSort(nums);
		
		for(int i = 0; i < SIZE; i++){
			System.out.println(nums[i]);
		}
	}

	private static int doSort(int[] arr){
		int low = 0;
		int end = arr.length-1;
		int temp = 0;
		
		while(low < end){
			if((arr[low] & 1) == 0)
				low++;
			else{
				while((arr[end] & 1) == 1 && low < end){
					end--;
				}
				temp = arr[low];
				arr[low] = arr[end];
				arr[end] = temp;
			}
		}
		return 0;
	}
}
