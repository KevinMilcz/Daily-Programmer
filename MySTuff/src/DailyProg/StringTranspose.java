package DailyProg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StringTranspose {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		int numStrings = 0;
		int longestWord = 0;
		ArrayList<String> list = null;
		StringBuilder sb = new StringBuilder();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		numStrings = Integer.parseInt(stdin.readLine());
		
		list = new ArrayList<String>(numStrings);
		
		for(int i = 0; i < numStrings; ++i)
		{
			list.add(stdin.readLine());
			longestWord = Math.max(list.get(i).length(), longestWord);
		}
		
		for(int i = 0; i < longestWord; ++i)
		{
			for(int j = 0; j < numStrings; ++j)
			{
				if(list.get(j).length() >= (i+1))
				{
					sb.append(list.get(j).charAt(i));
				}
				else
				{
					sb.append(' ');
				}
			}
			System.out.println(sb.toString());
			
			sb.setLength(0);	/* clear the buffer */			
		}
		
		/* Matrix style */
		System.out.println("****MATRIX STYLE *****");
		int M = longestWord;
		int N = numStrings;
		char matrix[][] = new char[N][M];
		
		for(int i = 0; i < N; ++i)
		{
			for(int j = 0; j < M; ++j)
			{
				matrix[i][j] = ' ';
			}
			System.arraycopy(list.get(i).toCharArray(), 0, matrix[i], 0, list.get(i).length());			
		}
		
		char vert[][] = new char[M][N];
		
		for(int i = 0; i < N; ++i)
		{
			for(int j = 0; j < M; ++j)
			{
				vert[j][i] = matrix[i][j];
			}
		}
		
		for(int i = 0; i < M; ++i)
		{
			System.out.println(new String(vert[i]));
		}
		
	}

}
