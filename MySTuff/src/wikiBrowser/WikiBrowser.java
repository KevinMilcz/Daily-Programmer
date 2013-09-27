package wikiBrowser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WikiBrowser {
	static String urlBase = "http://en.wikipedia.org/wiki/";
	static Pattern linkPat = Pattern.compile("<a href=\"(.*?)\"", Pattern.DOTALL);
	static Pattern paraPat = Pattern.compile("<p>(.*?)</p>", Pattern.DOTALL);
	
	public class WebPage {		
		String name;
		String pageData;
	
		public WebPage(String name, String data) {
			this.name = name;
			this.pageData = data;
		}
		public WebPage(){
			this.name = new String();
			this.pageData = new String();
		}
		
		/*Needs name of page to work */
		public String findFirstLink(){
			String link = "", paragraph;
			
			Matcher m = paraPat.matcher(pageData);
			m.reset();
			m.find();
			
			paragraph = m.group(1);
			
			/*Strip all content not in quotes but within parentheses*/
			int parenCount = 0, firstParen = paragraph.length()-1, lastParen = 0;
			StringBuilder sb = new StringBuilder();
			boolean inQuotes = false;
			for(int i = 0; i < paragraph.length(); ++i)
			{
				if((paragraph.charAt(i) == '\"'))
				{
					inQuotes = !inQuotes;
				}
				
				if((paragraph.charAt(i) == '(') && !inQuotes)
				{
					parenCount++;
					firstParen = i;
				}

				if(paragraph.charAt(i) == ')' && !inQuotes)
				{
					if(--parenCount == 0)
					{
						/*Add from the end of last sequence, to start of this one*/
						sb.append(paragraph.substring(lastParen, firstParen));
						firstParen = paragraph.length()-1;
						lastParen = i+1;
					}
				}	
			}
			
			sb.append(paragraph.substring(lastParen, firstParen));
			
			paragraph = sb.toString();
			m = linkPat.matcher(paragraph);
			
			while(m.find())
			{
				/* Ignore help pages and self links */
				if(!m.group(1).contains("Help")) {
					link = m.group(1);
					link = link.replaceAll("/wiki/", "").trim();
					if(link.charAt(0) == '#'){
						link = "";
						continue;
					} else {
						break;
					}
				}
			}

			
			return link;		
		}
		
		public String findName() {
			String pageName = "";
			Pattern namePat = Pattern.compile("<span dir=\"auto\">(.*?)</span>");
			Matcher m = namePat.matcher(pageData);
			if (!m.find()) return null;
			
			pageName = m.group(1);
			
			this.name = pageName;
			return pageName;
		}
	}
	
	public static String urlConnect(String address) throws Exception {
		StringBuilder sb = new StringBuilder();
		URL url = new URL(address);
		URLConnection spoof = url.openConnection();
		int count = 0;

		// Spoof the connection so we look like a web browser
		spoof.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
		BufferedReader in = new BufferedReader(new InputStreamReader( spoof.getInputStream()));
		char[] buf = new char[2048];

		// Loop through every line in the source
		while ((count = in.read(buf)) != -1) {
			sb.append(buf, 0, count);
		}
		
		in.close();
		return sb.toString();
	}
	 

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		HashSet<String> alreadyVisited = new HashSet<String>();
		Queue<WebPage> pageList = new LinkedList<WebPage>();
		
		WikiBrowser base = new WikiBrowser();
		
		Scanner scan = new Scanner(System.in);
		//String from = scan.nextLine().replaceFirst("From:", "").trim();
		//String target = scan.nextLine().replaceFirst("To:", "").trim();
		String target = "Logic";
		String from = "Food";
		String link = "";
		
		WebPage page = base.new WebPage();
		page.pageData = urlConnect(urlBase + from);
		page.findName();
		link = page.findFirstLink();
		
		while(link.length() > 0) {
			pageList.add(page);
			
			if(link.equalsIgnoreCase(target)){
				break;
			}
			else
			{
				alreadyVisited.add(page.name.toLowerCase());
				if(! alreadyVisited.contains(link.replaceAll("_", " ").toLowerCase())){
					page = base.new WebPage();
					page.pageData = urlConnect(urlBase + link);
					page.findName();
					link = page.findFirstLink();
				} else {
					/* hit a loop :( */
					break;
				}
				
			}
		}
		for(WebPage item : pageList){
			System.out.println(item.name);
		}
		System.out.println(link);
	}
}
