import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class ImplementBrowser implements Browser
{
	public boolean logSuccessful(String EID, String PIN) 
	{
		final WebClient myClient = new WebClient(BrowserVersion.CHROME);;
		final String USERNAME = EID;
		final String PASSWORD = PIN;
		String banner = "https://bannerweb.emich.edu/pls/banner/twbkwbis.P_WWWLogin";
		boolean flag = true;
			
		myClient.getOptions().setThrowExceptionOnScriptError(false);
		myClient.getOptions().setJavaScriptEnabled(true);
	    myClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    myClient.getOptions().setTimeout(300000);
	    myClient.getOptions().setRedirectEnabled(true);
		HtmlPage page1 = null;
		try {
			page1 = myClient.getPage(banner);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final HtmlForm form = (HtmlForm) page1.getFormByName("loginform");
		final HtmlTextInput textField = form.getInputByName("sid");
		textField.setValueAttribute(USERNAME);
		final HtmlPasswordInput textField2 = form.getInputByName("PIN");
		textField2.setValueAttribute(PASSWORD);
		HtmlPage page2 = null;
		try {
			page2 = (HtmlPage) form.getInputByValue("Login").click();
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HtmlForm form2 = null;
		try{
			form2 = page2.getFormByName("loginform");
		} catch(ElementNotFoundException e){
			flag = false;
			e.printStackTrace();
		}
		System.out.println(form2);

		return flag;
	}
	
	public String[] majorList() throws Exception
	{
		return new ParseList().classNamesArray;
	}
}
