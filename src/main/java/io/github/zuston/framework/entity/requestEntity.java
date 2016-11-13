package io.github.zuston.framework.entity;

/**
 * Created by zuston on 16/11/14.
 */
public class requestEntity {
    public String requestMethod;
    public String requestUrl;

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
}
