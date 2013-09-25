package DailyProg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class ChainReaction {

	static int curId = 0;
	static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	protected enum ElementType {
		UP(    1 << 0),
		DOWN(  1 << 1),
		LEFT(  1 << 2),
		RIGHT( 1 << 3);
		
	    private final int id;
	    ElementType (int id) { this.id = id; }
	    public int getValue() { return id; }
	}
	
	public class Element {
		int x, y, r, type;
		char id;
		
		
		Element(){
			x = y = r = type = 0;
			id = ' ';
		}
		public Element parseLine(String line)
		{
			Element retVal = new Element();
			String[] tokens = line.split("\\s");
			
	
			retVal.x = Integer.parseInt(tokens[0]);
			retVal.y = Integer.parseInt(tokens[1]);
			retVal.r = Integer.parseInt(tokens[2]);
			
			tokens[3] = tokens[3].toLowerCase();
			
			if(tokens[3].contains("u"))
				retVal.type |= ElementType.UP.getValue();
			if(tokens[3].contains("d"))
				retVal.type |= ElementType.DOWN.getValue();
			if(tokens[3].contains("l"))
				retVal.type |= ElementType.LEFT.getValue();
			if(tokens[3].contains("r"))
				retVal.type |= ElementType.RIGHT.getValue();
						
			retVal.id = alphabet.charAt(curId++ % 26);
			return retVal;
		}
	}
	
	public static void printMatrix(Element[][] matrix)
	{
		StringBuilder sb = new StringBuilder();
		
		for(Element[] line : matrix)
		{
			for(Element item : line)
			{
				sb.append(item.id);
			}
			System.out.println(sb.toString());
			sb.setLength(0);
		}
	}
	
	public static void updtateMatrix(Element[][] matrix, int r, Queue<Element> reactedQueue, Element temp)
	{
		if((temp.type & ElementType.UP.getValue()) > 0
				&& temp.y - r >= 0)
			{
				if (matrix[temp.y-r][temp.x].id != 'X' && matrix[temp.y-r][temp.x].id != ' ')
				{
					matrix[temp.y-r][temp.x].id = 'X';
					reactedQueue.add(matrix[temp.y-r][temp.x]);
				}
			}
			
			if((temp.type & ElementType.DOWN.getValue()) > 0
				&& temp.y + r < matrix.length)
			{
				if (matrix[temp.y+r][temp.x].id != 'X' && matrix[temp.y+r][temp.x].id != ' ')
				{
					matrix[temp.y+r][temp.x].id = 'X';
					reactedQueue.add(matrix[temp.y+r][temp.x]);
				}
			}
			
			if((temp.type & ElementType.LEFT.getValue()) > 0
				&& temp.x - r >= 0)
			{
				if (matrix[temp.y][temp.x-r].id != 'X'&& matrix[temp.y][temp.x-r].id != ' ')
				{
					matrix[temp.y][temp.x-r].id = 'X';
					reactedQueue.add(matrix[temp.y][temp.x-r]);
				}
			}
			if((temp.type & ElementType.RIGHT.getValue()) > 0
				&& temp.x + r < matrix.length)
			{
				if (matrix[temp.y][temp.x+r].id != 'X' && matrix[temp.y][temp.x+r].id != ' ')
				{
					matrix[temp.y][temp.x+r].id = 'X';
					reactedQueue.add(matrix[temp.y][temp.x+r]);
				}
			}
	}
	
	public static void main (String[] args) throws IOException
	{
		int N = 0;
		int M = 0;
		int step = 0;
		String[] tokens = null;
		Element[][] matrix = null;
		
		ChainReaction base = new ChainReaction();
		Element temp = base.new Element();
		Queue<Element> reactedQueue = new LinkedList<Element>();
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		tokens = stdin.readLine().split("\\s");
		N = Integer.parseInt(tokens[0]);
		M = Integer.parseInt(tokens[1]);
		
		matrix = new Element[M][M];
		for(Element[] line : matrix)
		{
			for(int i = 0; i < line.length; ++i)
			{
				line[i] = base.new Element();
			}
		}
		
		for (int i = 0; i < N; ++i)
		{
			temp = temp.parseLine(stdin.readLine());
			/* Swap x and y, so that iterating across x reads a row.
			 * This is because of row major ordering 
			 *  0 1 2
			 *  3 4 5
			 *  6 7 8
			 *  Reading matrix[2][1] should give 5, but it gives 7.
			 */
			matrix[temp.y][temp.x] = temp;			
		}
		
		System.out.printf("Step: %d\n",step++);
		printMatrix(matrix);
		
		matrix[0][0].id = 'X';
		reactedQueue.add(matrix[0][0]);	
		while(! reactedQueue.isEmpty())
		{
			System.out.printf("Step: %d\n",step++);
			printMatrix(matrix);
			temp = reactedQueue.poll();
			
			for(int r = 1; r <= temp.r; ++r)
			{
				updtateMatrix(matrix,r,reactedQueue,temp);
			} /* End for r */								
		} /* end while */
		
	}

}
