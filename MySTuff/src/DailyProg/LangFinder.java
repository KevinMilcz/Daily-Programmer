package DailyProg;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class LangFinder {

	private final String dicDir = "C:\\Users\\Alborak\\Documents\\Dictionaries";
	
	protected enum LangNames
	{
		FRENCH("French"),
		ENGLISH("English"),
		SPANISH("Spanish"),
		GERMAN("German"),
		PORTUGUESE("Portuguese");
		
	    private final String name;
	    LangNames (String name) { this.name = name; }
	    public String getValue() { return name; }
	}
	
	public class Language
	{
		HashSet<String> dictionary;
		LangNames name;
				
		Language(LangNames name)
		{
			String langFile = null;
			this.dictionary = new HashSet<String>();
			this.name = name;
			
			switch (name) {
			case FRENCH:
				langFile = "\\French\\fr.dic";
				break;
			case ENGLISH:
				langFile = "\\English\\eng_com.dic";
				break;
			case SPANISH:
				langFile = "\\Spanish\\ES.dic";
				break;
			case GERMAN:
				langFile = "\\German\\de_neu.dic";
				break;
			case PORTUGUESE:
				langFile = "\\Portuguese\\portugues.dic";
				break;
			default:
				break;
			}
			
			String line;
			try {
				String fileName = dicDir + langFile;
				FileInputStream fstream = new FileInputStream(fileName);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				while((line = br.readLine()) != null) {
					if(line.trim().startsWith("%")){
						continue;
					} else {
						this.dictionary.add(line);
					}		
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public boolean contains(String word){
			return dictionary.contains(word);
		}
	}
	
	public class LangCounter implements Comparable<LangCounter>{
		LangNames name;
		int sum;
		
		public LangCounter(LangNames name) {
			this.name = name;
			sum = 0;
		}

		@Override
		public int compareTo(LangCounter o) {
			return this.sum - o.sum;
		}		
	}

	public static void main(String[] args) throws IOException {
		LangNames[] langs = LangNames.values(); 
		int numLangs = langs.length;
		LangCounter[] tally = new LangCounter[numLangs];
		
		LangFinder base = new LangFinder();
		Language[] dictionaries = new Language[numLangs];
		
		for(int i = 0; i < numLangs; ++i){
			dictionaries[i] = base.new Language(langs[i]);
			tally[i] = base.new LangCounter(langs[i]);
		}
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		String[] tokens = stdin.readLine().split("\\s");
		
		for(String word : tokens){
			for(int i = 0; i < numLangs; ++i){
				if(dictionaries[i].contains(word)){
					tally[i].sum++;
				}
			}
		}
		
		Arrays.sort(tally, Collections.reverseOrder());
		
		if((1.0 *tally[0].sum / tokens.length) >= 0.6){
			System.out.println(tally[0].name.getValue());
		}else{
			System.out.printf("%2.2f match for %s\n", 1.0* tally[0].sum / tokens.length, tally[0].name.getValue());
			System.out.printf("%2.2f match for %s\n", 1.0* tally[1].sum / tokens.length, tally[1].name.getValue());
		}
	}
}
