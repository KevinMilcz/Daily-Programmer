package DailyProg;

public class Bitcount {
	public static void main(String args[]){
		int retVal = Integer.parseInt(args[0]);
		int count = 0;
		
		while(retVal != 0){
			retVal &= retVal -1;
			count++;
		}
		
		System.out.println(count);
	}
}
