package org.cherrygirl.pojo;

import java.util.List;

/**
 * @author nannanness
 */
public class WeatherResponse {
    private String time;
    private CityInfo cityInfo;
    private String date;
    private String message;
    private Integer status;
    private WeatherResponseData data;

    public static class CityInfo{
        private String city;
        private String cityKey;
        private String parent;
        private String updateTime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityKey() {
            return cityKey;
        }

        public void setCityKey(String cityKey) {
            this.cityKey = cityKey;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class WeatherResponseData{
        private String shidu;
        private Double pm25;
        private Double pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private List<WeatherForecastVO> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public Double getPm25() {
            return pm25;
        }

        public void setPm25(Double pm25) {
            this.pm25 = pm25;
        }

        public Double getPm10() {
            return pm10;
        }

        public void setPm10(Double pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public List<WeatherForecastVO> getForecast() {
            return forecast;
        }

        public void setForecast(List<WeatherForecastVO> forecast) {
            this.forecast = forecast;
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public WeatherResponseData getData() {
        return data;
    }

    public void setData(WeatherResponseData data) {
        this.data = data;
    }
}
