package edu.emich.hackeastern.Utility;


public interface Browser {
	/**
	 * Return true if EID/PIN is correct.
	 * @param EID
	 * @param PIN
	 * @return 
	 */
	public boolean logSuccessful(String EID, String PIN) throws Exception;
	/**
	 * Return a ArrayList with major name  
	 * @return
	 */
	public String[] majorList() throws Exception;
}
