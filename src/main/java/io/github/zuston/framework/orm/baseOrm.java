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
            System.out.println("init the connection");
            String dbUrl = String.format("jdbc:mysql://localhost:3306/%s?user=%s&password=%s&characterEncoding=utf8",dbName,dbUsername,dbPassword);
            Class.forName("com.mysql.jdbc.Driver") ;
            conn = (Connection) DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.out.println("connection error");
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            System.out.println("can not find the jdbc driver");
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

    // TODO: 16/11/30 update实现
    public baseOrm update(){
        String url = "update tablename set id = 10 where name=";
        if(conn==null){
            getConn();
        }
        String tableName = this.getClass().getSimpleName();
        for (Field field:this.getClass().getDeclaredFields()){

        }
        return this;
    }

    public ResultSet findBySql(String sql) throws SQLException {
        if(conn==null){
            getConn();
        }
        Statement sts = conn.createStatement();
        ResultSet res = sts.executeQuery(sql);
        return res;
    }



    public boolean delete(){
        return false;
    }

    public boolean deleteAll(){
        return false;
    }

    public boolean deleteOne(){
        return false;
    }


    public baseOrm where(HashMap<String,Object> hs){
        sqlCondition.put("condition",hs);
        return this;
    }

    public baseOrm orderby(String tag){
        sqlCondition.put("order",tag);
        return this;
    }

    public <T> T one() throws Exception {
        if(conn==null){
            getConn();
        }
        if(!sqlCondition.containsKey("tableName")){
            throw new Exception("tableName error");
        }
        String tableName = (String) sqlCondition.get("tableName");
        String baseSql = "select * from ";
        baseSql += tableName;

        String conditionSql = "";
        if(sqlCondition.containsKey("condition")){
            HashMap<String, Object> condition = (HashMap<String,Object>)sqlCondition.get("condition");
            conditionSql = " where ";
            if(condition!=null){
                for(Map.Entry<String,Object> mp:condition.entrySet()){
                    conditionSql += ""+mp.getKey()+"='"+mp.getValue()+"' and ";
                }
            }
        }
        String orderSql = "";
        if(sqlCondition.containsKey("order")){
            String tag = (String) sqlCondition.get("order");
            orderSql += " order by '" + tag +"'";
        }
        String limitSql = " limit 1";
        String sql = baseSql;
        if(!conditionSql.equals("")){
            String csql = conditionSql.substring(0,conditionSql.lastIndexOf("and"));
            sql += csql;
        }
        if(!orderSql.equals("")){
            sql += orderSql;
        }
        sql += limitSql;
        System.out.println(sql);
        Statement sts = conn.createStatement();
        ResultSet res = sts.executeQuery(sql);
        // TODO: 16/11/28 生成一个对象模型
        Object model = generateModel(tableName,res);
        flush();
        return (T)model;
    }

    public <T> List<T> all() throws Exception {
        if(conn==null){
            getConn();
        }

        if(!sqlCondition.containsKey("tableName")){
            throw new Exception("tableName error");
        }
        String tableName = (String) sqlCondition.get("tableName");
        String baseSql = "select * from ";
        baseSql += tableName;

        String conditionSql = "";
        if(sqlCondition.containsKey("condition")){
            HashMap<String, Object> condition = (HashMap<String,Object>)sqlCondition.get("condition");
            conditionSql = " where ";
            if(condition!=null){
                for(Map.Entry<String,Object> mp:condition.entrySet()){
                    conditionSql += ""+mp.getKey()+"='"+mp.getValue()+"' and ";
                }
            }
        }
        String sql = baseSql;
        if(!conditionSql.equals("")){
            String csql = conditionSql.substring(0,conditionSql.lastIndexOf("and"));
            sql += csql;
        }
        System.out.println(sql);
        Statement sts = conn.createStatement();
        ResultSet res = sts.executeQuery(sql);
        // TODO: 16/11/28 生成一个对象模型
        List<Object> models = generateModels(tableName,res);
        flush();
        return (List<T>) models;
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

    // every action must flush the condition hashmap,avoid influencing the next query action
    private boolean flush(){
        sqlCondition = new HashMap<String, Object>();
        return true;
    }
}
