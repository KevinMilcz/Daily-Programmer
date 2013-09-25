package DailyProg;

public class checkeredGrid {
	
	public static void main(String[] args) {
		checkeredGrid grid = new checkeredGrid();
		grid.GenerateGrid(Integer.parseInt(args[0]), 
				Integer.parseInt(args[1]),
				Integer.parseInt(args[2]));
	}

	public void GenerateGrid(int cols, int rows, int height){
		String box = "####";
		String blank = "    ";
		
		for(int y = 0; y < rows; y++){
			for(int i = 0; i < height; i++){
				for(int x = 0; x < cols; x++){
				
					if(y % 2 == 0){
						if(x % 2 == 0)
							System.out.print(blank);
						else
							System.out.print(box);
					}else{
						if(x % 2 == 1)
							System.out.print(blank);
						else
							System.out.print(box);
					}	
				}
			System.out.println();
			}
		}
	}
}
