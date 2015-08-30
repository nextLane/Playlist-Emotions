
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

import synesketch.emotion.EmotionalState;
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
		        searchSongs.add("English Lyrics "+listOfFiles[i].getName().replace(".mp3", ""));
		      } 
		    }
		    
		    return searchSongs;
	}
	public static String getLyrics(String url)
	{
		String lyrics="";
		if(url==null)
			return lyrics;
		
		if(url.contains("lyricsfreak"))
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

			Element division= doc.getElementById("content_h");

			
		
			    
			    	lyrics+=division.text()+" ";
			}
	
		else  if(url.contains("metro"))
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
			
			Element division= doc.getElementById("lyrics-body-text");
			Elements paragraphs = division.getElementsByTag("p");
			// Select the <p> Elements from the document
			

			// For each selected <p> element, print out its text
			
			for (Element e : paragraphs) {
			   
			    	lyrics+=e.text()+" ";
			    
			  
			}

			System.out.println("Metro exists");
			
		}
		//bollywood song case
		else if(url.contains("hindilyrics"))
		{
			System.out.println("*** Bollywood Song ***");
			
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Corrupted URL");
				e1.printStackTrace();
				return lyrics;
				
			}
			
			Elements ee = doc.getElementsByAttributeValueStarting("id", "t");

			for (Element e : ee) {
			   
			    	lyrics+=e.text()+" ";
			    
			  
			}
						  
			


		}
		
		else
		{
			System.out.println("*** Bollywood Song bolly wala***");
			
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Corrupted URL");
				e1.printStackTrace();
				return lyrics;
				
			}
			
			Elements ee = doc.getElementsByAttributeValueStarting("itemprop", "description articleBody");

			for (Element e : ee) {
			       System.out.println("processing...");
			    	lyrics+=e.text()+" ";
			    
			  
			}
						  

		}
		
		lyrics.replaceAll("<br>", " ");
		lyrics.replaceAll("</br>", " ");
		lyrics.replaceAll("<p>", " ");
		lyrics.replaceAll("</p>", " ");
		
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
            if (url.contains("www.metrolyrics.com") || url.contains("www.lyricsfreak.com") || url.contains("www.hindilyrics.net") || url.contains("www.bollymeaning.com"))
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
			double []emoWeights = new double[6];
		    String path= "C:\\Users\\.hp\\Music\\MobileSongs";
		    ArrayList<String> searchSongs=getSongs(path);
		    
		    
		    for(int index=0; index<searchSongs.size() ; index++)
		    {
		    	String search= searchSongs.get(index);
			    
			    String lyrics="";
			    String lyricsUrl="";
			    try
			    {
			    	lyricsUrl=getLyricsUrl(search);
			    	System.out.println("****URL***:"+lyricsUrl);
			    	lyrics=getLyrics(lyricsUrl);
				    
			    }
			    catch(Exception e)
			    {
			    	System.out.println("Must be bad internet connection!");
			    	e.printStackTrace();
			    }
			    
			    
			    Empathyscope es;
			    try {
			    	es = new Empathyscope();
			    	EmotionalState emo= es.feel(lyrics);
			    	System.out.println("Feel goes here:" +emo);
			    	
			        emoWeights[0]+= emo.getHappinessWeight();
			        emoWeights[1]+= emo.getSadnessWeight();
			        emoWeights[2]+= emo.getAngerWeight();
			        emoWeights[3]+= emo.getFearWeight();
			        emoWeights[4]+= emo.getDisgustWeight();
			        emoWeights[5]+= emo.getDisgustWeight();
			        
			        
			       
			        
			    	
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		    }
		    
		    
		    double positiveSentiment= (emoWeights[0]+emoWeights[5])/2;
		    double negativeSentiment= (emoWeights[1]+emoWeights[2]+emoWeights[3]+emoWeights[4])/4;
		    
		    System.out.println("Positivity:"+ (positiveSentiment)*100/(positiveSentiment+negativeSentiment)+"%  Negative Sentiment:"+(negativeSentiment)*100/(positiveSentiment+negativeSentiment)+"%");
		    
	}


}
