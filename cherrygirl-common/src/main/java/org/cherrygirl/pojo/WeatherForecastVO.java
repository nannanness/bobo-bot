package org.cherrygirl.pojo;

/**
 * @author nannanness
 */
public class WeatherForecastVO {

    private String date;
    private String ymd;
    private String week;
    private String sunrise;
    private String high;
    private String low;
    private String sunset;
    private Double aqi;
    private String fx;
    private String fl;
    private String type;
    private String notice;

    @Override
    public String toString() {
        return ymd + " " + week + "：" + "\t\n" +
                "天气：'" + type + '\'' + "\t\n" +
                "日出时间：" + sunrise + '\'' + "\t\n" +
                "日落时间：" + sunset + '\'' + "\t\n" +
                "最高温度：" + high + '\'' + "\t\n" +
                "最低温度：" + low + '\'' + "\t\n" +
                "风向：" + fx + '\'' + "\t\n" +
                "风力：" + fl + '\'' + "\t\n" +
                "小提示：" + notice + '\'' + "\t\n";
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public Double getAqi() {
        return aqi;
    }

    public void setAqi(Double aqi) {
        this.aqi = aqi;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
