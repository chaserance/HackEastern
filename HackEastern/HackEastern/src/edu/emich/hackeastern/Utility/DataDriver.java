package edu.emich.hackeastern.Utility;

import java.util.*;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class DataDriver implements Browser{
	
	//Instance variables
	private final WebClient webClient;
	private HtmlPage major;
	private HtmlPage lookUpClass;
	private HtmlForm termForm;
	private HtmlSelect termSelect;
	private HtmlOption termOption;
	private HtmlPage termSubmit;
	private HtmlSelect majorSelect;
	private HashMap<String,String> majorSubject;
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
	    majorSubject = new HashMap<>();
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
		final HtmlPage returnPage = (HtmlPage) form.getInputByValue("Login").click();
		boolean isLogged = returnPage.asText().indexOf("Login Not Allowed")==-1;
		return isLogged;
	}

	@Override
	public String[] majorList() throws Exception{
		
		major = webClient.getPage("https://bannerweb.emich.edu/pls/banner/twbkwbis.P_GenMenu?name=bmenu.P_RegMnu");
	    lookUpClass = major.getAnchorByHref("/pls/banner/bwskfcls.p_sel_crse_search").click();
	    termForm = lookUpClass.getForms().get(1);
	    termSelect = (HtmlSelect) lookUpClass.getElementById("term_input_id");
	    termOption = termSelect.getOption(1);
	    termSelect.setSelectedAttribute(termOption, true);
	    termSubmit = termForm.getInputByValue("Submit").click();
	    
	    majorSelect = (HtmlSelect) termSubmit.getElementById("subj_id");
	    List<HtmlOption> list = majorSelect.getOptions();
	    String[] result = new String[list.size()];
	    int i = 0;
	    for(HtmlOption e : list){
	    	String key = e.asText();
	    	String value = e.getAttribute("value");
	    	result[i++] = key;
	    	majorSubject.put(key,value);
	    }
	    return result;
	}
	
	public HashMap<String,String> getMajorDic(){
		return majorSubject;
	}
}
