package edu.emich.hackeastern.Utility;

import java.util.List;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class DataDriver implements Browser{
	
	//Instance variables
	private final WebClient webClient;
	private HtmlPage returnPage;
	private static final String banner = "https://bannerweb.emich.edu/pls/banner/twbkwbis.P_WWWLogin";
	public DataDriver(){
		//initialization
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    webClient.getOptions().setPrintContentOnFailingStatusCode(false);
	    webClient.getOptions().setTimeout(300000);
	    webClient.getOptions().setRedirectEnabled(true);
	}

	@Override
	public boolean logSuccessful(String EID, String PIN) throws Exception{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
		final HtmlPage loginPage = webClient.getPage(banner);
		final HtmlForm form = (HtmlForm) loginPage.getFormByName("loginform");
		final HtmlTextInput username = form.getInputByName("sid");
		username.setValueAttribute(EID);
		final HtmlPasswordInput password = form.getInputByName("PIN");
		password.setValueAttribute(PIN);
		returnPage = (HtmlPage) form.getInputByValue("Login").click();
		boolean isLogged = returnPage.asText().indexOf("Login Not Allowed")!=-1;
		return isLogged;
	}

	@Override
	public String[] majorList() throws Exception{
		final HtmlPage major = webClient.getPage("https://bannerweb.emich.edu/pls/banner/twbkwbis.P_GenMenu?name=bmenu.P_RegMnu");
	    System.out.println(major.asText());
	    final HtmlPage lookUpClass = major.getAnchorByHref("/pls/banner/bwskfcls.p_sel_crse_search").click();
	    final HtmlForm termForm = lookUpClass.getForms().get(1);
	    final HtmlSelect termSelect = (HtmlSelect) lookUpClass.getElementById("term_input_id");
	    final HtmlOption termOption = termSelect.getOption(0);
	    termSelect.setSelectedAttribute(termOption, true);
	    final HtmlPage termSubmit = termForm.getInputByValue("Submit").click();
	    
	    final HtmlSelect majorSelect = (HtmlSelect) termSubmit.getElementById("term_input_id");
	    List<HtmlOption> list = majorSelect.getOptions();
	    String[] result = new String[list.size()];
	    int i = 0;
	    for(HtmlOption e : list)
	    	result[i++] = e.asText();
	    return result;
	}
}
