package com.tinyurl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class mysqldb implements database {
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    String url = "jdbc:mysql://localhost:3306/tinyurldb";
    String user = "test";
    Logger logger = tinyLogger.getInstance().getLogger();

    public void connect(String bucket_name, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            String sql = "CREATE TABLE tinyurl ( " +
                    "key VARCHAR(255), " +
                    " longurl VARCHAR(255), " +
                    " shorturl VARCHAR(255)) " ;

            st.executeUpdate(sql);
        } catch(SQLException ex) {
            logger.error("Unable to connect mySQL " + ex);
        } catch(ClassNotFoundException ex) {
            logger.error(ex);
        }
        finally {
            disconnect();
        }
    }

    public void setter(String key, String lurl, String surl) {
        try {
            String sql = ("Insert into tinyurl(key, longurl, shorturl) " +
                    "values (|%s|,|%s|,|%s|)").
                    format(key, lurl, surl);
            st.executeUpdate(sql);
        } catch(SQLException ex) {
            logger.error("Unable to connect mySQL " + ex);
        }
    }

    public String getter(String key, String whichurl) {

        try {
            String sql = "SELECT " + whichurl + "from  tinyurldb where key="+key;
            ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            return rs.getString(whichurl);
        }
        }   catch (SQLException ex) {
        }
        finally {
            return null;
        }
    }

    public void disconnect() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {

        }
    }
}
