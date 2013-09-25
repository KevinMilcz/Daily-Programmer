package DailyProg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClassManager {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws IOException, NumberFormatException {
		int n, m;
		float grades[], classGrade = 0;	
		String names[], tokens[];
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		tokens = stdin.readLine().split("\\s");
		n = Integer.parseInt(tokens[0]);
		m = Integer.parseInt(tokens[1]);
		grades = new float[n];
		names = new String[n];
		
		for(int i=0; i<n; ++i){
			tokens = stdin.readLine().split("\\s");
			names[i] = tokens[0];
			for(int j=1; j <= m; ++j){
				/*adding integers to floats is safe provided they start as int*/
				grades[i] += Integer.parseInt(tokens[j]);
			}
			classGrade += grades[i];
			grades[i] /= m;			
		}
		classGrade /= (n*m);
		System.out.printf("%.2f\n",classGrade);
		
		for(int i=0; i < n; ++i){
			System.out.printf("%s:\t\t%.2f\n",names[i],grades[i]);	
		}
	}

}
