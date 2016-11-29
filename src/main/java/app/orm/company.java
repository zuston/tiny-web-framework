package app.orm;

/**
 * Created by zuston on 16-11-29.
 */

import io.github.zuston.framework.orm.baseOrm;

import java.util.HashMap;
import java.util.List;

/**
 *
 * 测试
 */
public class company extends baseOrm {
    public int id;
    public String cname;
    public String cnumber;
    public int ctag;
    public int patent_number;

    public company() {
    }

    public company(int id, String cname, String cnumber, int ctag, int patent_number) {
        this.id = id;
        this.cname = cname;
        this.cnumber = cnumber;
        this.ctag = ctag;
        this.patent_number = patent_number;
    }

    public company(String cname, String cnumber, int ctag, int patent_number) {
        this.cname = cname;
        this.cnumber = cnumber;
        this.ctag = ctag;
        this.patent_number = patent_number;
    }

    public static void main(String[] args) throws Exception {
        company one = new company("pp","ko9089348",1,344);
        one.save();
        company c = new company();
        company res = (company)c.find().orderby("id").one();
        company rt = (company)c.find().orderby("id").one();

        company a = new company();
        HashMap<String,Object> hs = new HashMap<String, Object>();
        hs.put("1","1");
        List<Object> r = a.find().where(hs).all();
        company re = (company)a.find().orderby("id").one();


    }
}
