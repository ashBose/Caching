package main.java.com.tinyurl;

public class tinyurlservice {

    public static void main(String[] args) throws Exception{
        httpserver htt = null;
        database dbObj = null;
        try {
            htt = new httpserver(8080);
            dbObj = new cbDB();
            dbObj.connect("tiny-url", "password");
            htt.start(dbObj);
        }
        finally {

        }
    }
}
