package edu.emich.hackeastern.Utility;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;


public class Search implements Closeable{
	//instance variables
	private WebClient myClient;
	private String classCode;
	private String classNum;
	private ArrayList<HtmlPage> pages;
	private HtmlPage currentPage;
	private String banner;
	
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
		List<HtmlElement> tags=currentPage.getDocumentElement().getElementsByAttribute("th", "CLASS", "ddtitle");
		
		ArrayList<String> links = new ArrayList<String>();
		for(HtmlElement e : tags)
		{
			String current = e.asXml();
			int start = current.indexOf("a href=");
			int end = current.indexOf("\"",start+9);
			String result = current.substring(start+8,end);
			links.add(result);
		}				
		getPageContent(links, currentPage);
	}
	
	private void getPageContent(ArrayList<String> patterns, HtmlPage page)
	{
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
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<HtmlPage> getPages(){
		return pages;
	}
	
	public WebClient getWebClient(){
		return myClient;
	}
	
	public void close(){
		myClient.close();
	}
}
