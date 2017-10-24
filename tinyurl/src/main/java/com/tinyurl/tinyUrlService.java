package com.tinyurl;

import org.apache.log4j.Logger;

public class tinyUrlService {
    public static void main(String[] args) throws Exception{
        Logger logger = tinyLogger.getInstance().getLogger();
        httpserver htt = null;
        database dbObj = null;
        logger.info(" Started test");
        try {
            htt = new httpserver(8080);
            dbObj = new cbDB();
            dbObj.connect("tiny-url", "password");
            htt.start(dbObj);
        }
        finally {
            logger.info(" Test Closed. HTTP Server stopped");
        }
    }
}
