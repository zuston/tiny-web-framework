package app.orm;

import io.github.zuston.framework.orm.baseOrm;

/**
 * Created by zuston on 16-12-7.
 */
public class thing extends baseOrm {

    public int userId;
    public String content;
//    public String time;

    public thing(int userId, String content) {
        this.userId = userId;
        this.content = content;
//        this.time = time;
    }

    public thing() {
    }


    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

}
