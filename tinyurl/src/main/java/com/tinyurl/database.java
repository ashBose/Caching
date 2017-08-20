package main.java.com.tinyurl;

public interface database {

    public void connect(String user, String pass);
    public void  setter(String key, String value, String value1);
    public String getter(String key, String whichurl);
    public void disconnect();
}


