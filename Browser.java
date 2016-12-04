import java.io.IOException;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;


public interface Browser 
{
	public boolean logSuccessful(String EID, String PIN);
	/**
	 * Return a string array with major name  
	 * @return
	 * @throws Exception 
	 */
	public String[] majorList() throws Exception;
}
