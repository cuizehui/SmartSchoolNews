package com.example.cuizehui.smartschool.entity;

/**
 * Created by cuizehui on 2016/9/25at ${time}.
 */
public class AskMap {
    /**
     * slots : {"location":{"type":"LOC_POI","poi":"上海","city":"上海市","cityAddr":"上海"},"category":"饭店"}
     */

    private SemanticBean semantic;
    /**
     * semantic : {"slots":{"location":{"type":"LOC_POI","poi":"上海","city":"上海市","cityAddr":"上海"},"category":"饭店"}}
     * rc : 0
     * operation : QUERY
     * service : restaurant
     * text : 上海附近有什么饭店?
     */

    private int rc;
    private String operation;
    private String service;
    private String text;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class SemanticBean {
        /**
         * location : {"type":"LOC_POI","poi":"上海","city":"上海市","cityAddr":"上海"}
         * category : 饭店
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
             * poi : 上海
             * city : 上海市
             * cityAddr : 上海
             */

            private LocationBean location;
            private String category;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public static class LocationBean {
                private String type;
                private String poi;
                private String city;
                private String cityAddr;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getPoi() {
                    return poi;
                }

                public void setPoi(String poi) {
                    this.poi = poi;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getCityAddr() {
                    return cityAddr;
                }

                public void setCityAddr(String cityAddr) {
                    this.cityAddr = cityAddr;
                }
            }
        }
    }
}
