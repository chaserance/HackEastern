

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;



public class HttpUrlConnectionExample {
  public static void main(String[] args) throws Exception {
	  final WebClient webClient = new WebClient(BrowserVersion.CHROME);

	  webClient.getOptions().setThrowExceptionOnScriptError(false);
	  webClient.getOptions().setJavaScriptEnabled(true);
      webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
      webClient.getOptions().setTimeout(300000);
      String emu = "https://netid.emich.edu/cas/login?service=https%3A%2F%2Fmy.emich.edu%2Fc%2Fportal%2Flogin";
      String banner = "https://bannerweb.emich.edu/pls/banner/twbkwbis.P_WWWLogin";
	  final HtmlPage page1 = webClient.getPage(banner);
	  final HtmlForm form = (HtmlForm) page1.getFormByName("loginform");
	  //System.out.println(form.asXml());
	  //final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputsByValue("LOGIN");
	  final HtmlTextInput textField = form.getInputByName("sid");
	  textField.setValueAttribute(*******");
	  final HtmlPasswordInput textField2 = form.getInputByName("PIN");
	  textField2.setValueAttribute("******");
	  final HtmlPage page2 = (HtmlPage) form.getInputByValue("Login").click();
	  //String cookie = page2.getElementById("js_cookie").getAttribute("value");
	  System.out.println(page2.asText());
	  System.out.println("*************************************************************************************");
	  System.out.println(page2.asXml());
	  
	  //final HtmlPage page3  = webClient.getPage("http://my.emich.edu"); 
	  //System.out.println(page2.asText());
	  //System.out.println(page3.asXml());
      //System.out.println("*************************************************************************************");
      //System.out.println(page2.getWebResponse().getContentAsString());
      System.out.println("*************************************************************************************");
      System.out.println("");
      //System.out.println("Cookies :");
      //Object[] arr = webClient.getCookieManager().getCookies().toArray();
      final HtmlPage ss = webClient.getPage("https://bannerweb.emich.edu/pls/banner/twbkwbis.P_GenMenu?name=bmenu.P_StuMainMnu");
      System.out.println(ss.asText());
      final HtmlPage weekGlance = webClient.getPage("https://bannerweb.emich.edu/pls/banner/bwskfshd.P_CrseSchd");
      System.out.println(weekGlance.asText());
      webClient.close();
  }
}
