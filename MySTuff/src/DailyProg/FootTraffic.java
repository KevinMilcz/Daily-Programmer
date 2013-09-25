package DailyProg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;


public class FootTraffic {

	public class TrafRecord
	{
		public int totalTime;
		public int rmId;
		public HashSet<Integer> uniqueVisitors;
		
		public TrafRecord() {
			totalTime = 0;
			rmId = -1;
			uniqueVisitors = new HashSet<Integer>();
		}		
	}
	
	public static void main(String args[]) throws IOException, NumberFormatException
	{
		int numLines = 0, rmId;
		TrafRecord[] table = new TrafRecord[100];
		FootTraffic base = new FootTraffic();
		String[] tokens;
		
		for(int i=0; i < table.length; ++i){
			table[i] =  base.new TrafRecord();
		}
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		numLines = Integer.parseInt(stdin.readLine());

		while(numLines --> 0){
			tokens = stdin.readLine().split("\\s");
			rmId = Integer.parseInt(tokens[1]);
			table[rmId].rmId = rmId;
			table[rmId].uniqueVisitors.add(Integer.parseInt(tokens[0]));
			table[rmId].totalTime += (tokens[2].equals("I") ? -1 : 1) 
				* Integer.parseInt(tokens[3]);
		}				
		for(TrafRecord room : table){
			if(room.rmId >= 0){
				room.totalTime = (int) Math.round(1.0 * room.totalTime / room.uniqueVisitors.size());
				System.out.printf("Room %d avg wait %d mins, %d visitors\n",
						room.rmId, room.totalTime, room.uniqueVisitors.size());
			}
		}
	}
}
