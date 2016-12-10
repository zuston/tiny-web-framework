package io.github.zuston.framework.util;

import com.mysql.jdbc.Connection;
import io.github.zuston.framework.helper.configHelper;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zuston on 16-12-10.
 */
public class dbUtil {
    public static String dbName = configHelper.dbName();
    public static String dbUsername = configHelper.dbUsername();
    public static String dbPassword = configHelper.dbPassword();

    private static ThreadLocal<Connection> connContainer = new ThreadLocal<>();

    public Connection getInstance(){
        Connection conn = connContainer.get();
        if (conn==null){
            try {
                System.out.println("init the connection");
                String dbUrl = String.format("jdbc:mysql://localhost:3306/%s?user=%s&password=%s&characterEncoding=utf8",dbName,dbUsername,dbPassword);
                Class.forName("com.mysql.jdbc.Driver") ;
                conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(dbUrl);
                connContainer.set(conn);
            } catch (SQLException e) {
                System.out.println("connection error");
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                System.out.println("can not find the jdbc driver");
                e.printStackTrace();
            }
        }
        return conn;
    }


    public void close(){
        Connection conn = connContainer.get();
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connContainer.remove();
        }
    }

}
