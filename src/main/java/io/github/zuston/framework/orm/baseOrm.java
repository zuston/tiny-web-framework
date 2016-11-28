package io.github.zuston.framework.orm;

import com.mysql.jdbc.Connection;
import io.github.zuston.framework.helper.configHelper;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuston on 16-11-28.
 */
 public class baseOrm {
    public static Connection conn = null;

    public static String dbName = configHelper.dbName();
    public static String dbUsername = configHelper.dbUsername();
    public static String dbPassword = configHelper.dbPassword();

    private void getConn(){
        try {
            System.out.println("初始化连接");
            String dbUrl = String.format("jdbc:mysql://localhost:3306/%s?user=%s&password=%s&characterEncoding=utf8",dbName,dbUsername,dbPassword);
            Class.forName("com.mysql.jdbc.Driver") ;
            conn = (Connection) DriverManager.getConnection(dbUrl);
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
        if(conn==null){
            getConn();
        }
        if(!sqlCondition.isEmpty()){
            String tableName = (String) sqlCondition.get("tableName");
            HashMap<String, Object> condition = (HashMap<String,Object>)sqlCondition.get("condition");
            if(tableName==null){
                throw new Exception("tableName error");
            }
            String baseSql = "select * from ";
            baseSql += tableName;
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
            Statement sts = conn.createStatement();
            ResultSet res = sts.executeQuery(sql);
            // TODO: 16/11/28 生成一个对象模型
            Object model = generateModel(tableName,res);
            return model;
        }
        return null;
    }

    public List<Object> all() throws Exception {
        if(conn==null){
            getConn();
        }
        if(!sqlCondition.isEmpty()){
            String tableName = (String) sqlCondition.get("tableName");
            HashMap<String, Object> condition = (HashMap<String,Object>)sqlCondition.get("condition");
            if(tableName==null){
                throw new Exception("tableName error");
            }
            String baseSql = "select * from ";
            baseSql += tableName;
            baseSql += " where ";
            String conditionSql = "";
            if(condition!=null){
                for(Map.Entry<String,Object> mp:condition.entrySet()){
                    conditionSql += ""+mp.getKey()+"='"+mp.getValue()+"' and ";
                }
            }
            String sql = baseSql;
            if(!conditionSql.equals("")){
                String csql = conditionSql.substring(0,conditionSql.lastIndexOf("and"));
                sql += csql;
            }
            Statement sts = conn.createStatement();
            ResultSet res = sts.executeQuery(sql);
            // TODO: 16/11/28 生成一个对象模型
            List<Object> returnList = generateModels(tableName,res);
            return returnList;
        }
        return null;
    }


    private Object generateModel(String modelName,ResultSet res) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        while (res.next()){
            String packageOrm = configHelper.packageOrm();

            ClassLoader cls = Thread.currentThread().getContextClassLoader();
            Class newModel = cls.loadClass(packageOrm+"."+modelName);
            Object instance = newModel.newInstance();
            for (Field field:this.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object value = null;
                if (field.getType()==String.class){
                    value = (String)res.getString(field.getName());
                }else{
                    value = (int)res.getInt(field.getName());
                }
                field.set(instance,value);
            }
            return instance;
        }
        return null;
    }



    private List<Object> generateModels(String modelName,ResultSet res) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<Object> container = new ArrayList<Object>();
        String packageOrm = configHelper.packageOrm();
        ClassLoader cls = Thread.currentThread().getContextClassLoader();
        Class newModel = cls.loadClass(packageOrm+"."+modelName);
        while (res.next()){
            Object instance = newModel.newInstance();
            for (Field field:this.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object value = null;
                if (field.getType()==String.class){
                    value = (String)res.getString(field.getName());
                }else{
                    value = (int)res.getInt(field.getName());
                }
                field.set(instance,value);
            }
            container.add(instance);
        }
        return container;
    }


}
