package edu.emich.hackeastern.Utility;

import java.util.*;

public interface Browser {
	/**
	 * Return true if EID/PIN is correct.
	 * @param EID
	 * @param PIN
	 * @return 
	 */
	public boolean logSuccessful(String EID, String PIN);
	/**
	 * Return a ArrayList with major name  
	 * @return
	 */
	public ArrayList<String> majorList();
}
