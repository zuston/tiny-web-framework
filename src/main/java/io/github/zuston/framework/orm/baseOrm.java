package io.github.zuston.framework.orm;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zuston on 16-11-28.
 */
 abstract public class baseOrm {
    public static Connection conn = null;
    private void getConn(){
        try {
            System.out.println("初始化连接");
            String url="jdbc:mysql://localhost:3306/patent?user=root&password=shacha&characterEncoding=utf8";
            Class.forName("com.mysql.jdbc.Driver") ;
            conn = (Connection) DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("连接错误");
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            System.out.println("找不到驱动");
            e.printStackTrace();
        }
    }
    public boolean save() throws IllegalAccessException {
        if(conn==null){
            getConn();
        }

        int paramCount = this.getClass().getDeclaredFields().length;
        String url = String.format("insert into %s(%s) values(%s)",
                this.getClass().getSimpleName(),
                this.getClass().getDeclaredFields()[0].getName(),
                this.getClass().getDeclaredFields()[0].get(this));

        String tableName = this.getClass().getSimpleName();
        String sqlFormatFront = "insert into "+tableName+"(";
        String sqlFormatEnd = "values(";
        for(java.lang.reflect.Field field:this.getClass().getDeclaredFields()){
            String name = field.getName();
            Object type = field.getType();
            Object value = field.get(this);
            sqlFormatFront = sqlFormatFront+name+",";
            if(type.equals(String.class)){
                sqlFormatEnd = sqlFormatEnd + "'" + (String)value + "',";
            }else{
                sqlFormatEnd = sqlFormatEnd + value + ",";
            }
        }
        sqlFormatFront = sqlFormatFront.substring(0,sqlFormatFront.length()-1)+")";
        sqlFormatEnd = sqlFormatEnd.substring(0,sqlFormatEnd.length()-1)+")";
        String sql = sqlFormatFront + " " + sqlFormatEnd;
        System.out.println(sql);

        try {
            Statement state = conn.createStatement();
            return state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static HashMap<String,Object> sqlCondition = new HashMap<String, Object>();
    public baseOrm find(){
        String tableName = this.getClass().getSimpleName();
        if (sqlCondition.isEmpty()){
            sqlCondition.put("tableName",tableName);
            return this;
        }
        return this;
    }

    public baseOrm findOne(){
        return this;
    }


    public baseOrm where(HashMap<String,Object> hs) throws Exception {
        if (!sqlCondition.isEmpty()){
            sqlCondition.put("condition",hs);
            return this;
        }else{
            throw new Exception("condition error");
        }
    }

    public Object one() throws Exception {
        if(!sqlCondition.isEmpty()){
            String tableName = (String) sqlCondition.get("tableName");
            HashMap<String, Object> condition = (HashMap<String,Object>)sqlCondition.get("condition");
            if(tableName==null){
                throw new Exception("tableName error");
            }
            String baseSql = "select * from ";
            baseSql += "company";
            baseSql += " where ";
            String conditionSql = "";
            if(condition!=null){
                for(Map.Entry<String,Object> mp:condition.entrySet()){
                    conditionSql += ""+mp.getKey()+"='"+mp.getValue()+"' and ";
                }
            }
            String limitSql = " limit 1";
            String sql = baseSql;
            if(!conditionSql.equals("")){
                String csql = conditionSql.substring(0,conditionSql.lastIndexOf("and"));
                sql += csql;
            }
            sql += limitSql;
            System.out.println(sql);
//            Statement sts =null;
//            sts = conn.createStatement();
//            ResultSet res = sts.executeQuery(sql);
//            while (res.next()){
//
//            }
            return null;
        }
        return null;
    }

    public baseOrm all(){
        return this;
    }
}
