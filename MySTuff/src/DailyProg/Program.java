package DailyProg;

public class Program {

	public int power(int x, int y){
		if(y == 1) return x;
		
		if(y%2 == 0)
			return power(x,(y/2)) * power(x,(y/2));
		
		return x *power(x,(y/2)) * power(x,(y/2));
	}
	
	public static void main(String args[]){
		Program obj = new Program();
		
		System.out.println(obj.power(3,5));
		
		
	}
	                                  
}
