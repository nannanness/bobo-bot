package org.cherrygirl.pojo;

import java.util.List;

/**
 * @author nannanness
 */
public class RollToolsApiVO {

    private String api;

    private List<String> paramList;

    public RollToolsApiVO() {
    }

    public RollToolsApiVO(String api, List<String> paramList) {
        this.api = api;
        this.paramList = paramList;
    }

    public List<String> getParamList() {
        return paramList;
    }

    public void setParamList(List<String> paramList) {
        this.paramList = paramList;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    @Override
    public String toString() {
        return "RollToolsApiDO{" +
                "api='" + api + '\'' +
                ", paramList=" + paramList +
                '}';
    }
}
