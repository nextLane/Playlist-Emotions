
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import synesketch.emotion.Empathyscope;

public class trial {
	public static void main(String args[])
	{
		File folder = new File("C:\\Users\\.hp\\Music");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  if(listOfFiles[i].getName().contains(".mp3") ||listOfFiles[i].getName().contains(".wav"))
		        System.out.println(listOfFiles[i].getName());
		      } 
		    }
		    
		    String google = "http://www.google.com/search?q=";
		    String search = "lyrics Hard Sun - Eddie Vedder [Original] Into The Wild Soundtrack";
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

		        System.out.println("Title: " + title);
		        System.out.println("URL: " + url);
		    }
			
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		System.out.println("Laal Ishq");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		System.out.println("Kare halal ishq");
	}
			
		
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
	
	}


}
