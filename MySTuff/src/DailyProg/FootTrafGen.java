package DailyProg;

import java.util.ArrayList;

public class FootTrafGen {

	public class Timeslot implements Comparable<Timeslot>{
		public int start, end;
			
		public Timeslot()
		{ start = 0; end = 0;}

		public int compareTo(Timeslot o) {
			return this.start - o.start;
		}
		
	}
	public class SortedArray{
		public ArrayList<Timeslot> data;
		
		public void add(Timeslot ts){
			for(int i=0; i < data.size(); ++i){
				if(data.get(i).compareTo(ts) < 0){
					data.
				}
			}
			return data.add(ts);
		}
	}
}
