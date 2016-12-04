package edu.emich.hackeastern.Utility;

import com.gargoylesoftware.htmlunit.html.*;

import java.util.*;

public class ParseHTML
{
	//instance variables
	private ArrayList<HtmlPage> pagesToParse;
	private ArrayList<Integer> numberOfSeatsAvailable;
	private ArrayList<Integer> numberOfWaitlistAvailable;
	private Search mySearch;
	
	/**
	 * Constructor
	 * @param a
	 * @param b
	 * @throws Exception
	 */
	public ParseHTML(String a, String b) throws Exception
	{
		mySearch = new Search(a,b); 
		pagesToParse = mySearch.getPages();
		numberOfSeatsAvailable = new ArrayList<>();
		numberOfWaitlistAvailable = new ArrayList<>();
		List<HtmlElement> tempTagsContainer;
		for(HtmlPage p: pagesToParse){
			tempTagsContainer = p.getDocumentElement().getElementsByAttribute("td", "CLASS", "dddefault");
			numberOfSeatsAvailable.add(Integer.parseInt(tempTagsContainer.get(3).asText()));
			numberOfWaitlistAvailable.add(Integer.parseInt(tempTagsContainer.get(6).asText()));
		}		
	}
	
	public static void main (String[] args) throws Exception{
		ParseHTML ph = new ParseHTML("COSC","511");
		System.out.println(ph.getRemainSeats());
	}
	
	public ArrayList<ArrayList<Integer>> getRemainSeats()
	{
		ArrayList<ArrayList<Integer>> re = new ArrayList<>();
		re.add(numberOfSeatsAvailable);
		re.add(numberOfWaitlistAvailable);
		return re;
	}
}
