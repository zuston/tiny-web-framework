package app.orm;

import io.github.zuston.framework.orm.baseOrm;

/**
 * Created by zuston on 16/11/30.
 */
public class user extends baseOrm {
    public int id;
    public String name;
    public String nick_name;
    public String pwd;
    public int grade;
    public int sex;
    public String teacher;
    public int background;

    public user(int id, String name, String nick_name, String pwd, int grade, int sex, String teacher,int background) {
        this.id = id;
        this.name = name;
        this.nick_name = nick_name;
        this.pwd = pwd;
        this.grade = grade;
        this.sex = sex;
        this.teacher = teacher;
        this.background = background;
    }

    public user() {
    }

    public int getId() {
        return id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String getPwd() {
        return pwd;
    }

    public int getGrade() {
        return grade;
    }

    public int getSex() {
        return sex;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getBackground() {
        return background;
    }

    public String getName(){
        return this.name;
    }

}
