package DailyProg;

import java.io.*;
import java.util.HashMap;

public class Assembler {

	static HashMap<String,Integer> table;
	
	private static void InitTable()
	{
		table = new HashMap<String,Integer>();
		table.put("AND",  0);
		table.put("OR",  2);
		table.put("XOR",  4);
		table.put("NOT",  6);
		table.put("MOV",  7);
		table.put("RANDOM",  9);
		table.put("ADD",  0x0A);
		table.put("SUB",  0x0c);
		table.put("JMP",  0x0e);
		table.put("JZ",  0x10);
		table.put("JEQ",  0x14);
		table.put("JLS",  0x18);
		table.put("JGT",  0x1c);
		table.put("HALT",  0xff);
		table.put("APRINT",  0x20);
		table.put("DPRINT",  0x22);	
	}

	private class opCode
	{
		public int op;
		public int arg1;
		public int arg2;
		public int arg3;
		
		opCode()
		{
			this.op = 0x00;
			this.arg1 = -1;
			this.arg2 = -1;
			this.arg3 = -1;			
		}
		public opCode readLine(String[] tokens)
		{
			opCode retVal = new opCode();
			
			retVal.op = table.get(tokens[0]);			
			
			/* jump ops */			
			if( retVal.op >= 0x14  && retVal.op < 0x20)
			{
				if(tokens.length == 4)
				{
					if(tokens[1].charAt(0) != '[') retVal.op += 1;
					if(tokens[3].charAt(0) != '[') retVal.op += 2;
				}else{
					return null;
				}
				retVal.arg1 = Integer.parseInt(tokens[1].replaceAll("\\[?(\\d*)\\]?", "$1"));
				retVal.arg2 = Integer.parseInt(tokens[2].replaceAll("\\[?(\\d*)\\]?", "$1"));
				retVal.arg3 = Integer.parseInt(tokens[3].replaceAll("\\[?(\\d*)\\]?", "$1"));
				
			}else if(tokens.length == 3)
			{
				if(tokens[2].charAt(0) != '[') retVal.op += 1;
				if(retVal.op > 0x10 && tokens[1].charAt(0) != '[') retVal.op += 1;
				
				retVal.arg1 = Integer.parseInt(tokens[1].replaceAll("\\[?(\\d*)\\]?", "$1"));
				retVal.arg2 = Integer.parseInt(tokens[2].replaceAll("\\[?(\\d*)\\]?", "$1"));
			}else if (tokens.length == 2)
			{
				if(tokens[1].charAt(0) != '[') retVal.op += 1;
				retVal.arg1 = Integer.parseInt(tokens[1].replaceAll("\\[?(\\d*)\\]?", "$1"));				
			}else if(retVal.op != 0xff){
				return null;
			}			
			return retVal;
		}
		
	}
	public static void main(String args[])
	{
		Assembler obj = new Assembler();
		opCode line = obj.new opCode(); 
		
		String strLine;
		String[] tokens;
		String filename =  "C:\\Test.txt.txt";

		InitTable();

		FileInputStream fstream;
		try {
			fstream = new FileInputStream(filename);
		
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		while ((strLine = br.readLine()) != null)
		{
			strLine = strLine.toUpperCase();
			tokens = strLine.split("\\s");
			
			line = line.readLine(tokens);
			
			tokens = new String[4];
			tokens[0] = Integer.toHexString(line.op);
			if (line.arg1 > -1){
				tokens[1] = Integer.toHexString(line.arg1);
			}else
			{
				tokens[1] = "";
			}
			if (line.arg2 > -1)
			{
				tokens[2] = Integer.toHexString(line.arg2);
			}else
			{
				tokens[2] = "";
			}
			if (line.arg3 > -1)
			{
				tokens[3] = Integer.toHexString(line.arg3);
			}else
			{
				tokens[3] = "";
			}
			
			for(int i = 0; i < tokens.length; ++i)
			{
				String prefix = "0x";				
				
				if(tokens[i].length() < 2)
					prefix += "0";
				
				if(tokens[i].length() > 0)
					tokens[i] = prefix + tokens[i];		
			}
			
			System.out.println(tokens[0] +" "+ tokens[1] +" "+ tokens[2] +" "+ tokens[3]);
		}
		in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
