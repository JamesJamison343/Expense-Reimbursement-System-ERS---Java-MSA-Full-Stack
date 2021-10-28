package com.project1.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogHelper {
	public final Logger log = Logger.getLogger(LogHelper.class);
	
	public void ExceptionLog(Exception e) {
		log.setLevel(Level.DEBUG);
        e.printStackTrace();
        log.debug(e);
	}
	
	public void infoLogger(String i) {
        log.setLevel(Level.INFO);
        log.info(i);
    }
	
	
}
