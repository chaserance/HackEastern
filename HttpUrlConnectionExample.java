
/* jiansong he's version*/
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;



public class HttpUrlConnectionExample {
  public static void main(String[] args) throws Exception {
	  final WebClient webClient = new WebClient(BrowserVersion.CHROME);

	  webClient.getOptions().setThrowExceptionOnScriptError(false);
	  webClient.getOptions().setJavaScriptEnabled(true);
      webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
      webClient.getOptions().setTimeout(300000);
      webClient.getOptions().setRedirectEnabled(true);
      String emu = "https://netid.emich.edu/cas/login?service=https%3A%2F%2Fmy.emich.edu%2Fc%2Fportal%2Flogin";
      String banner = "https://bannerweb.emich.edu/pls/banner/twbkwbis.P_WWWLogin";
	  final HtmlPage page1 = webClient.getPage(banner);
	  final HtmlForm form = (HtmlForm) page1.getFormByName("loginform");
	  //System.out.println(form.asXml());
	  //final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputsByValue("LOGIN");
	  final HtmlTextInput textField = form.getInputByName("sid");
	  textField.setValueAttribute("E01485860");
	  final HtmlPasswordInput textField2 = form.getInputByName("PIN");
	  textField2.setValueAttribute("617420");
	  final HtmlPage page2 = (HtmlPage) form.getInputByValue("Login").click();
	  //String cookie = page2.getElementById("js_cookie").getAttribute("value");
	  System.out.println(page2.asText());
	  System.out.println("*************************************************************************************");
	  //final HtmlPage page3 = page2.getAnchorByHref("https://bannerweb.emich.edu/pls/banner/twbkwbis.P_GenMenu?name=bmenu.P_RegMnu").click();
	  //System.out.println(page3.asText());
	  //System.out.println(page2.asXml());
	  
	  //final HtmlPage page3  = webClient.getPage("http://my.emich.edu"); 
	  //System.out.println(page2.asText());
	  //System.out.println(page3.asXml());
      //System.out.println("*************************************************************************************");
      //System.out.println(page2.getWebResponse().getContentAsString());
      //System.out.println("*************************************************************************************");
      //System.out.println("Cookies :");
      //Object[] arr = webClient.getCookieManager().getCookies().toArray();
      final HtmlPage ss = webClient.getPage("https://bannerweb.emich.edu/pls/banner/twbkwbis.P_GenMenu?name=bmenu.P_StuMainMnu");
      System.out.println(ss.asText());
      System.out.println("!!*************************************************************************************");
      final HtmlPage major = webClient.getPage("https://bannerweb.emich.edu/pls/banner/twbkwbis.P_GenMenu?name=bmenu.P_RegMnu");
      System.out.println(major.asText());
      final HtmlPage search = major.getAnchorByHref("/pls/banner/bwskfcls.p_sel_crse_search").click();
      System.out.println(search.asText());
      System.out.println("!!!!*************************************************************************************");
      final HtmlForm termForm = search.getForms().get(1);
      termForm.setAttribute("onsubmit", "");
      
      final HtmlSelect select = (HtmlSelect) search.getElementById("term_input_id");
      final HtmlOption option = select.getOptionByValue("201720");
      select.setSelectedAttribute(option, true);
      final HtmlPage submit = termForm.getInputByValue("Submit").click();
      //System.out.println(option);
      System.out.println(submit.asText());
      System.out.println("!!!!!!!*************************************************************************************");
      final HtmlSelect majorSelect = (HtmlSelect) submit.getElementById("term_input_id");
  }
}
