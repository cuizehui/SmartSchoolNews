package com.example.cuizehui.smartschool.entity;

import java.util.List;

/**
 * Created by cuizehui on 2016/9/25at ${time}.
 */
public class Weather {

    /**
     * header :
     * url : http://kcgz.openspeech.cn/service/iss?wuuid=fe3d677f24e1145193ff4d126bb8827c&ver=2.0&method=webPage&uuid=e648bd73ce845de500992f144373b602query
     */

    private WebPageBean webPage;
    /**
     * slots : {"location":{"type":"LOC_POI","city":"CURRENT_CITY","poi":"CURRENT_POI"},"datetime":{"date":"2016-09-25","type":"DT_BASIC","dateOrig":"今天"}}
     */

    private SemanticBean semantic;
    /**
     * webPage : {"header":"","url":"http://kcgz.openspeech.cn/service/iss?wuuid=fe3d677f24e1145193ff4d126bb8827c&ver=2.0&method=webPage&uuid=e648bd73ce845de500992f144373b602query"}
     * semantic : {"slots":{"location":{"type":"LOC_POI","city":"CURRENT_CITY","poi":"CURRENT_POI"},"datetime":{"date":"2016-09-25","type":"DT_BASIC","dateOrig":"今天"}}}
     * rc : 0
     * operation : QUERY
     * service : weather
     * data : {"result":[{"airQuality":"良","sourceName":"中国天气网","date":"2016-09-25","lastUpdateTime":"2016-09-25 18:25:34","dateLong":1474732800,"pm25":"60","city":"南昌","humidity":"58%","windLevel":0,"weather":"晴","tempRange":"24℃","wind":"无持续风向微风"},{"dateLong":1474819200,"sourceName":"中国天气网","date":"2016-09-26","lastUpdateTime":"2016-09-25 18:25:34","city":"南昌","windLevel":0,"weather":"晴","tempRange":"24℃~34℃","wind":"无持续风向微风"},{"dateLong":1474905600,"sourceName":"中国天气网","date":"2016-09-27","lastUpdateTime":"2016-09-25 18:25:34","city":"南昌","windLevel":1,"weather":"阴转多云","tempRange":"25℃~33℃","wind":"北风3-4级"},{"dateLong":1474992000,"sourceName":"中国天气网","date":"2016-09-28","lastUpdateTime":"2016-09-25 18:25:34","city":"南昌","windLevel":1,"weather":"阵雨转小到中雨","tempRange":"23℃~29℃","wind":"北风3-4级"},{"dateLong":1475078400,"sourceName":"中国天气网","date":"2016-09-29","lastUpdateTime":"2016-09-25 18:25:34","city":"南昌","windLevel":1,"weather":"小雨转阵雨","tempRange":"22℃~25℃","wind":"北风3-4级"},{"dateLong":1475164800,"sourceName":"中国天气网","date":"2016-09-30","lastUpdateTime":"2016-09-25 18:25:34","city":"南昌","windLevel":0,"weather":"阵雨转阴","tempRange":"21℃~25℃","wind":"无持续风向微风"},{"dateLong":1475251200,"sourceName":"中国天气网","date":"2016-10-01","lastUpdateTime":"2016-09-25 18:25:34","city":"南昌","windLevel":0,"weather":"阴","tempRange":"20℃~23℃","wind":"无持续风向微风"}]}
     * text : 今天天气。
     */

    private int rc;
    private String operation;
    private String service;
    private DataBean data;
    private String text;

    public WebPageBean getWebPage() {
        return webPage;
    }

    public void setWebPage(WebPageBean webPage) {
        this.webPage = webPage;
    }

    public SemanticBean getSemantic() {
        return semantic;
    }

    public void setSemantic(SemanticBean semantic) {
        this.semantic = semantic;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class WebPageBean {
        private String header;
        private String url;

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class SemanticBean {
        /**
         * location : {"type":"LOC_POI","city":"CURRENT_CITY","poi":"CURRENT_POI"}
         * datetime : {"date":"2016-09-25","type":"DT_BASIC","dateOrig":"今天"}
         */

        private SlotsBean slots;

        public SlotsBean getSlots() {
            return slots;
        }

        public void setSlots(SlotsBean slots) {
            this.slots = slots;
        }

        public static class SlotsBean {
            /**
             * type : LOC_POI
             * city : CURRENT_CITY
             * poi : CURRENT_POI
             */

            private LocationBean location;
            /**
             * date : 2016-09-25
             * type : DT_BASIC
             * dateOrig : 今天
             */

            private DatetimeBean datetime;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public DatetimeBean getDatetime() {
                return datetime;
            }

            public void setDatetime(DatetimeBean datetime) {
                this.datetime = datetime;
            }

            public static class LocationBean {
                private String type;
                private String city;
                private String poi;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getPoi() {
                    return poi;
                }

                public void setPoi(String poi) {
                    this.poi = poi;
                }
            }

            public static class DatetimeBean {
                private String date;
                private String type;
                private String dateOrig;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getDateOrig() {
                    return dateOrig;
                }

                public void setDateOrig(String dateOrig) {
                    this.dateOrig = dateOrig;
                }
            }
        }
    }

    public static class DataBean {
        /**
         * airQuality : 良
         * sourceName : 中国天气网
         * date : 2016-09-25
         * lastUpdateTime : 2016-09-25 18:25:34
         * dateLong : 1474732800
         * pm25 : 60
         * city : 南昌
         * humidity : 58%
         * windLevel : 0
         * weather : 晴
         * tempRange : 24℃
         * wind : 无持续风向微风
         */

        private List<ResultBean> result;

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            private String airQuality;
            private String sourceName;
            private String date;
            private String lastUpdateTime;
            private int dateLong;
            private String pm25;
            private String city;
            private String humidity;
            private int windLevel;
            private String weather;
            private String tempRange;
            private String wind;

            public String getAirQuality() {
                return airQuality;
            }

            public void setAirQuality(String airQuality) {
                this.airQuality = airQuality;
            }

            public String getSourceName() {
                return sourceName;
            }

            public void setSourceName(String sourceName) {
                this.sourceName = sourceName;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(String lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }

            public int getDateLong() {
                return dateLong;
            }

            public void setDateLong(int dateLong) {
                this.dateLong = dateLong;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public int getWindLevel() {
                return windLevel;
            }

            public void setWindLevel(int windLevel) {
                this.windLevel = windLevel;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTempRange() {
                return tempRange;
            }

            public void setTempRange(String tempRange) {
                this.tempRange = tempRange;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }
        }
    }
}
