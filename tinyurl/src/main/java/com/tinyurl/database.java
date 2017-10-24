package com.tinyurl;

public interface database {
    void connect(String user, String pass);
    void  setter(String key, String value, String value1);
    String getter(String key, String whichurl);
    void disconnect();
}


