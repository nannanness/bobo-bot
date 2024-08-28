package org.cherrygirl.pojo;

/**
 * @author nannanness
 */
public class WeatherVO {

    private Integer id;
    private Integer pid;
    private String cityCode;
    private String cityName;
    private String postCode;
    private String areaCode;
    private String ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "WeatherDO{" +
                "id=" + id +
                ", pid=" + pid +
                ", cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", postCode='" + postCode + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", ctime='" + ctime + '\'' +
                '}';
    }
}
