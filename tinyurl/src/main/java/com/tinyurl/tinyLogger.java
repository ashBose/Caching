package com.tinyurl;

import org.apache.log4j.Logger;

class tinyLogger {

    private static tinyLogger instance = null;
    static Logger logger;
    private tinyLogger() {}
    public static tinyLogger getInstance() {
        if(instance == null) {
            instance = new tinyLogger();
            logger = Logger.getLogger(tinyLogger.class);
        }
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }
}