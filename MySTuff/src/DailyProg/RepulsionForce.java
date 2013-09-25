package DailyProg;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Scanner;

public class RepulsionForce {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnsupportedFlavorException 
	 * @throws HeadlessException 
	 */
	public static void main(String[] args) throws HeadlessException, UnsupportedFlavorException, IOException {
		Scanner stdin = new Scanner(System.in);
		double[] values = new double[6];
		for(int i = 0; i < values.length; ++i)
			 values[i] = stdin.nextFloat();
		
		String data = (String) Toolkit.getDefaultToolkit()
        .getSystemClipboard().getData(DataFlavor.stringFlavor);
		
		double force = values[0]*values[3] / Math.pow(Math.sqrt( Math.pow(values[1]-values[4],2) 
				+ Math.pow(values[2] - values[5],2)),2);

		System.out.printf("%.3f\n", force);
	}

}
