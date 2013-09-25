package DailyProg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashSet;

public class Shakespeare {
	public static void main(String args[]){
		try{
			BufferedReader in = new BufferedReader(new FileReader("Input.txt"));
			
			String temp;
			String[] line;
			int baseLen = 14;
			int maxLen = 13;
			HashSet<String> found = new HashSet<String>();
			HashSet<String> good = new HashSet<String>();
			String longest = "";
			
			while((temp = in.readLine()) != null){
				temp = temp.toUpperCase();
				
				line = temp.split("[ .,?!-;:|\\]\\[]+");
				for(int i = 0; i < line.length; i++){		
					if(line[i].length() > baseLen){
						if(found.contains(line[i])){
							good.remove(line[i]);
						}else{
							found.add(line[i]);
							good.add(line[i]);
						}
					}
				}
			}
			
			for(String i : good){
				System.out.print(i + " ");
				if(i.length() > maxLen){
					maxLen = i.length();
					longest = i;
				}
			}
			System.out.println("\nThe longest word is: " +longest);
			
			in.close();
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage());
		}
	
	}
}
