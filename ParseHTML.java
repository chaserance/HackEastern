import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.*;

public class ParseHTML
{
	ArrayList<HtmlPage> pagesToParse;
	ArrayList<Integer> numberOfSeatsAvailable;
	ArrayList<Integer> numberOfWaitlistAvailable;
	Search mySearch;
	WebClient myClient;
	
	
	public ParseHTML() throws Exception
	{
		mySearch = new Search("COSC","511");
		myClient = mySearch.myClient;
		pagesToParse = mySearch.pages;
		
		for(HtmlPage p: pagesToParse)
		{
			List<HtmlElement> tags=p.getDocumentElement().getElementsByAttribute("th", "CLASS", "ddtitle");
			System.out.println(p.asText());
		}
	}
	
	public void initClient()
	{
		
	}
}