import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.*;


public class Search 
{
	final WebClient myClient;
	final String USERNAME = "E01247487";
	final String PASSWORD = "910813";
	String classCode;
	String classNum;
	
	ArrayList<HtmlPage> pages;
	
	HtmlPage currentPage;
	//String banner = "http://catalog.emich.edu/content.php?catoid=20&navoid=4199";
	String banner;
	
	public Search(String code, String num) throws Exception 
	{
		this.pages = new ArrayList<HtmlPage>();
		this.classCode = code.toUpperCase();
		this.classNum = num;
		this.banner = "https://bannerweb.emich.edu/pls/berp/bwckctlg.p_disp_listcrse?term_in=201720&subj_in="
				+ classCode + "&crse_in="+ classNum +"&schd_in=%";
		myClient = new WebClient(BrowserVersion.CHROME);
		myClient.getOptions().setThrowExceptionOnScriptError(false);
		myClient.getOptions().setJavaScriptEnabled(false);
	    myClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    myClient.getOptions().setTimeout(300000);
	    
		this.currentPage = myClient.getPage(banner);							
		/*loging into the webpage by currentPage info
		final HtmlForm form = (HtmlForm) currentPage.getFormByName("loginform");
		final HtmlTextInput textField = form.getInputByName("sid");
		textField.setValueAttribute(USERNAME);
		final HtmlPasswordInput textField2 = form.getInputByName("PIN");
		textField2.setValueAttribute(PASSWORD);
		currentPage = (HtmlPage) form.getInputByValue("Login").click();
		*/
		/*
		//set select option to course code
		HtmlSelect select = (HtmlSelect)currentPage.getElementByName("filter[27]");
		HtmlOption option = select.getOptionByValue(classCode);
		select.setSelectedAttribute(option, true);
		// set option to curse ID
		HtmlTextInput textField = (HtmlTextInput)currentPage.getElementByName("filter[29]");
		textField.setValueAttribute(classNum);
		currentPage = (HtmlPage) currentPage.getFormByName("course_search").getInputByValue("Filter").click();
		//click on available link
		HtmlAnchor link = (HtmlAnchor) currentPage.getFormByName("course_search").getElementsByTagName("a");
		System.out.println(link.asXml());
		*/
		//this.textResult = currentPage.asText();
		//this.xmlResult = currentPage.asXml();
		
		//System.out.println(currentPage.asXml());
		List<HtmlElement> tags=currentPage.getDocumentElement().getElementsByAttribute("th", "CLASS", "ddtitle");
		
		ArrayList<String> links = new ArrayList<String>();
		for(HtmlElement e : tags)
		{
			String current = e.asXml();
			//System.out.println(current);
			int start = current.indexOf("a href=");
			int end = current.indexOf("\"",start+9);
			String result = current.substring(start+8,end);
			//System.out.println(result);
			links.add(result);
		}		
		
		getPageContent(links, currentPage);
		for(HtmlPage p: this.pages)
		{
			System.out.println(p.asText());
		}
		myClient.close();

	}
	
	private void getPageContent(ArrayList<String> patterns, HtmlPage page)
	{
		//String url ="https://bannerweb.emich.edu/";
		ArrayList<HtmlAnchor> anchors = new ArrayList<HtmlAnchor>();
		ArrayList<HtmlPage> pages = new ArrayList<HtmlPage>();
		for(String s : patterns)
		{
			s = s.replaceAll("&amp;", "&");
			HtmlAnchor anchor = page.getAnchorByHref(s);
			try 
			{
				this.pages.add(anchor.click());
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}