
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import synesketch.emotion.Empathyscope;

public class trial {
	
	public static ArrayList<String> getSongs(String path)
	{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> searchSongs= new ArrayList<String>();
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  if(listOfFiles[i].getName().contains(".mp3") ||listOfFiles[i].getName().contains(".wav"))
		        searchSongs.add("Lyrics "+listOfFiles[i].getName().replace(".mp3", ""));
		      } 
		    }
		    
		    return searchSongs;
	}
	public static String getLyrics(String url)
	{
		String lyrics="";
		
		if(url.contains("direct"))
		{
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Corrupted URL");
				e1.printStackTrace();
				return lyrics;
				
			}

			// Select the <p> Elements from the document
		
			Elements paragraphs = doc.select("p");

			// For each selected <p> element, print out its text
			int count=0;
			for (Element e : paragraphs) {
			    if(count>0)
			    {
			    	lyrics+=e.text()+" ";
			    }
			    //chances of slight error in emotion , one/two facts may be involved
				count ++;
			}
		}
		else
		{
			
		}
		
		return lyrics;
		
	}
	public static String getLyricsUrl(String s)
	{
		String google = "http://www.google.com/search?q=";
	    String search = s;
	    String charset = "UTF-8";
	    String userAgent = "Student Application"; // Change this to your company's name and bot homepage!

	    Elements links;
	 	 	    
		try {
			links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select("li.g>h3>a");
	
	    for (Element link : links) {
	        String title = link.text();
	        String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
	        url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

	        if (!url.startsWith("http")) {
	            continue; // Ads/news/etc.
	        }
            if (url.contains("www.metrolyrics.com") || url.contains("www.directlyrics.com"))
            {
            	return url;
            }
            
	    }
		
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	   
	}
	
	public static void main(String args[])
	{
		
		    String path= "C:\\Users\\.hp\\Music";
		    ArrayList<String> searchSongs=getSongs(path);
		    
		    String search= "Lyrics Alexandra Stan - Lemonade 32";
		    System.out.println(getLyricsUrl(search));
		    
		    System.out.println(getLyrics(getLyricsUrl(search)));
		    
		    /*
		    		Empathyscope es;
		String str1= "Totally fucked up, I felt like crying today, want someone to lean on , missing my friend badly.";
		String str2="This red love, this remorse of love, this defect that love is, this enmity of love, Love, love, love, I have had such an enmity with you, That I haven't remained like myself anymore";
	String str3= "(Hey) now we got problems And I don't think we can solve them You made a really deep cut And baby now we got bad blood (Hey) did you have to do this I was thinking that you could be trusted Did you have to ruin what was shining now it's all rusted"+
"Did you have to hit me where I am weak baby I couldnt breathe";
		try {
			es = new Empathyscope();
		
			System.out.println("Feel goes here:" +es.feel(str3));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
	}


}
