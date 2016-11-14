package io.github.zuston.framework.entity;

/**
 * Created by zuston on 16/11/14.
 */
public class requestEntity {
    public String requestMethod;
    public String requestUrl;

    public String getRequestUrl() {
        return requestUrl;
    }

    public requestEntity(String requestMethod, String requestUrl) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }


    @Override
    public int hashCode() {
        return requestMethod.toUpperCase().hashCode()^requestUrl.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj==this){
            return true;
        }
        if(obj.getClass()!=this.getClass()){
            return false;
        }

        requestEntity req = (requestEntity)obj;
        // TODO: 16-11-14 将==换成equals 重大bug
        return req.requestMethod.equals(requestMethod)&&req.requestUrl.equals(requestUrl);
    }
}
